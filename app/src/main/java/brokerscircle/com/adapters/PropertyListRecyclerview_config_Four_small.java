package brokerscircle.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscircle.com.Activities.Brokers.BrokerProfileActivity;
import brokerscircle.com.Activities.Estates.EstateDetailActivity;
import brokerscircle.com.Activities.Properties.PropertyDetailActivity;
import brokerscircle.com.api_helpers.FavoriteDatabaseHelper;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.CustomDialogBox;
import brokerscircle.com.util.Helper;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.PhoneDatabaseHelper;
import brokerscircle.com.api_helpers.UsersDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Phone.PhoneData;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.model.Users.UsersData;
import brokerscircle.com.R;

public class PropertyListRecyclerview_config_Four_small {

    private Context mContext;
    private PropertyAdapter mPropertyAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<PropertyData> propertyData){
        mContext = context;
        mPropertyAdapter = new PropertyAdapter(propertyData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(mPropertyAdapter);
    }

    class PropertyItemView extends RecyclerView.ViewHolder{

        Helper helper;
        LoginUser user;

        private RoundedImageView mPropertyImage;
        private TextView mEstateName, mTitle, mDescription, mAddress, mName, mTime;
        private ToggleButton mFavoriteBTN, mCallBtn, mSMSBtn, mAudioBtn;
        private LinearLayout mAudioBtnLayout;
        private MediaPlayer mediaPlayer;

        public PropertyItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.property_item_row_single_four_small, parent, false));

            helper = new Helper(mContext);
            user = helper.getLoggedInUser();

            mPropertyImage =  itemView.findViewById(R.id.post_image);
            mEstateName =  itemView.findViewById(R.id.tv_estate);
            mTitle =  itemView.findViewById(R.id.tv_title);
            mDescription =  itemView.findViewById(R.id.tv_description);
            mAddress =  itemView.findViewById(R.id.tv_address);
            mName =  itemView.findViewById(R.id.tv_broker_name);
            mCallBtn =  itemView.findViewById(R.id.btn_call);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mSMSBtn =  itemView.findViewById(R.id.btn_sms);
            mAudioBtn =  itemView.findViewById(R.id.btn_audio);
            mAudioBtnLayout =  itemView.findViewById(R.id.layoutAudio);
            mTime =  itemView.findViewById(R.id.tv_time);
        }

        public void bind(final PropertyData propertyData){

            //Broker Name and Estate Name
            new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<UsersData> usersData) {
                    if (usersData.size() > 0){
                        if (usersData.get(0).getImg() == null || usersData.get(0).getImg().equals("")){
                            mPropertyImage.setImageResource(R.drawable.ic_user_icon_colored);
                        }else{
                            Picasso.get().load(usersData.get(0).getImg().toString()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mPropertyImage);
                        }

                        mName.setText(usersData.get(0).getFullName());
                        mEstateName.setText(usersData.get(0).getCompanyName());
                    }else {
                        mName.setVisibility(View.GONE);
                        mEstateName.setVisibility(View.GONE);
                    }

                    //Property Address
                    new AddressAPIHelper().readRefrenceWithTypeAdressesData(new AddressAPIHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<AddressesData> addressesData) {
                            if (!addressesData.isEmpty()){
                                String title = addressesData.get(0).getTitle().toString();
                                String locationTitle = " "+addressesData.get(0).getLocationTitle();
                                String areaTitle = " "+addressesData.get(0).getAreaTitle();
                                String cityTitle = " "+addressesData.get(0).getCityTitle();
                                String provinceTitle = " "+addressesData.get(0).getProvinceTitle();
                                String countryTitle = " "+addressesData.get(0).getCountryTitle()+".";
                                mAddress.setText(String.format("%s%s%s%s%s%s", title, locationTitle, areaTitle, cityTitle, provinceTitle, countryTitle));
                            }else {
                                mAddress.setText("DHA Karachi");
                                //mAddress.setVisibility(View.GONE);
                            }
                        }
                    }, mContext , propertyData.getId(), "Property");

                    //Property image
                    if (propertyData.getThumbnail() == null || propertyData.getThumbnail().equals("")){
                        mPropertyImage.setImageResource(R.mipmap.mainscreenbackground);
                    }else{
                        Picasso.get().load(propertyData.getThumbnail().toString()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mPropertyImage);
                    }
                    //Time
                    mTime.setText(Helper.covertTimeToText(propertyData.getCreatedAt()));

                    //Property Description
                    if ((propertyData.getTitle() == null) || (propertyData.getTitle().equals(""))){
                        mTitle.setVisibility(View.GONE);
                    }else {
                        mTitle.setText(propertyData.getTitle());
                    }

                    //Property Description
                    if ((propertyData.getDescription() == null) || (propertyData.getDescription().equals(""))){
                        mDescription.setVisibility(View.GONE);
                    }else {
                        mDescription.setText(propertyData.getDescription());
                    }


                }
            }, mContext, propertyData.getCreatedByUserId());

            //Action onClickListners
            mName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, BrokerProfileActivity.class);
                    intent.putExtra("brokerid", propertyData.getCreatedByUserId());
                    mContext.startActivity(intent);
                }
            });

            //On Estate Click
            mEstateName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EstateDetailActivity.class);
                    intent.putExtra("estateid", propertyData.getCreatedByCompId());
                    mContext.startActivity(intent);
                }
            });

            //Audio Visibility
            if ((propertyData.getAudioRecording() == null) || (propertyData.getAudioRecording().equals(""))){
                mAudioBtnLayout.setVisibility(View.VISIBLE);
            }else {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(String.valueOf(propertyData.getAudioRecording()));
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mAudioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        if (b){
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mediaPlayer.start();
                                }
                            });

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    mediaPlayer.stop();
                                }
                            });
                        }else {
                            mediaPlayer.stop();
                        }
                    }
                });
            }

            //Favorite Button Click
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
            }, mContext,user.getResponse().getData().getUser().getUserId(),propertyData.getId(), "Property");

            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            //Fav click
            mFavoriteBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(scaleAnimation);
                    if (mFavoriteBTN.isChecked()){
                        FavoriteMethod(propertyData);
                    }else {
                        deteleFavoriteMethod(propertyData);
                    }
                }
            });

            //Audio click
            mAudioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });

            //Geting phone numbers and implement sms-BTN and Call-BTN Listner
            new PhoneDatabaseHelper().readPhoneList(new PhoneDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<PhoneData> phoneData) {

                    //sms click
                    mSMSBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            //animation
                            compoundButton.startAnimation(scaleAnimation);
                            //Check if list is not null
                            if (phoneData.isEmpty()){
                                mSMSBtn.setChecked(false);
                                Toast.makeText(mContext, "No phone number Available", Toast.LENGTH_SHORT).show();
                            }else{
                                mSMSBtn.setChecked(true);
                                new CustomDialogBox().showSMSDialog(mContext, phoneData);
                            }
                        }
                    });

                    //Call click
                    mCallBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            //animation
                            compoundButton.startAnimation(scaleAnimation);
                            //Check if list is not null
                            if (phoneData.isEmpty()){
                                mCallBtn.setChecked(false);
                                Toast.makeText(mContext, "No phone number Available", Toast.LENGTH_SHORT).show();
                            }else{
                                mCallBtn.setChecked(true);
                                new CustomDialogBox().showCallDialog(mContext, phoneData);
                            }
                        }
                    });
                }
            }, mContext, propertyData.getCreatedByUserId(), "Broker");

            mPropertyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PropertyDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            //End layout filling
        }

        private void deteleFavoriteMethod(PropertyData propertyData) {

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
            }, mContext,user.getResponse().getData().getUser().getUserId(),propertyData.getId(), "Property");
        }

        private void FavoriteMethod(PropertyData propertyData) {

            Map<String,String> params = new HashMap<String, String>();

            params.put("reference_id",propertyData.getId());
            params.put("reference_type","Property");
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

    class PropertyAdapter extends RecyclerView.Adapter<PropertyItemView>{

        private List<PropertyData> mPropertyDataList;

        public PropertyAdapter(List<PropertyData> mPropertyUtilsList) {
            this.mPropertyDataList = mPropertyUtilsList;
        }

        @NonNull
        @Override
        public PropertyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PropertyItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PropertyItemView holder, int position) {
            holder.bind(mPropertyDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mPropertyDataList.size();
        }
    }

}
