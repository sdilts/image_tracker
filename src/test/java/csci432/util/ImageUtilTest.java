package csci432.util;

import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ImageUtilTest {

    //  @Rule
    // public TemporaryFolder folder= new TemporaryFolder();
    

    @Test
    public void testLoadImage() {
	String fileName = new
	    File(getClass().getClassLoader().getResource("Tux_soldier.jpg").getFile()).getAbsolutePath();

	BufferedImage img = ImageUtil.loadImage(fileName);
	assertThat(img, is(notNullValue()));
    }
    
    @Test
    public void testSaveImage() {
	// String fileName = new
	//     File(getClass().getClassLoader().getResource("Tux_soldier.jpg").getFile()).getAbsolutePath();
    	// BufferedImage img = ImageUtil.loadImage(fileName + ".jpg");
    	// //ImageUtil.saveImage(img, filename, ".jpg");
    }
}
