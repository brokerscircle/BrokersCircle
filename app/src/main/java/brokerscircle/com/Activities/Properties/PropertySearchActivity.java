package brokerscircle.com.Activities.Properties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.R;
import brokerscircle.com.api_helpers.Property.PropertyChildCategoryApiHelper;
import brokerscircle.com.api_helpers.Property.PropertyParentCatergoryApiHelper;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryData;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryUtils;
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryData;
import brokerscircle.com.model.Property.Property_Parent_Category.PropertyParentCategoryUtils;

public class PropertySearchActivity extends AppCompatActivity implements View.OnClickListener {

    //Top bar
    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;

    private RelativeLayout mSearchProperty;

    private ViewGroup propertyTypesChecks;
    private ViewGroup propertyTypesChildChecks;
    private ViewGroup bedroomsChecks;
    private ViewGroup bathroomsChecks;

    String[] bedrooms = new String[] {"Studio", "1", "2", "3", "4", "5", "6", "7", "8", "9+"};
    String[] bathrooms = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9+"};

    List<PropertyParentCategoryData> propertyParentCategoryDataList;
    PropertyParentCategoryData propertyParentCategoryData = null;
    List<PropertyChildCategoryData> propertyChildCategoryDataList;
    PropertyChildCategoryData propertyChildCategoryData = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_search_activity);

        initTopBar();

        initContent();

    }

    private void initTopBar() {

        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.btn_chat);
        mProfileButton = findViewById(R.id.btn_profile);
        mOnlineIcon = findViewById(R.id.user_online_icon);

        mBackButton.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
        mProfileButton.setOnClickListener(this);

    }

    private void initContent() {

        propertyParentCategoryDataList = new ArrayList<>();
        propertyChildCategoryDataList = new ArrayList<>();

        propertyTypesChecks = findViewById(R.id.property_types_radio_group);
        propertyTypesChildChecks = findViewById(R.id.types_child_radio_group);
        bedroomsChecks = findViewById(R.id.bedroom_radio_group);
        bathroomsChecks = findViewById(R.id.bathroom_radio_group);

        mSearchProperty = findViewById(R.id.btn_search);

        mSearchProperty.setOnClickListener(this);

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
        }, PropertySearchActivity.this);

        //bedrooms Layout
        setBedroomsLayout();

        //bathrooms Layout
        setBathroomLayout();

    }

    private void setPropertyTypesLayout() {
        new PropertyParentCatergoryApiHelper().readPropertyParentCatergoryList(new PropertyParentCatergoryApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyParentCategoryUtils> propertyParentCategoryUtils) {
                if (propertyParentCategoryUtils.get(0).getNoOfRecords() > 0){
                    propertyTypesChecks.removeAllViews();
                    for ( int i = 0; i < propertyParentCategoryUtils.get(0).getData().size(); i++) {
                        RadioButton button = new RadioButton(PropertySearchActivity.this);
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
        }, PropertySearchActivity.this);
    }

    private void setPropertyTypesChildLayout(PropertyParentCategoryData propertyParentCategoryData) {

        if (propertyParentCategoryData != null && !propertyChildCategoryDataList.isEmpty()){
            propertyTypesChildChecks.removeAllViews();
            for ( int i = 0; i < propertyChildCategoryDataList.size(); i++) {
                if (propertyChildCategoryDataList.get(i).getPropertyParentCategoryId().equals(propertyParentCategoryData.getId())){

                    RadioButton button = new RadioButton(PropertySearchActivity.this);
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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(PropertySearchActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.btn_search:
                intent = new Intent(PropertySearchActivity.this, ViewAllPropertiesActivity.class);
                startActivity(intent);
                break;
        }
    }
}
