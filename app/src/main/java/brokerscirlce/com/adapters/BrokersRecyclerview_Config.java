package brokerscirlce.com.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.Activities.Brokers.BrokerProfileActivity;
import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.Activities.Estates.EstateDetailActivity;
import brokerscirlce.com.api_helpers.AddressAPIHelper;
import brokerscirlce.com.api_helpers.MasterDatabaseHelper;
import brokerscirlce.com.api_helpers.UsersDatabaseHelper;
import brokerscirlce.com.model.Addresses.AddressesData;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.R;
import brokerscirlce.com.model.MasterAPI.MasterData;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

public class BrokersRecyclerview_Config {

    private Context mContext;
    private BrokersAdapter mBrokersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<BrokersData> brokersData, EditText mEdittextSearch){
        mContext = context;
        mBrokersAdapter = new BrokersAdapter(brokersData);

        if (mEdittextSearch != null){
            mEdittextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mBrokersAdapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mBrokersAdapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mBrokersAdapter.getFilter().filter(editable.toString());
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mBrokersAdapter);
    }

    class BrokersItemView extends RecyclerView.ViewHolder{

        Helper helper;

        private CircleImageView mProfileImage;
        private ImageView mOnlineStatus;
        private ToggleButton mFavoriteBTN;
        private TextView mNameTV, mEstateNameTV, mReviews, mTotalBuyTV, mTotalSellTV, mTotalRentTV;
        private TextView mExperienceTV, mLocationTV, mFollowesTV, mFollowingTV;
        private RatingBar ratingBar;
        private Button mProfileButton, mCallButton, mMessageButton, mGetDirectionButton;

        public BrokersItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.brokers_item_row_single, parent, false));
            //Get helper class instance
            helper = new Helper(mContext);

            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mOnlineStatus =  itemView.findViewById(R.id.online_icon);
            mNameTV =  itemView.findViewById(R.id.tv_name);
            mEstateNameTV =  itemView.findViewById(R.id.tv_estate);
            ratingBar =  itemView.findViewById(R.id.rating);
            mReviews =  itemView.findViewById(R.id.tv_review);
            mTotalBuyTV =  itemView.findViewById(R.id.tv_buy_count);
            mTotalSellTV =  itemView.findViewById(R.id.tv_sell_count);
            mTotalRentTV =  itemView.findViewById(R.id.tv_rent_count);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mExperienceTV =  itemView.findViewById(R.id.tv_experience);
            mLocationTV =  itemView.findViewById(R.id.tv_location);
            mFollowesTV =  itemView.findViewById(R.id.tv_followers);
            mFollowingTV =  itemView.findViewById(R.id.tv_following);
            mProfileButton =  itemView.findViewById(R.id.btn_view_profile);
            mCallButton =  itemView.findViewById(R.id.btn_call);
            mMessageButton =  itemView.findViewById(R.id.btn_message);
            mGetDirectionButton =  itemView.findViewById(R.id.btn_get_direction);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final BrokersData brokersData){

            //Profile image
            if (brokersData.getPicture() != null && !brokersData.getPicture().equals("")){
                Picasso.get().load(brokersData.getPicture()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
            }else{
                mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
            }

            //Status Online
//            if (brokersData.getStatus().equals("Active")){
//                mOnlineStatus.setVisibility(View.VISIBLE);
//            }else {
//                mOnlineStatus.setVisibility(View.GONE);
//            }

            //Name / Title
            if (!brokersData.getTitle().equals("") && brokersData.getTitle() != null ){
                mNameTV.setText(brokersData.getTitle());
            }else {
                mNameTV.setVisibility(View.GONE);
            }

            //EstateName
            new MasterDatabaseHelper().readMasterByReference_ID_AND_RefType(new MasterDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<MasterData> masterData) {
                    new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<UsersData> usersData) {
                            if (!usersData.get(0).getCompanyName().equals("") && usersData.get(0).getCompanyName() != null){
                                mEstateNameTV.setText(usersData.get(0).getCompanyName());
                            }else {
                                mEstateNameTV.setVisibility(View.GONE);
                            }
                        }
                    }, mContext, masterData.get(0).getUserId());
                }
            },mContext, brokersData.getId(),"Owner");

