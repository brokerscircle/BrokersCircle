package brokerscircle.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import brokerscircle.com.model.AddUtil;
import brokerscircle.com.R;

public class AddRecyclerview_horizontal_config {

    private Context mContext;
    private AddAdapter mAddAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<AddUtil> addUtils){
        mContext = context;
        mAddAdapter = new AddAdapter(addUtils);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAddAdapter);
    }

    class AddItemView extends RecyclerView.ViewHolder{
        private RoundedImageView mAddBannerImage;

        public AddItemView( ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.add_banner_single_row_item, parent, false));

            mAddBannerImage =  itemView.findViewById(R.id.add_img);
        }

        public void bind(final AddUtil addUtil){
            mAddBannerImage.setImageResource(addUtil.getImageSource());
        }
    }

    private class AddAdapter extends RecyclerView.Adapter<AddItemView> {

        private List<AddUtil> mAddList;

        public AddAdapter(List<AddUtil> mAddList) {
            this.mAddList = mAddList;
        }

        @NonNull
        @Override
        public AddItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AddItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AddItemView holder, int position) {
            holder.bind(mAddList.get(position));
        }

        @Override
        public int getItemCount() {
            return mAddList.size();
        }

    }

}
