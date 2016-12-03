package csci432;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;


/**
 * Provides a wrapper for loading and saving BufferedImages. Instances 
 * of this class allow you to save certain settings for easier management
 **/
public class ImageUtil {

    private String saveDir;
    private String fileExtension;
    
    public class ImageUtil(String saveDir, String fileExtension) {
	this.saveDir = saveDir;
	this.fileExtension = fileExtension;
    }

    /** 
     * Loads the given file as a BufferedImage. The directory used will be the one specified by this
     * object's saveDir variable
     *
     * @param filename the relative filename to this object's save directory
     **/
    public BufferedImage getImage(String filename) {
	return loadImage(saveDir + fileName);
    }
    /**
     * Loads the given file as a BufferedImage. The directory used will be the one specified by this
     * object's saveDir variable
     *
     * @param img the image to be saved
     * @param filename the relative filename to this object's save directory
     **/
    public void storeImage(BufferedImage img, String filename) {
	saveImage(img, fileName + saveDir, fileExtension);
    }
    
    /**
     * Loads the given file location as a buffered image
     *
     * @param filename the location of the image
     **/
    public static BufferedImage loadImage(String filename) {
	return ImageIO.read(new File(filename));
    }

    /**
     * Saves the given image with the filename and file extension
     * specified. Do not include the extension in the filename, as it
     * gets added when the file is saved. Extension types supported are png,
     * jpg, and gif.
     *
     * @param img the image to be saved
     * @param filename the name of the file without an extension
     * @param extension the type of image that will be saved
     **/
    public static void saveImage(BufferedImage img, String filename, String extension) {
	File outputfile = new File(filename + extension);
	ImageIO.write(img, extension, outputfile);
    }

    public String getSaveDir() {
	return saveDir;
    }

    public String getFileExtension() {
	return fileExtension;
    }

    public void setSaveDir(String saveDir) {
	this.saveDir = saveDir;
    }

    public String setFileExtension(String fileExtension) {
	this.fileExtension = fileExtension;
    }
}
