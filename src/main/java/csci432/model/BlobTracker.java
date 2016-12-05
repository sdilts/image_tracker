package csci432.model;

import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;

public class BlobTracker {

    private final int backGroundColor = new Color(0, 0, 0).getRGB();
    
    /**
     * Number of pixels that a blob has to be before it is called a blob:
     **/
    private int sizeThreshold = 10;
    /**
     * How close a blob in another frame needs to be to another blob 
     * before they are the same blob:
     **/
    private int checkThreshold = 20;

    private List<Blob> blobs;

    public BlobTracker(int sizeThreshold, int checkThreshold) {
	this.sizeThreshold = sizeThreshold;
	this.checkThreshold = checkThreshold;
	this.blobs = new ArrayList<Blob>();
    }

    public BlobTracker() {
	this.blobs = new ArrayList<Blob>();
    }

    /**
     * Populates this object's blob list with all the blobs in the image
     * Intended to be used once at the first filtered image
     * @param img the image that the blobs are extracted from
     *
     * @param picIndex the index of the given picture
     **/
    public void getInitialBlobs(BufferedImage img, int picIndex) {
	if(!blobs.isEmpty()) {
	    System.err.println("WARNING: ERASING BLOBS");
	}
	List<HitBox> boxes = findHitBoxes(img);
	addNewBlobs(boxes, picIndex);
    }

    public void trackBlobByBlob(BufferedImage img, int picIndex) {
	List<HitBox> boxes = findHitBoxes(img);
	List<HitBox> noBlob = new LinkedList(boxes);
	
	Iterator<Blob> iter = blobs.iterator();
	while(iter.hasNext()) {
	    Blob b = iter.next();
	    List<HitBox> overLaps = new LinkedList();
	    for(HitBox h : boxes) {
		if(h.isIn(b.getLocation(picIndex -1), checkThreshold)) {
		    overLaps.add(h);
		}
	    }
	    switch(overLaps.size()) {
	    case 1:
		b.addLocation(picIndex, overLaps.get(0).getCenter());
		noBlob.remove(overLaps.get(0));
		break;
	    case 0:
		//not in frame anymore: remove
		iter.remove();
		break;
	    default:
		//number other than 1 or 0: we have conflicts:
		System.out.println("There are conflicts");
		HitBox found = b.resolveConflict(overLaps);
		noBlob.remove(found);
	    }
	}
	addNewBlobs(noBlob, picIndex);
    }

    public void trackBlobByHitBox(BufferedImage img, int picIndex) {
	List<HitBox> boxes = findHitBoxes(img);
	List<HitBox> noBlob = new LinkedList(boxes);
	List<Blob> noBoxes = new LinkedList(blobs);
	
	for(HitBox h : boxes) {
	    Iterator<Blob> iter = blobs.iterator();
	    List<Blob> overlaps = new LinkedList();
	    
	    while(iter.hasNext()) {
		Blob b = iter.next();
		if(h.isIn(b.getLocation(picIndex - 1), checkThreshold)) {
		    overlaps.add(b);
		    noBoxes.remove(b);
		}
	    }
	    switch(overlaps.size()) {
	    case 1:
		//blob is in the correct hitbox:
		Blob b = overlaps.get(0);
		b.addLocation(picIndex, h.getCenter());
		break;
	    case 0:
		//hitbox doesn't have any blobs:
		noBlob.add(h);
		break;
	    default:
		//conflict: resolve
		h.resolveConflicts(overlaps);
	    }
	}
	addNewBlobs(noBlob,picIndex);
	blobs.remove(noBoxes);
    }

    private void addNewBlobs(List<HitBox> boxes, int picIndex) {
	for(HitBox h : boxes) {
	    Blob b = new Blob();
	    b.addLocation(picIndex, h.getCenter());
	    blobs.add(b);
	}
    }
	
    /**
     * Returns a list of hitboxes in the image
     * @param img
     **/
    List<HitBox> findHitBoxes(BufferedImage img) {
	List<HitBox> blobs = new LinkedList();
	for(int i = 0;  i < img.getWidth(); i++) {
	    for(int j = 0; j < img.getHeight(); j++) {
		if(img.getRGB(i,j) != backGroundColor) {
		    HitBox h = getHitBox(img,i,j, sizeThreshold);
		    if(h != null) {
			blobs.add(h);
		    }
		    System.out.println();
		}
	    }
	}
	return blobs;
    }

    /**
     * returns the hitbox for the blob at the given point.
     **/
    HitBox getHitBox(BufferedImage img, int x, int y, int sizeThreshold) {
	//don't want to use recursion: use a stack instead
	Deque<Point> stack = new LinkedList<Point>();
	int bigX = x;
	int bigY = y;
	int smallY = y;
	int smallX = x;
	int numVisited = 0;
	stack.push(new Point(x, y));
	//start 
	while(!stack.isEmpty()) {
	    Point p = stack.pop();
	    Color color = new Color(img.getRGB((int)p.getX(), (int) p.getY()));
	    System.out.print("coords: " + p.x + "," + p.y);
	    System.out.println(" Cur color = " + color);
	    if(img.getRGB((int)p.getX(), (int)p.getY()) != backGroundColor) {
		numVisited++;
		//mark it:
		int xPos = (int) p.getX();
		int yPos = (int) p.getY();
		img.setRGB((int)p.getX(),(int)p.getY(), backGroundColor);
		//check our values:
		if(xPos > bigX) {
		    bigX = (int) p.getX();
		}
		if(xPos < smallX) {
		    smallX = (int) p.getX();
		}
		if(yPos > bigY) {
		    bigY = (int) p.getY();
		}
		if(yPos < smallY) {
		    smallY = (int) p.getY();
		}
		for(int i = xPos - 1; i <= xPos+1; i++) {
		    for(int j = yPos-1; j <= yPos+1; j++) {
			if(i >= 0 && i < img.getWidth() &&
			   j >= 0 && j < img.getHeight() &&
			   (i != x || j != y)) {
			    stack.push(new Point(i, j));
			}
		    }
		} //endfor
	    } //endif
	    else {
		System.out.println("I'm am black at " + p.x + "," + p.y);
	    }
	} //endwhile
	if(numVisited >= sizeThreshold) {
	    return new HitBox(new Point(smallX, smallY), new Point(bigX, bigY));
	}
	//else
	return null;
    }
}
