# cordova-plugin-thumbnail

cordova plugin for generate thumbnail

This plugin defines a global ThumbCreator object.

The createThumb function creates a thumb from the uri in fromUri (ex: '/DCIM/Camera/2015011102020.jpg'), and saves the thumb to the folder specified in folderToSaveThumb (ex: '/YourFolder/thumbs').

The return String is sent to the successCallback:
  - absolutePath
