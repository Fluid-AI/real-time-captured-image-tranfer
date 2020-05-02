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

    //socket connections to server
    var socket = new SockJS('/web-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/sender/real-time-image', function (realTimeRequest) {
            stopStreamedVideo();
            startCameraAndStreamVideo(realTimeRequest.body);
            logUserRequest(realTimeRequest.body);
        });
    });

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
                takeImageAndSendToReceiver(realTimeRequest);
                stopStreamedVideo();
            });
        }, function (error) {
            alert('Couldnt stream camera video');
        });
        return;
    }

    document.getElementById('close-video').onclick = () => {
        stopStreamedVideo();
        document.getElementById('close-video').style.display = 'none';
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


    //converts canvas to image. and sends to server.
    function takeImageAndSendToReceiver(realTimeRequest) {
        var canvas = document.getElementById("canvas");
        var img = canvas.toDataURL("image/png");
        const base64Uri = img.replace('data:', '').replace(/^.+,/, '');

        //send to connected sockets
        stompClient.send('/app/receiver/real-time-image', {}, JSON.stringify({
            userName: realTimeRequest.userName,
            sentOn: new Date(),
            imgDataInBase64: base64Uri
        }));
    }
};

function logUserRequest(user) {
    user = JSON.parse(user);

    const logTable = document.getElementById('users').getElementsByTagName('tbody')[0];

    const row = logTable.insertRow();

    let i = 0;
    for (const cellValue of [logTable.rows.length, user.userName, new Date(user.requestedAt).toLocaleString()]) {
        const cell = row.insertCell(i++);
        const cellText = document.createTextNode(cellValue);
        cell.appendChild(cellText);
    }

}