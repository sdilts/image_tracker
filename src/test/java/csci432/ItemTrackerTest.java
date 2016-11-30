package csci432;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ItemTrackerTest {

    @Test
    public void insertItemTest() {
	ItemTracker t = new ItemTracker();
	t.insertItem(new Object());
	assert(t.stillProcessing() == true);
    }

    @Test
    public void processItemTest() {
	ItemTracker t = new ItemTracker();
	t.insertItem(new Object());
	t.processItem();
	assert(t.stillProcessing() == false);
    }
}