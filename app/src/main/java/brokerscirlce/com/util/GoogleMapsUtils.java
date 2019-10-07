package brokerscirlce.com.util;

import android.animation.Animator;
import android.location.Location;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import brokerscirlce.com.model.Addresses.AddressesData;

public class GoogleMapsUtils {

    public static void addMarkers(GoogleMap mMap, List<AddressesData> addressesDataList, LatLngBounds latLngBounds) {

        int count = 0;
        mMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int index = 0; index < addressesDataList.size(); index++) {
            LatLng getLatLng = new LatLng(Double.parseDouble(addressesDataList.get(index).getLatitude()), Double.parseDouble(addressesDataList.get(index).getLongitude()));

            if (latLngBounds != null) {
                if (latLngBounds.contains(getLatLng)) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(getLatLng)
                            .title(addressesDataList.get(index).getAreaTitle())
                            .icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360))));
                    marker.setTag(addressesDataList.get(index).getReferenceId());
                }
            } else {
                Marker marker = mMap.addMarker(new MarkerOptions().position(getLatLng)
                        .title(addressesDataList.get(index).getAreaTitle())
                        .icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360))));
                marker.setTag(addressesDataList.get(index).getReferenceId());
                builder.include(getLatLng);
                count++;
            }

        }
        if (count > 0 && latLngBounds == null) {
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 80);
            mMap.animateCamera(cu);
        }
    }

    public static LatLng getPolygonCenterPoint(ArrayList<LatLng> polygonPointsList, LatLngBounds bounds, LatLng latLng) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < polygonPointsList.size(); i++) {
            builder.include(polygonPointsList.get(i));
        }
        bounds = builder.build();
        latLng = bounds.getCenter();

        return latLng;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        Location selected_location = new Location("locationA");
        selected_location.setLatitude(lat1);
        selected_location.setLongitude(lon1);
        Location near_locations = new Location("locationA");
        near_locations.setLatitude(lat2);
        near_locations.setLongitude(lon2);

        double distance = selected_location.distanceTo(near_locations);
        return distance;
    }

    public static LatLngBounds toBounds(LatLng center, double radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    public static void setFadeOutAfterSomeTime(LinearLayout llMapActionContainer) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        llMapActionContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).playOn(llMapActionContainer);

            }
        }, 10000);
    }
}
