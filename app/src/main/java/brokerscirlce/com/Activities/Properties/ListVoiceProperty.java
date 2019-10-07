package brokerscirlce.com.Activities.Properties;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.devlomi.record_view.OnBasketAnimationEnd;
import com.devlomi.record_view.OnRecordClickListener;
import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.R;
import brokerscirlce.com.api_helpers.Property.PropertyChildCategoryApiHelper;
import brokerscirlce.com.api_helpers.Property.PropertyParentCatergoryApiHelper;
import brokerscirlce.com.model.Property.Property_Child_Category.PropertyChildCategoryData;
import brokerscirlce.com.model.Property.Property_Child_Category.PropertyChildCategoryUtils;
import brokerscirlce.com.model.Property.Property_Parent_Category.PropertyParentCategoryData;
import brokerscirlce.com.model.Property.Property_Parent_Category.PropertyParentCategoryUtils;

public class ListVoiceProperty extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;

    //moreOptions
    private ToggleButton mToggleMoreOptions;
    //private ToggleButton mToggleHome, mTogglePlots, mToggleCommercial;
    private LinearLayout mLayoutMoreOptions;
    private ViewGroup propertyTypesChecks;
    private ViewGroup propertyTypesChildChecks;
    private ViewGroup bedroomsChecks;
    private ViewGroup bathroomsChecks;

    private RecordView recordView;
    RecordButton recordButton;


    List<PropertyParentCategoryData> propertyParentCategoryDataList;
    PropertyParentCategoryData propertyParentCategoryData = null;
    List<PropertyChildCategoryData> propertyChildCategoryDataList;
    PropertyChildCategoryData propertyChildCategoryData = null;

    String[] bedrooms = new String[] {"Studio", "1", "2", "3", "4", "5", "6", "7", "8", "9+"};
    String[] bathrooms = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9+"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_voice_property_activity);

        propertyParentCategoryDataList = new ArrayList<>();
        propertyChildCategoryDataList = new ArrayList<>();

        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.btn_chat);
        mProfileButton = findViewById(R.id.btn_profile);
        mOnlineIcon = findViewById(R.id.user_online_icon);

        //MoreOptions
        mToggleMoreOptions = findViewById(R.id.toggle_more_options);
        mLayoutMoreOptions = findViewById(R.id.layout_more_options);
//        mToggleHome = findViewById(R.id.toggle_home);
//        mTogglePlots = findViewById(R.id.toggle_plot);
//        mToggleCommercial = findViewById(R.id.toggle_commercial);
        propertyTypesChecks = findViewById(R.id.property_types_radio_group);
        propertyTypesChildChecks = findViewById(R.id.types_child_radio_group);
        bedroomsChecks = findViewById(R.id.bedroom_radio_group);
        bathroomsChecks = findViewById(R.id.bathroom_radio_group);

        mToggleMoreOptions.setOnCheckedChangeListener(this);
