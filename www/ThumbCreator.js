var exec = require('cordova/exec');

function ThumbCreator() {
    console.log("ThumbCreator.js: is created.");
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

