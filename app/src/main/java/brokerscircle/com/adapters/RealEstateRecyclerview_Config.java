package brokerscircle.com.adapters;

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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.Activities.Estates.EstateDetailActivity;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.FavoriteDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.model.RealEstates.RealEstateData;
import brokerscircle.com.R;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.Helper;

public class RealEstateRecyclerview_Config {

    private Context mContext;
    private EstateAdapter mEstateAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<RealEstateData> realEstateData, EditText mEdittextSearch){
        mContext = context;
        mEstateAdapter = new EstateAdapter(realEstateData);
        if (mEdittextSearch != null){
            mEdittextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mEstateAdapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mEstateAdapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mEstateAdapter.getFilter().filter(editable.toString());
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mEstateAdapter);
    }

    class EstateItemView extends RecyclerView.ViewHolder{

        Helper helper;
        LoginUser user;

        private RoundedImageView mEstateImage;
        private ImageView mTrustedEstate;
        private ToggleButton mFavoriteBTN;
        private TextView mEstateNameTV, mSinceTV, mReviews, mTotalBrokersTV, mTotalBuyTV, mTotalSellTV, mTotalRentTV;
        private TextView mDealsTV, mListingTV, mLocationTV, mFollowesTV;
        private RatingBar ratingBar;
        private Button mDetailButton, mCallButton, mMessageButton, mGetDirectionButton;

        public EstateItemView( ViewGroup parent) {

            super(LayoutInflater.from(mContext).
                    inflate(R.layout.estate_item_row_single, parent, false));

            helper = new Helper(mContext);
            user = helper.getLoggedInUser();

            mEstateImage =  itemView.findViewById(R.id.estate_image);
            mTrustedEstate =  itemView.findViewById(R.id.trusted_icon);
            mEstateNameTV =  itemView.findViewById(R.id.tv_estate_name);
            mSinceTV =  itemView.findViewById(R.id.tv_since);
            ratingBar =  itemView.findViewById(R.id.rating);
            mReviews =  itemView.findViewById(R.id.tv_review);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mDealsTV =  itemView.findViewById(R.id.tv_deals);
            mListingTV =  itemView.findViewById(R.id.tv_listing);
            mFollowesTV =  itemView.findViewById(R.id.tv_followers);
            mLocationTV =  itemView.findViewById(R.id.tv_location);
            mTotalBrokersTV =  itemView.findViewById(R.id.tv_brokers_count);
            mTotalBuyTV =  itemView.findViewById(R.id.tv_buy_count);
            mTotalSellTV =  itemView.findViewById(R.id.tv_sell_count);
            mTotalRentTV =  itemView.findViewById(R.id.tv_rent_count);
            mDetailButton =  itemView.findViewById(R.id.btn_details);
            mCallButton =  itemView.findViewById(R.id.btn_call);
            mMessageButton =  itemView.findViewById(R.id.btn_message);
            mGetDirectionButton =  itemView.findViewById(R.id.btn_get_direction);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final RealEstateData realEstateData){

            //Estate Logo
            if (realEstateData.getLogo() != null && !realEstateData.getLogo().equals("")){
                Picasso.get().load(realEstateData.getLogo().toString()).placeholder(R.drawable.ic_real_state_icon_colored).centerCrop().fit().into(mEstateImage);
            }else {
                mEstateImage.setImageResource(R.drawable.ic_real_state_icon_colored);
            }

            //Estate Name
            if (realEstateData.getName() != null && !realEstateData.getName().equals("")){
                mEstateNameTV.setText(realEstateData.getName());
            }else {
                mEstateNameTV.setVisibility(View.GONE);
            }

            new FavoriteDatabaseHelper().readFavoriteWithCreatedUserIdAndReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FavoriteData> favoriteData) {
                    if (favoriteData.isEmpty()){
                        mFavoriteBTN.setChecked(false);
                    }else {
                        mFavoriteBTN.setChecked(true);
                    }
                }

                @Override
                public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

                }

                @Override
                public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

                }
            }, mContext,user.getResponse().getData().getUser().getUserId(),realEstateData.getId(), "Company");


            //Since TV
            if (realEstateData.getSince() != null && !realEstateData.getSince().equals("")){
                mSinceTV.setText(realEstateData.getSince().toString());
            }else {
                mSinceTV.setVisibility(View.GONE);
            }

            //Rating bar
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
            if (realEstateData.getAverageRating() != null && !realEstateData.getAverageRating().equals("")){
                ratingBar.setRating((float) realEstateData.getAverageRating());
            }else{
                ratingBar.setRating(0);
            }

            //Reviews
            if (realEstateData.getNoOfReviews() != null && !realEstateData.getNoOfReviews().equals("")){
                mReviews.setText(String.format("%s Reviews", realEstateData.getNoOfReviews().toString()));
            }else{
                mReviews.setText("0 Reviews");
            }

            //Brokers Count
            if (realEstateData.getNoOfEmployee() != null && !realEstateData.getNoOfEmployee().equals("")){
                mTotalBuyTV.setText(String.format("Brokers: %s", realEstateData.getNoOfEmployee()));
            }else {
                mTotalBuyTV.setText("Buy: 0");
            }
            //buy Count
            if (realEstateData.getNoOfBuy() != null && !realEstateData.getNoOfBuy().equals("")){
                mTotalBuyTV.setText(String.format("Buy: %s", realEstateData.getNoOfBuy()));
            }else {
                mTotalBuyTV.setText("Buy: 0");
            }
            //Sell Count
            if (realEstateData.getNoOfSell() != null && !realEstateData.getNoOfSell().equals("")){
                mTotalSellTV.setText(String.format("Sell: %s", realEstateData.getNoOfSell()));
            }else {
                mTotalSellTV.setText("Sell: 0");
            }
            //Rent Count
            if (realEstateData.getNoOfRent() != null && !realEstateData.getNoOfRent().equals("")){
                mTotalRentTV.setText(String.format("Sell: %s", realEstateData.getNoOfRent()));
            }else {
                mTotalRentTV.setText("Rent: 0");
            }

            //Past dealing
            if (realEstateData.getNoOfDeal() != null && !realEstateData.getNoOfDeal().equals("")){
                mTotalRentTV.setText(String.format("%s past dealing", realEstateData.getNoOfDeal()));
            }else {
                mTotalRentTV.setText("0 past dealing");
            }

            //Listings
            mTotalRentTV.setText("0 Listings");
