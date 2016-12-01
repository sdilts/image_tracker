package csci432;

import com.hopding.jrpicam.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.awt.image.BufferedImage;

public class CameraTimer {

    private Timer t;
    private RPiCamera camera;
    private BlockingQueue<BufferedImage> queue;
    private long delay;
    
    public CameraTimer(RPiCamera camera, BlockingQueue<BufferedImage>
		       queue, long delay) {
	this.camera = camera;
	this.queue = queue;
	this.delay = delay;
    }

    public void start() {
	if(t == null) {
	    t = new Timer("CameraThread");
	    t.schedule(new TakePicture(), delay);
	} else {
	    //yes, I could use an exception here
	    System.out.println("Timer already active");
	    System.exit(0);
	}
    }

    public void stop() {
	if(t != null) {
	    t.cancel();
	    t = null;
	} else {
	    //yes, I could use an exception here
	    System.out.println("Timer not active");
	    System.exit(0);
	}
    }

    private class TakePicture extends TimerTask {
	
	public void run() {
	    queue.put(camera.takeBufferedStill());
	}

    }
}
