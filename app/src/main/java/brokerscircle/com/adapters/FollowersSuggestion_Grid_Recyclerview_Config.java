package brokerscircle.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.model.Users.UsersData;
import brokerscircle.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersSuggestion_Grid_Recyclerview_Config {

    private Context mContext;
    private FollowesAdapter mFollowesAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<UsersData> usersData){
        mContext = context;
        mFollowesAdapter = new FollowesAdapter(usersData);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerView.setAdapter(mFollowesAdapter);
    }

    class FollowersItemView extends RecyclerView.ViewHolder{

        private CircleImageView mImage;
        private TextView mUserName, mCompanyName;
        private RatingBar mRating;

        public FollowersItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.follow_suggestion_row_single_layout, parent, false));

            mImage =  itemView.findViewById(R.id.profile_image);
            mUserName =  itemView.findViewById(R.id.tv_name);
            mCompanyName =  itemView.findViewById(R.id.tv_company);
            mRating =  itemView.findViewById(R.id.rating);
        }

        public void bind(final UsersData usersData){

            //Profile image
            if (usersData.getImg() == null){
                mImage.setImageResource(R.drawable.ic_user_icon_colored);
            }else{
                Picasso.get().load(usersData.getImg().toString()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mImage);
            }

            mUserName.setText(usersData.getFullName());
            mCompanyName.setText(usersData.getCompanyName());

        }
    }

    class FollowesAdapter extends RecyclerView.Adapter<FollowersItemView>{

        private List<UsersData> mSuggestionList;

        public FollowesAdapter(List<UsersData> mSuggestionList) {
            this.mSuggestionList = mSuggestionList;
        }

        @NonNull
        @Override
        public FollowersItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FollowersItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowersItemView holder, int position) {
            holder.bind(mSuggestionList.get(position));
        }

        @Override
        public int getItemCount() {
            return mSuggestionList.size();
        }
    }

}
