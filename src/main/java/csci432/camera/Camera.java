package csci432.camera;

public interface Camera {
    /**
     * Takes a picture
     */
    void takePicture();

    /**
     * Takes a picture given the interval
     *
     * @param pause Pause between each picture
     * @param duration The duration of the picture taking session
     */
    void takePictureOnInterval(Long pause, Long duration);
}
