package hungdn.app.almanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button from, to;
    TextView txt_sum, txt_title;
    EditText ed_search_name;
    ListView listView;
    AlAdapters adapters;
    String phone = "";
    Boolean check = false;
    public static String name_order = "";
    public static String phone_order = "";
    ArrayList<ModelAL> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

    }


    public void initview() {
        list= new ArrayList<>();
        findViewById(R.id.bt_search).setOnClickListener(this);
        from = (Button) findViewById(R.id.from_date);
        to = (Button) findViewById(R.id.to_date);
        findViewById(R.id.ic_adđ).setOnClickListener(this);
        findViewById(R.id.ic_search).setOnClickListener(this);
        from.setOnClickListener(this);
        to.setOnClickListener(this);
        adapters = new AlAdapters(MainActivity.this);
        listView = (ListView) findViewById(R.id.listviewshow);
        listView.setAdapter(adapters);
        txt_title = (TextView) findViewById(R.id.txt_title);
        ed_search_name = (EditText) findViewById(R.id.edit_search);
        txt_sum = (TextView) findViewById(R.id.txt_sum);
        from.setText(FomartDate.getfirtdateofmounth());
        to.setText(FomartDate.getdatenow());
        ed_search_name.setOnClickListener(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Cảnh báo");
                alertDialog.setMessage("Mày có chắc chắn muốn xóa không");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Xóa",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new ModelAL.Builder(MainActivity.this).build().delete(ModelAL.ID + "=?", new String[]{""+list.get(i).getId()});
                                list = getData(phone, from.getText().toString(), to.getText().toString());
                                adapters.updatedata(list);
                               dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

    public ArrayList<ModelAL> getData(String phone, String start, String end) {
        ModelAL.Builder builder = new ModelAL.Builder(MainActivity.this);
        ArrayList<ModelAL> list = new ModelAL(builder).queryDateOld(phone, start, end);
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ed_search_name.getVisibility()==View.VISIBLE){
            phone= phone_order;
        }

        list = getData(phone, from.getText().toString(), to.getText().toString());
        adapters.updatedata(list);
        int sum=0;
        for (int i=0;i<list.size();i++){
            sum= sum + (int)(Float.parseFloat(list.get(i).getQuantity())* Float.parseFloat(list.get(i).getUnit_price()));
        }
        txt_sum.setText(FomartDate.PricefromString(""+sum,0)+" vnd");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                if (ed_search_name.getVisibility() == View.VISIBLE) {
                    phone = phone_order;
                }
                list.clear();
                list = getData(phone, from.getText().toString(), to.getText().toString());
                adapters.updatedata(list);
                int sum=0;
                for (int i=0;i<list.size();i++){
                    sum= sum + (int)(Float.parseFloat(list.get(i).getQuantity())* Float.parseFloat(list.get(i).getUnit_price()));
                }
                txt_sum.setText(FomartDate.PricefromString(""+sum,0)+" vnd");
                break;
            case R.id.from_date:
                DatePickerDialog mDatePicker = FomartDate.setdateButton(MainActivity.this, from);
                mDatePicker.show();
                break;
            case R.id.to_date:
                DatePickerDialog mDatePickers = FomartDate.setdateButton(MainActivity.this, to);
                mDatePickers.show();
                break;
            case R.id.ic_adđ:
                Intent in = new Intent(this, AddAlActivity.class);
                startActivity(in);
                break;
            case R.id.ic_search:
                if (check == false) {
                    txt_title.setVisibility(View.GONE);
                    ed_search_name.setVisibility(View.VISIBLE);
                    check = true;
                } else {
                    txt_title.setVisibility(View.VISIBLE);
                    ed_search_name.setVisibility(View.GONE);
                    check = false;
                }
                break;
            case R.id.edit_search:
                ChoosePersonDialog choosedialog = new ChoosePersonDialog(this,ed_search_name,MainActivity.class);
                choosedialog.show();
                break;
        }
    }
}
