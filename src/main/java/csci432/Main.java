package csci432;

import com.hopding.jrpicam.*;
import com.hopding.jrpicam.exceptions.*;

import java.time.LocalDateTime;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.*;

/**
 *
 * @param The foldername of the save directory. Must end in "/"
 **/
public class Main {

    public static void main(String [] args) {
	if(args.length != 1) {
	    System.out.println("Please specify a location to save images");
	    System.exit(0);
	}

	String saveDir = args[1];
	
	RPiCamera camera = setupCamera(args);
	SigmaDeltaFilter filter = new SigmaDeltaFilter();
	
	//CameraTimer uses a separate thread, need a blocking queue
	BlockingQueue<BufferedImage> toProcess = new LinkedBlockingQueue<>();
	
	//takes a picture every delay seconds with specified camera, adds
	//it to the given queue
	long delay = 1000; //in milliseconds; start big
        CameraTimer timer = new CameraTimer(camera, toProcess, delay);
	
	int picNum = 0;
	timer.start();
	while(picNum < 100) {
	    if(!toProcess.isEmpty()) {
		picNum++;
		try {
		    //use fully blocking function:
		    BufferedImage nonProc = toProcess.take();
		    BufferedImage img = filter.filter(nonProc);
		    LocalDateTime t = LocalDateTime.now();
		    saveImage(nonProc, saveDir, "nonProc" + t.toString());
		    saveImage(img, saveDir, "proc" + t.toString());
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}
	timer.stop();
    }

    public static void saveImage(BufferedImage img, String saveDir, String fileName) {
	try {
	    File outputfile = new File(saveDir + fileName + ".jpg");
	    System.out.println(saveDir + fileName + ".jpg");
	    ImageIO.write(img, "jpg", outputfile);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static RPiCamera setupCamera(String[] settings) {
	RPiCamera c = null;
	try {
	    c = new RPiCamera();
	    //put all of your configuration stuff here
	    //this is only some of the stuff you can do. See
	    //http://hopding.com/docs/jrpicam/
	    c.setRotation(0);
	    c.setHeight(500);
	    c.setWidth(500);
	    c.setColourEffect(128, 128); //grayscale image
	} catch(FailedToRunRaspistillException e) {
	    e.printStackTrace();
	}
	return c;
    }
}
