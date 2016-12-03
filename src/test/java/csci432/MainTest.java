package csci432;

import joptsimple.OptionSet;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MainTest {
    @Test
    public void getOptionsTest() {
        OptionSet optionSet = Main.getOptions("-save_loc", "sage is cool");
        assertThat(optionSet.valueOf("save_loc").toString(), is(equalTo("sage is cool")));
    }

    @Test(expected = Exception.class)
    public void getOptionsIncorrect() {
        Main.getOptions("-f", "steve");
    }
}
