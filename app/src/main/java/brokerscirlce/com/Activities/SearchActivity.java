package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import brokerscirlce.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscirlce.com.Activities.Developers.ViewAllDevelopersActivity;
import brokerscirlce.com.Activities.Estates.ViewAllEstateActivity;
import brokerscirlce.com.Activities.Projects.ViewAllProjectsActivity;
import brokerscirlce.com.Activities.Properties.ViewAllPropertiesActivity;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout mBackButton;
    private LinearLayout mPropertyButton, mProjectButton, mBrokersButton, mDevelopersButton, mEstateButton, mJobButton;
    private CircleImageView mProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //Get Ids
        mBackButton = findViewById(R.id.backbtn);
        mProfileImage = findViewById(R.id.profile_image);
        mPropertyButton = findViewById(R.id.search_property);
        mProjectButton = findViewById(R.id.search_projects);
        mBrokersButton = findViewById(R.id.search_brokers);
        mDevelopersButton = findViewById(R.id.search_developers);
        mEstateButton = findViewById(R.id.search_estates);
        mJobButton = findViewById(R.id.search_job_board);

        //Back button
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Property button
        mPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, ViewAllPropertiesActivity.class);
                startActivity(intent);
            }
        });

        //Brokers button
        mBrokersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, ViewAllBrokersActivity.class);
                startActivity(intent);
            }
        });

        //Developers button
        mDevelopersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, ViewAllDevelopersActivity.class);
                startActivity(intent);
            }
        });

        //Estate button
        mEstateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, ViewAllEstateActivity.class);
                startActivity(intent);
            }
        });

        //Project button
        mProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, ViewAllProjectsActivity.class);
                startActivity(intent);            }
        });

        //Jobs button
        mJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this, "Jobs Coming Soon", Toast.LENGTH_LONG).show();
            }
        });
    }
}
