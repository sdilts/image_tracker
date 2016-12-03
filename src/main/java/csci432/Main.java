package csci432;

import csci432.camera.Camera;
import csci432.camera.RaspberryPiCam;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName()
    );
    public static void main(String ... args) {
        OptionSet options = getOptions(args);
        Camera camera = new RaspberryPiCam(options.valueOf("save_loc").toString());
        camera.takePicture();

        LOGGER.info("Processing files...");
        BufferedImage input, output;
        if (options.hasArgument("load_loc")) {
            String path = options.valueOf("load_loc").toString();
            String fileName = new String();
            SigmaDeltaFilter f = new SigmaDeltaFilter();
            for (int i = 0; i < 55; i++) {
                fileName = path + "/capture" + i + ".jpg";
                input = ImageUtil.loadImage(fileName);
                if (input != null) {
                    output = f.filter(input);
                    ImageUtil.saveImage(output, path + "/filter" + i + ".", "jpg");
                    LOGGER.info(path + "/filter:" + i + ".jpg");
                } else {
                    LOGGER.info("Couldn't find image" +fileName);
                }
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
