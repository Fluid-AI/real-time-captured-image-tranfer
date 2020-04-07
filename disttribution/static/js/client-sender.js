const INTERVAL_OF_CAPTURING_IMAGE_IN_MILI = 30_000;

window.onload = () => {
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
    
    //send image in every INTERVAL_OF_CAPTURING_IMAGE_IN_MILI seconds interval
    setInterval(() => {
        context.drawImage(video, 0, 0, 600, 500);
        takeImage();
    }, INTERVAL_OF_CAPTURING_IMAGE_IN_MILI);

    document.getElementById('capture').addEventListener('click', function () {
        context.drawImage(video, 0, 0, 600, 500);
        takeImage();
    });

    function takeImage() {
        var canvas = document.getElementById("canvas");
        var img = canvas.toDataURL("image/png");
        // console.log(i++);
        // document.write('<img src="' + img + '"/>');
        uploadImageToServer(img);
    }
};

function uploadImageToServer(imageFile) {
    const formData = new FormData();
    formData.append("file", convertDataUriToBlob(imageFile));
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/upload");

    // xhr.onload = function () {
    //     // console.log(xhr.responseText);
    // }

    xhr.send(formData);
}
function convertDataUriToBlob(dataUri) {
    const byteString = atob(dataUri.split(',')[1]);
    const mimeString = dataUri.split(',')[0].split(':')[1].split(';')[0]
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([ab], { type: mimeString });
    return blob;

}