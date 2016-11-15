var exec = require('cordova/exec');

function ThumbCreator() {
    console.log("ThumbCreator.js: is created.");
}

ThumbCreator.prototype.createThumb = function(originImage, thumbDir,scaleWidth, scaleHeight, quality, successCallback, errorCallback) {
    exec(function(result){
        console.log("sucess from native", result);
            successCallback(result);
        },
        function (result) {
            errorCallback(result);
        },
        "ThumbCreator",
        "createThumb",
        [originImage, thumbDir, scaleWidth, scaleHeight, quality]
    );
}
ThumbCreator.prototype.loadThumbs = function(origins, thumbDir,scaleWidth, scaleHeight, quality, successCallback, errorCallback){
    exec(function(result){
        successCallback(result);
    }, function(err){
        errorCallback(err);
    },
    "ThumbCreator",
    "loadThumbs",
    [origins, thumbDir, scaleWidth, scaleHeight, quality]);
}

var thumb = new ThumbCreator();
module.exports = thumb;

