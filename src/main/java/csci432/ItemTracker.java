package csci432;
import java.util.concurrent.*;
import java.util.*;

/**
 * Class used for keeping track of the moving objects, called blobs,
 * taken from the recorded images.
 *
 **/
/* probably use an actual class rather than generics: (I don't know
 * what class to use) 
 */
public class ItemTracker<T> {
    BlockingQueue<T> queue;
    List<T> currentBlobs;

    public ItemTracker() {
	queue = new LinkedBlockingQueue<T>();
	currentBlobs = new LinkedList<T>();
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
     * @return true if still processing
     **/
    public boolean stillProcessing() {
	return !queue.isEmpty();
    }

    /**
     * Inserts the given item into the queue to be processed
     * @param item an item to be inserted into the processing queue
     **/
    public void insertItem(T item) {
	try {
	//put will block until finished:
	    queue.put(item);
	} catch(InterruptedException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    /**
     * Inserts the given items into the queue to be processed
     *
     * @param item a list whose contents are to be inserted into the processing queue
     **/
    public void insertItem(T[] item) {
	for(T i : item) {
	    insertItem(i);
	}
    }

    /**
     * Process the next element in the queue:
     **/
    public void processItem() {
	processItem(queue.poll());
    }
    
    
    /**
     * Method that determines how the given item fits into the history
     * of previous items
     * @param item Item to be processed;
     **/
    private void processItem(T item) {
	
    }
}
