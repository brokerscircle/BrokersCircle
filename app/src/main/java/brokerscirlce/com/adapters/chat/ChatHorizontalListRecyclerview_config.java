package brokerscirlce.com.adapters.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.model.ChatUtils;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatHorizontalListRecyclerview_config {

    private Context mContext;
    private BrokersAdapter mBrokersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<ChatUtils> chatUtils, List<String> keys){
        mContext = context;
        mBrokersAdapter = new BrokersAdapter(chatUtils,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mBrokersAdapter);
    }

    class BrokersItemView extends RecyclerView.ViewHolder{

        private CircleImageView mProfileImage;
        private TextView mName;

        private String key;

        public BrokersItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.dashboard_brokers_item_row_one, parent, false));

            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mName =  itemView.findViewById(R.id.tv_broker_name);
        }

        public void bind(final ChatUtils chatUtils, String key){

            //Profile image
            if (chatUtils.getProfileImage().equals("")){
                mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
            }else{
                Picasso.get().load(chatUtils.getProfileImage()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
            }
            //Name
            mName.setText(chatUtils.getName());

            this.key = key;
        }
    }

    class BrokersAdapter extends RecyclerView.Adapter<BrokersItemView>{

        private List<ChatUtils> mChatList;
        private List<String> mKeys;

        public BrokersAdapter(List<ChatUtils> mChatList, List<String> mKeys) {
            this.mChatList = mChatList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BrokersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BrokersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BrokersItemView holder, int position) {
            holder.bind(mChatList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mChatList.size();
        }
    }

}
