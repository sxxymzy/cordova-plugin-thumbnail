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


public class ThumbCreator extends CordovaPlugin {

    public static final String TAG = "ThumbCreator";

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

    private static String thumbnail(String originImage, String thumbDir, int scaleWidth, int scaleHeight, int quality) throws IOException, FileNotFoundException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(originImage, options);
        Log.d("thumbnail origin", originImage);

        File file = new File(originImage);
        Bitmap thumbBmp = ThumbnailUtils.extractThumbnail(bitmap, scaleWidth, scaleHeight);

        OutputStream fOut = null;
        File folder = new File(thumbDir);
        Log.d("thumbnail thumbDir", thumbDir);
        if (!folder.exists()) {
            Log.d("thumb folder not exist", "");
            folder.mkdir();
        }
        File targetFile = new File(thumbDir + "_thumb_" + file.getName());
        Log.d("targetFile ", thumbDir + "_thumb_" + file.getName());
        Log.d("file exist: ", String.valueOf(targetFile.exists()));
        if (!targetFile.exists()) {
            Log.d("create new file", thumbDir + "_thumb_" + file.getName());
            targetFile.createNewFile();
            Log.d("create done", "thumb");
            fOut = new FileOutputStream(targetFile);
            thumbBmp.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
            fOut.flush();
            fOut.close();
            return "file://" + targetFile.getAbsolutePath();
        }
        if (targetFile.exists()) {
            Log.d("file exist: ", String.valueOf(targetFile.exists()));
            return "file://" + targetFile.getAbsolutePath();
        }
        return null;
    }

    private boolean loadThumbs(final JSONArray origins, final String thumbDir, final int scaleWidth, final int scaleHeight, final int quality, final CallbackContext callback) throws JSONException {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    JSONArray arr = new JSONArray();
                    for (int i = 0; i < origins.length(); i++) {
                        String origin = origins.getString(i);
                        if (origin.startsWith("file://")) {
                            origin = origin.substring(6);
                        }
                        Log.d("thumbnailing", origin);
                        arr.put(ThumbCreator.thumbnail(origin, thumbDir, scaleWidth, scaleHeight, quality));
                    }
                    Log.i("loadThumb done", arr.toString());
                    callback.success(arr);
                } catch (Exception e) {
                    Log.e("thumbnail error", e.toString());
                    callback.error("An errror occured: " + e.toString());
                }
            }
        });
        return true;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
        String thumbDir = args.getString(1);
        int scaleWidth = args.getInt(2);
        int scaleHeight = args.getInt(3);
        int quality = args.getInt(4);
        if (thumbDir.startsWith("file://")) {
            thumbDir = thumbDir.substring(6);
        }
        Log.d("thumblog", thumbDir);
        if (action.equals("loadThumbs")) {
            JSONArray origins = (JSONArray) args.get(0);
            Log.d("thumblog", origins.toString());
            return this.loadThumbs(origins, thumbDir, scaleWidth, scaleHeight, quality, callback);
        }
        return false;
    }
}

