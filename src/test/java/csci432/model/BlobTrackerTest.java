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
	    File(getClass().getClassLoader().getResource("blobTest3.png").getFile()).getAbsolutePath();
	BufferedImage img = ImageUtil.loadImage(fileName);

	BlobTracker tracker = new BlobTracker();
	HitBox h = tracker.getHitBox(img, 335,303, 10);
	Point left = h.getLeftPoint();
	Point right = h.getRightPoint();
	System.out.printf("LeftPoint: x = %s y = %s\n", left.x, left.y);
	System.out.println(right);
	//System.out.printf("RightPoint: x = %s y = %s\n", right.x, right.y);
	assertThat(left.x, is(equalTo(279)));
	assertThat(left.y, is(equalTo(277)));
	assertThat(right.x, is(equalTo(367)));
	assertThat(right.y, is(equalTo(330)));
    }

    @Test
    public void findHitBoxesTest() {
	System.out.println("Testing findBlob:");
	String fileName = new
	    File(getClass().getClassLoader().getResource("blobTest3.png").getFile()).getAbsolutePath();
	BufferedImage img = ImageUtil.loadImage(fileName);

	BlobTracker tracker = new BlobTracker();
	List<HitBox> boxes = tracker.findHitBoxes(img);
	assertThat(boxes.size(), is(equalTo(1)));
    }

}
