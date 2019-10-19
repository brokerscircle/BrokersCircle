package brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import java.util.List;

import brokerscircle.com.R;
import brokerscircle.com.interfaces.RadioListSelectedIndex;
import brokerscircle.com.model.Currency.CurrencyData;

public class CurrencyArrayAdapter extends BaseAdapter implements RadioListSelectedIndex {

    private List<CurrencyData> dataList;
    private Context mContext;
    private int mSelectedIndex = -1;

    public CurrencyArrayAdapter(Context context, List<CurrencyData> storeDataLst) {
        this.mContext = context;
        this.dataList = storeDataLst;
    }

    static class ViewHolder {
        RadioButton mRadioButton;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.dialog_radio_item_row, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mRadioButton = (RadioButton) rowView.findViewById(R.id.radio_button);

            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.mRadioButton.setText(dataList.get(position).getTitle());
        if (mSelectedIndex == position) {
            holder.mRadioButton.setChecked(true);
        } else {
            holder.mRadioButton.setChecked(false);
        }

        return rowView;
    }

    @Override
    public void setSelectedIndex(int position) {
        mSelectedIndex = position;
    }
}
