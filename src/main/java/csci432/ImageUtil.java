package csci432;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


/**
 * Provides a wrapper for loading and saving BufferedImages. Instances 
 * of this class allow you to save certain settings for easier management
 **/
public class ImageUtil {

    // private String saveDir;
    // private String fileExtension;
    
    // public ImageUtil(String saveDir, String fileExtension) {
    // 	this.saveDir = saveDir;
    // 	this.fileExtension = fileExtension;
    // }

    // /** 
    //  * Loads the given file as a BufferedImage. The directory used will be the one specified by this
    //  * object's saveDir variable
    //  *
    //  * @param filename the relative filename to this object's save directory
    //  **/
    // public BufferedImage getImage(String fileName) {
    // 	return loadImage(saveDir + fileName);
    // }
    
    // /**
    //  * Loads the given file as a BufferedImage. The directory used will be the one specified by this
    //  * object's saveDir variable
    //  *
    //  * @param img the image to be saved
    //  * @param filename the relative filename to this object's save directory
    //  **/
    // public void storeImage(BufferedImage img, String fileName) throws IOException {
    // 	saveImage(img, fileName + saveDir, fileExtension);
    // }
    
	
    /**
     * Loads the given file location as a buffered image
     * Returns null if the image is not found
     *
     * @param filename the location of the image
     * @return specified image or null
     **/
    public static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
     * @return true if the flile was saved correctly
     **/
    public static boolean saveImage(BufferedImage img, String filename, String extension) {
	File outputfile = new File(filename + extension);
	try {
	    
	    ImageIO.write(img, extension, outputfile);
	    return true;
	} catch(IOException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    // public String getSaveDir() {
    // 	return saveDir;
    // }

    // public String getFileExtension() {
    // 	return fileExtension;
    // }

    // public void setSaveDir(String saveDir) {
    // 	this.saveDir = saveDir;
    // }

    // public void setFileExtension(String fileExtension) {
    // 	this.fileExtension = fileExtension;
    // }
}
