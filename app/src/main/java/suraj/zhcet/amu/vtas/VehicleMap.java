package suraj.zhcet.amu.vtas;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.google.android.gms.common.api.GoogleApiClient.*;

public class VehicleMap extends FragmentActivity implements
        OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng1,latLng2;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    Double latitude,logitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_map);
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lng");
        Double latt= Double.parseDouble(lat);
        latitude=changeCoordinates(latt);
        Double lonn=Double.parseDouble(lon);
        logitude=changeCoordinates(lonn);
    }

    private Double changeCoordinates(double in_coords) {
        //Initialize the location.
        double f = in_coords;
        // Get the first two digits by turning f into an integer, then doing an integer divide by 100;
        // firsttowdigits should be 77 at this point.
        int firsttwodigits = ((int)f)/100; //This assumes that f < 10000.
        double nexttwodigits = f - (double) (firsttwodigits*100);
        double theFinalAnswer = (double) (firsttwodigits + nexttwodigits/60.0);
        return theFinalAnswer;
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        mGoogleMap = gMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        //Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng1 = new LatLng(latitude, logitude);
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(latLng1);
            markerOptions1.title("Your Vehicle Location");
            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions1);

            latLng2 = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(latLng2);
            markerOptions2.title("My Location");
            markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions2);

            Polyline polyline=mGoogleMap.addPolyline(new PolylineOptions()
                    .add(
                            new LatLng(latitude,logitude),
                            new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()))

            );


        }

        mLocationRequest = new LocationRequest();
       // mLocationRequest.setInterval(5000); //5 seconds
       // mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng1 = new LatLng(latitude, logitude);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(latLng1);
        markerOptions1.title("Your Vehicle Location");
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mGoogleMap.addMarker(markerOptions1);
        //Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();
        //zoom to current position:
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1,5));
        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        latLng2 = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(latLng2);
        markerOptions2.title("Your Location");
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mGoogleMap.addMarker(markerOptions2);
        //Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();
        //zoom to current position:
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2,5));

        Polyline polyline=mGoogleMap.addPolyline(new PolylineOptions()
        .add(
           new LatLng(latitude,logitude),
           new LatLng(location.getLatitude(),location.getLongitude())));
    }
}
