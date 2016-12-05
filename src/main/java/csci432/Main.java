package csci432;

import csci432.camera.RaspberryPiCam;
import csci432.processor.SigmaDeltaFilter;
import csci432.util.ImageUtil;
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

        if (options.hasArgument("load_loc")) {
            LOGGER.info("Processing files...");
            BufferedImage input, output;
            String path = options.valueOf("load_loc").toString();
            String fileName = new String();
            SigmaDeltaFilter f = new SigmaDeltaFilter();
            for (int i = 0; i < 55; i++) {
                fileName = path + "/capture" + i + ".jpg";
                input = ImageUtil.loadImage(fileName);
                if (input != null) {
                    output = f.filter(input);
                    ImageUtil.saveImage(output, path + "/filter" + i, "jpg");
                    LOGGER.info(path + "/filter:" + i + ".jpg");
                } else {
                    LOGGER.info("Couldn't find image" +fileName);
                }
            }
        } else {
            RaspberryPiCam camera = new RaspberryPiCam(options.valueOf("save_loc").toString());
            camera.takePictureOnInterval((Long) options.valueOf("interval"), (Long) options.valueOf("duration"));
            SigmaDeltaFilter sigmaDeltaFilter = new SigmaDeltaFilter();
            LOGGER.info("Begin taking images...");
            BufferedImage input, output;
            Integer index = 0;
            while (true) {
                input = camera.getUnfilteredImage();
                if (input!=null) {
                    LOGGER.info("File: "+index+".jpg");
                    output = sigmaDeltaFilter.filter(input);
                    LOGGER.info("Finished Filtering...");
                    LOGGER.info("Saving Image filtered image...");
                    ImageUtil.saveImage(output, options.valueOf("save_loc").toString()+"filtered"+index+".", "jpg");
                    LOGGER.info("Finished Saving image...");
                    index++;
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
        parser.accepts("interval").withRequiredArg().ofType(Long.class).defaultsTo(500L);
        parser.accepts("duration").withRequiredArg().ofType(Long.class).defaultsTo(300000L);
        return parser.parse(args);
    }
}
