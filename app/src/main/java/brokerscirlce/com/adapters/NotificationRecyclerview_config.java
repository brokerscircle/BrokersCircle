package brokerscirlce.com.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.util.Helper;
import brokerscirlce.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscirlce.com.api_helpers.UsersDatabaseHelper;
import brokerscirlce.com.model.Notification.NotificationData;
import brokerscirlce.com.model.Property.PropertyData;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationRecyclerview_config {

    private Context mContext;
    private NotificatonAdapter mNotificationAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<NotificationData> notificationData){
        mContext = context;
        mNotificationAdapter = new NotificatonAdapter(notificationData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mNotificationAdapter);
    }

    class NotificationItemView extends RecyclerView.ViewHolder{

        private RelativeLayout mMainLayout;
        private CircleImageView mProfileImage;
        private TextView mContentTV, mTimeTV;

        private String key;

        public NotificationItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.notification_item_row_single, parent, false));

            mMainLayout =  itemView.findViewById(R.id.mainlayout);
            mProfileImage =  itemView.findViewById(R.id.profile_image);
            mContentTV =  itemView.findViewById(R.id.tv_content);
            mTimeTV =  itemView.findViewById(R.id.tv_time);
        }

        public void bind(final NotificationData notificationData){

            if (notificationData.getNotificationType().equals("Follow")){

                new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<UsersData> usersData) {
                        //Profile image
                        if ( (usersData.get(0).getImg() == null) || (usersData.get(0).getImg().equals("")) ){
                            mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
                        }else{
                            Picasso.get().load(usersData.get(0).getImg().toString()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
                        }

                        //Content
                        mContentTV.setText(Html.fromHtml("Congratulations! <b>"+usersData.get(0).getFullName()+"</b> Followed you from <b> \""+usersData.get(0).getCompanyName()+"\"</b>."));
                    }
                }, mContext, notificationData.getCreatedByUserId());

            }else if (notificationData.getNotificationType().equals("List")){
                //Getting broker data
                new UsersDatabaseHelper().readSingleUserList(new UsersDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<UsersData> usersData) {

                        //Profile image
                        if ( (usersData.get(0).getImg() == null) || (usersData.get(0).getImg().equals("")) ){
                            mProfileImage.setImageResource(R.drawable.ic_user_icon_colored);
                        }else{
                            Picasso.get().load(usersData.get(0).getImg().toString()).placeholder(R.drawable.ic_user_icon_colored).centerCrop().fit().into(mProfileImage);
                        }

                        //Getting property data
                        new PropertyDatabaseHelper().readSinglePropertyList(new PropertyDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<PropertyData> propertyData) {
                                //Content
                                mContentTV.setText(Html.fromHtml("<b>"+usersData.get(0).getFullName()+"</b> listed new property by the title <b> \""+propertyData.get(0).getTitle()+"\"</b>."));
                            }
                        }, mContext, notificationData.getReferenceId());

                    }
                }, mContext, notificationData.getCreatedByUserId());
            }

            //time
            mTimeTV.setText(Helper.covertTimeToText(notificationData.getCreatedAt()));


//
//            //Awarded
//            String award = "";
//            if (!notificationUtils.getNotificationAward().equals("")){
//                award = notificationUtils.getNotificationAward()+" ";
//            }
//
//            String name = notificationUtils.getNotificationName();
//            String description = notificationUtils.getNotificationDescription();
//            String content = notificationUtils.getNotificationContent();
//
//            //Content
//            mContentTV.setText(Html.fromHtml(award+"<b>"+name+"</b> "+description+"<b> \""+content+"\"</b>"));
//
//
//
//            mMainLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent;
//                    switch (notificationUtils.getNotificationType()){
//                        case "property":
//                            Toast.makeText(mContext, "Property Comming Soon", Toast.LENGTH_SHORT).show();
//                            break;
//                        case "profile":
//                            intent = new Intent(mContext, BrokerProfileActivity.class);
//                            mContext.startActivity(intent);
//                            break;
//                        case "estate":
//                            intent = new Intent(mContext, EstateDetailActivity.class);
//                            mContext.startActivity(intent);
//                            break;
//                    }
//                }
//            });

        }
    }

    class NotificatonAdapter extends RecyclerView.Adapter<NotificationItemView>{

        private List<NotificationData> mNotificationList;

        public NotificatonAdapter(List<NotificationData> mNotificationList) {
            this.mNotificationList = mNotificationList;
        }

        @NonNull
        @Override
        public NotificationItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NotificationItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationItemView holder, int position) {
            holder.bind(mNotificationList.get(position));
        }

        @Override
        public int getItemCount() {
            return mNotificationList.size();
        }
    }

}
