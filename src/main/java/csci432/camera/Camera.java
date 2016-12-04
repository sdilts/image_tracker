package csci432.camera;

public interface Camera {
    /**
     * Takes a picture
     */
    void takePicture();

    /**
     * Takes a picture given the interval
     *
     * @param milliseconds Pause between each picture
     */
    void takePictureOnInterval(Long milliseconds);
}
