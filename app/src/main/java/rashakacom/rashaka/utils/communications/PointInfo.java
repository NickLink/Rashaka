package rashakacom.rashaka.utils.communications;

import com.google.android.gms.maps.model.LatLng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 19.09.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointInfo{

    private long position;
    private long timestamp;
    private String description;
    private LatLng coordinates;

}