package csci432;
import csci432.model.Blob;

import java.util.concurrent.*;
import java.util.*;
import java.awt.image.BufferedImage;

/**
 * Class used for identifying moving objects, called blobs, based off
 * of images that have been passed through Sigma-Delta and edge
 * detection algorithms.
 *
 * @param T the type of the object that will process each item
 **/
public class ProcessController<T extends IProcessImage> {
    private Queue<BufferedImage> queue;
    private List<Blob> currentBlobs;
    private T processor;

    public ProcessController(T processor) {
	this.processor = processor;
	
	queue = new LinkedBlockingQueue<BufferedImage>();
	currentBlobs = new LinkedList<Blob>();
    }

    /**
     * Starts the ImageTracker service
     */
    public void start() {
	new Thread(() -> {
		run();
	}).start();
    }

    /**
     * The method that does stuff
     **/
    private void run() {
	while(true) {
	    if(!queue.isEmpty()) {
		//take is blocking equivalent
		BufferedImage item = queue.poll();
		processItem(item);
	    }
	}
    }

    /**
     * Processes all items left in the queue
     **/
    public void flushQueue() {
	while(!queue.isEmpty()) {
	    //take is blocking equivalent
	    BufferedImage item = queue.poll();
	    processItem(item);
	}

    }

    /**
     * Returns whether or not there are still items to be processed in
     * the image queue.
     * 
     * @return the items that are still in the image queue
     **/
    public int itemsWaiting() {
	return queue.size();
    }

    /**
     * Inserts the given item into the image queue to be processed
     * 
     * @param item an item to be inserted into the processing queue
     **/
    public void insertItem(BufferedImage item) {
	//BlockingQueue.put will block until finished:
	queue.add(item);
    }

    /**
     * Inserts the given items into the image queue to be processed
     *
     * @param item a list whose contents are to be inserted into the processing queue
     **/
    public void insertItem(List<BufferedImage> item) {
	for(BufferedImage i : item) {
	    insertItem(i);
	}
    }

    /**
     * Public method for making the ImageTracker to process an
     * item. Should not be used when the ImageTracker is in its own thread.
     * Processes the next element in the input queue
     **/
    public void processItem() {
	//BlockingQueue.take() is equivalent blocking function
	processItem(queue.poll());
    }
    
    
    /**
     * Method that determines how the given image relates to the
     * previous images given and determines what blobs are present 
     * 
     * @param item Item to be processed;
     **/
    private void processItem(BufferedImage item) {
	processor.processImage(item);
    }
}
