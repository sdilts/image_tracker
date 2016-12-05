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
     * returns the hitbox for the blob at the given point.
     **/
    public static HitBox getBlob(BufferedImage img, int x, int y) {
	//don't want to use recursion: use a stack instead
	Deque<Point> stack = new LinkedList<Point>();
	int bigX = x;
	int bigY = y;
	int smallY = y;
	int smallX = x;
	int numVisisted = 0;
	stack.push(new Point(x, y));
	//start 
	while(!stack.isEmpty()) {
	    Point p = stack.pop();
	    if(img.getRGB((int)p.getX(), (int)p.getY()) != backGroundColor) {
		numVisisted++;
		System.out.println("Num visisted = " + numVisisted);
		//mark it:
		img.setRGB((int)p.getX(),(int)p.getY(), backGroundColor);
		if(p.getX() > bigX) {
		    bigX = (int) p.getX();
		}
		if(p.getX() < smallX) {
		    smallX = (int) p.getX();
		}
		if(p.getY() > bigY) {
		    bigY = (int) p.getY();
		}
		if(p.getY() < smallY) {
		    smallY = (int) p.getY();
		}
		for(int xPos = x - 1; xPos <= x+1; xPos++) {
		    for(int yPos = y-1; yPos <= y+1; yPos++) {
			if(xPos >= 0 && xPos < img.getWidth() &&
			   yPos >= 0 && yPos < img.getHeight() &&
			   xPos != x && yPos != y) {
			    stack.push(new Point(xPos, yPos));
			}
		    }
		} //endfor
	    } //endif
	} //endwhile
	System.out.println("BigX = " + bigX);
	System.out.println("BigY = " + bigY);
	System.out.println("smallX = " + smallX);
	System.out.println("smally = " + smallY);
	return new HitBox(new Point(smallX, smallY), new Point(bigX, bigY));
    }
    
    /**
     * Returns a list of hitboxes in the imaged
     **/
    public static List<HitBox> findBlobs(BufferedImage img) {
	List<HitBox> blobs = new LinkedList();
	for(int i = 0;  i < img.getWidth(); i++) {
	    for(int j = 0; j < img.getHeight(); j++) {
		if(img.getRGB(i,j) != backGroundColor) {
		    blobs.add(getBlob(img,i,j));
		    System.out.println();
		}
	    }
	}
	return blobs;
    }
}
