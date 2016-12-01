package csci432;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Created by alex on 11/30/16.
 */
public class SigmaDeltaFilterTest  {
    SigmaDeltaFilter f;

    @Before
    public void setup() {
        f = new SigmaDeltaFilter();
    }

    @Test
    public void testRefreshBackground()  {
        f.refreshBackground(new BufferedImage(300, 300, BufferedImage.TYPE_BYTE_BINARY));
    }

    @Test
    public void testSetBackground() {
        BufferedImage a = new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);
        f.setBackground(a);
        f.filter(a);
        f.setBackground(a);
    }

    @Test
    public void testFilter() {
        BufferedImage a = new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage b = f.filter(a);
        assertThat(b,is(equalTo(a)));

        for(int i = 0; i < 22; i++){
            b = f.filter(a);
        }
        assertThat(b,is(equalTo(a)));
    }

    @Test
    public void testFilterImage() {
        f.filterImage(new BufferedImage(300, 300, BufferedImage.TYPE_BYTE_BINARY));
    }

}
