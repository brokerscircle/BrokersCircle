package brokerscirlce.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import brokerscirlce.com.model.MessageUtils;
import brokerscirlce.com.R;

public class ChatDetailRecyclerview_config {

    private Context mContext;
    private MessageAdapter mMessageAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<MessageUtils> messageUtils, List<String> keys){
        mContext = context;
        mMessageAdapter = new MessageAdapter(messageUtils,keys);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMessageAdapter);
    }

    class MessageItemView extends RecyclerView.ViewHolder{

        private LinearLayout mCurrentUserLayout, mMessageUserLayout;
        private TextView mTime, mCurrentUserMessage, mCurrentUsertime, mMessageUserMessage, mMessageUsertime;
        //private boolean status;

        private String key;

        public MessageItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.chat_detail_row_item, parent, false));

            mTime =  itemView.findViewById(R.id.tv_time);
            mCurrentUserLayout =  itemView.findViewById(R.id.layout_current_user);
            mMessageUserLayout =  itemView.findViewById(R.id.layout_message_user);
            mCurrentUserMessage =  itemView.findViewById(R.id.tv_current_user_message);
            mCurrentUsertime =  itemView.findViewById(R.id.tv_current_user_time);
            mMessageUserMessage =  itemView.findViewById(R.id.tv_message_user_message);
            mMessageUsertime =  itemView.findViewById(R.id.tv_message_user_time);
        }

        public void bind(final MessageUtils messageUtils, String key){

            if (messageUtils.isCurrentUser()){
                mMessageUserLayout.setVisibility(View.GONE);
            }else {
                mCurrentUserLayout.setVisibility(View.GONE);
            }

            //time
            mTime.setText(messageUtils.getTime());
            //current user Message
            mCurrentUserMessage.setText(messageUtils.getMessage());
            //current user Message time
            mCurrentUsertime.setText(messageUtils.getTime());
            //current user Message
            mMessageUserMessage.setText(messageUtils.getMessage());
            //current user Message time
            mMessageUsertime.setText(messageUtils.getTime());

            this.key = key;
        }
    }

    class MessageAdapter extends RecyclerView.Adapter<MessageItemView>{

        private List<MessageUtils> mMessageList;
        private List<String> mKeys;

        public MessageAdapter(List<MessageUtils> mMessageList, List<String> mKeys) {
            this.mMessageList = mMessageList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public MessageItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MessageItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageItemView holder, int position) {
            holder.bind(mMessageList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }
    }

}
