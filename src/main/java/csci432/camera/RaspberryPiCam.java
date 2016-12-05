package csci432.camera;

import csci432.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Runtime.getRuntime;

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
            Process p = Runtime.getRuntime().exec("raspistill -o "+saveLocation + picNumber + ".jpg --nopreview --timeout 1");
            p.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ix) {
            ix.printStackTrace();
        }
        picNumber++;
    }

    @Override
    public void takePictureOnInterval(Long milliseconds) {
        try {
            Process p = getRuntime().exec("raspistill -w 500 -h 500 -tl "+ milliseconds+ " -o " +saveLocation+"original_%03d.jpg --nopreview");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets an unfiltered image to filter
     *
     * @return an unfiltered image
     */
    public BufferedImage getUnfilteredImage() {
        String location = String.format(saveLocation+"original_%1$3d.jpg", picNumber);
        BufferedImage image = ImageUtil.loadImage(location);
        picNumber++;
        return image;
    }
}
