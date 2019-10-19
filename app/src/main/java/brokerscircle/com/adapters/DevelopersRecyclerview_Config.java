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
import brokerscircle.com.Activities.Developers.DevelopersDetailActivity;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.FavoriteDatabaseHelper;
import brokerscircle.com.api_helpers.PhoneDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Developers.DevelopersData;
import brokerscircle.com.R;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.model.Phone.PhoneData;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.CustomDialogBox;
import brokerscircle.com.util.Helper;

public class DevelopersRecyclerview_Config {

    private Context mContext;
    private DevelopersAdapter mDevelopersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<DevelopersData> developersData, EditText mEdittextSearch){
        mContext = context;
        mDevelopersAdapter = new DevelopersAdapter(developersData);
        if (mEdittextSearch != null){
            mEdittextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mDevelopersAdapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mDevelopersAdapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mDevelopersAdapter.getFilter().filter(editable.toString());
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mDevelopersAdapter);
    }

    class DevelopersItemView extends RecyclerView.ViewHolder{

        Helper helper;
        LoginUser user;

        private RoundedImageView mImage;
        private ImageView mTrustedEstate;
        private ToggleButton mFavoriteBTN;
        private TextView mNameTV, mSinceTV, mReviews, mCompletedTV, mOffPlanTV, mInProgressTV;
        private TextView mTotalBrokersTV, mLocationTV, mFollowesTV;
        private RatingBar ratingBar;
        private Button mDetailButton, mCallButton, mMessageButton, mGetDirectionButton;

        public DevelopersItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.developer_row_item_single, parent, false));

            helper = new Helper(mContext);
            user = helper.getLoggedInUser();

            mImage =  itemView.findViewById(R.id.imageview);
            mTrustedEstate =  itemView.findViewById(R.id.trusted_icon);
            mNameTV =  itemView.findViewById(R.id.tv_name);
            mSinceTV =  itemView.findViewById(R.id.tv_since);
            ratingBar =  itemView.findViewById(R.id.rating);
            mReviews =  itemView.findViewById(R.id.tv_review);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mCompletedTV =  itemView.findViewById(R.id.tv_completed);
            mOffPlanTV =  itemView.findViewById(R.id.tv_off_plan);
            mInProgressTV =  itemView.findViewById(R.id.tv_in_progress);
            mTotalBrokersTV =  itemView.findViewById(R.id.tv_brokers);
            mFollowesTV =  itemView.findViewById(R.id.tv_followers);
            mLocationTV =  itemView.findViewById(R.id.tv_location);
            mDetailButton =  itemView.findViewById(R.id.btn_details);
            mCallButton =  itemView.findViewById(R.id.btn_call);
            mMessageButton =  itemView.findViewById(R.id.btn_message);
            mGetDirectionButton =  itemView.findViewById(R.id.btn_get_direction);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final DevelopersData developersData){

            //Estate Logo
            if (developersData.getLogo() != null && !developersData.getLogo().equals("")){
                Picasso.get().load(developersData.getLogo().toString()).placeholder(R.drawable.ic_real_state_icon_colored).centerCrop().fit().into(mImage);
            }else {
                mImage.setImageResource(R.drawable.ic_real_state_icon_colored);
            }

            //Estate Name
            if (developersData.getTitle() != null && !developersData.getTitle().equals("")){
                mNameTV.setText(developersData.getTitle());
            }else {
                mNameTV.setVisibility(View.GONE);
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
            }, mContext,user.getResponse().getData().getUser().getUserId(),developersData.getId(), "Developer");

            //Since TV
            if (developersData.getSince() != null && !developersData.getSince().equals("")){
                mSinceTV.setText(developersData.getSince().toString());
            }else {
                mSinceTV.setVisibility(View.GONE);
            }

            //Rating bar
            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);
            if (developersData.getAverageRating() != null && !developersData.getAverageRating().equals("")){
                ratingBar.setRating((float) developersData.getAverageRating());
            }else{
                ratingBar.setRating(0);
            }

            //Reviews
            if (developersData.getNoOfReviews() != null && !developersData.getNoOfReviews().equals("")){
                mReviews.setText(String.format("%s Reviews", developersData.getNoOfReviews().toString()));
            }else{
                mReviews.setText("0 Reviews");
            }

            //Project Completed Count
            if (developersData.getNoOfProjectCompleted() != null && !developersData.getNoOfProjectCompleted().equals("")){
                mCompletedTV.setText(String.format("Completed: %s", developersData.getNoOfProjectCompleted()));
            }else {
                mCompletedTV.setText("Completed: 0");
            }

            //Project OffPlan Count
            if (developersData.getNoOfProjectOffPlan() != null && !developersData.getNoOfProjectOffPlan().equals("")){
                mOffPlanTV.setText(String.format("Off Plan: %s", developersData.getNoOfProjectOffPlan()));
            }else {
                mOffPlanTV.setText("Off Plan: 0");
            }

            //Project InProgress Count
            if (developersData.getNoOfProjectInProgress() != null && !developersData.getNoOfProjectInProgress().equals("")){
                mInProgressTV.setText(String.format("In Progress: %s", developersData.getNoOfProjectInProgress()));
            }else {
                mInProgressTV.setText("In Progress: 0");
            }

            //Brokers Count
            if (developersData.getNoOfBrokers() != null && !developersData.getNoOfBrokers().equals("")){
                mTotalBrokersTV.setText(String.format("%s Brokers", developersData.getNoOfBrokers()));
            }else {
                mTotalBrokersTV.setText("0 Broker");
            }

            //Number of Followers
            if (developersData.getNoOfFollowers() != null && !developersData.getNoOfFollowers().equals("")){
                int followers = (int) developersData.getNoOfFollowers();
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
            }, mContext, developersData.getId(), "PropertyDeveloper");

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
                        FavoriteMethod(developersData);
                    }else {
                        deteleFavoriteMethod(developersData);
                    }
                }
            });

            // Actions Detail Button Click Clicklistners
            mDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DevelopersDetailActivity.class);
                    intent.putExtra("developerid",developersData.getId());
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

            //onCall Listener
            new PhoneDatabaseHelper().readPhoneList(new PhoneDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<PhoneData> phoneData) {

                    //CALL click
                    mCallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (phoneData.isEmpty()){
                                Toast.makeText(mContext, "No phone number Available", Toast.LENGTH_SHORT).show();
                            }else{
                                new CustomDialogBox().showCallDialog(mContext, phoneData);
                            }
                        }
                    });
                }
            }, mContext, developersData.getId(),"PropertyDeveloper");
        }

        private void deteleFavoriteMethod(DevelopersData developersData) {
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
            }, mContext,user.getResponse().getData().getUser().getUserId(),developersData.getId(), "Developer");
        }

        private void FavoriteMethod(DevelopersData developersData) {

            Map<String,String> params = new HashMap<String, String>();

            params.put("reference_id",developersData.getId());
            params.put("reference_type","Developer");
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

    class DevelopersAdapter extends RecyclerView.Adapter<DevelopersItemView> implements Filterable {

        private List<DevelopersData> mListFiltered;
        private List<DevelopersData> mList;

        public DevelopersAdapter(List<DevelopersData> mList) {
            this.mList = mList;
            this.mListFiltered = mList;
        }

        @NonNull
        @Override
        public DevelopersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DevelopersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DevelopersItemView holder, int position) {
            holder.bind(mListFiltered.get(position));
        }

        @Override
        public int getItemCount() {
            return mListFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mListFiltered = mList;
                    } else {
                        List<DevelopersData> filteredList = new ArrayList<>();
                        for (DevelopersData row : mList) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                    || row.getTitle().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }
                        mListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mListFiltered = (ArrayList<DevelopersData>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
