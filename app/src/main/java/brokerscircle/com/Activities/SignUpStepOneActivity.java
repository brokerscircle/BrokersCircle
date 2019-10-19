package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brokerscircle.com.R;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.CityArrayAdapter;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.CompanyArrayAdapter;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.CountryArrayAdapter;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.LocationArrayAdapter;
import brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters.PhoneCountryCodeArrayAdapter;
import brokerscircle.com.api_helpers.AreaApiHelper;
import brokerscircle.com.api_helpers.CityAPIHelper;
import brokerscircle.com.api_helpers.CountryAPIHelper;
import brokerscircle.com.api_helpers.LocationApiHelper;
import brokerscircle.com.api_helpers.PhoneCountryCodeAPIHelper;
import brokerscircle.com.api_helpers.ProvinceAPIHelper;
import brokerscircle.com.api_helpers.RealEstateDatabaseHelper;
import brokerscircle.com.api_helpers.SignUp.CheckEmailFoundApiHelper;
import brokerscircle.com.api_helpers.SignUp.CheckPhoneFoundApiHelper;
import brokerscircle.com.interfaces.IResult;
import brokerscircle.com.model.Area.AreaData;
import brokerscircle.com.model.Area.AreaUtil;
import brokerscircle.com.model.City.CityData;
import brokerscircle.com.model.Country.CountryData;
import brokerscircle.com.model.Location.LocationData;
import brokerscircle.com.model.Location.LocationUtil;
import brokerscircle.com.model.PhoneCountryCode.PhoneCountryCodeData;
import brokerscircle.com.model.Province.ProvinceData;
import brokerscircle.com.model.RealEstates.RealEstateData;
import brokerscircle.com.model.Signup.CheckEmail.CheckEmailFoundUtil;
import brokerscircle.com.model.Signup.CheckPhone.CheckPhoneFoundUtil;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.services.VolleyService;
import brokerscircle.com.util.Constant;
import brokerscircle.com.util.Helper;
import brokerscircle.com.util.KeyboardUtil;

