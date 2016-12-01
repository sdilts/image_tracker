package csci432;

import csci432.camera.Camera;
import csci432.camera.RaspberryPiCam;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Main {
    public static void main(String ... args) {
        OptionSet options = getOptions(args);
        Camera camera = new RaspberryPiCam(options.valueOf("save_loc").toString());
        camera.takePicture();
    }

    /**
     * Gets the options from the args
     * @param args commandline arguments
     * @return the value for the options
     */
    static OptionSet getOptions(String... args) {
        OptionParser parser = new OptionParser();
        parser.accepts("save_loc").withRequiredArg().ofType(String.class).defaultsTo("/media/pi/SAGE/pictures/");
        OptionSet options = parser.parse(args);
        return options;
    }
}
