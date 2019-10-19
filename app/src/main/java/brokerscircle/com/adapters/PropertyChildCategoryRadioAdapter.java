package brokerscircle.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import brokerscircle.com.R;
import brokerscircle.com.model.Property.Property_Child_Category.PropertyChildCategoryData;

public class PropertyChildCategoryRadioAdapter extends RecyclerView.Adapter<PropertyChildCategoryRadioAdapter.ViewHolder> {

    public int mSelectedItem = -1;
    public List<PropertyChildCategoryData> mItems;
    private Context mContext;

    public PropertyChildCategoryRadioAdapter(List<PropertyChildCategoryData> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(PropertyChildCategoryRadioAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.circle_button_layout_items, viewGroup, false);
        return new ViewHolder(view, i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ToggleButton mRadio;
        public TextView mText;

        public ViewHolder(final View inflate, int pos) {
            super(inflate);
            mText =  inflate.findViewById(R.id.textview);
            mRadio =  inflate.findViewById(R.id.toggle_btn);

            mText.setText(mItems.get(pos).getTitle());

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }
}
