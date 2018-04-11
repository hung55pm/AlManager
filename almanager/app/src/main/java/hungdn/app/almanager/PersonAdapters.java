package hungdn.app.almanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hungdn on 4/10/2018.
 */

public class PersonAdapters extends BaseAdapter {
    Context mContext;
    ArrayList<AcountDB> data;
    LayoutInflater inflater;
    public PersonAdapters(Context mContext){
        this.mContext=mContext;
        data= new ArrayList<>();
        inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void updatedata(ArrayList<AcountDB> modelALS){
        data.clear();
        data= modelALS;
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

        Holler holler =new Holler();
        View con = null;
        if(con==null){
            con = inflater.inflate(R.layout.row_dialog,viewGroup,false);
            holler.txt_name =(TextView)con.findViewById(R.id.row_dialog_name);
            holler.txt_phone =(TextView)con.findViewById(R.id.row_dialog_phone);
        }
        holler.txt_phone.setText(data.get(i).getPhone());
        holler.txt_name.setText(data.get(i).getFullname());


        return con;
    }

    public class Holler{
        TextView txt_name,txt_phone,txt_qty,txt_price,txt_monney;
    }
}
