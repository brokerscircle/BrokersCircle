package brokerscirlce.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import brokerscirlce.com.Activities.Developers.DevelopersDetailActivity;
import brokerscirlce.com.model.Project.ProjectData;
import brokerscirlce.com.R;

public class ProjectDetailTabsRecyclerview_Config {

    private Context mContext;
    private ProjectAdapter mProjectAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<ProjectData> projectData){
        mContext = context;
        mProjectAdapter = new ProjectAdapter(projectData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.hasFixedSize();
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mProjectAdapter);
    }

    class ProjectItemView extends RecyclerView.ViewHolder{

        private RoundedImageView mProjectImage;
        private TextView mProjectName, mProjectLocation, mDeveloperName, mProjectType, mProjectTime;
        private ToggleButton mFavoriteBTN, mCallBtn, mSMSBtn;
        private String key;

        public ProjectItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.project_detail_tabs_row_item, parent, false));

            mProjectImage =  itemView.findViewById(R.id.project_image);
            mProjectName =  itemView.findViewById(R.id.tv_projectname);
            mProjectLocation =  itemView.findViewById(R.id.tv_location);
            mDeveloperName =  itemView.findViewById(R.id.tv_developer_name);
            mProjectType =  itemView.findViewById(R.id.tv_project_type);
            mFavoriteBTN =  itemView.findViewById(R.id.btn_favorite);
            mCallBtn =  itemView.findViewById(R.id.btn_call);
            mSMSBtn =  itemView.findViewById(R.id.btn_sms);
            mProjectTime =  itemView.findViewById(R.id.tv_time);
        }

        public void bind(final ProjectData projectData){

            //Profile image
//            if (projectUtils.getProjectImage().equals("")){
//                mProjectImage.setImageResource(R.mipmap.background);
//            }else{
//                Picasso.get().load(projectUtils.getProjectImage()).placeholder(R.mipmap.background).centerCrop().fit().into(mProjectImage);
//            }
//            //other properties
//            mProjectName.setText(projectUtils.getProjectName());
//            mProjectLocation.setText(projectUtils.getProjectLocation());
//            mDeveloperName.setText(projectUtils.getEstateName());
//            mProjectType.setText(projectUtils.getProjectType());

            //Action onClickListners
            mDeveloperName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DevelopersDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });

            //Animation Button Click
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            BounceInterpolator bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            //Favorite click
            mFavoriteBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            //Favorite click
            mSMSBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            //Favorite click
            mCallBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            //End layout filling

            this.key = key;
        }
    }

    class ProjectAdapter extends RecyclerView.Adapter<ProjectItemView>{

        private List<ProjectData> mProjectList;

        public ProjectAdapter(List<ProjectData> mProjectList) {
            this.mProjectList = mProjectList;
        }

        @NonNull
        @Override
        public ProjectItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProjectItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProjectItemView holder, int position) {
            holder.bind(mProjectList.get(position));
        }

        @Override
        public int getItemCount() {
            return mProjectList.size();
        }
    }

}
