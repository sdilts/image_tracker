package csci432.processor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SigmaDeltaFilter {

    public int numFiltered;
    public BufferedImage background;
    protected int[][] backCount, curPix, curCount;
    public int colorThresh = 10;
    public int initBackground = 5;     //amount of pictures to be taken to initialize background
    public int backgroundThresh = 5;

    /**
     * Initializes SigmaDelta Filter
     **/
    public SigmaDeltaFilter() {
        this.numFiltered = 0;
    }

    /**
     * Decides either to filter image, or just set the background using
     * a threshold for images processed so far. On the first call,
     * initializes background, curCount, curPix, and backCount
     *
     * @param image a BufferedImage to be filtered
     * @return the newly filtered image if numFiltered is more than
     * 20 or just the original image if numFiltered is 20 or less
     **/
    public BufferedImage filter(BufferedImage image) {
        if (numFiltered > initBackground) {
            refreshBackground(image);
            image = filterImageSubtract(image);
        } else if (numFiltered > 0) {
            refreshBackground(image);
        } else {
            background = image;
            curCount = new int[image.getWidth()][image.getHeight()];
            curPix = new int[image.getWidth()][image.getHeight()];
            backCount = new int[image.getWidth()][image.getHeight()];
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    curPix[x][y] = image.getRGB(x, y);
                }
            }
        }
        numFiltered++;
        return image;
    }

    /**
     * For each pixel in the inputted image,
     * if current pixel (in inputted image) matches corresponding
     * pixel in background, increment backCount.
     * else, if curPix value (temp pixel value) in curColor matches
     * current pixel, increment curCount and if curCount is higher
     * than backCount, update background. else, update curPix
     *
     * @param image a BufferedImage to be filtered
     **/
    public void refreshBackground(BufferedImage image) {
        Color backColor, curColor, imageColor = null;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                backColor = new Color(background.getRGB(x, y));
                imageColor = new Color(image.getRGB(x, y));
                if (colorMatch(backColor, imageColor)) {
                    backCount[x][y]++;
                } else {
                    curColor = new Color(curPix[x][y]);
                    if (colorMatch(curColor, imageColor)) {
                        curCount[x][y]++;
                        if (curCount[x][y] > backgroundThresh) {
                            background.setRGB(x, y, rgb);
                        }
                    } else {
                        curPix[x][y] = rgb;
                        curCount[x][y] = 0;
                    }
                }
            }
        }
    }

    /**
     * Separates each color value into red, green, and blue
     * values. If each red, green, and blue values are within
     * a threshold of each other, return true. Otherwise return
     * false.
     *
     * @param a first color value to be compared
     * @param b second color value to be compared
     * @return true or false for whether the colors are close
     **/
    public boolean colorMatch(Color a, Color b) {
        int aRed = a.getRed();
        int aGreen = a.getGreen();
        int aBlue = a.getBlue();
        int bRed = b.getRed();
        int bGreen = b.getGreen();
        int bBlue = b.getBlue();

        if (Math.abs(aRed - bRed) < colorThresh && Math.abs(aGreen - bGreen)
                < colorThresh && Math.abs(aBlue - bBlue) < colorThresh) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * for each pixel, calls subColor to subtract color
     * value
     *
     * @param image a BufferedImage to be filtered
     * @return the newly filtered image
     **/
    public BufferedImage filterImageSubtract(BufferedImage image) {
        Color bColor = null;
        Color iColor = null;
        int rgb = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                bColor = new Color(background.getRGB(j, i));
                iColor = new Color(image.getRGB(j, i));
                rgb = subColor(bColor, iColor);
                image.setRGB(j, i, rgb);
            }
        }
        return image;
    }

    public int subColor(Color a, Color b) {
        int aRed = a.getRed();
        int aGreen = a.getGreen();
        int aBlue = a.getBlue();
        int bRed = b.getRed();
        int bGreen = b.getGreen();
        int bBlue = b.getBlue();

        aRed = Math.abs(aRed - bRed);
        aGreen = Math.abs(aGreen - bGreen);
        aBlue = Math.abs(aBlue - bBlue);
        int rgb = (new Color(aRed, aGreen, aBlue)).getRGB();
        return rgb;
    }
}
