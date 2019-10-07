package brokerscirlce.com.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import brokerscirlce.com.R;
import brokerscirlce.com.model.ExternelLink.ExternalLinkData;
import brokerscirlce.com.model.Phone.PhoneData;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomDialogBox {

    private Dialog dialog;
    //Dialog Content
    private LinearLayout layoutInfo;
    private CircleImageView dialogImage;
    private ImageView dialogIcon;
    private TextView dialogName, dialogEstate, dialogGoto, dialogDecline;

    private com.xw.repo.BubbleSeekBar bubbleSeekBar;
    private EditText mSearchView;

    //For Sms Dialog
    public void showSMSDialog(Context context, List<PhoneData> phoneData) {

        // Create custom dialog object
        dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_info_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Getting Dialog ids
        layoutInfo = dialog.findViewById(R.id.layoutinfo);
        dialogImage = dialog.findViewById(R.id.img_profile);
        dialogName = dialog.findViewById(R.id.tv_name);
        dialogEstate = dialog.findViewById(R.id.tv_estatename);
        dialogIcon = dialog.findViewById(R.id.icon_content);
        dialogGoto = dialog.findViewById(R.id.intent_btn);
        dialogDecline = dialog.findViewById(R.id.decline_btn);
        //End Dialog ids

        dialogGoto.setText("SMS now");
        dialogIcon.setImageResource(R.drawable.ic_sms_icon_colored);
        /*
         * Fetching all phone number which will have this broker
         * */
        int i = 0;
        for (PhoneData data : phoneData){
            //Setting layout of phone dynamically
            LinearLayout childLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayout.setPadding(0,5,0,5);
            childLayout.setOrientation(LinearLayout.HORIZONTAL);
            childLayout.setLayoutParams(linearParams);

            TextView mType = new TextView(context);
            TextView mValue = new TextView(context);
            mType.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            mValue.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            mType.setTextSize(15);
            mType.setTextColor(context.getResources().getColor(R.color.colorDark));
            mType.setText("Phone: ");
            mType.setAlpha(0.5f);

            mValue.setTextSize(15);
            mValue.setTextColor(context.getResources().getColor(R.color.colorDark));
            mValue.setText(String.format("%s-%s", data.getCountryCode(), data.getNumber()));

            childLayout.addView(mValue, 0);
            childLayout.addView(mType, 0);

            View mline = new View(context);
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1);
            mline.setLayoutParams(viewParams);
            mline.setBackgroundColor(context.getResources().getColor(R.color.colorDark));
            mline.setAlpha(0.5f);

            layoutInfo.addView(childLayout);
            layoutInfo.addView(mline);
        }

        //Send to sms activity
        dialogGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String mblNumVar = "9876543210;9123456789";
                Intent smsMsgAppVar = new Intent(Intent.ACTION_VIEW);
                smsMsgAppVar.setData(Uri.parse("sms:" +  phoneData.get(0).getCountryCode()+phoneData.get(0).getNumber()));
                //smsMsgAppVar.putExtra("sms_body", "Hello Msg Tst Txt");
                context.startActivity(smsMsgAppVar);
            }
        });

        //Close the dialog
        dialogDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close dialog
                layoutInfo.removeAllViews();
                dialog.dismiss();

            }
        });

        //Show dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //For Sms Dialog
    public void showCallDialog(Context context, List<PhoneData> phoneData) {

        // Create custom dialog object
        dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_info_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Getting Dialog ids
        layoutInfo = dialog.findViewById(R.id.layoutinfo);
        dialogImage = dialog.findViewById(R.id.img_profile);
        dialogName = dialog.findViewById(R.id.tv_name);
        dialogEstate = dialog.findViewById(R.id.tv_estatename);
        dialogIcon = dialog.findViewById(R.id.icon_content);
        dialogGoto = dialog.findViewById(R.id.intent_btn);
        dialogDecline = dialog.findViewById(R.id.decline_btn);
        //End Dialog ids

        dialogGoto.setText("SMS now");
        dialogIcon.setImageResource(R.drawable.ic_sms_icon_colored);
        /*
         * Fetching all phone number which will have this broker
         * */
        int i = 0;
        dialogGoto.setText("Call now");
        dialogIcon.setImageResource(R.drawable.ic_call_icon_colored);

        for (PhoneData data : phoneData){
            //Setting layout of phone dynamically
            LinearLayout childLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayout.setPadding(0,5,0,5);
            childLayout.setOrientation(LinearLayout.HORIZONTAL);
            childLayout.setLayoutParams(linearParams);

            TextView mType = new TextView(context);
            TextView mValue = new TextView(context);
            mType.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            mValue.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            mType.setTextSize(15);
            mType.setTextColor(context.getResources().getColor(R.color.colorDark));
            mType.setText("Phone: ");
            mType.setAlpha(0.5f);

            mValue.setTextSize(15);
            mValue.setTextColor(context.getResources().getColor(R.color.colorDark));
            mValue.setText(String.format("%s-%s", data.getCountryCode(), data.getNumber()));

            childLayout.addView(mValue, 0);
            childLayout.addView(mType, 0);

            View mline = new View(context);
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1);
            mline.setLayoutParams(viewParams);
            mline.setBackgroundColor(context.getResources().getColor(R.color.colorDark));

            mline.setAlpha(0.5f);

            layoutInfo.addView(childLayout);
            layoutInfo.addView(mline);
        }

        //Send to Call dial activity
        dialogGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneData.get(0).getCountryCode()+phoneData.get(0).getNumber()));
