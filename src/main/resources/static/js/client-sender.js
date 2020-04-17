function senderFunction(captureImageInterval) {
    var video = document.getElementById('video'),
        canvas = document.getElementById('canvas'),
        context = canvas.getContext('2d'),
        vendorURL = window.URL || window.webkitURL;

    // On loading page. It will show you the camera streams.
    // user can set up the camera in the mean time.
    navigator.getMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia || navigator.msGetUserMedia;

    navigator.getMedia({
        video: true,
        audio: false
    }, async function(stream) {
        video.srcObject = stream;
        video.play();
    }, function(error) {
        alert('Couldnt stream camera video');
    });

    //send image in every INTERVAL_OF_CAPTURING_IMAGE_IN_MILI seconds interval 
    // (from client-sender.hrml by server)
    setInterval(takeImageFromCamera, captureImageInterval * 1000);

    //manually clicking the take image
    document.getElementById('capture').addEventListener('click', function() {
        takeImageFromCamera();
    });

    //starts the camera takes the image and closes the camera.
    async function startCameraAndStreamVideo() {
        navigator.getMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia || navigator.msGetUserMedia;

        navigator.getMedia({
            video: true,
            audio: false
        }, async function(stream) {
            video.srcObject = stream;

            //play the video. then draw it on canvas. after then image will be sent to server.
            //close the camera.
            video.play().then(() => {
                context.drawImage(video, 0, 0, 600, 500);
                takeImage();
                stopStreamedVideo();
            });
        }, function(error) {
            alert('Couldnt stream camera video');
        });
        return;
    }

    //closes the camera.
    function stopStreamedVideo() {
        if (video.srcObject == null) {
            return;
        }
        const stream = video.srcObject;
        const tracks = stream.getTracks();

        tracks.forEach((track) => track.stop());

        video.srcObject = null;
        return;
    }

    //will be called by interval method.
    function takeImageFromCamera() {
        //remember to stop already running camera.
        stopStreamedVideo();
        startCameraAndStreamVideo();
    }

    //converts canvas to image. and sends to server.
    function takeImage() {
        var canvas = document.getElementById("canvas");
        var img = canvas.toDataURL("image/png");
        uploadImageToServer(img);
    }
};

//upload the given image to server.
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

// convert image uril to blob for sening the data.
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