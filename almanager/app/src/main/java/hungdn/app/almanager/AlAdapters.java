package hungdn.app.almanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hungdn on 4/10/2018.
 */

public class AlAdapters extends BaseAdapter {
    Context mContext;
    ArrayList<ModelAL> data;
    LayoutInflater inflater;

    public AlAdapters(Context mContext) {
        this.mContext = mContext;
        data = new ArrayList<>();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updatedata(ArrayList<ModelAL> modelALS) {
        ArrayList<ModelAL> vaccinationLists = sortarraydate(modelALS);
        data.clear();
        data.addAll(vaccinationLists);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holler holler = new Holler();
        View con = null;
        if (con == null) {
            con = inflater.inflate(R.layout.row_al, viewGroup, false);
            holler.txt_name = (TextView) con.findViewById(R.id.row_name);
            holler.txt_phone = (TextView) con.findViewById(R.id.row_phone);
            holler.txt_qty = (TextView) con.findViewById(R.id.row_qty);
            holler.txt_price = (TextView) con.findViewById(R.id.row_unit_price);
            holler.txt_monney = (TextView) con.findViewById(R.id.row_monney);
            holler.date = (TextView) con.findViewById(R.id.row_date);
        }
        holler.date.setText(data.get(i).getDate());
        holler.txt_phone.setText(data.get(i).getPhone());
        holler.txt_name.setText(data.get(i).getFullname());
        holler.txt_qty.setText(FomartDate.PricefromString(data.get(i).getQuantity(),0));
        holler.txt_price.setText(FomartDate.PricefromString(data.get(i).getUnit_price(),0));
        holler.txt_monney.setText(FomartDate.PricefromString(((int) (Float.parseFloat(data.get(i).getQuantity()) * Float.parseFloat(data.get(i).getUnit_price())) + ""), 0));

        return con;
    }

    public class Holler {
        TextView txt_name, txt_phone, txt_qty, txt_price, txt_monney, date;
    }
    public static ArrayList<ModelAL> sortarraydate(ArrayList<ModelAL> data){
        ModelAL tempSort;
        for (int i = 0; i < data.size() - 1; i++) {
            for (int j = data.size() - 1; j >= 1; j--) {

                if (FomartDate.convertdatetolong(data.get(j).getDate()) > FomartDate.convertdatetolong(data.get(j-1).getDate())) {
                    tempSort = data.get(j);
                    data.set(j,data.get(j-1));
                    data.set(j-1, tempSort);
                }
            }
        }
        return data;
    }
}
