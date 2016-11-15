import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import android.util.Log;
import android.provider.Settings;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.Exception;

import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.*;

import com.google.gson.Gson;

public class ThumbCreator extends CordovaPlugin {

    public static final String TAG = "ThumbCreator";
    public static int width = 100;
    public static int height = 100;

    /**
     * Constructor.
     */
    public ThumbCreator() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    private String thumbnail(String originImage, String toPath, int scaleWidth, int scaleHeight, float quality) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        File file = new File(fromPath);

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Bitmap thumb = ThumbnailUtils.extractThumbnail(bitmap, ThumbCreator.width, ThumbCreator.height);

        OutputStream fOut = null;
        File folder = new File(toPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File targetFile = new File(toPath + "_thumb_" + file.getName());
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        fOut = new FileOutputStream(targetFile);
        thumb.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return targetFile.getAbsolutePath();
    }

    private boolean createThumb(String fromPath, String toPath, int scaleWidth, int scaleHeight, float quality, CallbackContext callback) throws JSONException {

        if (fromPath.startsWith("file://")) {
            fromPath = fromPath.substring(6);
        }
        if (toPath.startsWith("file://")) {
            toPath = toPath.substring(6);
        }
        System.out.println(fromPath);
        System.out.println(toPath);

        try {
            String absolutePath = this.thumbnail(fromPath, toPath, scaleWidth, scaleHeight, quality);
            System.out.println(absolutePath);
            callback.success(absolutePath);
        } catch (Exception e) {
            callback.error("An errror occured: " + e.toString());
            return false;
        }
        callback.success(targetFile.getAbsolutePath());
        return true;
    }

    private boolean loadThumbs(JSONArray origins, String thumbDir, String thumbDir, int scaleWidth, int scaleHeight, float quality, CallbackContext callback) throws JSONException {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {

                    JSONArray arr = new JSONArray();
                    for (String origin : origins) {
                        arr.put(ThumbCreator.this.thumbnail(origin, thumbDir, scaleWidth, scaleHeight, quality));
                    }
                    callback.success(arr);
                } catch (Exception e) {
                    callback.error("An errror occured: " + e.toString());
                }
            }
        })
        return true;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
        if (action.equals("createThumb")) {
            String fromPath = args.getString(0);
            String toPath = args.getString(1);
            int scaleWidth = args.getInt(2);
            int scaleHeight = args.getInt(3);
            float quality = args.getFloat(4);
            return this.createThumb(fromPath, toPath, scaleWidth, scaleHeight, quality, callback);
        } else if (action.equals("loadThumb")) {
            JSONArray origins = args.getJSONArray(0);
            String thumbDir = args.getString(1);
            int scaleWidth = args.getInt(2);
            int scaleHeight = args.getInt(3);
            float quality = args.getFloat(4);
            return this.loadThumbs(origins, thumbDir, scaleWidth, scaleHeight, quality, callback);
        }
        return false;
    }
}

