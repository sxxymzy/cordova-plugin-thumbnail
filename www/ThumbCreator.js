var exec = require('cordova/exec');

function ThumbCreator() {
    console.log("ThumbCreator.js: is created.");
}

ThumbCreator.prototype.createThumb = function(fromPath, toPath, successCallback, errorCallback) {   
    var args = [fromPath, toPath];
    exec(function(result){
        console.log("sucess from native", result);
            successCallback(result);
        },
        function (result) {
            errorCallback(result);
        },
        "ThumbCreator",
        "createThumb",
        args
    );
}
ThumbCreator.prototype.loadThumbs = function(origins, thumbDir, successCallback, errorCallback){
    exec(function(result){
        successCallback(result);
    }, function(err){
        errorCallback(err);
    },
    "ThumbCreator",
    "loadThumbs",
    [origins, thumbDir])
}

var thumb = new ThumbCreator();
module.exports = thumb;

