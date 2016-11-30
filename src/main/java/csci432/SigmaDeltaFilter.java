package csci432;

import java.awt.image.BufferedImage;

public class SigmaDeltaFilter {

    private int numFiltered;
    private BufferedImage background;

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
        if (numFiltered <= 20) {
            setBackground(image);
        } else {
            image = filterImage(image);
        }

        return image;
    }

    /**
     * Uses image to continue the averaging of pixels in background
     *
     * @param image a BufferedImage to be filtered
     **/
    public void refreshBackground(BufferedImage image) {
        //pixel averaging loop
    }

    /**
     * Starts to average pixels in background. Threshold for
     * this can be changed. If this is the first image,
     * background is initialized as that image.
     *
     * @param image a BufferedImage to be filtered
     **/
    public void setBackground(BufferedImage image) {
        if (numFiltered == 0) {
            background = image;
        } else {
            refreshBackground(image);
        }
    }

    /**
     * Calls refreshBackground() then removes background from current image
     * and returns new backgroundless image
     *
     * @param image a BufferedImage to be filtered
     *
     * @return the newly filtered image
     **/
    public BufferedImage filterImage(BufferedImage image) {
        refreshBackground(image);
        //pixel removal loop
        return image;
    }
}
