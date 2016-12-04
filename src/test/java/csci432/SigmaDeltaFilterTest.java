package csci432;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Created by alex on 12/1/16.
 */
public class SigmaDeltaFilterTest  {
    SigmaDeltaFilter f;

    @Before
    public void setup() {
        f = new SigmaDeltaFilter();
    }

    @Test
    public void testFilter() {
        BufferedImage a = new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage b = a;    //will be calculated background
        BufferedImage c = a;    //save result here
        BufferedImage s = a;    //will be calculated result

        for(int i = 0; i < a.getHeight(); i++) {
            for(int j = 0; j < a.getWidth(); j++) {
                a.setRGB(j, i, 10);
                b.setRGB(j, i, 10);
            }
        }
        c = f.filter(a);
        assertThat(f.background, equalTo(b));
        assertThat(c,is(equalTo(a)));

        for(int i = 1; i < f.initBackground ; i++){
            int aPixel = a.getRGB(0,0) + i + 1;
            int bPixel = b.getRGB(0, 0) + i;
            for(int y = 0; y < a.getHeight(); y++) {
                for(int x = 0; x < a.getWidth(); x++) {
                    a.setRGB(x, y, aPixel);
                    b.setRGB(x, y, bPixel);
                    s.setRGB(x, y, aPixel - bPixel);
                }
            }
            c = f.filter(a);
            assertThat(b, equalTo(f.background));
            assertThat(c, equalTo(a));
        }

        int i = f.initBackground;
        int aPixel = a.getRGB(0,0) + i + 1;
        int bPixel = b.getRGB(0, 0) + i;
        for(int y = 0; y < a.getHeight(); y++) {
            for(int x = 0; x < a.getWidth(); x++) {
                a.setRGB(x, y, aPixel);
                b.setRGB(x, y, bPixel);
            }
        }
        c = f.filter(a);
        assertThat(b, equalTo(f.background));
        assertThat(c,is(equalTo(s)));
    }

    @Test
    public void testRefreshBackground()  {
        BufferedImage a = new BufferedImage(300, 300, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage b = a;
        BufferedImage c = a;
        for (int i = 0; i < a.getHeight(); i++){
            for (int j = 0; j < a.getWidth(); j++){
                a.setRGB(j, i, 4);
                b.setRGB(j, i, 8);
                c.setRGB(j, i, 6);
            }
        }
        f.background = a;
        f.refreshBackground(b);
        assertThat(f.background, equalTo(c));
    }

    @Test
    public void testFilterImageSubtract() {
        BufferedImage a = new BufferedImage(300, 300, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage b = a;
        BufferedImage c = a;
        BufferedImage d = a;
        for (int i = 0; i < a.getHeight(); i++){
            for (int j = 0; j < a.getWidth(); j++){
                a.setRGB(j, i, 10);
                b.setRGB(j, i, 8);
                c.setRGB(j, i, 2);
            }
        }
        //check to see if it works subtracting lower from higher
        f.background = a;
        d = f.filterImageSubtract(b);
        assertThat(d, equalTo(c));

        //check to see if it works subtracting higher from lower
        f.background = b;
        d = f.filterImageSubtract(a);
        assertThat(d, equalTo(c));
    }
}
