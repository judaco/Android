package com.bikebeacon.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juda.bbexample.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

/**
 * GoogleApiClient.ConnectionCallbacks provides callbacks that are triggered when the client is connected (onConnected())
 * or temporarily disconnected (onConnectionSuspended()) from the service.
 *
 * GoogleApiClient.OnConnectionFailedListener provides a callback method (onConnectionFailed())
 * that is triggered when an attempt to connect the client to the service results in a failure.
 *
 * GoogleMap.OnMarkerClickListener defines the onMarkerClick() which is called when a marker is clicked or tapped.
 *
 * LocationListener defines the onLocationChanged() which is called when a user’s location changes.
 * This method is only called if the LocationListener has been registered.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, com.google.android.gms.location.LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleApiClient apiClient;
    private Location lastLocation;

    private LocationRequest locationRequest;
    private boolean locationUpdate;

    private double mLatitude;
    private double mLongitude;

    private GoogleMap mMap;
    private FloatingActionButton settings;
    private TextView speed;
    private TextView speedValue;
    private TextView latitude;
    private TextView longitude;
    private TextView latValue;
    private TextView lonValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
         //Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final SupportMapFragment fragment = SupportMapFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.map, fragment).commit();
                    fragment.getMapAsync(MapsActivity.this);
                }catch (Exception ignored){

                }
            }
        });

        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);

        settings = (FloatingActionButton) findViewById(R.id.settings);
        speed = (TextView) findViewById(R.id.speed);
        speedValue = (TextView) findViewById(R.id.speed_value);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        latValue = (TextView) findViewById(R.id.lat_value);
        lonValue = (TextView) findViewById(R.id.lon_value);

        NumberFormat number = NumberFormat.getNumberInstance();
        latValue.setText(number.format(mLatitude));
        lonValue.setText(number.format(mLongitude));

        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, SettingsActivity.class));
            }
        });
    }

    /**
     * Request a location permission
     *
     * @param permissionType
     * @param requestCode
     */
    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this, permissionType);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissionType}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    /**
     * Start the update request regarding the user's location.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                locationUpdate = true;
                startLocationUpdates();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    /**
     * Stop location update request.
     */
    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
    }

    /**
     * Restart the location update request.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (apiClient.isConnected() && !locationUpdate) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (apiClient != null && apiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
            apiClient.disconnect();
        }
    }

    /**
     * Called when the Map is ready to be used.
     * The method creates a marker and adds it to the Map.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng jerusalem = new LatLng(31.765232, 35.213613);
        mMap.addMarker(new MarkerOptions().position(jerusalem).title("Marker in Jerusalem"));
        CameraPosition position = CameraPosition.builder().target(jerusalem).zoom(13).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

        //Enable the zoom controls on the map
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Declare the MapActivity as the callback triggered when the user clicks on the marker
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if (lastLocation != null) {
            placeMarkerOnMap(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
        }

        mLatitude = lastLocation.getLatitude();
        mLongitude = lastLocation.getLongitude();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        setUpMap();
        if (locationUpdate) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * Check if the App has been granted the ACCESS_FINE_LOCATION permission.
     * If it has not, then request it from the user.
     */
    public void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        //Enable the 'current location' layer (the light blue dot) to indicate the user's location,
        //and also add a button (at the top right side), which centers the map on the user's location.
        mMap.setMyLocationEnabled(true);
        //Set the Map type
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //Determine the availability of location data on the device.
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi
                .getLocationAvailability(apiClient);
        if (locationAvailability != null && locationAvailability.isLocationAvailable()) {
            //Give the most recent current location available
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            if (lastLocation != null) {
                LatLng currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                placeMarkerOnMap(currentLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
            }
        }

        float zoomLevel = 13;
        LatLng latLng = new LatLng(31.765232, 35.213613);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
    }

    public class MapTask extends AsyncTask<LatLng, Void, Marker> {

        @Override
        protected Marker doInBackground(LatLng... latLngs) {
            LatLng location = new LatLng(30.000, 32.00000);
            MarkerOptions markerOptions = new MarkerOptions().position(location).title("new location");
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                    (getResources(), R.mipmap.user_location)));
            String string = getAddress(location);
            markerOptions.title(string);
            return mMap.addMarker(markerOptions);
        }

        @Override
        protected void onPostExecute(Marker marker) {
            super.onPostExecute(marker);
        }
    }

    /**
     * Place a marker at a particular point on the map's surface.
     *
     * @param location
     */
    protected void placeMarkerOnMap(final LatLng location) {
        MapTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
//        //Create a MarkerOptions object and sets the user’s current location as the position for the marker.
//        MarkerOptions markerOptions = new MarkerOptions().position(location).title("new position");
//        //Create a unique marker with a custom icon.
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
//                (getResources(), R.mipmap.user_location)));
//
//        //Add an address as a marker title.
//        String string = getAddress(location);
//        markerOptions.title(string);
//
//        //Add the marker to the Map
//        mMap.addMarker(markerOptions);
    }

    /**
     * Show the address of the location when the user clicks on the marker.
     *
     * Geocoder - a class in Google, which takes the coordinates of a location and returns
     * a readable address and vice versa.
     *
     * @param latLng
     * @return String
     */
    private String getAddress(LatLng latLng) {
        //Create a Geocoder object to turn a latitude and longitude coordinate into an address.
        Geocoder geocoder = new Geocoder(this);
        String addressText = "";
        List<Address> addresses = null;
        Address address = null;
        try {
            //Ask the geocoder to get the address from the location passed to the method.
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    addressText += (i == 0) ? address.getAddressLine(i) : ("\n" +
                            address.getAddressLine(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressText;
    }

    /**
     * Know your user's location at all times and receive his updated location continuously.
     */
    protected void startLocationUpdates() {
        //Request for permission granted or not.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        //Request for location updates.
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, this);
    }

    /**
     * Create an instance of LocationRequest, add it to an instance of LocationSettingsRequest.Builder
     * and retrieve and handle any changes to be made based on the current state of the user’s location settings.
     */
    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        //Specify the rate at which your App will like to receive updates.
        locationRequest.setInterval(10000);
        //Specify the fastest rate at which the App can handle updates.
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Check the state of the user’s location settings.
        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(apiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        locationUpdate = true;
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }


}
