package csci432.model;

import java.awt.*;

public class HitBox {
    private Point upperLeft;
    private Point lowerRight;

    public HitBox(Point upperLeft, Point lowerRight) {
	this.upperLeft = upperLeft;
	this.lowerRight = lowerRight;
    }

    /**
     * Returns whether or not the given point is in the hitbox
     * @param p the point to be checked
     * @param range how far away from the bounds of the box to be checked
     **/
    public boolean isIn(Point p, int range) {
	//check the x values:
	return (upperLeft.x-range < p.x) &&
	    (p.x < lowerRight.x + range) &&
	    (upperLeft.y -range < p.y) &&
	    (p.y + range < lowerRight.y);
    }

    public Point getCenter() {
	double xPos = (upperLeft.x + lowerRight.x)/2;
	doulbe yPos = (upperLeft.y + lowerRight.y)/2;
	return new Point(xPos, yPos);j
    }

    public Point getLeftPoint() {
	return upperLeft;
    }

    public Point getRightPoint() {
	return lowerRight;
    }
}
