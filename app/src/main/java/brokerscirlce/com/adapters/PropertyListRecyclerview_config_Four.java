package brokerscirlce.com.adapters;

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
import java.util.List;

import brokerscirlce.com.Activities.Brokers.BrokerProfileActivity;
import brokerscirlce.com.Activities.Estates.EstateDetailActivity;
import brokerscirlce.com.Activities.Properties.PropertyDetailActivity;
import brokerscirlce.com.api_helpers.AddressAPIHelper;
import brokerscirlce.com.util.CustomDialogBox;
import brokerscirlce.com.util.Helper;
import brokerscirlce.com.api_helpers.PhoneDatabaseHelper;
import brokerscirlce.com.api_helpers.UsersDatabaseHelper;
import brokerscirlce.com.model.Addresses.AddressesData;
import brokerscirlce.com.model.Phone.PhoneData;
import brokerscirlce.com.model.Property.PropertyData;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.R;

public class PropertyListRecyclerview_config_Four {

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

        private RoundedImageView mPropertyImage;
        private TextView mEstateName, mTitle, mDescription, mAddress, mName, mTime;
        private ToggleButton mFavoriteBTN, mCallBtn, mSMSBtn, mAudioBtn;
        private LinearLayout mAudioBtnLayout;
        private MediaPlayer mediaPlayer;

        public PropertyItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.property_item_row_single_four, parent, false));

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
