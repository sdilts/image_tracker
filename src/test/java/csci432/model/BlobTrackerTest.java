package csci432.model;

import csci432.util.*;
import org.junit.Test;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;



public class BlobTrackerTest {

    @Test
    public void getBlobTest() {
	System.out.println("Testing getBlob:");
	String fileName = new
	    File(getClass().getClassLoader().getResource("blobTestxs3.png").getFile()).getAbsolutePath();
	BufferedImage img = ImageUtil.loadImage(fileName);
	HitBox h = BlobTracker.getBlob(img, 270,270);
	Point left = h.getLeftPoint();
	Point right = h.getRightPoint();
	System.out.printf("LeftPoint: x = %s y = %s\n", left.x, left.y);
	System.out.println(right);
	//System.out.printf("RightPoint: x = %s y = %s\n", right.x, right.y);
	assertThat(left.x, is(equalTo(250)));
	assertThat(left.y, is(equalTo(250)));
	assertThat(right.x, is(equalTo(290)));
	assertThat(right.y, is(equalTo(290)));
    }

    @Test
    public void findBlobTest() {
	System.out.println("Testing findBlob:");
	String fileName = new
	    File(getClass().getClassLoader().getResource("blobTest3.png").getFile()).getAbsolutePath();
	BufferedImage img = ImageUtil.loadImage(fileName);
	List<HitBox> boxes = BlobTracker.findBlobs(img);
	assertThat(boxes.size(), is(equalTo(3)));
    }

}
