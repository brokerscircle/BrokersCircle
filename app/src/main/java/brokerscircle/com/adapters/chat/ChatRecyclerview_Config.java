package brokerscircle.com.adapters.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.Activities.Chat.ChatDetailActivity;
import brokerscircle.com.model.ChatUtils;
import brokerscircle.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRecyclerview_Config {

    private Context mContext;
    private ChatAdapter mChatAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<ChatUtils> chatUtils, List<String> keys){
        mContext = context;
        mChatAdapter = new ChatAdapter(chatUtils,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mChatAdapter);
    }

    class ChatItemView extends RecyclerView.ViewHolder{

        private LinearLayout mMainLayout;
        private CircleImageView mProfileImage;
        private ImageView mStatus;
        private TextView mName, mMessage, mTime;
        //private boolean status;

        private String key;

        public ChatItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.chat_row_item_single, parent, false));

            mMainLayout =  itemView.findViewById(R.id.layoutchat);
            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mStatus =  itemView.findViewById(R.id.online_icon);
            mName =  itemView.findViewById(R.id.tv_name);
            mMessage =  itemView.findViewById(R.id.tv_content);
            mTime =  itemView.findViewById(R.id.tv_time);
        }

        public void bind(final ChatUtils chatUtils, String key){

            //Profile image
            if (chatUtils.getProfileImage().equals("")){
                mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
            }else{
                Picasso.get().load(chatUtils.getProfileImage()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
            }
            //online Status
            if (chatUtils.isStatus()){
                mStatus.setVisibility(View.VISIBLE);
            }
            //Name
            mName.setText(chatUtils.getName());
            //Message
            mMessage.setText(chatUtils.getMessage());
            //Time
            mTime.setText(chatUtils.getTime());

            mMainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ChatDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });

            this.key = key;
        }
    }

    class ChatAdapter extends RecyclerView.Adapter<ChatItemView>{

        private List<ChatUtils> mChatList;
        private List<String> mKeys;

        public ChatAdapter(List<ChatUtils> mChatList, List<String> mKeys) {
            this.mChatList = mChatList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ChatItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ChatItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatItemView holder, int position) {
            holder.bind(mChatList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mChatList.size();
        }
    }

}
