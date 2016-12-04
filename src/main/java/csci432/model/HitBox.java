package csci432.model;

import java.awt.*;

public class HitBox {
    private Point upperLeft;
    private Point lowerRight;

    public HitBox(Point upperLeft, Point upperRight) {
	this.upperLeft = upperLeft;
	this.lowerRight = lowerRight;
    }

    /**
     * Returns whether or not the given point is in the hitbox
     * @param p the point to be checked
     **/
    public boolean isIn(Point p) {
	//check the x values:
	return upperLeft.x < p.x && p.x < lowerRight.x && upperLeft.y < p.y && p.y < lowerRight.y;
    }
}
