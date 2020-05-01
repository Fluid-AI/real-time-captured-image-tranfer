function startCapturingImage() {
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
    }, async function (stream) {
        video.srcObject = stream;
        video.play();
    }, function (error) {
        alert('Couldnt stream camera video');
    });

    //send image in every INTERVAL_OF_CAPTURING_IMAGE_IN_MILI seconds interval 
    // (from client-sender.hrml by server)
    // setInterval(takeImageFromCamera, 10 * 1000);

    var socket = new SockJS('/web-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/sender/real-time-image', function (realTimeRequest) {
            stopStreamedVideo();
            startCameraAndStreamVideo(realTimeRequest.body);
            logUserRequest(realTimeRequest.body);
        });
    });


    //manually clicking the take image
    // document.getElementById('capture').addEventListener('click', function () {
    //     takeImageFromCamera();
    // });

    //starts the camera takes the image and closes the camera.
    async function startCameraAndStreamVideo(realTimeRequest) {
        navigator.getMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia || navigator.msGetUserMedia;

        navigator.getMedia({
            video: true,
            audio: false
        }, async function (stream) {
            video.srcObject = stream;

            //play the video. then draw it on canvas. after then image will be sent to server.
            //close the camera.
            video.play().then(() => {
                context.drawImage(video, 0, 0, 600, 500);
                takeImage(realTimeRequest);
                stopStreamedVideo();
            });
        }, function (error) {
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
    function takeImage(realTimeRequest) {
        var canvas = document.getElementById("canvas");
        var img = canvas.toDataURL("image/png");
        const base64Uri = img.replace('data:', '').replace(/^.+,/, '');
        
        stompClient.send('/app/receiver/real-time-image', {}, JSON.stringify({
            userName: realTimeRequest.userName,
            sentOn: new Date(),
            imgDataInBase64: base64Uri
        }));
    }
};

function logUserRequest(user){
    // const logTable = document.getElementById('users').getElementsByTagName('tbody')[0];

    // const row = logTable.insertRow();

    // for(const cell in [''])

}