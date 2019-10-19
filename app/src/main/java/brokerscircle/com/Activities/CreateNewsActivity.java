package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import brokerscircle.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewsActivity extends AppCompatActivity {

    LinearLayout mCloseBtn;
    CircleImageView mProfileImage, mPostImage;
    EditText mTitleEditText, mDescEditText;
    ImageView mTitleDrawable, mDescDrawable;
    Spinner mTypeSpinner;
    Button mCreateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_news_activity);

        mCloseBtn = findViewById(R.id.btnclose);
        mProfileImage = findViewById(R.id.profile_image);
        mPostImage = findViewById(R.id.iv_image);
        mTitleEditText = findViewById(R.id.et_title);
        mTitleDrawable = findViewById(R.id.drawable_title);
        mDescEditText = findViewById(R.id.et_desc);
        mDescDrawable = findViewById(R.id.drawable_desc);
        mTypeSpinner = findViewById(R.id.type_spinner);
        mCreateBtn = findViewById(R.id.create_btn);
    }
}
