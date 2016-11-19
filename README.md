# cordova-plugin-gallery-thumbnail

cordova plugin for compressing images, use case example: gallery.

The `loadThumbs` function will create a background thread to load thumbs from a array of uri, so it will not block ui.

After saves all thumbs to the folder specified in `thumbDir`, successCallback will be called.

# Dependencies

  # Currently you have to request read and write permissions by yourself, but the code will be add to plugin soon.
  # You can use the following plugin to request any permissions as you wish.
  cordova plugin add cordova-plugin-android-permissions


# Example

    var scaleWidth = 100;
    var scaleHeight = 100;
    var quality = 50;
    /*
    * You can use another plugin to retrieve all origin images path:
    *     cordova plugin add cordova-plugin-media-retrieve
    * Also you can use any path as you wish, but be sure the image exists.
    **/
    ThumbCreator.loadThumbs(
                              ['/path/to/image1','/path/to/image2'], // paths of origin images
                              cordova.file.externalCacheDirectory,  // a directory which you want to save generated thumbnails
                              scaleWidth,  // thumbnail's scaled width, whiche should be equal to your display element's width
                              scaleHeight,  // thumbnail's scaled height
                              quality,  // an integer from 0 to 100
                              function (thumbnailImageAbsolutePathArray) {
                                      // success
                                      console.log(thumbailImageAbsolutePathArray);
                                  }, function (err) {
                                      // failed
                                      console.log(err);
                                  });
