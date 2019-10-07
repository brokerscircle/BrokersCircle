package brokerscirlce.com.Activities.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import brokerscirlce.com.adapters.chat.ChatHorizontalListRecyclerview_config;
import brokerscirlce.com.adapters.chat.ChatRecyclerview_Config;
import brokerscirlce.com.api_helpers.ChatDatabaseHelper;
import brokerscirlce.com.model.ChatUtils;
import brokerscirlce.com.R;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerviewTop, mRecyclerviewMain;
    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        //Getting IDs
        mBackButton = findViewById(R.id.btn_back);
        mRecyclerviewTop = findViewById(R.id.toprecyclerview);
        mRecyclerviewMain = findViewById(R.id.mainrecyclerview);

        //Top Recyclerview Implmentation
        new ChatDatabaseHelper().readChatList(new ChatDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ChatUtils> chatUtils, List<String> keys) {
                new ChatHorizontalListRecyclerview_config().setConfig(mRecyclerviewTop,ChatActivity.this, chatUtils, keys);
                new ChatRecyclerview_Config().setConfig(mRecyclerviewMain, ChatActivity.this, chatUtils, keys);
            }
        });

        //back listener
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
