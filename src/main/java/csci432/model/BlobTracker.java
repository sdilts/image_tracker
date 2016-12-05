package csci432.model;

import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;

public class BlobTracker {

    private final static int backGroundColor = new Color(0, 0, 0).getRGB();
    
    /**
     * Number of pixels that a blob has to be before it is counted:
     **/
    private static int sizeThreshold = 10;

    
    /**
     * Returns a list of hitboxes in the image
     * @param img
     **/
    public static List<HitBox> findHitBoxes(BufferedImage img) {
	List<HitBox> blobs = new LinkedList();
	for(int i = 0;  i < img.getWidth(); i++) {
	    for(int j = 0; j < img.getHeight(); j++) {
		if(img.getRGB(i,j) != backGroundColor) {
		    HitBox h = getHitBox(img,i,j, sizeThreshold);
		    if(h != null) {
			blobs.add(h):
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
    public static HitBox getHitBox(BufferedImage img, int x, int y, int sizeThreshold) {
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
