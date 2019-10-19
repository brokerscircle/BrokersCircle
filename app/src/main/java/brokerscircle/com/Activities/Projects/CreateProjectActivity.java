package brokerscircle.com.Activities.Projects;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.R;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.CurrencyArrayAdapter;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.UnitArrayAdapter;
import brokerscircle.com.api_helpers.CurrencyApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyChildCategoryApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyFeaturesApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyNearByApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyParentCatergoryApiHelper;
import brokerscircle.com.api_helpers.UnitApiHelper;
import brokerscircle.com.model.Currency.CurrencyData;
import brokerscircle.com.model.Property.PropertyFeatures.PropertyFeaturesData;
import brokerscircle.com.model.Property.PropertyFeatures.PropertyFeaturesUtils;
import brokerscircle.com.model.Property.PropertyNearBy.PropertyNearByData;
import brokerscircle.com.model.Property.PropertyNearBy.PropertyNearByUtils;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryData;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryUtils;
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryData;
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryUtils;
import brokerscircle.com.model.Units.UnitsData;

public class CreateProjectActivity extends AppCompatActivity implements View.OnClickListener {

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
    private ViewGroup propertyTypesChildChecks;
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
        setContentView(R.layout.create_project_activity);

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

        propertyTypesChecks = findViewById(R.id.types_radio_group);
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

        dialog = new Dialog(CreateProjectActivity.this);
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
        }, CreateProjectActivity.this);

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
        }, CreateProjectActivity.this);
    }

    private void getAreaUnits() {
        new UnitApiHelper().readUnitsList(new UnitApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UnitsData> unitsData) {
                unitsDataList.clear();
                unitsDataList = unitsData;
            }
        }, CreateProjectActivity.this);
    }

    private void setPropertyTypesLayout() {
        new PropertyParentCatergoryApiHelper().readPropertyParentCatergoryList(new PropertyParentCatergoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyParentCategoryUtils> propertyParentCategoryUtils) {
                if (propertyParentCategoryUtils.get(0).getNoOfRecords() > 0){
                    propertyTypesChecks.removeAllViews();
                    for ( int i = 0; i < propertyParentCategoryUtils.get(0).getData().size(); i++) {
                        RadioButton button = new RadioButton(CreateProjectActivity.this);
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
        }, CreateProjectActivity.this);
    }

    private void setPropertyTypesChildLayout(PropertyParentCategoryData propertyParentCategoryData) {

        if (propertyParentCategoryData != null && !propertyChildCategoryDataList.isEmpty()){
            propertyTypesChildChecks.removeAllViews();
            for ( int i = 0; i < propertyChildCategoryDataList.size(); i++) {
                if (propertyChildCategoryDataList.get(i).getPropertyParentCategoryId().equals(propertyParentCategoryData.getId())){

                    RadioButton button = new RadioButton(CreateProjectActivity.this);
                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                    RadioGroup.LayoutParams paramindexone = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
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
                        CheckBox button = new CheckBox(CreateProjectActivity.this);
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
                                }
                            }
                        });
                        featuresChecks.addView(button);
                    }
                }
            }
        }, CreateProjectActivity.this);
    }

    private void setPropertyNearByLayout() {
        new PropertyNearByApiHelper().readPropertyNearByList(new PropertyNearByApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyNearByUtils> propertyNearByUtils) {
                if (propertyNearByUtils.get(0).getNoOfRecords() > 0){
                    nearByChecks.removeAllViews();
                    propertyNearByDataSelectedList.clear();
                    for ( int i = 0; i < propertyNearByUtils.get(0).getData().size(); i++) {
                        CheckBox button = new CheckBox(CreateProjectActivity.this);
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
        }, CreateProjectActivity.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(CreateProjectActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.txt_change_area_unit:
                mDialogTitle.setText("Select Area Unit");
                UnitArrayAdapter unitArrayAdapter = new UnitArrayAdapter(CreateProjectActivity.this,unitsDataList);
                mDialoglistView.setAdapter(unitArrayAdapter);
                dialog.show();
                mDialoglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        unitsData = (UnitsData) adapterView.getItemAtPosition(i);
                        Toast.makeText(CreateProjectActivity.this, unitsData.getTitle(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.txt_change_currency:
                mDialogTitle.setText("Select Currency");
                CurrencyArrayAdapter currencyArrayAdapter = new CurrencyArrayAdapter(CreateProjectActivity.this,currencyDataList);
                mDialoglistView.setAdapter(currencyArrayAdapter);
                dialog.show();
                mDialoglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        currencyData = (CurrencyData) adapterView.getItemAtPosition(i);
                        Toast.makeText(CreateProjectActivity.this, currencyData.getTitle(), Toast.LENGTH_SHORT).show();
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
