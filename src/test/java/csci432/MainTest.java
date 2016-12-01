package csci432;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MainTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void testSaveImage() throws IOException {
	BufferedImage b = new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB);
	System.out.println(testFolder.getRoot().getAbsolutePath());
	Main.saveImage(b, testFolder.getRoot().getAbsolutePath() + "/", "test");
	File f = new File(testFolder.getRoot().getAbsolutePath() + "/test.jpg");
	assertThat(f.exists(), is(true));
    }
}
