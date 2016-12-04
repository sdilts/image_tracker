package csci432.camera;

import java.io.IOException;

public class RaspberryPiCam implements Camera{
    private Integer picNumber;
    private final String saveLocation;

    public RaspberryPiCam(String saveLocation){
        this.saveLocation = saveLocation;
        this.picNumber = 0;
    }

    @Override
    public void takePicture() {
        try {
            Runtime.getRuntime().exec("raspistill -o "+saveLocation + picNumber + ".jpg");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        picNumber++;
    }

    @Override
    public void takePictureOnInterval(Long milliseconds) {
        try {
            Runtime.getRuntime().exec("raspistill -w 500 -h 500 -tl "+ milliseconds+ " -o " +saveLocation+"original_%03.jpg");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
