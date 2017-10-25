package rashakacom.rashaka;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import org.junit.runner.RunWith;

import rashakacom.rashaka.fragments.main.home.exercise.it.LocationMath;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("rashakacom.rashaka", appContext.getPackageName());
    }

    @Test
    public void getDistance() throws Exception {
        double test = LocationMath.getDistanceBetween(new LatLng(49.8460912, 23.9832229), new LatLng(49.849, 23.985));
        assert(test > 0);
        System.out.println("getDistance IS - > " + test);
    }


}
