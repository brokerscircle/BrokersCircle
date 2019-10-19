package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import brokerscircle.com.Activities.Brokers.CreateBrokerAndOwner;
import brokerscircle.com.Activities.Estates.CreateEstateActivity;
import brokerscircle.com.Activities.Projects.CreateProjectActivity;
import brokerscircle.com.Activities.Developers.CreateDeveloperActivity;
import brokerscircle.com.Activities.Properties.ListPropertyOptions;
import brokerscircle.com.Activities.JobBroard.CreateJobsActivity;
import brokerscircle.com.R;

public class CreateActivity extends AppCompatActivity {

    private LinearLayout mBackBtn, mCreateProperty, mCreateProject, mCreateBroker, mCreateDeveloper, mCreateEstate, mCreateJob, mCreateNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        mBackBtn = findViewById(R.id.backbtn);
        mCreateProperty = findViewById(R.id.create_property);
        mCreateProject = findViewById(R.id.create_projects);
        mCreateBroker = findViewById(R.id.create_brokers);
        mCreateDeveloper = findViewById(R.id.create_developers);
        mCreateEstate = findViewById(R.id.create_estates);
        mCreateJob = findViewById(R.id.create_job_board);
        mCreateNews = findViewById(R.id.create_news);

        //Back action
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mCreateProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, ListPropertyOptions.class);
                startActivity(intent);
            }
        });

        mCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CreateProjectActivity.class);
                startActivity(intent);
            }
        });

        mCreateBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CreateBrokerAndOwner.class);
                startActivity(intent);
            }
        });

        mCreateDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CreateDeveloperActivity.class);
                startActivity(intent);
            }
        });

        mCreateEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CreateEstateActivity.class);
                startActivity(intent);
            }
        });

        mCreateJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CreateJobsActivity.class);
                startActivity(intent);
            }
        });

        mCreateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateActivity.this, CreateNewsActivity.class);
                startActivity(intent);
            }
        });
    }
}
