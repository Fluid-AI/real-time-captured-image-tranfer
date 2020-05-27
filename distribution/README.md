# Real Time Captured Image Transfer

Note: You will need Lombok installed on your IDE to be able to run in it. 

## How to run?

You can run directly via an IDE or can use distribution folder. Download it and run the following command.
```dtd
java -jar real-time-captured-image-transfer.jar
```

To change the port, you can do so in ```application.yaml``` file.

## Credentials

Go to ```application.yaml```  and in ```auth``` we have to sub values 
or role, ```receiver``` and ```sender```.
write ```user:password``` in this sub values and role is either sender or receiver, depending on parent value.

for example, lets say, user1-rec for the receiver with password 12345 and user1-send with password 123456 so it will be written as follows:
```dtd yaml
auth:
  #Note: don't use space in either user or password
  receiver:
    user1-rec: 12345
    user2-rec: 123457
  sender:
    user1-send: 123455
```
You don't have to write -rec, -send of course.

## How to use?

You have two options, **Receiver** and **Sender**. After clicking one of these, you have to login.
The authentication process is in-memory, so it is hardcoded in the ```Spring Security``` itself.

### Sender

Login via credentials in ```auth.sender``. Click the take photo button. 
It connects you to the pool and starts streaming the camera, which you can close after setting up the position.
The camera closes itself after a receiver requests an image. 
A request will log in the log table at the bottom of the screen,and,
it sends out the current image, and shows the last image sent as well.

### Receiver

After logging in  credentials in ```auth.receiver``. Click on the receive button which will send a request to the sender and in response,
the latest image will be populated on the screen of the receiver sent by the sender.

 


