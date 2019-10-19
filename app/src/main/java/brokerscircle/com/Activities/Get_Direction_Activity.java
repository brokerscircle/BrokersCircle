package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.R;

public class Get_Direction_Activity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    ArrayList markerPoints= new ArrayList();
    private LocationManager locationManager;
    private static final int MY_PERMISSIONS_LOCATION = 100;

    private ImageView btn_chat;
    private RoundedImageView btn_profile;
    private LinearLayout mBackButton;

    LatLng targetPositionLatLng = null;
    LatLng mCurrentPositionLatLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_direction_activity);

        //get Target Position
        targetPositionLatLng = getIntent().getExtras().getParcelable("targetPosition");

        initAppBar();

        initMapsControls();
    }

    private void initAppBar() {
        btn_chat = findViewById(R.id.btn_chat);
        btn_profile = findViewById(R.id.btn_profile);
        mBackButton = findViewById(R.id.btn_back);

        //back listener
        mBackButton.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_profile.setOnClickListener(this);
    }

    private void initMapsControls() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setOnMarkerClickListener(this);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (mMap != null) {
                    // get  addresses
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    int permissionCheck = ContextCompat.checkSelfPermission(Get_Direction_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Get_Direction_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_LOCATION);
                    }else {
                        //get location and add marker
                        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        mCurrentPositionLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        //Add Marker
                        Marker currentMarker = mMap.addMarker(new MarkerOptions()
                                .position(mCurrentPositionLatLng)
                                .snippet("You"));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(mCurrentPositionLatLng).zoom(10).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//                        //Taget Marker
                        if (targetPositionLatLng != null){
                            Marker targetMarker = mMap.addMarker(new MarkerOptions()
                                    .position(targetPositionLatLng)
                                    .snippet("Target"));
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_chat:
                intent = new Intent(Get_Direction_Activity.this, ChatActivity.class);
                startActivity(intent);
                //TODO Listeners
                break;
            case R.id.btn_profile:
                //TODO Listeners
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    @SuppressLint("MissingPermission")
                    Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(current).snippet("You"));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(14).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }else{
                    Toast.makeText(Get_Direction_Activity.this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }
}
