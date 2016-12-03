package csci432;
//import com.hopding.jrpicam.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    /**
     * loads images from a file to be inputted into SigmaDeltaFilter
     *
     * @return A string that says Hello World!
     */
    public static void main(String[] args) {
        System.out.println("Processing files");
        BufferedImage input, output;
        String path = args[0];  //"~/testImage1";
        String fileName = "~/testImage1";
        SigmaDeltaFilter f = new SigmaDeltaFilter();
        for (int i = 0; i < 55; i++) {
            fileName = path + "/capture" + i + ".jpg";
            input = ImageUtil.loadImage(fileName);
            if (input != null) {
                output = f.filter(input);
                ImageUtil.saveImage(output, path + "/filter"
                        + i + ".", "jpg");
                System.out.println(path + "/filter:" + i + ".jpg");
            } else {
                System.out.printf("Couldn't find image %s\n", fileName);
            }
        }
    }
}