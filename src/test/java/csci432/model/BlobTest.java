package csci432.model;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class BlobTest {
    Blob blob;

    @Before
    public void setup() {
        blob = new Blob();
    }

    @Test
    public void testAddLocation() {
        blob.addLocation(0, new Point(0,0));
        assertThat(blob.getLocation(0), is(equalTo(new Point(0,0))));
        assertThat(blob.getPictureRange(), is(equalTo(Arrays.asList(0, 0))));
        assertThat(blob.inPicture(0), is(true));
    }

    @Test
    public void testGetLocationNull() {
        assertThat(blob.getLocation(0), is(nullValue()));
    }

    @Test
    public void testGetLocation() {
        blob.addLocation(0, new Point(0,0));
        assertThat(blob.getLocation(0), is(equalTo(new Point(0,0))));
    }

    @Test
    public void testGetPictureRangeNull() {
        assertThat(blob.getPictureRange(), is(nullValue()));
    }

    @Test
    public void testGetPictureRange() {
        blob.addLocation(0, new Point(0, 0));
        assertThat(blob.getPictureRange(), is(equalTo(Arrays.asList(0, 0))));
        blob.addLocation(1, new Point(0, 0));
        assertThat(blob.getPictureRange(), is(equalTo(Arrays.asList(0, 1))));
    }

    @Test
    public void testInPictureNull() {
        assertThat(blob.inPicture(0), is(false));
    }

    @Test
    public void testInPicture() {
        blob.addLocation(0, new Point(0, 0));
        assertThat(blob.inPicture(0), is(true));
        blob.addLocation(1, new Point(0, 0));
        assertThat(blob.inPicture(1), is(true));
    }

    @Test
    public void testGetLocationsInRangeNull() {
        assertThat(blob.getLocationsInRange(0, 4), is(equalTo(Arrays.asList(null, null, null, null, null))));
    }

    @Test
    public void testGetLocationsInRange() {
        Point p = new Point(0, 0);
        for (int i = 0; i < 5; i++) {
            blob.addLocation(i, p);
        }
        assertThat(blob.getLocationsInRange(0, 4), is(equalTo(Arrays.asList(p, p, p, p, p))));
    }

    @Test
    public void testIsEmptyNull() {
        assertThat(blob.isEmpty(), is(true));
    }

    @Test
    public void isEmpty() {
        blob.addLocation(0, new Point(0, 0));
        assertThat(blob.isEmpty(), is(false));
    }

    @Test
    public void getAllLocationsNull() {
        assertThat(blob.getAllLocations(), is(nullValue()));
    }

    @Test
    public void getAllLocations() {
        Point p = new Point(0, 0);
        blob.addLocation(0, p);
        assertThat(blob.getAllLocations(), is(equalTo(Arrays.asList(p))));
        blob.addLocation(1, p);
        assertThat(blob.getAllLocations(), is(equalTo(Arrays.asList(p, p))));
    }

    @Test
    @Ignore
    public void testSaveNull() throws IOException {
        blob.save("blob");
        assertThat(Files.readAllLines(Paths.get("blob")).get(0), is(equalTo("{\"empty\":true,\"allLocations\":null,\"pictureRange\":null,\"locations\":{}}")));
    }

    @Test
    @Ignore
    public void testEquals() {
        assertThat(blob, is(equalTo(new Blob())));
    }

    @Test
    public void testToString() {
        assertThat(blob.toString(), is(equalTo("Blob:{}")));
    }

    @Test
    public void loadSavedNull() {
        Blob savedBlob = Blob.load("blob");
        assertThat(savedBlob, is(equalTo(savedBlob)));
    }

    @AfterClass
    public static void cleanup() {
        try {
            deleteFileOrFolder(Paths.get("blob"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void deleteFileOrFolder(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace(); // replace with more robust error handling
                return TERMINATE;
            }

            @Override public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                    throws IOException {
                if(e!=null)return handleException(e);
                Files.delete(dir);
                return CONTINUE;
            }
        });
    }
}
