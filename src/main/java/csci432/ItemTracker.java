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
 **/
public class ItemTracker<T> {
    BlockingQueue<BufferedImage> queue;
    List<Blob> currentBlobs;

    public ItemTracker() {
	queue = new LinkedBlockingQueue<BufferedImage>();
	currentBlobs = new LinkedList<Blob>();
    }

    /**
     * The method that does stuff
     **/
    public void run() {
	/*
	while(true) {
	    if(!queue.isEmpty) {
		T item = queue.take();
		processItem(item);
	    }
	} 
	*/
    }

    /**
     * Returns whether or not there are still items to be processed in
     * the image queue.
     * 
     * @return true if still processing
     **/
    public boolean isProcessing() {
	return !queue.isEmpty();
    }

    /**
     * Inserts the given item into the image queue to be processed
     * 
     * @param item an item to be inserted into the processing queue
     **/
    public void insertItem(BufferedImage item) {
	try {
	    //put will block until finished:
	    queue.put(item);
	    //errors when interrupted:
	} catch(InterruptedException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    /**
     * Inserts the given items into the image queue to be processed
     *
     * @param item a list whose contents are to be inserted into the processing queue
     **/
    public void insertItem(BufferedImage[] item) {
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
	try {
	//BlockingQueue.take() is a blocking function
	    processItem(queue.take());
	} catch(InterruptedException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }
    
    
    /**
     * Method that determines how the given image relates to the
     * previous images given and determines what blobs are present 
     * 
     * @param item Item to be processed;
     **/
    private void processItem(BufferedImage item) {
	
    }
}