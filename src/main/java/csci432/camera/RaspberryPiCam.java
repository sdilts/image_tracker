package csci432.camera;

import csci432.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Runtime.getRuntime;

public class RaspberryPiCam implements Camera{
    private Integer picNumber;
    private final String saveLocation;
    private Queue<String> unfilteredImages;

    public RaspberryPiCam(String saveLocation){
        this.saveLocation = saveLocation;
        this.picNumber = 0;
        this.unfilteredImages = new LinkedList<>();
    }

    @Override
    public void takePicture() {
        try {
            Process p = Runtime.getRuntime().exec("raspistill -o "+saveLocation + picNumber + ".jpg --nopreview --timeout 1");
            p.waitFor();
            this.unfilteredImages.add(saveLocation+picNumber+".jpg");
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
            p.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ix) {
            ix.printStackTrace();
        }
    }

    /**
     * Gets an unfiltered image to filter
     *
     * @return an unfiltered image
     */
    public BufferedImage getUnfilteredImage() {
        String location = String.format(saveLocation+"%3$d.jpg", picNumber);
        BufferedImage image = ImageUtil.loadImage(location);
        picNumber++;
        return image;
    }
}
