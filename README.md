# cordova-plugin-gallery-thumbnail

cordova plugin for compressing images, use case example: gallery.

The `loadThumbs` function will create a background thread to load thumbs from a array of uri, so it will not block ui.

After saves all thumbs to the folder specified in `thumbDir`, successCallback will be called.

# Dependencies

  cordova plugin add cordova-plugin-android-permissions
  cordova plugin add cordova-plugin-media-retrieve
  cordova plugin add cordova-plugin-file

# Example

    var scaleWidth = 100;
    var scaleHeight = 100;
    var quality = 50;
    ThumbCreator.loadThumbs(['/path/to/image1','/path/to/image2'], cordova.file.externalCacheDirectory, scaleWidth, scaleHeight, quality, function (rsp) {
        // success
    }, function (err) {
        // failed
        console.log(err);
    });
