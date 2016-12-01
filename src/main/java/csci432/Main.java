package csci432;

import csci432.camera.Camera;
import csci432.camera.RaspberryPiCam;

public class Main {
    /**
     * Returns a string that says Hello World!
     *
     * @return A string that says Hello Worl!
     */
    public static String helloWorld() {
        return "Hello World!";
    }

    public static void main(String [] args) {
        Camera camera = new RaspberryPiCam(".");
        camera.takePicture();
    }
}