package brokerscircle.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.Activities.Developers.DevelopersDetailActivity;
import brokerscircle.com.util.Helper;
import brokerscircle.com.api_helpers.UsersDatabaseHelper;
import brokerscircle.com.model.Project.ProjectData;
import brokerscircle.com.model.Users.UsersData;
import brokerscircle.com.R;

public class ProjectRecyclerview_Horizontal_Config {

    private Context mContext;
    private ProjectAdapter mProjectAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<ProjectData> projectData){
        mContext = context;
        mProjectAdapter = new ProjectAdapter(projectData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.hasFixedSize();
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mProjectAdapter);
    }

    class ProjectItemView extends RecyclerView.ViewHolder{

        private RoundedImageView mProjectImage;
        private TextView mProjectName, mProjectLocation, mDeveloperName, mProjectType, mProjectTime;
        private ToggleButton mFavoriteBTN, mCallBtn, mSMSBtn;
        private RatingBar mRatingBar;

        private String key;

        public ProjectItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.project_item_row_item_small, parent, false));

            mProjectImage =  itemView.findViewById(R.id.project_image);
            mProjectName =  itemView.findViewById(R.id.tv_projectname);
            mProjectLocation =  itemView.findViewById(R.id.tv_location);
            mDeveloperName =  itemView.findViewById(R.id.tv_developer_name);
            mProjectType =  itemView.findViewById(R.id.tv_project_type);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mCallBtn =  itemView.findViewById(R.id.btn_call);
            mSMSBtn =  itemView.findViewById(R.id.btn_sms);
            mProjectTime =  itemView.findViewById(R.id.tv_time);
            mRatingBar =  itemView.findViewById(R.id.rating);
        }

        public void bind(final ProjectData projectData){

            new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<UsersData> usersData) {

                    // image
                    if (projectData.getThumbnail().toString().equals("") || projectData.getThumbnail() == null){
                        mProjectImage.setImageResource(R.mipmap.background);
                    }else{
                        Picasso.get().load(projectData.getThumbnail().toString()).placeholder(R.mipmap.background).centerCrop().fit().into(mProjectImage);
                    }

                    //Project Name
                    if ((projectData.getTitle() == null) || (projectData.getTitle().equals(""))){
                        mProjectName.setVisibility(View.GONE);
                    }else {
                        mProjectName.setText(projectData.getTitle());
                    }

                    //Developer Name
                    if (usersData.get(0).getFullName().toString().equals("") || usersData.get(0).getFullName() == null){
                        mDeveloperName.setVisibility(View.GONE);
                    }else {
                        mDeveloperName.setText(usersData.get(0).getFullName());
                    }

                    //Rating
                    mRatingBar.setRating((float) 4.5);
                    LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                    //Location
                    mProjectLocation.setText("DHA PHASE V");

                    //Time
                    mProjectTime.setText(Helper.covertTimeToText(projectData.getCreatedAt()));
                }
            }, mContext, projectData.getCreatedByUserId());


            //Action onClickListners
            mDeveloperName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DevelopersDetailActivity.class);
                    intent.putExtra("developerid", projectData.getCreatedByUserId());
                    mContext.startActivity(intent);
                }
            });
            //End layout filling

            //Fav button
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            //Fav button
            mSMSBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            //Fav button
            mCallBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });

            this.key = key;
        }
    }

    class ProjectAdapter extends RecyclerView.Adapter<ProjectItemView>{

        private List<ProjectData> mProjectDatasList;

        public ProjectAdapter(List<ProjectData> mProjectDatasList) {
            this.mProjectDatasList = mProjectDatasList;
        }

        @NonNull
        @Override
        public ProjectItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProjectItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProjectItemView holder, int position) {
            holder.bind(mProjectDatasList.get(position));
        }

        @Override
        public int getItemCount() {
            return mProjectDatasList.size();
        }
    }

}
