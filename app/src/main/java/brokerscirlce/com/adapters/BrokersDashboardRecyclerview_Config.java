package brokerscirlce.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.Activities.Brokers.BrokerProfileActivity;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class BrokersDashboardRecyclerview_Config {

    private Context mContext;
    private BrokersAdapter mBrokersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<BrokersData> brokersUtils){
        mContext = context;
        mBrokersAdapter = new BrokersAdapter(brokersUtils);
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

        public void bind(final BrokersData brokersUtils){

            //Profile image
            if (brokersUtils.getPicture().equals("")){
                mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
            }else{
                String url = brokersUtils.getPicture();
                Picasso.get().load(url).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
            }
            //Name
            if (!brokersUtils.getTitle().equals("") || brokersUtils.getTitle() != null ){
                mName.setText(brokersUtils.getTitle());
            }else {
                mName.setVisibility(View.GONE);
            }

            // Actions Clicklistners
            mProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, BrokerProfileActivity.class);
                    intent.putExtra("brokerid",brokersUtils.getId());
                    mContext.startActivity(intent);
                }
            });

            this.key = key;
        }
    }

    class BrokersAdapter extends RecyclerView.Adapter<BrokersItemView>{

        private List<BrokersData> mBrokersList;
        //private List<String> mKeys;

        public BrokersAdapter(List<BrokersData> mBrokersList) {
            this.mBrokersList = mBrokersList;
            //this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BrokersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BrokersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BrokersItemView holder, int position) {
            holder.bind(mBrokersList.get(position));
        }

        @Override
        public int getItemCount() {
            return mBrokersList.size();
        }
    }

}
