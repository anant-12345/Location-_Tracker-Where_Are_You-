package com.example.whereareyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager ;
    LocationListener locationListener ;
    TextView latitude ;
    TextView longitude ;
    TextView altitude ;
    TextView speed ;
    TextView accuracy ;
    TextView country ;
    TextView city ;
    TextView place ;
    TextView state ;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
            if( ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )
                    == PackageManager.PERMISSION_GRANTED ) {
                locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                        0, 0, locationListener ) ;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE ) ;
        latitude = findViewById(R.id.latitudetextView) ;
        longitude = findViewById(R.id.longitudetextView) ;
        altitude = findViewById(R.id.altitudetextView) ;
        speed = findViewById(R.id.speedtextView) ;
        accuracy = findViewById(R.id.accuracytextView2) ;
        country = findViewById(R.id.countrytextView) ;
        state = findViewById(R.id.statetextView) ;
        city = findViewById(R.id.citytextView) ;
        place = findViewById(R.id.placetextView) ;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault() ) ;

                try {
                    List<Address> listOfAddress = geocoder.getFromLocation( location.getLatitude(),
                            location.getLongitude(), 1 ) ;
                    if( listOfAddress != null && listOfAddress.size() > 0 ) {

                        String s ;
                        double val ;

                        val = listOfAddress.get(0).getLatitude() ;
                        s = String.format( "%.2f", Math.abs(val) ) ;
                        if( val < 0 ){
                            latitude.setText( "Latitude : " + s + "° S" );
                        }
                        else{
                            latitude.setText( "Latitude : " + s + "° N" );
                        }

                        val = listOfAddress.get(0).getLongitude() ;
                        s = String.format( "%.2f", Math.abs(val) ) ;
                        if( val < 0 ){
                            longitude.setText( "Longitude : " + s + "° W" );
                        }
                        else{
                            longitude.setText( "Longitude : " + s + "° E" );
                        }

                        val = location.getAltitude() ;
                        s = String.format( "%.2f", Math.abs(val) ) ;
                        altitude.setText( "Altitude : " + s  );

                        val = location.getSpeed() ;
                        s = String.format( "%.2f", Math.abs(val) ) ;
                        speed.setText( "Speed : " + s  );

                        val = location.getAccuracy() ;
                        s = String.format( "%.2f", Math.abs(val) ) ;
                        accuracy.setText( "Accuracy : " + s + "%" );

                        s = null ;
                        s = listOfAddress.get(0).getCountryName() ;
                        if( s != null ){
                            country.setText( "Country : " + s );
                        }

                        s = null ;
                        s = listOfAddress.get(0).getAdminArea() ;
                        if( s != null ){
                            state.setText( "State : " + s );
                        }

                        s = null ;
                        s = listOfAddress.get(0).getSubAdminArea() ;
                        if( s != null ){
                            city.setText( "City : " + s );
                        }

                        s = null ;
                        s = listOfAddress.get(0).getLocality() ;
                        if( s != null ){
                            place.setText( "Place : " + s );
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        } ;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 1 );
        }
        else {
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                    0, 0, locationListener ) ;
        }

    }
}








