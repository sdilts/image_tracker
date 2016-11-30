package csci432;

import org.junit.Test;
import java.awt.image.BufferedImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ItemTrackerTest {

    @Test
    public void insertItemTest() {
	ItemTracker t = new ItemTracker();
	//TYPE_INT_ARGB is a placeholder 
	t.insertItem(new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB));
	assert(t.isProcessing() == true);
    }

    @Test
    public void processItemTest() {
	ItemTracker t = new ItemTracker();
	//TYPE_INT_ARGB is a placeholder 
	t.insertItem(new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB));
	t.processItem();
	assert(t.isProcessing() == false);
    }
}