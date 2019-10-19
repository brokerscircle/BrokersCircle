package brokerscircle.com.adapters;

import android.annotation.SuppressLint;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.Activities.Get_Direction_Activity;
import brokerscircle.com.R;
import brokerscircle.com.api_helpers.AddressAPIHelper;
import brokerscircle.com.api_helpers.PhoneDatabaseHelper;
import brokerscircle.com.api_helpers.UsersDatabaseHelper;
import brokerscircle.com.model.Addresses.AddressesData;
import brokerscircle.com.model.Phone.PhoneData;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.model.Users.UsersData;
import brokerscircle.com.util.CustomDialogBox;
import brokerscircle.com.util.Helper;
import de.hdodenhof.circleimageview.CircleImageView;

public class PropertyDashboardTwoListRecyclerview_config {

    private Context mContext;
    private PropertyAdapter mPropertyAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<PropertyData> propertyData){
        mContext = context;
        mPropertyAdapter = new PropertyAdapter(propertyData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(mPropertyAdapter);
    }

    class PropertyItemView extends RecyclerView.ViewHolder{

        private CircleImageView mBrokerProfileImage;
        private TextView mBrokerName, mEstateName;
        private RatingBar mBrokerRating;
        private RoundedImageView mPropertyImage;
        private TextView mTitle, mAddress, mNoBed, mNoBath, mNoUnit, mTime, mPrice;
        private ToggleButton mFavoriteBTN, mGetDirectionBTN, mCallBtn, mSMSBtn, mAudioBtn;
        private LinearLayout mAudioBtnLayout;
        private MediaPlayer mediaPlayer;

        private LatLng mTargetLocation = null;

        public PropertyItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.property_list_dashboard_two_item, parent, false));

            mBrokerProfileImage =  itemView.findViewById(R.id.profile_images);
            mBrokerName =  itemView.findViewById(R.id.tv_broker_name);
            mEstateName =  itemView.findViewById(R.id.tv_estate);
            mBrokerRating =  itemView.findViewById(R.id.rating);
            mPropertyImage =  itemView.findViewById(R.id.post_image);
            mTitle =  itemView.findViewById(R.id.tv_title);
            mPrice =  itemView.findViewById(R.id.tv_price);
            mAddress =  itemView.findViewById(R.id.tv_address);
            mNoBed =  itemView.findViewById(R.id.tv_beds);
            mNoBath =  itemView.findViewById(R.id.tv_baths);
            mNoUnit =  itemView.findViewById(R.id.tv_units);
            mCallBtn =  itemView.findViewById(R.id.btn_call);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mGetDirectionBTN =  itemView.findViewById(R.id.btn_get_direction);
            mSMSBtn =  itemView.findViewById(R.id.btn_sms);
            mAudioBtn =  itemView.findViewById(R.id.btn_audio);
            mAudioBtnLayout =  itemView.findViewById(R.id.layoutAudio);
            mTime =  itemView.findViewById(R.id.tv_time);
        }

        public void bind(final PropertyData propertyData){

            new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<UsersData> usersData) {

                    //Broker image
                    if (usersData.get(0).getImg() != null){
                        Picasso.get().load(usersData.get(0).getImg().toString()).placeholder(R.drawable.ic_user_icon_white).centerCrop().fit().into(mBrokerProfileImage);
                    }else{
                        mBrokerProfileImage.setImageResource(R.drawable.ic_user_icon_white);
                    }

                    //Broker Name
                    if ((usersData.get(0).getFullName() == null) || (usersData.get(0).getFullName().equals(""))){
                        mBrokerName.setVisibility(View.GONE);
                    }else {
                        mBrokerName.setText(usersData.get(0).getFullName());
                    }

                    //Estate Name
                    if ((usersData.get(0).getCompanyName() == null) || (usersData.get(0).getCompanyName().equals(""))){
                        mEstateName.setVisibility(View.GONE);
                    }else {
                        mEstateName.setText(usersData.get(0).getCompanyName());
                    }

                }
            },mContext, propertyData.getCreatedByUserId());

            //Property image
            if (propertyData.getThumbnail() != null || !propertyData.getThumbnail().equals("")){
                Picasso.get().load(propertyData.getThumbnail().toString()).placeholder(R.mipmap.mainscreenbackground).centerCrop().fit().into(mPropertyImage);
            }else{
                mPropertyImage.setImageResource(R.mipmap.mainscreenbackground);
            }
            //Time
            mTime.setText(Helper.covertTimeToText(propertyData.getCreatedAt()));

            //Property Description
            if ((propertyData.getTitle() == null) || (propertyData.getTitle().equals(""))){
                mTitle.setVisibility(View.GONE);
            }else {
                mTitle.setText(propertyData.getTitle());
            }

            //Location Address
            new AddressAPIHelper().readRefrenceWithTypeAdressesData(new AddressAPIHelper.DataStatus() {
                @SuppressLint("SetTextI18n")
                @Override
                public void DataIsLoaded(List<AddressesData> addressesData) {
                    if (addressesData.isEmpty()){
                        mAddress.setText("Not found");
                    }else {
                        mAddress.setText(addressesData.get(0).getLocationTitle() +" "
                                +addressesData.get(0).getAreaTitle()+" "
                                +addressesData.get(0).getAreaTitle()+" "
                                +addressesData.get(0).getCityTitle()+" "
                                +addressesData.get(0).getProvinceTitle()+" "
                                +addressesData.get(0).getCountryTitle()
                        );

                        mTargetLocation = new LatLng(Double.parseDouble(addressesData.get(0).getLatitude()), Double.parseDouble(addressesData.get(0).getLongitude()));
                    }
                }
            }, mContext, propertyData.getId(), "Property");

            //Audio Visibility
            if ((propertyData.getAudioRecording() == null) || (propertyData.getAudioRecording().equals(""))){
                mAudioBtnLayout.setVisibility(View.GONE);
            }

            //Favorite Button Click
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            //Fav click
            mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });

            //Get direction Click
            mGetDirectionBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                    if (mTargetLocation != null){
                        Intent intent = new Intent(mContext, Get_Direction_Activity.class);
                        intent.putExtra("targetPosition",mTargetLocation);
                        mContext.startActivity(intent);
                    }else {
                        Toast.makeText(mContext, "Location not found", Toast.LENGTH_SHORT).show();
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
            //End layout filling
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
