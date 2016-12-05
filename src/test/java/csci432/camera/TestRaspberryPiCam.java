package csci432.camera;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestRaspberryPiCam {
    RaspberryPiCam camera;

    @Before
    public void setup() {
        camera = new RaspberryPiCam("/temp/");
    }

    @Test
    @Ignore
    public void testTakePicture() {
        camera.takePicture();
        assertThat(new File("/temp/1.jpg"), is(notNullValue()));
    }

    @Test
    @Ignore
    public void testTakePictureOnTimer() {
        camera.takePictureOnInterval(500L, 300000L);
        assertThat(new File("/temp/1.jpg"), is(notNullValue()));
        try {
            Thread.sleep(2000L);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertThat(new File("/temp/1.jpg"), is(notNullValue()));
    }

    @Test
    @Ignore
    public void testGetUnfilteredImage () {
        camera.takePicture();
        assertThat(camera.getUnfilteredImage(), is(notNullValue()));
    }
}
