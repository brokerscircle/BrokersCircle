package brokerscirlce.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.Activities.Brokers.BrokerProfileActivity;
import brokerscirlce.com.Activities.Estates.EstateDetailActivity;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.PastDealing.PastDealingData;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class PastDealingRecyclerview_Config {

    private Context mContext;
    private PastDealingAdapter mPastDealingAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<PastDealingData> pastDealingData){
        mContext = context;
        mPastDealingAdapter = new PastDealingAdapter(pastDealingData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mPastDealingAdapter);
    }

    class PastDealingItemView extends RecyclerView.ViewHolder{

        private CircleImageView mListerBrokerProfileImage, mLinkerBrokerProfileImage;
        private TextView mListerBrokerName, mListerBrokerEstateName, mRatingTV, mLinkerBrokerName, mLinkerBrokerEstateName;
        private RatingBar mRating;


        public PastDealingItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.past_dealing_row_item_single, parent, false));

            mListerBrokerProfileImage =  itemView.findViewById(R.id.broker_profile_image);
            mListerBrokerName =  itemView.findViewById(R.id.tv_broker_name);
            mListerBrokerEstateName =  itemView.findViewById(R.id.tv_estate_name);
            mRatingTV =  itemView.findViewById(R.id.rating);
            mRating =  itemView.findViewById(R.id.rating_stars);
            mLinkerBrokerProfileImage =  itemView.findViewById(R.id.with_broker_profile_image);
            mLinkerBrokerName =  itemView.findViewById(R.id.tv_with_broker_name);
            mLinkerBrokerEstateName =  itemView.findViewById(R.id.tv_with_estate_name);
        }

        public void bind(final PastDealingData pastDealingData){

            //Getting Lister LoginData
            new BrokersDatabaseHelper().readBrokersDetail(new BrokersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<BrokersData> brokersUtils) {
                    //Lister Profile image
                    if (brokersUtils.get(0).getPicture() == null){
                        mListerBrokerProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
                    }else{
                        Picasso.get().load(brokersUtils.get(0).getPicture()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mListerBrokerProfileImage);
                    }
                    //Lister Name
                    mListerBrokerName.setText(brokersUtils.get(0).getTitle());
                    //Lister Estate Name
                    if (brokersUtils.get(0).getRealEstateName() == null){
                        mListerBrokerEstateName.setVisibility(View.GONE);
                    }else {
                        mListerBrokerEstateName.setText(brokersUtils.get(0).getRealEstateName());
                    }

                    // Lister Click listner
                    mListerBrokerProfileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, BrokerProfileActivity.class);
                            intent.putExtra("brokerid",pastDealingData.getBrokerPropertyListerId());
                            mContext.startActivity(intent);
                        }
                    });

                    //Lister Estate Click listner
                    mListerBrokerEstateName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, EstateDetailActivity.class);
                            intent.putExtra("estateid",brokersUtils.get(0).getCreatedByCompId());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }, mContext,pastDealingData.getBrokerPropertyListerId());

            //Rating count
            mRatingTV.setText(String.valueOf(Float.parseFloat(pastDealingData.getRating())));
            //Rating Start
            mRating.setRating(Float.parseFloat(pastDealingData.getRating()));

            //Getting Lister LoginData
            new BrokersDatabaseHelper().readBrokersDetail(new BrokersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<BrokersData> brokersUtils) {

                    //Linker Profile image
                    if (brokersUtils.get(0).getPicture() == null){
                        mLinkerBrokerProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
                    }else{
                        Picasso.get().load(brokersUtils.get(0).getPicture()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mLinkerBrokerProfileImage);
                    }
                    //Linker Name
                    mLinkerBrokerName.setText(brokersUtils.get(0).getTitle());
                    //Linker Estate Name
                    if (brokersUtils.get(0).getRealEstateName() == null){
                        mLinkerBrokerEstateName.setVisibility(View.GONE);
                    }else {
                        mLinkerBrokerEstateName.setText(brokersUtils.get(0).getRealEstateName());
                    }

                    // Linker Click listner
                    mLinkerBrokerProfileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, BrokerProfileActivity.class);
                            intent.putExtra("brokerid",pastDealingData.getBrokerPropertyLinkerId());
                            mContext.startActivity(intent);
                        }
                    });

                    //Linker Estate Click listner
                    mLinkerBrokerEstateName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, EstateDetailActivity.class);
                            intent.putExtra("estateid",brokersUtils.get(0).getCreatedByCompId());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }, mContext,pastDealingData.getBrokerPropertyLinkerId());
        }
    }

    class PastDealingAdapter extends RecyclerView.Adapter<PastDealingItemView>{

        private List<PastDealingData> mPastDealingList;

        public PastDealingAdapter(List<PastDealingData> mPastDealingList) {
            this.mPastDealingList = mPastDealingList;
        }

        @NonNull
        @Override
        public PastDealingItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PastDealingItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PastDealingItemView holder, int position) {
            holder.bind(mPastDealingList.get(position));
        }

        @Override
        public int getItemCount() {
            return mPastDealingList.size();
        }
    }

}
