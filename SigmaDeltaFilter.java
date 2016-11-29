package csci432;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SigmaDeltaFilter {

    List<BufferedImage> imageList;

    public SigmaDeltaFilter() {
        this.imageList = new ArrayList<BufferedImage>();
    }

    public BufferedImage Filter(BufferedImage b) {
        return b;
    }

}