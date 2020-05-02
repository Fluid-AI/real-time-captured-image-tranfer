# Real Time Captured Image Transfer

Note: You will need Lombok installed on your IDE to be able to run in it. 

## How to run?
You can run directly via an IDE or can use distribution folder. Download it and run the following command.
```dtd
java -jar real-time-captured-image-transfer.jar
```

To change the port, you can do so in ```application.yaml``` file.

## How to use?
You have two options, **Receiver** and **Sender**. After clicking one of these, you have to login.
The authentication process is in-memory, so it is hardcoded in the ```Spring Security``` itself.

### Sender
Login via ```admin``` and put password as ```admin```. Click the take photo button. 
It connects you to the pool and starts streaming the camera, which you can close after setting up the position.
The camera closes itself after a receiver requests an image. 
A request will log in the log table at the bottom of the screen,and,
it sends out the current image, and shows the last image sent as well.

### Receiver
After logging in  via ```user``` and ```password``` (yes the authentication credentials),
click on the receive button which will send a request to the sender and in response,
the latest image will be populated on the screen of the receiver sent by the sender.

 


