package csci432;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class SigmaDeltaFilter {

    public int numFiltered;
    public BufferedImage background;
    public int bBuffer = 1;            //buffer for averaging the background
    public int initBackground = 5;     //amount of pictures to be taken to initialize background

    /**
     * Initializes SigmaDelta Filter
     **/
    public SigmaDeltaFilter() {
        this.numFiltered = 0;
    }

    /**
     * Decides either to filter image, or just set the background using
     * a threshold for images processed so far.
     *
     * @param image a BufferedImage to be filtered
     *
     * @return the newly filtered image if numFiltered is more than
     * 20 or just the original image if numFiltered is 20 or less
     **/
    public BufferedImage filter(BufferedImage image) {
        if (numFiltered > initBackground) {
            refreshBackground(image);
            image = filterImageSubtract(image);
        } else if(numFiltered > 0) {
            refreshBackground(image);
        } else { background = image; }
        numFiltered++;
        return image;
    }

    /**
     * Uses a for loop to average the pixels in the image.
     * For each pixel, add the RGB values at (j, i) in eahc image
     * together and devide by two to get average and then assign
     * average to the background pixel at (j, i). May need to buffer
     * this averaging doesn't throw the background off.
     *
     * @param image a BufferedImage to be filtered
     **/
    public void refreshBackground(BufferedImage image) {
        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                int average = (bBuffer * background.getRGB(j, i))
                        + image.getRGB(j, i);
                average = average / (bBuffer + 1);
                background.setRGB(j, i, average);
            }
        }
    }

    /**
     * Calls refreshBackground() then for each pixel in image,
     * subtracts the corresponding RGB value in background from
     * the pixel in image. If the new value is negative, it is set
     * to positive before being assigned to the pixel. Since both
     * filter methods are called, refreshBackground() is commented
     * out so the background isn't averaged twice. We will decide
     * on one later
     *
     * @param image a BufferedImage to be filtered
     *
     * @return the newly filtered image
     **/
    public BufferedImage filterImageSubtract(BufferedImage image) {
        //refreshBackground(image);
        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                int newrgb = image.getRGB(j, i)
                        - background.getRGB(j, i);
                if(newrgb < 0) {
                    newrgb = -newrgb;
                }
                image.setRGB(j, i, newrgb);
            }
        }
        return image;
    }
}
