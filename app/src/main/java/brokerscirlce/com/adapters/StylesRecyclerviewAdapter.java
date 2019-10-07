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

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import brokerscirlce.com.model.DashboardStyle;
import brokerscirlce.com.R;

public class StylesRecyclerviewAdapter {

    private Context mContext;
    private StylesAdapter stylesAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<DashboardStyle> styles){
        mContext = context;
        stylesAdapter = new StylesAdapter(styles);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(stylesAdapter);
    }

    class StylesItemView extends RecyclerView.ViewHolder{

        private LinearLayout mMainLayout;
        private RoundedImageView mImage, cover;
        private TextView mName;


        public StylesItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.style_item_row, parent, false));

            mMainLayout = itemView.findViewById(R.id.mainlayout);
            mImage =  itemView.findViewById(R.id.image);
            cover =  itemView.findViewById(R.id.cover);
            mName =  itemView.findViewById(R.id.title);
        }

        public void bind(final DashboardStyle dashboardStyle, List<DashboardStyle> list){

            if (dashboardStyle.getSelected()){
                cover.setVisibility(View.GONE);
                mName.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
                mMainLayout.setBackground(mContext.getResources().getDrawable(R.drawable.rechtanglebluebordershape));
            }else {
                cover.setVisibility(View.VISIBLE);
                mMainLayout.setBackground(null);
                mName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                mMainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i<list.size(); i++){
                            list.get(i).setSelected(false);
                        }
                        dashboardStyle.setSelected(true);
                        stylesAdapter.notifyDataSetChanged();
                    }
                });
            }

            mImage.setImageResource(dashboardStyle.getImageSource());
            mName.setText(dashboardStyle.getName());

        }
    }

    class StylesAdapter extends RecyclerView.Adapter<StylesItemView>{

        private List<DashboardStyle> dashboardStyles;
        //private List<String> mKeys;

        public StylesAdapter(List<DashboardStyle> dashboardStyles) {
            this.dashboardStyles = dashboardStyles;
            //this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public StylesItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StylesItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StylesItemView holder, int position) {
            holder.bind(dashboardStyles.get(position), dashboardStyles);
        }

        @Override
        public int getItemCount() {
            return dashboardStyles.size();
        }
    }
}
