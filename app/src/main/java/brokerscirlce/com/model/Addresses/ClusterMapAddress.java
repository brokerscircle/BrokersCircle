package brokerscirlce.com.model.Addresses;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMapAddress implements ClusterItem {

    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private final String mTag;

    public ClusterMapAddress(LatLng mPosition, String mTitle, String mSnippet, String mTag) {
        this.mPosition = mPosition;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
        this.mTag = mTag;
    }

    @Override
    public LatLng getPosition() {  // 1
        return mPosition;
    }

    @Override
    public String getTitle() {  // 2
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public String getTag() {
        return mTag;
    }
}
