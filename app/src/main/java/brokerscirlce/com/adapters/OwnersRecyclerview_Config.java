package brokerscirlce.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.Activities.Estates.EstateDetailActivity;
import brokerscirlce.com.api_helpers.RealEstateDatabaseHelper;
import brokerscirlce.com.model.Owner.OwnerData;
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class OwnersRecyclerview_Config {

    private Context mContext;
    private OwnersAdapter mOwnersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<OwnerData> ownerData){
        mContext = context;
        mOwnersAdapter = new OwnersAdapter(ownerData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mOwnersAdapter);
    }

    class OwnersItemView extends RecyclerView.ViewHolder{

        private CircleImageView mProfileImage;
        private ImageView mOnlineStatus;
        private ToggleButton mFavoriteBTN;
        private TextView mNameTV, mEstateNameTV;

        public OwnersItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.brokers_item_row_single, parent, false));

            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mOnlineStatus =  itemView.findViewById(R.id.online_icon);
            mNameTV =  itemView.findViewById(R.id.tv_name);
            mEstateNameTV =  itemView.findViewById(R.id.tv_estate);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
        }

        public void bind(final OwnerData ownerData){

            //Profile image
            if (ownerData.getPicture()== null){
                mProfileImage.setImageResource(R.drawable.ic_estate_owner_icon_colored);
            }else{
                Picasso.get().load(ownerData.getPicture().toString()).placeholder(R.drawable.ic_estate_owner_icon_colored).centerCrop().fit().into(mProfileImage);
            }
            //Status Online
//            if (ownersEstateUtils.isOnlineStatus()){
//                mOnlineStatus.setVisibility(View.VISIBLE);
//            }else {
//                mOnlineStatus.setVisibility(View.GONE);
//            }
            //Name
            mNameTV.setText(ownerData.getTitle());

            //EstateName
            new RealEstateDatabaseHelper().readEstateDetail(new RealEstateDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<RealEstateData> realEstateData) {
                    if (realEstateData.size() > 0){
                        mEstateNameTV.setText(realEstateData.get(0).getName());
                    }else {
                        mEstateNameTV.setVisibility(View.GONE);
                    }
                }
            }, mContext, ownerData.getCreatedByCompId());

            // Actions Estate Click Clicklistners
            mEstateNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EstateDetailActivity.class);
                    intent.putExtra("estateid",ownerData.getCreatedByCompId());
                    mContext.startActivity(intent);
                }
            });

            //Favorite Button Click
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
        }
    }

    class OwnersAdapter extends RecyclerView.Adapter<OwnersItemView>{

        private List<OwnerData> mOwnersList;

        public OwnersAdapter(List<OwnerData> mOwnersList) {
            this.mOwnersList = mOwnersList;
        }

        @NonNull
        @Override
        public OwnersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OwnersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull OwnersItemView holder, int position) {
            holder.bind(mOwnersList.get(position));
        }

        @Override
        public int getItemCount() {
            return mOwnersList.size();
        }
    }

}