public class SignUpStepOneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpStepOneActivity";
    String signUPURL = "https://brokerscircles.com/admin/public/sign-up/create?app_id="+Constant.APP_ID+"&app_key="+Constant.APP_KEY;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    boolean checkEmail = false;
    boolean checkPhone = false;

    Helper helper;
    KeyboardUtil keyboardUtil;

    RelativeLayout contentLayoutOne, contentLayoutTwo, contentLayoutThree;
    private View progress_overlay;
    private TextView mLoadingHeading;

    //Contens of layout one
    private TabLayout mAccountTypeSelect;
    private EditText mNameEdittext, mEmailEdittext, mPhoneEdittext, mPasswordEdittext;
    private ImageView mDrawableName, mDrawableEmail, mDrawablePhone, mDrawablePassword, mDrawablePhoneCode;
    private AutoCompleteTextView mPhoneCodeAutoEditText;
    private RelativeLayout mButtonNext;
    private TextView mBacktoSigninTV;
    private SpinKitView mLoadingEmail, mLoadingPhone;

    //Contents of layout Two
    private TabLayout mGenderSelect;
    private EditText mDateOfBirthEdittext, mCnicEdittext;
    private ImageView mDrawableDateOfBirth, mDrawableCnic, mDrawableCountry, mDrawableCity, mDrawableLocation, mDrawablePostalCode;
    private AutoCompleteTextView mCountryAutoComplete, mCityAutoComplete, mLocationAutoComplete, mPostalCodeEdittext;
    private RelativeLayout btnNextTwo, btnPrevTwo;

    CountryArrayAdapter countryArrayAdapter = null;
    //String dateofBirth = "";
    List<CountryData> countryDatalist = null;
    List<CityData> cityDatalist = null;
    List<CityData> citiesByProvices = null;
    List<AreaData> areaByCity = null;
    List<LocationData> locationDataList = null;
    List<LocationData> locationByArea = null;
    List<PhoneCountryCodeData> phoneCountryCodeDataList = null;
    List<RealEstateData> companyList = null;

    RealEstateData realEstateData1 = null;
    PhoneCountryCodeData phoneCountryCodeData = null;
    CountryData countryData = null;
    CityData cityData = null;
    ProvinceData provinceData = null;
    LocationData locationData = null;

    //Contents of layout Three
    private RelativeLayout btnSubmit, btnPrevThree;
    private EditText mAddressEditText;
    private ImageView mAddressDrawable, mCompanyDrawable, mExperienceDrawable;
    private AutoCompleteTextView mCompanyAutoEditText;
    private Spinner mExperienceSpinner;
    private CheckBox mTermandConditionCheckbox;

    private LinearLayout mBottomSheet, mBottomSheetLoading;
    private BottomSheetBehavior bottomSheetBehavior;
    private EditText mBottomSheetSearch;
    private ListView mBottomSheetListView;
    private TextView mBottomSheetTitle;

    //Strings tabs
    String accountType = "";
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        keyboardUtil = KeyboardUtil.getInstance(this);
        helper.clearSignUpCollection();
        //Helper Instance
        helper = new Helper(this);
        LoginUser user = helper.getLoggedInUser();
        if (user != null) {    //Check if user if logged in
            Log.d(TAG, "onCreate: user Success "+user.getResponse().getCode());
            done(user);
        }else {
            setContentView(R.layout.sign_up_activity_step_one);

            countryDatalist = new ArrayList<>();
            cityDatalist = new ArrayList<>();
            locationDataList = new ArrayList<>();
            phoneCountryCodeDataList = new ArrayList<>();
            companyList = new ArrayList<>();

            contentLayoutOne = findViewById(R.id.contentlayoutone);
            contentLayoutTwo = findViewById(R.id.contentlayouttwo);
            contentLayoutThree = findViewById(R.id.contentlayoutthree);

            //Progress Layout
            progress_overlay = findViewById(R.id.progress_overlay);
            mLoadingHeading = findViewById(R.id.tvheading);

            //Contens of layout one
            mAccountTypeSelect = findViewById(R.id.tabs);
            mNameEdittext = findViewById(R.id.et_name);
            mDrawableName = findViewById(R.id.drawable_name);
            mEmailEdittext = findViewById(R.id.et_email);
            mDrawableEmail = findViewById(R.id.drawable_email);
            mLoadingEmail = findViewById(R.id.loading_email);
            mPasswordEdittext = findViewById(R.id.et_password);
            mDrawablePassword = findViewById(R.id.drawable_password);
            mPhoneCodeAutoEditText = findViewById(R.id.et_phone_code);
            mDrawablePhoneCode = findViewById(R.id.drawable_phone_code);
            mPhoneEdittext = findViewById(R.id.et_phone);
            mDrawablePhone = findViewById(R.id.drawable_phone);
            mLoadingPhone = findViewById(R.id.loading_phone);
            mButtonNext = findViewById(R.id.btn_next);
            mBacktoSigninTV = findViewById(R.id.tv_backtosignin);

            //Contents of layout Two
            mGenderSelect = findViewById(R.id.tabsgender);
            mDateOfBirthEdittext = findViewById(R.id.et_dob);
            mDrawableDateOfBirth = findViewById(R.id.drawable_date);
            mCnicEdittext = findViewById(R.id.et_cnic);
            mDrawableCnic = findViewById(R.id.drawable_cnic);
            mCountryAutoComplete = findViewById(R.id.et_country);
            mDrawableCountry = findViewById(R.id.drawable_country);
            mCityAutoComplete = findViewById(R.id.et_city);
            mDrawableCity = findViewById(R.id.drawable_city);
            mLocationAutoComplete = findViewById(R.id.et_location);
            mDrawableLocation = findViewById(R.id.drawable_location);
            mPostalCodeEdittext = findViewById(R.id.et_postalcode);
            mDrawablePostalCode = findViewById(R.id.drawable_postalcode);
            btnNextTwo = findViewById(R.id.btn_next_two);
            btnPrevTwo = findViewById(R.id.btn_prev_two);

            mCountryAutoComplete.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdownbackgroundround));
            mCountryAutoComplete.setDropDownVerticalOffset(5);
            mCountryAutoComplete.setDropDownHorizontalOffset(5);
            mCityAutoComplete.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.dropdownbackgroundround));

            //Contents of layout Three
            mAddressEditText = findViewById(R.id.et_address);
            mAddressDrawable = findViewById(R.id.drawable_address);
            mCompanyAutoEditText = findViewById(R.id.et_company);
            mCompanyDrawable = findViewById(R.id.drawable_company);
            mExperienceSpinner = findViewById(R.id.sp_exp);
            mExperienceDrawable = findViewById(R.id.drawable_exp);
            mTermandConditionCheckbox = findViewById(R.id.checkbox_term_condition);
            btnSubmit = findViewById(R.id.btn_submit);
            btnPrevThree = findViewById(R.id.btn_prev_three);

            mBottomSheet  = findViewById(R.id.bottom_sheet_layout_list);
            bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            mBottomSheetSearch = findViewById(R.id.search_view);
            mBottomSheetListView = findViewById(R.id.dialoglist);
            mBottomSheetTitle = findViewById(R.id.bottomsheettitle);
            mBottomSheetLoading = findViewById(R.id.loadingdata);

            //Content one by default tab
            mAccountTypeSelect.getTabAt(1).select();
            mGenderSelect.getTabAt(0).select();
            //default tab text
            accountType = mAccountTypeSelect.getTabAt(1).getText().toString();
            gender = mAccountTypeSelect.getTabAt(0).getText().toString();

            //Tabs Account Type selected listner
            mAccountTypeSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    accountType = tab.getText().toString();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            //Gender Select listner
            mGenderSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    gender = tab.getText().toString();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            editTextWatchers();

            //Onclick
            mButtonNext.setOnClickListener(this);
            mBacktoSigninTV.setOnClickListener(this);
            btnNextTwo.setOnClickListener(this);
            btnPrevTwo.setOnClickListener(this);
            mDateOfBirthEdittext.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);
            btnPrevThree.setOnClickListener(this);

            getPhoneCountryCode();
            getCountries();
            getCities();
            getLocationsByArea();

            getCompanies();
        }
    }

    private void editTextWatchers() {

        mNameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //mEmailEdittext.getText().toString().trim().length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.personNameValidation(mNameEdittext, mDrawableName);
            }
        });

        mEmailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.emailValid(mEmailEdittext, mDrawableEmail);
            }
        });

        mPasswordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.passwordValid(mPasswordEdittext, mDrawablePassword);
            }
        });

        mPhoneCodeAutoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.nullFieldValidation(mPhoneCodeAutoEditText, mDrawablePhoneCode,10,"Please select your country code!");
            }
        });

        mPhoneEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.phoneValid(mPhoneEdittext, mDrawablePhone);
            }
        });

        mDateOfBirthEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (countryArrayAdapter != null) {
                    countryArrayAdapter.getFilter().filter(charSequence);
                } else {
                    Log.d("filter", "no filter availible");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.nullFieldValidation(mDateOfBirthEdittext, mDrawableDateOfBirth,10, "Please enter your country!");
            }
        });

        mCnicEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.cnicValidation(mCnicEdittext, mDrawableCnic);
            }
        });

        mCountryAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mCityAutoComplete.setText("");
                helper.nullFieldValidation(mCountryAutoComplete, mDrawableCountry, 250,"Please enter your country!");
            }
        });

        mCityAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mLocationAutoComplete.setText("");
                helper.nullFieldValidation(mCityAutoComplete, mDrawableCity,250, "Please Select city");

            }
        });

        mLocationAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.nullFieldValidation(mLocationAutoComplete, mDrawableLocation,250, "Please Select Location");
            }
        });

        mPostalCodeEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.isLengthValidation(mPostalCodeEdittext, mDrawablePostalCode,10 );
            }
        });

        mAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.nullFieldValidation(mAddressEditText, mAddressDrawable,250, "Please enter your address!");
            }
        });

        mCompanyAutoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                helper.nullFieldValidation(mCompanyAutoEditText, mCompanyDrawable,50, "Please enter your company!");
            }
        });

    }

    private void getCompanies() {
        final CompanyArrayAdapter[] companyArrayAdapter = new CompanyArrayAdapter[1];
        new RealEstateDatabaseHelper().readEstateList(new RealEstateDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<RealEstateData> realEstateData) {

                mCompanyAutoEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }else {
                            mBottomSheetTitle.setText("Select company");
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            mBottomSheetSearch.setText("");
                            if (realEstateData.isEmpty()){
                                mBottomSheetLoading.setVisibility(View.VISIBLE);
                            }else {
                                mBottomSheetLoading.setVisibility(View.GONE);
                                companyArrayAdapter[0] = new CompanyArrayAdapter(SignUpStepOneActivity.this, realEstateData);
                                mBottomSheetListView.setAdapter(companyArrayAdapter[0]);

                                mBottomSheetSearch.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        companyArrayAdapter[0].getFilter().filter(charSequence.toString());
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

                                mBottomSheetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        realEstateData1 = (RealEstateData) adapterView.getItemAtPosition(i);
                                        mCompanyAutoEditText.setText(realEstateData1.getName());
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                    }
                                });
                            }
                        }
                    }
                });

            }
        }, SignUpStepOneActivity.this);

    }

    private void getLocationsByArea() {
        new LocationApiHelper().readLocationList(new LocationApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<LocationUtil> locationData) {
                locationDataList.clear();
                locationDataList = locationData.get(0).getData();
            }
        }, SignUpStepOneActivity.this);
    }

    private void getPhoneCountryCode() {
        final PhoneCountryCodeArrayAdapter[] phoneCountryCodeArrayAdapter = new PhoneCountryCodeArrayAdapter[1];
        new PhoneCountryCodeAPIHelper().readPhoneCodeList(new PhoneCountryCodeAPIHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PhoneCountryCodeData> phoneCountryCodeData1) {

                mPhoneCodeAutoEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }else {
                            mBottomSheetTitle.setText("Select country phone code");
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                            if(phoneCountryCodeData1.isEmpty()){
                                mBottomSheetLoading.setVisibility(View.VISIBLE);
                            }else {
                                mBottomSheetLoading.setVisibility(View.GONE);
                                phoneCountryCodeArrayAdapter[0] = new PhoneCountryCodeArrayAdapter(SignUpStepOneActivity.this, phoneCountryCodeData1);
                                mBottomSheetListView.setAdapter(phoneCountryCodeArrayAdapter[0]);

                                mBottomSheetSearch.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        phoneCountryCodeArrayAdapter[0].getFilter().filter(charSequence.toString());
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

                                mBottomSheetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        phoneCountryCodeData = (PhoneCountryCodeData) adapterView.getItemAtPosition(i);
                                        mPhoneCodeAutoEditText.setText(phoneCountryCodeData.getCode());
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }, SignUpStepOneActivity.this);
    }

    private void getCities() {
        new CityAPIHelper().readCitiesList(new CityAPIHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<CityData> cityData) {
                cityDatalist.clear();
                cityDatalist = cityData;
            }
        }, SignUpStepOneActivity.this);

    }

    private void getCountries() {
        final CountryArrayAdapter[] countryArrayAdapter = new CountryArrayAdapter[1];
        new CountryAPIHelper().readCountryList(new CountryAPIHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<CountryData> countryAPIData) {
                mCountryAutoComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }else {
                            mBottomSheetTitle.setText("Select country");
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            mBottomSheetSearch.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            mBottomSheetSearch.setText("");

                            if (countryAPIData.isEmpty()){
                                mBottomSheetLoading.setVisibility(View.VISIBLE);
                            }else {
                                mBottomSheetLoading.setVisibility(View.GONE);
                                countryArrayAdapter[0] = new CountryArrayAdapter(SignUpStepOneActivity.this, countryAPIData);
                                mBottomSheetListView.setAdapter(countryArrayAdapter[0]);

                                mBottomSheetSearch.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        countryArrayAdapter[0].getFilter().filter(charSequence.toString());
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

                                mBottomSheetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        countryData = (CountryData) adapterView.getItemAtPosition(i);
                                        getProvincesByCountry(countryData.getId());
                                        mCountryAutoComplete.setText(countryData.getName());
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }, SignUpStepOneActivity.this);
    }

    private void getProvincesByCountry(String id) {
        final CityArrayAdapter[] cityArrayAdapter = new CityArrayAdapter[1];
        citiesByProvices = new ArrayList<>();
        new ProvinceAPIHelper().readProvincebyCountry(new ProvinceAPIHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProvinceData> provinceData) {
                citiesByProvices.clear();
                //provinceDatalist = provinceData;

                if (!provinceData.isEmpty() && !cityDatalist.isEmpty()){
                    for (ProvinceData provinceData1 : provinceData) {
                        for (CityData cityData1 : cityDatalist) {
                            if (provinceData1.getId().equals(cityData1.getProvinceId())){
                                citiesByProvices.add(cityData1);
                            }
                        }
                    }
                }

                mCityAutoComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (citiesByProvices.isEmpty()){
                            Toast.makeText(SignUpStepOneActivity.this, "Please select country first!", Toast.LENGTH_SHORT).show();
                        }else {
                            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                            }else {
                                mBottomSheetTitle.setText("Select city");
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                mBottomSheetSearch.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                                mBottomSheetSearch.setText("");

                                mBottomSheetLoading.setVisibility(View.GONE);
                                cityArrayAdapter[0] = new CityArrayAdapter(SignUpStepOneActivity.this, citiesByProvices);
                                mBottomSheetListView.setAdapter(cityArrayAdapter[0]);

                                mBottomSheetSearch.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        cityArrayAdapter[0].getFilter().filter(charSequence.toString());
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

                                mBottomSheetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        cityData = (CityData) adapterView.getItemAtPosition(i);
                                        getAreasByCity(cityData.getId());
                                        mCityAutoComplete.setText(String.format("%s, %s", cityData.getName(), cityData.getProvinceTitle()));
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                    }
                                });
                            }
                        }
                    }
                });

            }
        },SignUpStepOneActivity.this, id);
    }

    private void getAreasByCity(String id) {
        final LocationArrayAdapter[] locationArrayAdapter = new LocationArrayAdapter[1];
        locationByArea = new ArrayList<>();
        new AreaApiHelper().readAreasByCity(new AreaApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<AreaUtil> areaUtils) {
                locationByArea.clear();
                if (areaUtils.get(0).getNoOfRecords() > 0){
                    for (AreaData areaData  : areaUtils.get(0).getData()) {
                        for (LocationData locationData : locationDataList){
                            if (locationData.getAreaId().equals(areaData.getId())){
                                locationByArea.add(locationData);
                            }
                        }
                    }
                }

                mLocationAutoComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (locationByArea.isEmpty()){
                            Toast.makeText(SignUpStepOneActivity.this, "Please select city first!", Toast.LENGTH_SHORT).show();
                        }else {
                            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                            }else {
                                mBottomSheetTitle.setText("Select location");
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                mBottomSheetSearch.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                                mBottomSheetSearch.setText("");

                                if (locationByArea.isEmpty()){
                                    mBottomSheetLoading.setVisibility(View.VISIBLE);
                                }else {
                                    mBottomSheetLoading.setVisibility(View.GONE);
                                    locationArrayAdapter[0] = new LocationArrayAdapter(SignUpStepOneActivity.this, locationByArea);
                                    mBottomSheetListView.setAdapter(locationArrayAdapter[0]);

                                    mBottomSheetSearch.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            locationArrayAdapter[0].getFilter().filter(charSequence.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    });

                                    mBottomSheetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            locationData = (LocationData) adapterView.getItemAtPosition(i);
                                            mLocationAutoComplete.setText(String.format("%s, %s", locationData.getName(), locationData.getAreaName()));
                                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                                            //keyboardUtil.closeKeyboard();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });

            }
        }, SignUpStepOneActivity.this, id);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_backtosignin:
                intent = new Intent(SignUpStepOneActivity.this,MainActivity.class);
                AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
                helper.clearSignUpCollection();
                startActivity(intent);
                finish();
                break;
            case R.id.btn_next:
                if (helper.personNameValidation(mNameEdittext, mDrawableName) &&
                    helper.passwordValid(mPasswordEdittext, mDrawablePassword) &&
                    helper.nullFieldValidation(mPhoneCodeAutoEditText, mDrawablePhoneCode,10,"Please select your country code!") &&
                    helper.emailValid(mEmailEdittext, mDrawableEmail) &&
                    helper.phoneValid(mPhoneEdittext, mDrawablePhone)){

                    contentLayoutOne.setVisibility(View.GONE);
                    contentLayoutTwo.setVisibility(View.VISIBLE);
//                    checkEmailAndPhoneFound();

                }else {
                    Log.d(TAG, "onClick: "+ helper.emailValid(mEmailEdittext, mDrawableEmail));
                    Toast.makeText(this, "Please fill required fields!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_next_two:
                if (helper.nullFieldValidation(mDateOfBirthEdittext, mDrawableDateOfBirth,10,"Please select your date of birth!") &&
                        helper.nullFieldValidation(mCountryAutoComplete, mDrawableCountry, 250,"Please select your country!") &&
                        helper.nullFieldValidation(mCityAutoComplete, mDrawableCity, 250, "Please select your city!") &&
                        helper.isLengthValidation(mPostalCodeEdittext, mDrawablePostalCode,10 ) &&
                        helper.nullFieldValidation(mLocationAutoComplete, mDrawableLocation,250, "Please Select your Location")){
                    contentLayoutTwo.setVisibility(View.GONE);
                    contentLayoutThree.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_prev_two:
                contentLayoutOne.setVisibility(View.VISIBLE);
                contentLayoutTwo.setVisibility(View.GONE);
                break;
            case R.id.et_dob:
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(SignUpStepOneActivity.this, R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                                + "-" + String.valueOf(year);
                        mDateOfBirthEdittext.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
                break;
            case R.id.btn_submit:
                if (helper.nullFieldValidation(mAddressEditText, mAddressDrawable,250, "Please enter your address!") &&
                        helper.nullFieldValidation(mCompanyAutoEditText, mCompanyDrawable,250, "Please enter your company!") &&
                        helper.isSpinnerSelected(mExperienceSpinner, mExperienceDrawable) &&
                        mTermandConditionCheckbox.isChecked()){
                    //helper.companyValidation(mCompanyAutoEditText, mCompanyDrawable, companyList, 250) &&
                    collectData();
                }else {
                    if (!mTermandConditionCheckbox.isChecked()){
                        Toast.makeText(this, "Please accept term and conditions!", Toast.LENGTH_SHORT).show();
                    }
                    AndroidUtils.animateView(progress_overlay, View.GONE, 0f, 200);
                }
                break;
            case R.id.btn_prev_three:
                contentLayoutTwo.setVisibility(View.VISIBLE);
                contentLayoutThree.setVisibility(View.GONE);
                break;
        }
    }

    private void checkEmailAndPhoneFound() {
        checkEmail = false;
        checkPhone = false;
        new CheckEmailFoundApiHelper().checkEmailFound(new CheckEmailFoundApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<CheckEmailFoundUtil> emailFoundData) {
                mLoadingEmail.setVisibility(View.VISIBLE);
                if(emailFoundData.get(0).getNoOfRecords() == 0){
                    checkEmail = true;
                    mLoadingEmail.setVisibility(View.GONE);
                    mDrawableEmail.setVisibility(View.VISIBLE);
                    mDrawableEmail.setImageResource(R.drawable.ic_done_all_black_24dp);
                    mDrawableEmail.setBackgroundResource(R.drawable.circlegreen);
                    mDrawableEmail.setOnClickListener(null);
                    mEmailEdittext.setError(null);
                }else{
                    checkEmail = false;
                    mLoadingEmail.setVisibility(View.GONE);
                    mDrawableEmail.setVisibility(View.VISIBLE);
                    mDrawableEmail.setImageResource(R.drawable.ic_close_black_24dp);
                    mDrawableEmail.setBackgroundResource(R.drawable.circle_red);
                    mDrawableEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mEmailEdittext.setError("This phone already exist please try to login.", null);
                        }
                    });
                }

            }
        }, SignUpStepOneActivity.this, mEmailEdittext.getText().toString().trim());

        new CheckPhoneFoundApiHelper().checkPhoneFound(new CheckPhoneFoundApiHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<CheckPhoneFoundUtil> phoneFoundData) {
                mLoadingPhone.setVisibility(View.VISIBLE);
                if(phoneFoundData.get(0).getNoOfRecords() == 0){
                    checkPhone = true;
                    mLoadingPhone.setVisibility(View.GONE);
                    mDrawablePhone.setVisibility(View.VISIBLE);
                    mDrawablePhone.setImageResource(R.drawable.ic_done_all_black_24dp);
                    mDrawablePhone.setBackgroundResource(R.drawable.circlegreen);
                    mDrawablePhone.setOnClickListener(null);
                    mPhoneEdittext.setError(null);

                }else{
                    checkPhone = false;
                    mLoadingPhone.setVisibility(View.GONE);
                    mDrawablePhone.setVisibility(View.VISIBLE);
                    mDrawablePhone.setImageResource(R.drawable.ic_close_black_24dp);
                    mDrawablePhone.setBackgroundResource(R.drawable.circle_red);
                    mDrawablePhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPhoneEdittext.setError("This email address already exist please try to login.", null);
                        }
                    });
                }
            }
        },SignUpStepOneActivity.this, mPhoneEdittext.getText().toString().trim());

        if (checkEmail && checkPhone){
            contentLayoutOne.setVisibility(View.GONE);
            contentLayoutTwo.setVisibility(View.VISIBLE);
        }else {
            contentLayoutOne.setVisibility(View.VISIBLE);
            contentLayoutTwo.setVisibility(View.GONE);
        }

    }

    private void collectData() {
        AndroidUtils.animateView(progress_overlay, View.VISIBLE, 1.0f, 200);
        mLoadingHeading.setText("You are Done! please wait a moment we just setting up your account...");

        String name = mNameEdittext.getText().toString().trim();
        //Gernerating username
        String username = name.replaceAll(" ","-");

        String email = mEmailEdittext.getText().toString().trim();
        String password = mPasswordEdittext.getText().toString().trim();
        String phone = mPhoneEdittext.getText().toString().trim();
        String phonecodeid = phoneCountryCodeData.getId();

        //String gender = mGenderSelect.getSelectedTabPosition();
        String dateofbirth = mDateOfBirthEdittext.getText().toString().trim();
        String cnic = mCnicEdittext.getText().toString().trim();
        String countryid = countryData.getId();
        String cityid = cityData.getId();
        String provinceid = cityData.getProvinceId();
        String postalcode = mPostalCodeEdittext.getText().toString().trim();
        String areaid = locationData.getAreaId();
        String locationid = locationData.getId();

        String address = mAddressEditText.getText().toString().trim();
        String companyid = realEstateData1.getId();
        //String companyid = "5";
        String experience = "";
        if (mExperienceSpinner.getSelectedItemPosition() > 0){
            experience = mExperienceSpinner.getSelectedItem().toString();
        }else {
            experience = "1 – 3";
        }
        String condition = (mTermandConditionCheckbox.isChecked())? "true" : "false";

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);

        Map<String, String> sendObj = new HashMap<>();
        sendObj.put("company_id", companyid);
        sendObj.put("country_code_id", phonecodeid);
        sendObj.put("full_name", name);
        sendObj.put("username", username);
        sendObj.put("slug", username);
        sendObj.put("email", email);
        sendObj.put("mobile_no", phone);
        sendObj.put("password", password);
        sendObj.put("status", "Active");
        sendObj.put("country_id", countryid);
        sendObj.put("provence_id", provinceid);
        sendObj.put("city_id", cityid);
        sendObj.put("area_id", areaid);
        sendObj.put("location_id", locationid);
        sendObj.put("account_type", accountType);
        sendObj.put("gender", gender);
        sendObj.put("date_of_birth", dateofbirth);
        sendObj.put("cnic_no", cnic);
        sendObj.put("postal_code", postalcode);
        sendObj.put("contact_address", address);
        Log.d(TAG, "signInUser: "+sendObj+" Url "+signUPURL);
        mVolleyService.postDataVolley("POSTCALL", signUPURL, sendObj);

        Log.d("collectData", "collectData: "+accountType+", "+name+", "+username+", "+email+", "+password+", "+phonecodeid+" "+phone+", "+gender+", "+dateofbirth+", "
                +cnic+", "+countryid+", "+cityid+", "+postalcode+", "+locationid+", "+address+", "+companyid+", "+experience+", "+condition);

    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject responseObject = jsonObject.getJSONObject("response");
                    String error = responseObject.getString("error");
                    if (error.equals("false")){
                        Toast.makeText(SignUpStepOneActivity.this, "Successfully Created Account!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpStepOneActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                        Log.d(TAG, "Response Error " + responseObject.getString("message"));
//                        Toast.makeText(SignUpStepOneActivity.this, "Something went wrong, please try again later!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignUpStepOneActivity.this, responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                AndroidUtils.animateView(progress_overlay, View.GONE, 0, 200);
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley requester " + error);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                Toast.makeText(SignUpStepOneActivity.this, "Something went wrong, please check your internet connection again later!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void done(LoginUser user) {
        startActivity(new Intent(this, DashboardActivityTwo.class));
        finish();
    }
}
