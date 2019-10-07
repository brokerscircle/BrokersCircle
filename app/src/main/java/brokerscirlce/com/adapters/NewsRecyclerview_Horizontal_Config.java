package brokerscirlce.com.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.model.News.NewsData;
import brokerscirlce.com.R;

public class NewsRecyclerview_Horizontal_Config {

    private Context mContext;
    private NewsAdapter mNewsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<NewsData> newsData){
        mContext = context;
        mNewsAdapter = new NewsAdapter(newsData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.hasFixedSize();
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mNewsAdapter);
    }

    class NewItemView extends RecyclerView.ViewHolder{

        private RoundedImageView mImage;
        private TextView mTitle, mDescription, mtime;
        private ToggleButton mFavoriteBTN;

        public NewItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.news_item_row_single_small, parent, false));

            mImage =  itemView.findViewById(R.id.post_image);
            mTitle =  itemView.findViewById(R.id.tv_title);
            mDescription =  itemView.findViewById(R.id.tv_description);
            mtime =  itemView.findViewById(R.id.tv_time);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
        }

        public void bind(final NewsData newsData){

            //Profile image
            if (newsData.getThumbnail() == null){
                mImage.setImageResource(R.mipmap.mainscreenbackground);
            }else{
                Picasso.get().load(newsData.getThumbnail()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mImage);
            }
            //other properties
            mTitle.setText(newsData.getTitle());

            if (newsData.getDescription() != null){
                mDescription.setText(Html.fromHtml(newsData.getDescription()));
            }else{
                mDescription.setVisibility(View.GONE);
            }

            Log.d("desc", "bind: "+newsData.getDescription());


            mtime.setText(newsData.getCreatedAt());

            //Action onClickListners
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            //End layout filling
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewItemView>{

        private List<NewsData> mNewsDataList;

        public NewsAdapter(List<NewsData> mNewsDataList) {
            this.mNewsDataList = mNewsDataList;
        }

        @NonNull
        @Override
        public NewItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NewItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NewItemView holder, int position) {
            holder.bind(mNewsDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mNewsDataList.size();
        }
    }

}
