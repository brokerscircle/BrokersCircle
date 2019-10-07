package brokerscirlce.com.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import brokerscirlce.com.api_helpers.RealEstateDatabaseHelper;
import brokerscirlce.com.R;
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.model.jobs.JobData;

public class JobsRecyclerview_Horizontal_Config {

    private Context mContext;
    private JobsAdapter mJobsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<JobData> jobsUtils){
        mContext = context;
        mJobsAdapter = new JobsAdapter(jobsUtils);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mJobsAdapter);
    }

    class JobsItemView extends RecyclerView.ViewHolder{
        private TextView mJobTitle, mJobCompany, mPay, mType, mLocation;

        public JobsItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.jobs_single_row_item_small, parent, false));

            mJobTitle =  itemView.findViewById(R.id.tv_jobtitle);
            mJobCompany =  itemView.findViewById(R.id.tv_company);
            mPay =  itemView.findViewById(R.id.tv_pay);
            mType =  itemView.findViewById(R.id.tv_jobtype);
            mLocation =  itemView.findViewById(R.id.tv_location);
        }

        public void bind(final JobData jobsUtil){
            mJobTitle.setText(jobsUtil.getTitle());

            new RealEstateDatabaseHelper().readEstateDetail(new RealEstateDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<RealEstateData> realEstateData) {
                    if (!realEstateData.isEmpty()){
                        if (!realEstateData.get(0).getName().equals("")){
                            mJobCompany.setText(realEstateData.get(0).getName());
                        }else {
                            mJobCompany.setText("Unknown");
                        }
                    }else {
                        mJobCompany.setText("Unknown");
                    }

                }
            }, mContext, jobsUtil.getCreatedByCompId());

            mPay.setText(Html.fromHtml("<b>Pay: </b>"+"0"));
            mType.setText(Html.fromHtml("<b>Type: </b>"+"Unknown"));
            mLocation.setText(Html.fromHtml("<b>Location: </b>"+"Unknown"));
        }
    }

    private class JobsAdapter extends RecyclerView.Adapter<JobsItemView> {

        private List<JobData> mJobList;

        public JobsAdapter(List<JobData> mJobList) {
            this.mJobList = mJobList;
        }

        @NonNull
        @Override
        public JobsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JobsItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull JobsItemView holder, int position) {
            holder.bind(mJobList.get(position));
        }

        @Override
        public int getItemCount() {
            return mJobList.size();
        }

    }
}
