package brokerscircle.com.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.model.Galleries.GalleriesData;
import brokerscircle.com.R;

public class GalleriesHorizontalRecyclerview_Config {

    private Context mContext;
    private GalleryAdapter mGalleryAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<GalleriesData> galleriesData){
        mContext = context;
        mGalleryAdapter = new GalleryAdapter(galleriesData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mGalleryAdapter);
    }

    class GalleryItemView extends RecyclerView.ViewHolder{

        private RoundedImageView mImage;

        public GalleryItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.single_image_layout, parent, false));

            mImage =  itemView.findViewById(R.id.image);
        }

        public void bind(final GalleriesData galleriesData){

            Log.d("bind", "notifySuccess: Galleries "+galleriesData);
            //Profile image
            if (galleriesData.getSource() == null){
                mImage.setImageResource(R.mipmap.mainscreenbackground);
            }else{
                Picasso.get().load(galleriesData.getSource()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mImage);
            }
        }
    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryItemView>{

        private List<GalleriesData> mGalleryList;

        public GalleryAdapter(List<GalleriesData> mGalleryList) {
            this.mGalleryList = mGalleryList;
        }

        @NonNull
        @Override
        public GalleryItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GalleryItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GalleryItemView holder, int position) {
            holder.bind(mGalleryList.get(position));
        }

        @Override
        public int getItemCount() {
            return mGalleryList.size();
        }
    }

}
