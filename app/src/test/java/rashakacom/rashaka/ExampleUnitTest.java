package rashakacom.rashaka;

import org.junit.Test;

import rashakacom.rashaka.fragments.main.home.HomeBasePresenter;
import rashakacom.rashaka.utils.Support;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getBMI() throws Exception {
        double bmi = Support.getBMI("82.4", "167");
        assert(bmi > 0);
        System.out.println("BMI IS - > " + bmi);
    }

    @Test
    public void getProgress() throws Exception {
        int test = HomeBasePresenter.getProgress(11, 1000);
        assert(test > 0);
        System.out.println("Progress IS - > " + test);
    }
}