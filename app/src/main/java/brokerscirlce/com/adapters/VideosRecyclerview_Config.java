package brokerscirlce.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.model.Videos.VideosData;
import brokerscirlce.com.R;

public class VideosRecyclerview_Config {

    private Context mContext;
    private VideoAdapter mVideoAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<VideosData> videosData){
        mContext = context;
        mVideoAdapter = new VideoAdapter(videosData);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.setAdapter(mVideoAdapter);
    }

    class VideoItemView extends RecyclerView.ViewHolder{

        private ImageView mImage;

        public VideoItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.single_image_layout, parent, false));

            mImage =  itemView.findViewById(R.id.image);
        }

        public void bind(final VideosData videosData){

            //Profile image
            if (videosData.getThumbnail() == null){
                mImage.setImageResource(R.mipmap.mainscreenbackground);
            }else{
                Picasso.get().load(videosData.getThumbnail()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mImage);
            }
        }
    }

    class VideoAdapter extends RecyclerView.Adapter<VideoItemView>{

        private List<VideosData> mVideoList;

        public VideoAdapter(List<VideosData> mVideoList) {
            this.mVideoList = mVideoList;
        }

        @NonNull
        @Override
        public VideoItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VideoItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull VideoItemView holder, int position) {
            holder.bind(mVideoList.get(position));
        }

        @Override
        public int getItemCount() {
            return mVideoList.size();
        }
    }
}
