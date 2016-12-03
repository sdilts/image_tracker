package csci432;

import csci432.camera.Camera;
import csci432.camera.RaspberryPiCam;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String ... args) {
        OptionSet options = getOptions(args);
        Camera camera = new RaspberryPiCam(options.valueOf("save_loc").toString());
        camera.takePicture();

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

    /**
     * Gets the options from the args
     * @param args commandline arguments
     * @return the value for the options
     */
    static OptionSet getOptions(String... args) {
        OptionParser parser = new OptionParser();
        parser.accepts("save_loc").withRequiredArg().ofType(String.class).defaultsTo("/media/pi/SAGE/pictures/");
        parser.accepts("load_loc").withOptionalArg().ofType(String.class);
        OptionSet options = parser.parse(args);
        return options;
    }
}
