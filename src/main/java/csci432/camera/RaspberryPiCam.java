package csci432.camera;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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
    public void takePictureOnInterval(Long pause, Long duration) {
        try {
            getRuntime().exec("raspistill -w 500 -h 500 -tl " + pause + " -t " + duration + " -o " + saveLocation + "original_%03d.jpg --nopreview");
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
        String location = String.format(saveLocation+"original_%1$03d.jpg", picNumber);
        BufferedImage image = null;
        try {
            File file = new File(location);
            for (int i = picNumber+1; i < picNumber+5 && !file.exists(); i++) {
                location = String.format(saveLocation+"original_%1$03d.jpg", i);
                file = new File(location);
                if (file.exists()) {
                    picNumber = i;
                }
            }
            image = ImageIO.read(new File(location));
            picNumber++;
        } catch (IOException e) {
            System.out.println(location+ " and 5 following images not taking yet, waiting...");
        } finally {
            return image;
        }
    }
}