//                if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.CALL_PHONE)) {
//
//                    } else {
//                        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 112);
//                    }
//                }
                context.startActivity(callIntent);
            }
        });

        //Close the dialog
        dialogDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close dialog
                layoutInfo.removeAllViews();
                dialog.dismiss();

            }
        });

        //Show dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //For Sms Dialog
    public void showWebsiteDialog(Context context, List<ExternalLinkData> externalLinkData) {

        // Create custom dialog object
        dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_info_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Getting Dialog ids
        layoutInfo = dialog.findViewById(R.id.layoutinfo);
        dialogImage = dialog.findViewById(R.id.img_profile);
        dialogName = dialog.findViewById(R.id.tv_name);
        dialogEstate = dialog.findViewById(R.id.tv_estatename);
        dialogIcon = dialog.findViewById(R.id.icon_content);
        dialogGoto = dialog.findViewById(R.id.intent_btn);
        dialogDecline = dialog.findViewById(R.id.decline_btn);
        //End Dialog ids

        dialogGoto.setText("Visit now");
        dialogIcon.setImageResource(R.drawable.ic_web_icon_colored);
        /*
         * Fetching all phone number which will have this broker
         * */
        int i = 0;
        for (ExternalLinkData data : externalLinkData){
            //Setting layout of phone dynamically
            LinearLayout childLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayout.setPadding(0,5,0,5);
            childLayout.setOrientation(LinearLayout.HORIZONTAL);
            childLayout.setLayoutParams(linearParams);

            TextView mType = new TextView(context);
            TextView mValue = new TextView(context);
            mType.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            mValue.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            mType.setTextSize(15);
            mType.setTextColor(context.getResources().getColor(R.color.colorDark));
            mType.setText("Website: ");
            mType.setAlpha(0.5f);

            mValue.setTextSize(15);
            mValue.setTextColor(context.getResources().getColor(R.color.colorDark));
            mValue.setText(String.format("%s", data.getUrl()));

            childLayout.addView(mValue, 0);
            childLayout.addView(mType, 0);

            View mline = new View(context);
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1);
            mline.setLayoutParams(viewParams);
            mline.setBackgroundColor(context.getResources().getColor(R.color.colorDark));
            mline.setAlpha(0.5f);

            layoutInfo.addView(childLayout);
            layoutInfo.addView(mline);
        }

        //Send to sms activity
        dialogGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = externalLinkData.get(0).getUrl();
                if (!url.startsWith("http://") && !url.startsWith("https://")){
                    url = "http://"+url;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            }
        });

        //Close the dialog
        dialogDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close dialog
                layoutInfo.removeAllViews();
                dialog.dismiss();

            }
        });

        //Show dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

}