//            if (realEstateData.get() != null && !realEstateData.getNoOfDeal().equals("")){
//                mTotalRentTV.setText(String.format("%s Listings", realEstateData.getNoOfDeal()));
//            }else {
//                mTotalRentTV.setText("0 Listings");
//            }

            //Number of Followers
            if (realEstateData.getNoOfFollowers() != null && !realEstateData.getNoOfFollowers().equals("")){
                int followers = (int) realEstateData.getNoOfFollowers();
                String followerSting = (followers > 1)? followers+" Followers" : followers+" Follower";
                mFollowesTV.setText(followerSting);
            }else{
                mFollowesTV.setText("0 Follower");
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
            }, mContext, realEstateData.getId(), "Company");

            //Favorite Button Click
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            //Follow click
            mFavoriteBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(scaleAnimation);
                    if (mFavoriteBTN.isChecked()){
                        FavoriteMethod(realEstateData);
                    }else {
                        deteleFavoriteMethod(realEstateData);
                    }
                }
            });

            // Actions Detail Button Click Clicklistners
            mDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EstateDetailActivity.class);
                    intent.putExtra("estateid",realEstateData.getId());
                    mContext.startActivity(intent);
                }
            });
            // Actions Message Button Click Clicklistners
            mMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        private void deteleFavoriteMethod(RealEstateData realEstateData) {

            new FavoriteDatabaseHelper().readFavoriteWithCreatedUserIdAndReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FavoriteData> favoriteData) {
                    if (!favoriteData.isEmpty()){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("id",favoriteData.get(0).getId());
                        params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

                        new FavoriteDatabaseHelper().deleteFavorite(new FavoriteDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<FavoriteData> favoriteData) {

                            }

                            @Override
                            public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

                            }

                            @Override
                            public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

                            }
                        },mContext, params);
                    }
                }

                @Override
                public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

                }

                @Override
                public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {
                    if (responseUtils.get(0).getError().equals("false")){
                        Toast.makeText(mContext, "Successfully Removed", Toast.LENGTH_SHORT).show();
                        mFavoriteBTN.setChecked(false);
                    }else {
                        Toast.makeText(mContext, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                        mFavoriteBTN.setChecked(true);
                    }
                }
            }, mContext,user.getResponse().getData().getUser().getUserId(),realEstateData.getId(), "Company");
        }

        private void FavoriteMethod(RealEstateData realEstateData) {

            Map<String,String> params = new HashMap<String, String>();

            params.put("reference_id",realEstateData.getId());
            params.put("reference_type","Company");
            params.put("created_by_comp_id",user.getResponse().getData().getUser().getCompId());
            params.put("created_by_user_id",user.getResponse().getData().getUser().getUserId());

            new FavoriteDatabaseHelper().postFavorite(new FavoriteDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FavoriteData> favoriteData) {

                }

                @Override
                public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {
                    if (responseUtils.get(0).getError().equals("false")){
                        Toast.makeText(mContext, "Successfully Saved", Toast.LENGTH_SHORT).show();
                        mFavoriteBTN.setChecked(true);
                    }else {
                        Toast.makeText(mContext, responseUtils.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                        mFavoriteBTN.setChecked(false);
                    }
                }

                @Override
                public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

                }
            },mContext, params);
        }
    }

    class EstateAdapter extends RecyclerView.Adapter<EstateItemView> implements Filterable {

        private List<RealEstateData> mEstateListFiltered;
        private List<RealEstateData> mEstateList;

        public EstateAdapter(List<RealEstateData> mEstateList) {
            this.mEstateList = mEstateList;
            this.mEstateListFiltered = mEstateList;
        }

        @NonNull
        @Override
        public EstateItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EstateItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EstateItemView holder, int position) {
            holder.bind(mEstateListFiltered.get(position));
        }

        @Override
        public int getItemCount() {
            return mEstateListFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mEstateListFiltered = mEstateList;
                    } else {
                        List<RealEstateData> filteredList = new ArrayList<>();
                        for (RealEstateData row : mEstateList) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getName().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }
                        mEstateListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mEstateListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mEstateListFiltered = (ArrayList<RealEstateData>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

}
