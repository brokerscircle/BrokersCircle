package brokerscirlce.com.adapters.DropDownSuggestionsArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.R;
import brokerscirlce.com.model.Location.LocationData;

public class LocationArrayAdapter extends BaseAdapter implements Filterable {

    private List<LocationData> dataList;
    private Context mContext;
    LayoutInflater inflater;

    private ListFilter listFilter = new ListFilter();
    private List<LocationData> dataListAllItems;

    public LocationArrayAdapter(Context context, List<LocationData> storeDataLst) {
        mContext = context;
        this.dataList = storeDataLst;
        inflater = LayoutInflater.from(mContext);
        this.dataListAllItems = new ArrayList<LocationData>();
        this.dataListAllItems.addAll(storeDataLst);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public LocationData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.autocomplete_list_row_item, parent, false);
        }

        //PhoneCountryCodeData codeData = dataList.get(position);
        ImageView image = view.findViewById(R.id.image);

        image.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.tv_title);
        title.setText(String.format("%s, %s", dataList.get(position).getName(), dataList.get(position).getAreaName()));
        return view;
    }

    static class ViewHolder {
        TextView text;
        ImageView image;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<LocationData>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<LocationData> matchValues = new ArrayList<>();

                for (LocationData dataItem : dataListAllItems) {
                    if (dataItem.getName().toLowerCase().startsWith(searchStrLowerCase)
                            || dataItem.getName().toLowerCase().contains(searchStrLowerCase)
                            || dataItem.getAreaName().toLowerCase().contains(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }
                results.values = matchValues;
                results.count = matchValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<LocationData>)results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}