//            if (!brokersData.getRealEstateName().equals("") && brokersData.getRealEstateName() != null){
//                mEstateNameTV.setText(brokersData.getRealEstateName());
//            }else {
//                mEstateNameTV.setVisibility(View.GONE);
//            }

            //Rating bar
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
            if (brokersData.getAverageRating() != null && !brokersData.getAverageRating().equals("")){
                ratingBar.setRating((float) brokersData.getAverageRating());
            }else{
                ratingBar.setRating(0);
            }

            //Reviews
            if (brokersData.getNoOfReviews() != null && !brokersData.getNoOfReviews().equals("")){
                mReviews.setText(String.format("%s Reviews", brokersData.getNoOfReviews().toString()));
            }else{
                mReviews.setText("0 Reviews");
            }

            //buy Count
            if (brokersData.getNoOfBuy() != null && !brokersData.getNoOfBuy().equals("")){
                mTotalBuyTV.setText(String.format("Buy: %s", brokersData.getNoOfBuy()));
            }else {
                mTotalBuyTV.setText("Buy: 0");
            }
            //Sell Count
            if (brokersData.getNoOfSell() != null && !brokersData.getNoOfSell().equals("")){
                mTotalSellTV.setText(String.format("Sell: %s", brokersData.getNoOfSell()));
            }else {
                mTotalSellTV.setText("Sell: 0");
            }

            //Rent Count
            if (brokersData.getNoOfRent() != null && !brokersData.getNoOfRent().equals("")){
                mTotalRentTV.setText(String.format("Sell: %s", brokersData.getNoOfRent()));
            }else {
                mTotalRentTV.setText("Rent: 0");
            }

            //Experience
            if (brokersData.getExperience() != null && !brokersData.getExperience().equals("")){
                mExperienceTV.setText(String.format("%s years experience", brokersData.getExperience().toString()));
            }else{
                mExperienceTV.setText("Less then 1 year experience");
            }

            //Experience
            if (brokersData.getExperience() != null && !brokersData.getExperience().equals("")){
                mExperienceTV.setText(String.format("%s years experience", brokersData.getExperience().toString()));
            }else{
                mExperienceTV.setText("Less then 1 year experience");
            }

            //Location Address
            new AddressAPIHelper().readRefrenceWithTypeAdressesData(new AddressAPIHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<AddressesData> addressesData) {
                    if (addressesData.isEmpty()){
                        mLocationTV.setText("Not found");
                    }else {
                        mLocationTV.setText(addressesData.get(0).getLocationTitle() +" "
                                +addressesData.get(0).getAreaTitle()+" "
                                +addressesData.get(0).getAreaTitle()+" "
                                +addressesData.get(0).getCityTitle()+" "
                                +addressesData.get(0).getProvinceTitle()+" "
                                +addressesData.get(0).getCountryTitle()
                        );
                    }
                }
            }, mContext, brokersData.getId(), "Broker");


            //Number of Followers
            if (brokersData.getNoOfFollowers() != null && !brokersData.getNoOfFollowers().equals("")){
                int followers = (int) brokersData.getNoOfFollowers();
                String followerSting = (followers > 1)? followers+" Followers" : followers+" Follower";
                mFollowesTV.setText(followerSting);
            }else{
                mFollowesTV.setText("0 Follower");
            }

            //Number of Following
            mFollowingTV.setText("0 Following");

            // Actions Estate Click Clicklistners
            mEstateNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EstateDetailActivity.class);
                    intent.putExtra("estateid",brokersData.getCreatedByCompId());
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

            // Actions Profile button Click Clicklistners
            mProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, BrokerProfileActivity.class);
                    intent.putExtra("brokerid",brokersData.getId());
                    mContext.startActivity(intent);
                }
            });

            mMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    class BrokersAdapter extends RecyclerView.Adapter<BrokersItemView> implements Filterable {

        private List<BrokersData> mBrokersListFiltered;
        private List<BrokersData> mBrokersList;

        public BrokersAdapter(List<BrokersData> mBrokersList) {
            this.mBrokersListFiltered = mBrokersList;
            this.mBrokersList = mBrokersList;
        }

        @NonNull
        @Override
        public BrokersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BrokersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BrokersItemView holder, int position) {
            holder.bind(mBrokersListFiltered.get(position));
        }

        @Override
        public int getItemCount() {
            return mBrokersListFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mBrokersListFiltered = mBrokersList;
                    } else {
                        List<BrokersData> filteredList = new ArrayList<>();
                        for (BrokersData row : mBrokersList) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                    || row.getTitle().contains(charSequence)
                                    || row.getRealEstateName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getRealEstateName().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }
                        mBrokersListFiltered = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mBrokersListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mBrokersListFiltered = (ArrayList<BrokersData>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

}
