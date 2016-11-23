package csci432;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MainTest {
    @Test
    public void helloWorldTest() {
        assertThat(Main.helloWorld(), is(equalTo("Hello World!")));
    }
}
