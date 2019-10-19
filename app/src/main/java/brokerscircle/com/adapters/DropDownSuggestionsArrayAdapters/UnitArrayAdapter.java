package brokerscircle.com.adapters.DropDownSuggestionsArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.R;
import brokerscircle.com.model.Units.UnitsData;

public class UnitArrayAdapter extends BaseAdapter {

    private List<UnitsData> dataList;
    private Context mContext;
    LayoutInflater inflater;

    public UnitArrayAdapter(Context mContext, List<UnitsData> storeDataLst) {
        mContext = mContext;
        this.dataList = storeDataLst;
        inflater = LayoutInflater.from(mContext);
        this.dataList = new ArrayList<>();
        this.dataList.addAll(storeDataLst);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.dialog_radio_item_row, viewGroup, false);
        }

        MaterialRadioButton radioButton = view.findViewById(R.id.radio_button);
        radioButton.setText(dataList.get(i).getTitle());

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    radioButton.setChecked(true);
                }else {
                    radioButton.setChecked(false);
                }
            }
        });

        return view;
    }
}
