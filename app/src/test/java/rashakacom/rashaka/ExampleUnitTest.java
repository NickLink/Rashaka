package rashakacom.rashaka;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import rashakacom.rashaka.fragments.main.home.HomeBasePresenter;
import rashakacom.rashaka.fragments.main.home.exercise.it.LocationMath;
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

    @Test
    public void getDistance() throws Exception {
        double test = LocationMath.getDistanceBetween(new LatLng(49.8460912, 23.9832229), new LatLng(49.849, 23.985));
        assert(test > 0);
        System.out.println("getDistance IS - > " + test);
    }
}