//        mToggleHome.setOnCheckedChangeListener(this);
//        mTogglePlots.setOnCheckedChangeListener(this);
//        mToggleCommercial.setOnCheckedChangeListener(this);

        mBackButton.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
        mProfileButton.setOnClickListener(this);

        recordView =  findViewById(R.id.record_view);
        recordButton =  findViewById(R.id.record_button);
        setRecordingListeners();

        //PropertyTypes Layout
        setPropertyTypesLayout();

        //PropertyTypesChild Layout
        new PropertyChildCategoryApiHelper().readPropertyChildCatergoryList(new PropertyChildCategoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyChildCategoryUtils> propertyChildCategoryUtils) {
                if (!propertyChildCategoryUtils.get(0).getData().isEmpty()){
                    propertyChildCategoryDataList.clear();
                    propertyChildCategoryDataList = propertyChildCategoryUtils.get(0).getData();
                }
            }
        }, ListVoiceProperty.this);

        //bedrooms Layout
        setBedroomsLayout();

        //bathrooms Layout
        setBathroomLayout();
    }

    private void setPropertyTypesLayout() {
        new PropertyParentCatergoryApiHelper().readPropertyParentCatergoryList(new PropertyParentCatergoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyParentCategoryUtils> propertyParentCategoryUtils) {
                if (!propertyParentCategoryUtils.get(0).getData().isEmpty()){
                    propertyTypesChecks.removeAllViews();
                    for ( int i = 0; i < propertyParentCategoryUtils.get(0).getData().size(); i++) {
                        RadioButton button = new RadioButton(ListVoiceProperty.this);
                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(210, ViewGroup.LayoutParams.MATCH_PARENT);
                        if (i == 0){
                            //setting default check
                            button.setChecked(true);
                            button.setTextColor(getResources().getColor(R.color.colorWhite));
                            propertyParentCategoryData = propertyParentCategoryUtils.get(0).getData().get(i);
                            setPropertyTypesChildLayout(propertyParentCategoryData);
                            params.setMargins(40, 0, 0, 0);
                        }else {
                            params.setMargins(10, 0, 10, 0);
                        }
                        button.setLayoutParams(params);
                        button.setGravity(Gravity.CENTER);
                        button.setId(Integer.parseInt(propertyParentCategoryUtils.get(0).getData().get(i).getId()));
                        button.setText(propertyParentCategoryUtils.get(0).getData().get(i).getTitle());
                        button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                        button.setButtonDrawable(null);
                        button.setPadding(10,0,10,0);

                        //Performing on Check
                        int finalI = i;
                        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b){
                                    propertyParentCategoryData = propertyParentCategoryUtils.get(0).getData().get(finalI);
                                    setPropertyTypesChildLayout(propertyParentCategoryData);
                                    button.setTextColor(getResources().getColor(R.color.colorWhite));
                                }else {
                                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                                }
                            }
                        });
                        propertyTypesChecks.addView(button);
                    }
                }
            }
        }, ListVoiceProperty.this);
    }

    private void setPropertyTypesChildLayout(PropertyParentCategoryData propertyParentCategoryData) {

        if (propertyParentCategoryData != null && !propertyChildCategoryDataList.isEmpty()){
            propertyTypesChildChecks.removeAllViews();
            for ( int i = 0; i < propertyChildCategoryDataList.size(); i++) {
                if (propertyChildCategoryDataList.get(i).getPropertyParentCategoryId().equals(propertyParentCategoryData.getId())){

                    RadioButton button = new RadioButton(ListVoiceProperty.this);
                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.setMargins(10, 0, 10, 0);
                    if (i == 0){

                        //setting default check
                        button.setChecked(true);
                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                        propertyChildCategoryData = propertyChildCategoryDataList.get(i);
                    }
                    button.setLayoutParams(params);
                    button.setGravity(Gravity.CENTER);
                    button.setId(Integer.parseInt(propertyChildCategoryDataList.get(i).getId()));
                    button.setText(propertyChildCategoryDataList.get(i).getTitle());
                    button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                    //button.setButtonDrawable(null);
                    button.setPadding(30,0,30,0);

                    //Performing on Check
                    int finalI = i;
                    button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b){
                                propertyChildCategoryData = propertyChildCategoryDataList.get(finalI);

                                button.setTextColor(getResources().getColor(R.color.colorWhite));
                            }else {
                                button.setTextColor(getResources().getColor(R.color.colorBlack));
                            }
                        }
                    });
                    propertyTypesChildChecks.addView(button);
                    //this is params for index one layout only
                    RadioGroup.LayoutParams paramindexone = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    paramindexone.setMargins(40, 0, 10, 0);
                    propertyTypesChildChecks.getChildAt(0).setLayoutParams(new RadioGroup.LayoutParams(paramindexone));
                }
            }
        }
    }

    private void setBedroomsLayout() {
        bedroomsChecks.removeAllViews();
        for (int i = 0; i < bedrooms.length; i++) {
            RadioButton button = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (i == 0){
                params.setMargins(40, 0, 0, 0);
            }else {
                params.setMargins(10, 0, 10, 0);
            }
            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setText(bedrooms[i]);
            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
            button.setButtonDrawable(null);
            button.setPadding(40,0,40,0);

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                    }else {
                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }
            });

            bedroomsChecks.addView(button);
        }

    }

    private void setBathroomLayout() {
        bathroomsChecks.removeAllViews();
        for (int i = 0; i < bathrooms.length; i++) {
            RadioButton button = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (i == 0){
                params.setMargins(40, 0, 0, 0);
            }else {
                params.setMargins(10, 0, 10, 0);
            }
            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER);
            button.setId(i);
            button.setText(bathrooms[i]);
            button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
            button.setButtonDrawable(null);
            button.setPadding(40,0,40,0);

            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        button.setTextColor(getResources().getColor(R.color.colorWhite));
                    }else {
                        button.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                }
            });

            bathroomsChecks.addView(button);
        }
    }

    private void setRecordingListeners() {

        //IMPORTANT
        recordButton.setRecordView(recordView);

        // if you want to click the button (in case if you want to make the record button a Send Button for example..)
//        recordButton.setListenForRecord(false);

        //ListenForRecord must be false ,otherwise onClick will not be called
        recordButton.setOnRecordClickListener(new OnRecordClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListVoiceProperty.this, "RECORD BUTTON CLICKED", Toast.LENGTH_SHORT).show();
                Log.d("RecordButton", "RECORD BUTTON CLICKED");
            }
        });
        //Cancel Bounds is when the Slide To Cancel text gets before the timer . default is 8
        //recordView.setCancelBounds(8);
        recordView.setSmallMicColor(getResources().getColor(R.color.colorBlue));
        recordView.setSmallMicIcon(R.drawable.ic_audio_icon);
        //prevent recording under one Second
        recordView.setLessThanSecondAllowed(false);
        recordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0);
        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {
                Log.d("RecordView", "onStart");
                Toast.makeText(ListVoiceProperty.this, "OnStartRecord", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(ListVoiceProperty.this, "onCancel", Toast.LENGTH_SHORT).show();
                Log.d("RecordView", "onCancel");
            }
            @Override
            public void onFinish(long recordTime) {
                String time = getHumanTimeText(recordTime);
                Toast.makeText(ListVoiceProperty.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                Log.d("RecordView", "onFinish");

                Log.d("RecordTime", time);
            }
            @Override
            public void onLessThanSecond() {
                Toast.makeText(ListVoiceProperty.this, "OnLessThanSecond", Toast.LENGTH_SHORT).show();
                Log.d("RecordView", "onLessThanSecond");
            }
        });
        recordView.setOnBasketAnimationEndListener(new OnBasketAnimationEnd() {
            @Override
            public void onAnimationEnd() {
                Log.d("RecordView", "Basket Animation Finished");
            }
        });

    }

    @SuppressLint("DefaultLocale")
    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(ListVoiceProperty.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.btn_voice_recording:
                intent = new Intent(ListVoiceProperty.this, ListVoiceProperty.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.toggle_more_options:
                if (b){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_down);
                    mLayoutMoreOptions.startAnimation(animation);
                    mLayoutMoreOptions.setVisibility(View.VISIBLE);

                }else {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_up);
                    mLayoutMoreOptions.startAnimation(animation);
                    mLayoutMoreOptions.setVisibility(View.GONE);
                }
                break;
//            case R.id.toggle_home:
//                if (b){
//                    mToggleHome.setTextColor(getResources().getColor(R.color.colorWhite));
//                    mTogglePlots.setChecked(false);
//                    mToggleCommercial.setChecked(false);
//                }else {
//                    mToggleHome.setTextColor(getResources().getColor(R.color.colorBlack));
//                }
//                break;
//            case R.id.toggle_plot:
//                if (b){
//                    mTogglePlots.setTextColor(getResources().getColor(R.color.colorWhite));
//                    mToggleHome.setChecked(false);
//                    mToggleCommercial.setChecked(false);
//                }else {
//                    mTogglePlots.setTextColor(getResources().getColor(R.color.colorBlack));
//                }
//                break;
//            case R.id.toggle_commercial:
//                if (b){
//                    mToggleCommercial.setTextColor(getResources().getColor(R.color.colorWhite));
//                    mToggleHome.setChecked(false);
//                    mTogglePlots.setChecked(false);
//                }else {
//                    mToggleCommercial.setTextColor(getResources().getColor(R.color.colorBlack));
//                }
//                break;
        }
    }
}
