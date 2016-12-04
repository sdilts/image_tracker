package csci432.camera;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                takePicture();
            }
        }, 0, milliseconds);
    }
}
