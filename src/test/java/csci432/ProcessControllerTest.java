package csci432;

import org.junit.Test;
import java.awt.image.BufferedImage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ProcessControllerTest {

    //prep for when using threads for this
    private String threadName = "testing";
    private int checkColor = 255;

    public void enterItems(ProcessController proc, int numItems) {
	for(int i = 0; i < numItems; i++) {
	    BufferedImage b = new BufferedImage(500,500,
						BufferedImage.TYPE_INT_ARGB);
	    b.setRGB(i,0,checkColor);
	    proc.insertItem(b);
	}
    }

    
    @Test
    public void insertItemTest() {
	TestProcess t = new TestProcess();
	ProcessController<TestProcess> proc = new ProcessController<TestProcess>(t);
	int numEnter = 4;

	enterItems(proc, numEnter);
	
	//may fail if items are being processed in the background:
	assertThat(proc.itemsWaiting(), is(numEnter));
    }

    @Test
    public void processItemTest() {
	TestProcess t = new TestProcess();
	ProcessController proc = new ProcessController(t);
	int numEnter = 4;

	enterItems(proc, numEnter);

	proc.flushQueue();
	
	assertThat(t.getOrderCorrect(), is(true));
	assertThat(t.getItemsGiven(), is(equalTo(numEnter)));
    }

    private class TestProcess implements IProcessImage {

	private int itemsGiven = 0;

	private boolean orderCorrect = true;

	//private boolean threadCorrect;

	public TestProcess() {
	    
	}

	public void processImage(BufferedImage item) {
	    assertThat(item, is(notNullValue()));
	    checkOrder(item);
	    itemsGiven++;
	}

	private void checkOrder(BufferedImage item) {
	    if(orderCorrect && item.getRGB(itemsGiven, 0) != checkColor) {
		orderCorrect = false;
	    }
	}

	public int getItemsGiven() {
	    return itemsGiven;
	}

	public boolean getOrderCorrect() {
	    return orderCorrect;
	}
	

    }
}
