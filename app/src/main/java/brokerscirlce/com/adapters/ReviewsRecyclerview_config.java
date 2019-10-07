package brokerscirlce.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.Activities.Brokers.BrokerProfileActivity;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Reviews.ReviewData;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsRecyclerview_config {

    private Context mContext;
    private ReviewCommentsAdapter mReviewCommentsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<ReviewData> reviewData){
        mContext = context;
        mReviewCommentsAdapter = new ReviewCommentsAdapter(reviewData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mReviewCommentsAdapter);
    }

    class ReviewCommentsItemView extends RecyclerView.ViewHolder{

        private CircleImageView mProfileImage;
        private TextView mCommentText;
        private RatingBar mRating;

        private String key;

        public ReviewCommentsItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.review_comment_row_single, parent, false));

            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mCommentText =  itemView.findViewById(R.id.tv_review_comment);
            mRating =  itemView.findViewById(R.id.rating);
        }

        public void bind(final ReviewData reviewData){

            //Profile image
            new BrokersDatabaseHelper().readBrokersDetail(new BrokersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<BrokersData> brokersUtils) {
                    if (brokersUtils.get(0).getPicture() == null){
                        mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
                    }else{
                        Picasso.get().load(brokersUtils.get(0).getPicture()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
                    }

                    mRating.setRating((brokersUtils.get(0).getAverageRating() == null)? (float) 0 : (float) brokersUtils.get(0).getAverageRating());
                    LayerDrawable stars = (LayerDrawable) mRating.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                    mProfileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, BrokerProfileActivity.class);
                            intent.putExtra("brokerid", brokersUtils.get(0).getId());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }, mContext, reviewData.getCreatedByUserId());


            //Review Comment Text
            if (reviewData.getNote() == null){
                mCommentText.setVisibility(View.GONE);
            }else {
                mCommentText.setText(reviewData.getNote());
            }
        }
    }

    class ReviewCommentsAdapter extends RecyclerView.Adapter<ReviewCommentsItemView>{

        private List<ReviewData> mReviewList;

        public ReviewCommentsAdapter(List<ReviewData> mReviewList) {
            this.mReviewList = mReviewList;
        }

        @NonNull
        @Override
        public ReviewCommentsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ReviewCommentsItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewCommentsItemView holder, int position) {
            holder.bind(mReviewList.get(position));
        }

        @Override
        public int getItemCount() {
            return mReviewList.size();
        }
    }

}
