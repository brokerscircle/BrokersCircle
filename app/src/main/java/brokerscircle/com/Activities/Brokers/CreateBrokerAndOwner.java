package brokerscircle.com.Activities.Brokers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import brokerscircle.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateBrokerAndOwner extends AppCompatActivity {

    LinearLayout mCloseBtn;
    CircleImageView mProfileImage, mPostImage;
    EditText mNameEditText, mEmailEditText, mPasswordEditText, mCNICEditText, mMobileEditText;
    ImageView mNameDrawable, mEmailDrawable, mPasswordDrawable, mMobileDrawable;
    Spinner mEstateSpinner, mTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_broker_and_owner_activity);

        mCloseBtn = findViewById(R.id.btnclose);
        mProfileImage = findViewById(R.id.profile_image);
        mProfileImage = findViewById(R.id.iv_image);
        mNameEditText = findViewById(R.id.et_name);
        mNameDrawable = findViewById(R.id.drawable_name);
        mEmailEditText = findViewById(R.id.et_email);
        mEmailDrawable = findViewById(R.id.drawable_email);
        mPasswordEditText = findViewById(R.id.et_password);
        mPasswordDrawable = findViewById(R.id.drawable_password);
        mEstateSpinner = findViewById(R.id.estae_spinner);
        mCNICEditText = findViewById(R.id.et_cnic);
        mMobileEditText = findViewById(R.id.et_mobile);
        mMobileDrawable = findViewById(R.id.drawable_mobile);
        mTypeSpinner = findViewById(R.id.type_spinner);

    }
}
