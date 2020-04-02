const INTERVAL_OF_CAPTURING_IMAGE_IN_MILI = 10_000;

window.onload = ()=> {
    var video = document.getElementById('video'),
        canvas = document.getElementById('canvas'),
        context = canvas.getContext('2d'),
        vendorURL = window.URL || window.webkitURL;

    navigator.getMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
        || navigator.mozGetUserMedia || navigator.msGetUserMedia;

    navigator.getMedia({
        video: true,
        audio: false
    }, function (stream) {
        video.srcObject = stream;
        video.play();

    }, function (error) {
        alert('Couldnt stream camera video');
    });
    // setInterval(() => {
        document.getElementById('capture').addEventListener('click', function () {
            context.drawImage(video, 0, 0, 400, 300);
            takeImage();
        });
    // }, INTERVAL_OF_CAPTURING_IMAGE_IN_MILI);

    function takeImage() {
        var canvas = document.getElementById("canvas");
        var img = canvas.toDataURL("image/png");
        // console.log(i++);
        // document.write('<img src="' + img + '"/>');
        uploadImageToServer(img);
    }
};

var i = 0;

function uploadImageToServer(file) {
    var formData = new FormData();
    formData.append("file", dataURItoBlob(file));
  var xhr = new XMLHttpRequest();
    xhr.open("POST", "/upload");

    xhr.onload = function () {
        console.log(xhr.responseText);
//        var response = JSON.parse(xhr.responseText);
//        if (xhr.status == 200) {
//            singleFileUploadError.style.display = "none";
//            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
//            singleFileUploadSuccess.style.display = "block";
//        } else {
//            singleFileUploadSuccess.style.display = "none";
//            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
//        }
    }

    xhr.send(formData);
}
function dataURItoBlob(dataURI) {
    var byteString = atob(dataURI.split(',')[1]);
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    var blob = new Blob([ab], {type: mimeString});
    return blob;

  }