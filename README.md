# cordova-plugin-thumbnail

cordova plugin for compress images, can be used for multi images load, for example: gallery.

The `loadThumbs` function will throw a background thread to load thumbs from a array of uri, so it will not block ui.

After saves all thumbs to the folder specified in `thumbDir`, successCallback will be called.

# Example

    var scaleWidth = 100;
    var scaleHeight = 100;
    var quality = 0.5;
    ThumbCreator.loadThumbs(['/path/to/image1','/path/to/image2'], cordova.file.externalCacheDirectory, scaleWidth, scaleHeight, quality, function (rsp) {
        // success
    }, function (err) {
        // failed
        console.log(err);
    });
