package brokerscircle.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.Activities.MapDetailActivity;
import brokerscircle.com.model.Map.MapTypes.MapTypesData;
import brokerscircle.com.R;

public class MapTypesRecyclerview_Config {

    private Context mContext;
    private MapsAdapter mMapsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<MapTypesData> mapsData){
        mContext = context;
        mMapsAdapter = new MapsAdapter(mapsData);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerView.setAdapter(mMapsAdapter);
    }

    class MapsItemView extends RecyclerView.ViewHolder{
        private RoundedImageView mImage;
        private TextView mHeading;
        //private RelativeLayout mLayout;

        public MapsItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.map_grid_single_item, parent, false));

            mImage =  itemView.findViewById(R.id.image);
            mHeading =  itemView.findViewById(R.id.heading);
        }

        public void bind(final MapTypesData mapsData){

            //Profile image
            if (mapsData.getThumbnail() == null){
                mImage.setImageResource(R.mipmap.background);
            }else{
                Picasso.get().load(mapsData.getThumbnail().toString())
                        .placeholder(R.mipmap.background)
                        .fit()
                        .into(mImage);
            }

            //Name
            if (mapsData.getTitle() == null ){
                mHeading.setVisibility(View.GONE);
            }else {
                mHeading.setText(mapsData.getTitle());
            }

            mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MapDetailActivity.class);
                    intent.putExtra("maptypeid", mapsData.getId());
                    intent.putExtra("maptitle", mapsData.getTitle());
                    mContext.startActivity(intent);
                }
            });

        }
    }

    class MapsAdapter extends RecyclerView.Adapter<MapsItemView>{

        private List<MapTypesData> mMapsList;
        //private List<String> mKeys;

        public MapsAdapter(List<MapTypesData> mMapsList) {
            this.mMapsList = mMapsList;
        }

        @NonNull
        @Override
        public MapsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MapsItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MapsItemView holder, int position) {
            holder.bind(mMapsList.get(position));
        }

        @Override
        public int getItemCount() {
            return mMapsList.size();
        }
    }

}
