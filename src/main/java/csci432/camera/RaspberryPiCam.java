package csci432.camera;

import csci432.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.SynchronousQueue;

public class RaspberryPiCam implements Camera{
    private Integer picNumber;
    private final String saveLocation;
    private Queue<String> unfilteredImages;

    public RaspberryPiCam(String saveLocation){
        this.saveLocation = saveLocation;
        this.picNumber = 0;
        this.unfilteredImages = new SynchronousQueue<>();
    }

    @Override
    public void takePicture() {
        try {
            Runtime.getRuntime().exec("raspistill -o -n "+saveLocation + picNumber + ".jpg");
            this.unfilteredImages.add(saveLocation+picNumber+".jpg");
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

    /**
     * Gets an unfiltered image to filter
     *
     * @return an unfiltered image
     */
    public BufferedImage getUnfilteredImage() {
        BufferedImage image = null;
        if (!unfilteredImages.isEmpty()) {
            image = ImageUtil.loadImage(unfilteredImages.poll());
        }
        return image;
    }
}
