package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;
import brokerscirlce.com.adapters.FollowersSuggestion_Grid_Recyclerview_Config;
import brokerscirlce.com.api_helpers.UsersDatabaseHelper;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.R;

public class FollowersSuggestionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private LinearLayout mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_suggestion_activity);

        mBackBtn  = findViewById(R.id.btn_back);
        mRecyclerview  = findViewById(R.id.recyclerview);

        new UsersDatabaseHelper().readUserList(new UsersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UsersData> usersData) {
                new FollowersSuggestion_Grid_Recyclerview_Config().setConfig(mRecyclerview,FollowersSuggestionActivity.this, usersData);
            }
        }, FollowersSuggestionActivity.this);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
