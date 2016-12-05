package csci432.processor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import csci432.processor.SigmaDeltaFilter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by alex on 12/1/16.
 */
public class SigmaDeltaFilterTest {
    SigmaDeltaFilter f;

    @Before
    public void setup() {
        f = new SigmaDeltaFilter();
    }

    @Test
    @Ignore
    public void testFilter() {
        BufferedImage a, b, c;
        a = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
        b = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                a.setRGB(j, i, -16777216);
            }
        }
        a = f.filter(a);
        assertThat(a.getRGB(150, 150), equalTo(-16777216));
        assertThat(f.background.getRGB(150, 150), equalTo(-16777216));
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                a.setRGB(j, i, -16577216);
                b.setRGB(j, i, -16777216);
            }
        }
        Color aColor = new Color(-16577216);
        Color subColor = new Color(-16777216);
        int aRed = aColor.getRed();
        int aGreen = aColor.getGreen();
        int aBlue = aColor.getBlue();
        int bRed = subColor.getRed();
        int bGreen = subColor.getGreen();
        int bBlue = subColor.getBlue();
        aRed = Math.abs(aRed - bRed);
        aGreen = Math.abs(aGreen - bGreen);
        aBlue = Math.abs(aBlue - bBlue);
        int n = (new Color(aRed, aGreen, aBlue)).getRGB();
        c = f.filter(a);
        assertThat(f.background.getRGB(150,150), equalTo(-16777216));
//        assertThat(c.getRGB(150, 150), equalTo(n));
        for (int i = 0; i < 22; i++) {
            c = f.filter(a);
        }
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                b.setRGB(j, i, -16577216);
            }
        }
//        assertThat(c.getRGB(150, 150), equalTo(b.getRGB(150, 150)));
    }

    @Test
    @Ignore
    public void testRefreshBackground() {
        BufferedImage a, b;
        int w = 200;
        int h = 200;
        a = new BufferedImage(h, h, BufferedImage.TYPE_4BYTE_ABGR);
        b = new BufferedImage(h, h, BufferedImage.TYPE_4BYTE_ABGR);
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                a.setRGB(x, y, -16777216);
                b.setRGB(x, y, -16777216);
                f.curCount = new int[w][h];
                f.curPix = new int[w][h];
                f.backCount = new int[w][h];
                f.curPix[x][y] = -16777216;
            }
        }
        f.background = b;
        f.refreshBackground(a);
        assertThat(a.getRGB(150, 150), equalTo(f.background.getRGB(150,150)));
//        assertThat(f.backCount[150][150], equalTo(1));
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                a.setRGB(x, y, -16577216);
            }
        }
        for (int i = 0; i <= f.backgroundThresh; i++) {
            f.refreshBackground(a);
        }
        assertThat(b, equalTo(f.background));
        assertThat(f.curCount[150][150], equalTo(f.backgroundThresh));
        f.refreshBackground(a);
        assertThat(a.getRGB(150, 150), equalTo(f.background.getRGB(150, 150)));
//        assertThat(f.backCount, equalTo(0));
    }

    @Test
    @Ignore
    public void testColorMatch() {
        Color a, b;
        a = new Color(-16777216);
        assertThat(f.colorMatch(a, a), is(true));
        a = new Color(100, 100, 0);
        b = new Color(91, 91, 0);
        assertThat(f.colorMatch(a, b), is(true));
        a = new Color(100, 100, 200);
        b = new Color(91, 91, 0);
        assertThat(f.colorMatch(a, b), is(false));
    }

    @Test
    @Ignore
    public void testFilterImageSubtract() {
        BufferedImage a, b, c;
        int rgb;
        Color aColor, bColor;
        a = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
        b = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
        c = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
        aColor = new Color(-16577216);
        bColor = new Color(-16777216);
        rgb = f.subColor(aColor, bColor);
        for (int y = 0; y < a.getHeight(); y++) {
            for (int x = 0; x < a.getWidth(); x++) {
                a.setRGB(x, y, -16577216);
                b.setRGB(x, y, -16777216);
                c.setRGB(x, y, rgb);
            }
        }
        f.background = b;
        b = f.filterImageSubtract(a);
        assertThat(b.getRGB(150, 150), equalTo(c.getRGB(150, 150)));
    }

    @Test
    @Ignore
    public void testSubColor() {
        Color a, b, c;
        int rgb = -16777216;
        a = new Color(rgb);
        assertThat(f.subColor(a, a), equalTo(rgb));
        a = new Color(100, 100, 0);
        b = new Color(91, 91, 0);
        c = new Color(9, 9, 0);
        rgb = c.getRGB();
        assertThat(f.subColor(a, b), equalTo(rgb));
        a = new Color(100, 100, 0);
        b = new Color(91, 91, 0);
        c = new Color(9, 9, 0);
        rgb = c.getRGB();
        System.out.println("rgb: " + rgb + ", calculated: ");
        assertThat(f.subColor(b, a), equalTo(rgb));
    }
}
