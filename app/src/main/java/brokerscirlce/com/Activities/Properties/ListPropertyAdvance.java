package brokerscirlce.com.Activities.Properties;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.R;
import brokerscirlce.com.adapters.DropDownSuggestionsArrayAdapters.CurrencyArrayAdapter;
import brokerscirlce.com.adapters.DropDownSuggestionsArrayAdapters.UnitArrayAdapter;
import brokerscirlce.com.api_helpers.CurrencyApiHelper;
import brokerscirlce.com.api_helpers.Property.PropertyChildCategoryApiHelper;
import brokerscirlce.com.api_helpers.Property.PropertyFeaturesApiHelper;
import brokerscirlce.com.api_helpers.Property.PropertyNearByApiHelper;
import brokerscirlce.com.api_helpers.Property.PropertyParentCatergoryApiHelper;
import brokerscirlce.com.api_helpers.UnitApiHelper;
import brokerscirlce.com.model.Currency.CurrencyData;
import brokerscirlce.com.model.Property.PropertyFeatures.PropertyFeaturesData;
import brokerscirlce.com.model.Property.PropertyFeatures.PropertyFeaturesUtils;
import brokerscirlce.com.model.Property.PropertyNearBy.PropertyNearByData;
import brokerscirlce.com.model.Property.PropertyNearBy.PropertyNearByUtils;
import brokerscirlce.com.model.Property.Property_Child_Category.PropertyChildCategoryData;
import brokerscirlce.com.model.Property.Property_Child_Category.PropertyChildCategoryUtils;
import brokerscirlce.com.model.Property.Property_Parent_Category.PropertyParentCategoryData;
import brokerscirlce.com.model.Property.Property_Parent_Category.PropertyParentCategoryUtils;
import brokerscirlce.com.model.Units.UnitsData;

public class ListPropertyAdvance extends AppCompatActivity implements View.OnClickListener {

    private ScaleAnimation scaleAnimation;

    private Dialog dialog;
    private ListView mDialoglistView;
    private TextView mDialogTitle;
    List<CurrencyData> currencyDataList;
    CurrencyData currencyData = null;
    List<UnitsData> unitsDataList;
    UnitsData unitsData = null;

    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;

    private LinearLayout mContentOne, mContentTwo, mContentThree, mContentFour;

    private RelativeLayout mNextButton;
    private RelativeLayout mPrevButtonTwo, mNextButtonTwo;
    private RelativeLayout mPrevButtonThree, mNextButtonThree;
    private RelativeLayout mPrevButtonFour, mSubmitButton;


    private ViewGroup propertyTypesChecks;
    private LinearLayout propertyTypesChildChecks;
    private ViewGroup bedroomsChecks;
    private ViewGroup bathroomsChecks;
    private LinearLayout featuresChecks;
    private LinearLayout nearByChecks;

    private TextView mChangeCurrency;
    private TextView mCurrencyText;
    private TextView mChangeAreaUnit;
    private TextView mAreaTextUnit;

    private LinearLayout mButtonPhoto, mButtonVideo, mButtonMaps, mButtonAudio;

    String[] bedrooms = new String[] {"Studio", "1", "2", "3", "4", "5", "6", "7", "8", "9+"};
    String[] bathrooms = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9+"};

    List<PropertyParentCategoryData> propertyParentCategoryDataList;
    PropertyParentCategoryData propertyParentCategoryData = null;
    List<PropertyChildCategoryData> propertyChildCategoryDataList;
    PropertyChildCategoryData propertyChildCategoryData = null;

