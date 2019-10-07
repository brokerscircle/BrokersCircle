package brokerscirlce.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.Activities.Estates.EstateDetailActivity;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerview_Config {

    private Context mContext;
    private UsersAdapter mUsersAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<UsersData> usersData){
        mContext = context;
        mUsersAdapter = new UsersAdapter(usersData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mUsersAdapter);
    }

    class UsersItemView extends RecyclerView.ViewHolder{

        private CircleImageView mProfileImage;
        private ImageView mOverflowBTN;
        private TextView mNameTV, mEstateNameTV;

        public UsersItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.users_item_row_single, parent, false));

            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mNameTV =  itemView.findViewById(R.id.tv_name);
            mEstateNameTV =  itemView.findViewById(R.id.tv_estate);
            mOverflowBTN =  itemView.findViewById(R.id.btn_overflow);
        }

        public void bind(final UsersData usersData){

            //Profile image
            if (usersData.getImg() == null){
                mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
            }else{
                Picasso.get().load(usersData.getImg().toString()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
            }
            //Name
            mNameTV.setText(usersData.getFullName());

            //EstateName
            if (usersData.getCompanyName() == null){
                mEstateNameTV.setVisibility(View.GONE);
            }else {
                mEstateNameTV.setText(usersData.getCompanyName());
            }

            // Actions Estate Click Clicklistners
            mEstateNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EstateDetailActivity.class);
                    intent.putExtra("estateid",usersData.getCompId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    class UsersAdapter extends RecyclerView.Adapter<UsersItemView>{

        private List<UsersData> mUsersList;

        public UsersAdapter(List<UsersData> mUsersList) {
            this.mUsersList = mUsersList;
        }

        @NonNull
        @Override
        public UsersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UsersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UsersItemView holder, int position) {
            holder.bind(mUsersList.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsersList.size();
        }
    }

}
