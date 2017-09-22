package rashakacom.rashaka.utils.communications;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class FusedLocationService implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "FusedLocationService";
    private Activity locationActivity;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long ONE_MIN = 1000 * 60;
    private static final long REFRESH_TIME = ONE_MIN * 5;
    private static final float MINIMUM_ACCURACY = 50.0f;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    FusedLocationReceiver locationReceiver = null;

    public FusedLocationService(Activity locationActivity, FusedLocationReceiver locationReceiver) {
        Log.e(TAG, "FusedLocationService");
        this.locationReceiver = locationReceiver;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.locationActivity = locationActivity;

        googleApiClient = new GoogleApiClient.Builder(locationActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (googleApiClient != null) {
            googleApiClient.connect();
            Log.e(TAG, "FusedLocationService googleApiClient.connect()");
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Toast.makeText(locationActivity, "WE GET -> I'm connected now ", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "I'm connected now");
        if (ActivityCompat.checkSelfPermission(locationActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(locationActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "return");
            return;
        }
        fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        Location currentLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
        if (currentLocation != null) { // && currentLocation.getTime() > REFRESH_TIME
            Toast.makeText(locationActivity, "WE GET -> currentLocation != null", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "currentLocation != null");
            location = currentLocation;
            locationReceiver.onLocationChanged(this.location);
        } else {
            Log.e(TAG, "requestLocationUpdates");
            Toast.makeText(locationActivity, "WE GET -> requestLocationUpdates", Toast.LENGTH_SHORT).show();
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

            //change the time of location updates
            locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL);

            //restart location updates with the new interval
            fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);

            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


//            // Schedule a Thread to unregister location listeners
//            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
//                @Override
//                public void run() {
//                    fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
//                            FusedLocationService.this);
//                }
//            }, ONE_MIN, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(locationActivity, "WE GET -> Lon " + location.getLongitude()
                        + " Lat " + location.getLatitude()
                        + " Speed " + location.getSpeed()
                , Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Location is changed!");
        //if the existing location is empty or
        //the current location accuracy is greater than existing accuracy
        //then store the current location
        if (null == this.location || location.getAccuracy() < this.location.getAccuracy()) {
            Toast.makeText(locationActivity, "WE GET -> NO_ACCURACY ", Toast.LENGTH_SHORT).show();
            this.location = location;
            // let's inform my client class through the receiver
            locationReceiver.onLocationChanged(this.location);
            //if the accuracy is not better, remove all location updates for this listener
            if (this.location.getAccuracy() < MINIMUM_ACCURACY) {
                Toast.makeText(locationActivity, "WE GET -> MINIMUM_ACCURACY ", Toast.LENGTH_SHORT).show();
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient, this);
            }
        }
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed!-> " + connectionResult.getErrorMessage());
    }

}