    List<PropertyFeaturesData> propertyFeaturesDataSelectedList;
    PropertyFeaturesData propertyFeaturesData = null;
    List<PropertyNearByData> propertyNearByDataSelectedList;
    PropertyNearByData propertyNearByData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_property_advance_activity);

        propertyParentCategoryDataList = new ArrayList<>();
        propertyChildCategoryDataList = new ArrayList<>();

        propertyFeaturesDataSelectedList = new ArrayList<>();
        propertyNearByDataSelectedList = new ArrayList<>();

        unitsDataList = new ArrayList<>();
        currencyDataList = new ArrayList<>();

        scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(50);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.btn_chat);
        mProfileButton = findViewById(R.id.btn_profile);
        mOnlineIcon = findViewById(R.id.user_online_icon);


        mContentOne = findViewById(R.id.content_step_one);
        mContentTwo = findViewById(R.id.content_step_two);
        mContentThree = findViewById(R.id.content_step_three);
        mContentFour = findViewById(R.id.content_step_four);

        propertyTypesChecks = findViewById(R.id.property_types_radio_group);
        propertyTypesChildChecks = findViewById(R.id.types_child_radio_group);
        bedroomsChecks = findViewById(R.id.bedroom_radio_group);
        bathroomsChecks = findViewById(R.id.bathroom_radio_group);
        featuresChecks = findViewById(R.id.features_view);
        nearByChecks = findViewById(R.id.nearby_view);

        mChangeCurrency = findViewById(R.id.txt_change_currency);
        mCurrencyText = findViewById(R.id.txt_currency);
        mChangeAreaUnit = findViewById(R.id.txt_change_area_unit);
        mAreaTextUnit = findViewById(R.id.txt_area_unit);

        mChangeCurrency.setOnClickListener(this);
        mChangeAreaUnit.setOnClickListener(this);

        dialog = new Dialog(ListPropertyAdvance.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_radio_group_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialoglistView = dialog.findViewById(R.id.list);
        mDialogTitle = dialog.findViewById(R.id.tv_title);

        mButtonPhoto = findViewById(R.id.btn_photo);
        mButtonVideo = findViewById(R.id.btn_video);
        mButtonMaps = findViewById(R.id.btn_maps);
        mButtonAudio = findViewById(R.id.btn_audio);

        mButtonPhoto.setOnClickListener(this);
        mButtonVideo.setOnClickListener(this);
        mButtonMaps.setOnClickListener(this);
        mButtonAudio.setOnClickListener(this);

        mNextButton = findViewById(R.id.btn_next);
        mPrevButtonTwo = findViewById(R.id.btn_prev_two);
        mNextButtonTwo = findViewById(R.id.btn_next_two);
        mPrevButtonThree = findViewById(R.id.btn_prev_three);
        mNextButtonThree = findViewById(R.id.btn_next_three);
        mPrevButtonFour = findViewById(R.id.btn_prev_four);
        mSubmitButton = findViewById(R.id.btn_submit);

        mNextButton.setOnClickListener(this);
        mPrevButtonTwo.setOnClickListener(this);
        mNextButtonTwo.setOnClickListener(this);
        mPrevButtonThree.setOnClickListener(this);
        mNextButtonThree.setOnClickListener(this);
        mPrevButtonFour.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
        mProfileButton.setOnClickListener(this);

        //PropertyTypes Layout
        setPropertyTypesLayout();

        //PropertyTypesChild Layout
        new PropertyChildCategoryApiHelper().readPropertyChildCatergoryList(new PropertyChildCategoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyChildCategoryUtils> propertyChildCategoryUtils) {
                if (propertyChildCategoryUtils.get(0).getNoOfRecords() > 0){
                    propertyChildCategoryDataList.clear();
                    propertyChildCategoryDataList = propertyChildCategoryUtils.get(0).getData();
                }
            }
        }, ListPropertyAdvance.this);

        //bedrooms Layout
        setBedroomsLayout();

        //bathrooms Layout
        setBathroomLayout();

        //Property Features Layout
        setPropertyFeaturesLayout();

        //Property Near By Layout
        setPropertyNearByLayout();

        //GetCurrenciew
        getCurrenriesData();

        //get area units
        getAreaUnits();
    }

    private void getCurrenriesData() {
        new CurrencyApiHelper().readCurrencyList(new CurrencyApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<CurrencyData> currencyData) {
                currencyDataList.clear();
                currencyDataList = currencyData;
            }
        }, ListPropertyAdvance.this);
    }

    private void getAreaUnits() {
        new UnitApiHelper().readUnitsList(new UnitApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UnitsData> unitsData) {
                unitsDataList.clear();
                unitsDataList = unitsData;
            }
        }, ListPropertyAdvance.this);
    }

    private void setPropertyTypesLayout() {
        new PropertyParentCatergoryApiHelper().readPropertyParentCatergoryList(new PropertyParentCatergoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyParentCategoryUtils> propertyParentCategoryUtils) {
                if (propertyParentCategoryUtils.get(0).getNoOfRecords() > 0){
                    propertyTypesChecks.removeAllViews();
                    for ( int i = 0; i < propertyParentCategoryUtils.get(0).getData().size(); i++) {
                        RadioButton button = new RadioButton(ListPropertyAdvance.this);
                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(210, 60);
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
                        button.setPadding(0,0,0,0);

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
        }, ListPropertyAdvance.this);
    }

    private void setPropertyTypesChildLayout(PropertyParentCategoryData propertyParentCategoryData) {

        if (propertyParentCategoryData != null && !propertyChildCategoryDataList.isEmpty()){

//            RecyclerView recyclerViewchild = findViewById(R.id.recyclerviewchild);
//            //basic yak shaving required
//            recyclerViewchild.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//            recyclerViewchild.setAdapter(new PropertyChildCategoryRadioAdapter(propertyChildCategoryDataList, this));

            propertyTypesChildChecks.removeAllViews();
            for ( int i = 0; i < propertyChildCategoryDataList.size(); i++) {
                if (propertyChildCategoryDataList.get(i).getPropertyParentCategoryId().equals(propertyParentCategoryData.getId())){

                    LinearLayout parent = new LinearLayout(ListPropertyAdvance.this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    //addContentView(parent,layoutParams);

                    parent.setOrientation(LinearLayout.VERTICAL);
                    layoutParams.setMargins(10, 0, 10, 0);
                    parent.setGravity(Gravity.CENTER);
                    parent.setLayoutParams(layoutParams);

                    ToggleButton toggle = new ToggleButton(ListPropertyAdvance.this);
                    toggle.setId(Integer.parseInt(propertyChildCategoryDataList.get(i).getId()));
                    toggle.setTag(propertyChildCategoryDataList.get(i).getId());
                    Log.d("TAg", "setPropertyTypesChildLayout: "+toggle.getTag());
                    ViewGroup.LayoutParams togglelayout = new ViewGroup.LayoutParams(100,100);
                    toggle.setLayoutParams(togglelayout);
                    toggle.setText("");
                    toggle.setTextOn("");
                    toggle.setTextOff("");

                    //toggle.setButtonDrawable(R.drawable.toggle_selector_round_with_greycolor);
                    toggle.setBackground(getResources().getDrawable(R.drawable.toggle_selector_round_with_greycolor));
                    TextView tv = new TextView(ListPropertyAdvance.this);
                    ViewGroup.LayoutParams tvlayout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tv.setText(propertyChildCategoryDataList.get(i).getTitle());
                    tv.setLayoutParams(tvlayout);

                    parent.addView(toggle);
                    parent.addView(tv);

                    propertyTypesChildChecks.addView(parent);

//                    int finalI = i;
//                    toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            if (b){
//                                Log.d("TAg", "current: "+toggle.getTag());
//                                propertyChildCategoryData = propertyChildCategoryDataList.get(finalI);
//                                for ( PropertyChildCategoryData child : propertyChildCategoryDataList){
//                                    if (compoundButton.getTag() ==  child.getId()){
//                                        toggle.setChecked(true);
//                                        Log.d("TAg", "loop: "+toggle.getTag());
//                                    }
//                                }
//                            }
//                        }
//                    });

                }
            }
        }
    }

    private void setBedroomsLayout() {
        bedroomsChecks.removeAllViews();
        for (int i = 0; i < bedrooms.length; i++) {
            RadioButton button = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
            if (i == 0){
                button.setChecked(true);
                button.setTextColor(getResources().getColor(R.color.colorWhite));
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
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
            if (i == 0){
                button.setChecked(true);
                button.setTextColor(getResources().getColor(R.color.colorWhite));
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

    private void setPropertyFeaturesLayout() {
        new PropertyFeaturesApiHelper().readPropertyFeaturesList(new PropertyFeaturesApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyFeaturesUtils> propertyFeaturesUtils) {
                if (propertyFeaturesUtils.get(0).getNoOfRecords() > 0){
                    featuresChecks.removeAllViews();
                    propertyFeaturesDataSelectedList.clear();
                    for ( int i = 0; i < propertyFeaturesUtils.get(0).getData().size(); i++) {
                        CheckBox button = new CheckBox(ListPropertyAdvance.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (i == 0){
                            params.setMargins(40, 0, 0, 0);
                        }else {
                            params.setMargins(10, 0, 10, 0);
                        }
                        button.setLayoutParams(params);
                        button.setGravity(Gravity.CENTER);
                        button.setId(Integer.parseInt(propertyFeaturesUtils.get(0).getData().get(i).getId()));
                        button.setText(propertyFeaturesUtils.get(0).getData().get(i).getTitle());
                        button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                        button.setButtonDrawable(null);
                        button.setPadding(30,0,30,0);

                        //Performing on Check
                        int finalI = i;
                        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b){
                                    propertyFeaturesDataSelectedList.add(propertyFeaturesUtils.get(0).getData().get(finalI));
                                    button.setTextColor(getResources().getColor(R.color.colorWhite));
                                }else {
                                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                                    for (int d = 0; d < propertyFeaturesDataSelectedList.size();d++) {
                                        if (propertyFeaturesDataSelectedList.get(d).getId().equals(propertyFeaturesUtils.get(0).getData().get(finalI).getId())){
                                            propertyFeaturesDataSelectedList.remove(d);
                                        }
                                    }
                                }
                            }
                        });
                        featuresChecks.addView(button);
                    }
                }
            }
        }, ListPropertyAdvance.this);
    }

    private void setPropertyNearByLayout() {
        new PropertyNearByApiHelper().readPropertyNearByList(new PropertyNearByApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyNearByUtils> propertyNearByUtils) {
                if (propertyNearByUtils.get(0).getNoOfRecords() > 0){
                    nearByChecks.removeAllViews();
                    propertyNearByDataSelectedList.clear();
                    for ( int i = 0; i < propertyNearByUtils.get(0).getData().size(); i++) {
                        CheckBox button = new CheckBox(ListPropertyAdvance.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (i == 0){
                            params.setMargins(40, 0, 0, 0);
                        }else {
                            params.setMargins(10, 0, 10, 0);
                        }
                        button.setLayoutParams(params);
                        button.setGravity(Gravity.CENTER);
                        button.setId(Integer.parseInt(propertyNearByUtils.get(0).getData().get(i).getId()));
                        button.setText(propertyNearByUtils.get(0).getData().get(i).getTitle());
                        button.setBackgroundResource(R.drawable.toggle_selector_round_with_greycolor); // This is a custom button drawable, defined in XML
                        button.setButtonDrawable(null);
                        button.setPadding(30,0,30,0);

                        //Performing on Check
                        int finalI = i;
                        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b){
                                    propertyNearByDataSelectedList.add(propertyNearByUtils.get(0).getData().get(finalI));
                                    button.setTextColor(getResources().getColor(R.color.colorWhite));
                                }else {
                                    button.setTextColor(getResources().getColor(R.color.colorBlack));
                                }
                            }
                        });
                        nearByChecks.addView(button);
                    }
                }
            }
        }, ListPropertyAdvance.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(ListPropertyAdvance.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.txt_change_area_unit:
                mDialogTitle.setText("Select Area Unit");
                UnitArrayAdapter unitArrayAdapter = new UnitArrayAdapter(ListPropertyAdvance.this,unitsDataList);
                mDialoglistView.setAdapter(unitArrayAdapter);
                dialog.show();
                mDialoglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        unitsData = (UnitsData) adapterView.getItemAtPosition(i);
                        Toast.makeText(ListPropertyAdvance.this, unitsData.getTitle(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.txt_change_currency:
                mDialogTitle.setText("Select Currency");
                CurrencyArrayAdapter currencyArrayAdapter = new CurrencyArrayAdapter(ListPropertyAdvance.this,currencyDataList);
                mDialoglistView.setAdapter(currencyArrayAdapter);
                dialog.show();
                mDialoglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        currencyData = (CurrencyData) adapterView.getItemAtPosition(i);
                        Toast.makeText(ListPropertyAdvance.this, currencyData.getTitle(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_photo:
                //Todo Photo
                mButtonPhoto.startAnimation(scaleAnimation);
                break;
            case R.id.btn_video:
                //Todo Video
                mButtonVideo.startAnimation(scaleAnimation);
                break;
            case R.id.btn_maps:
                //Todo Maps
                mButtonMaps.startAnimation(scaleAnimation);
                break;
            case R.id.btn_audio:
                //Todo Audio
                mButtonAudio.startAnimation(scaleAnimation);
                break;
            case R.id.btn_next:
                mContentOne.setVisibility(View.GONE);
                mContentTwo.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_prev_two:
                mContentOne.setVisibility(View.VISIBLE);
                mContentTwo.setVisibility(View.GONE);
                break;
            case R.id.btn_next_two:
                mContentThree.setVisibility(View.VISIBLE);
                mContentTwo.setVisibility(View.GONE);
                break;
            case R.id.btn_prev_three:
                mContentThree.setVisibility(View.GONE);
                mContentTwo.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_next_three:
                mContentThree.setVisibility(View.GONE);
                mContentFour.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_prev_four:
                mContentThree.setVisibility(View.VISIBLE);
                mContentFour.setVisibility(View.GONE);
                break;
            case R.id.btn_submit:
                Toast.makeText(this, "Data saved Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
