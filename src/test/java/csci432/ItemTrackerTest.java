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
	int numEnter = 4;
	for(int i = 0; i < numEnter; i++) {
	    t.insertItem(new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB));
	}
	//may fail if items are being processed in the background:
	assertThat(t.itemsWaiting(), is(numEnter));
    }

    @Test
    public void processItemTest() {
	ItemTracker t = new ItemTracker();
	//TYPE_INT_ARGB is a placeholder 
	t.insertItem(new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB));
	t.insertItem(new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB));
	assertThat(t.itemsWaiting(), is(2));
	t.processItem();
	assertThat(t.itemsWaiting(), is(1));
	t.processItem();
	assertThat(t.itemsWaiting(), is(0));
    }
}
