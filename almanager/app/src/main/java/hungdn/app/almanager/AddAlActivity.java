package hungdn.app.almanager;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAlActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ed_qty, ed_price,ed_date;
    EditText bt;
    public static String name_order="",phone_order="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_al);

        initview();

    }

    public void initview() {
        findViewById(R.id.bt_add).setOnClickListener(this);
        findViewById(R.id.add_person).setOnClickListener(this);
        findViewById(R.id.ic_back).setOnClickListener(this);
        ed_date=(EditText)findViewById(R.id.add_date) ;
        ed_date.setText(FomartDate.getdatenow());
        bt = (EditText) findViewById(R.id.bt_choose_person);
        bt.setOnClickListener(this);
        ed_date.setOnClickListener(this);
        ed_qty = (EditText) findViewById(R.id.add_qty);
        ed_price = (EditText) findViewById(R.id.add_price);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                addal();
                break;
            case R.id.add_person:
                AddPersonDialog dialog = new AddPersonDialog(this);
                dialog.show();
                break;
            case R.id.bt_choose_person:
                ChoosePersonDialog choosedialog = new ChoosePersonDialog(this,bt,AddAlActivity.class);
                choosedialog.show();
                break;
            case R.id.add_date:
                DatePickerDialog mDatePicker = FomartDate.setdateEdittext(AddAlActivity.this, ed_date);
                mDatePicker.show();
                break;
            case R.id.ic_back:
                finish();
                break;
        }
    }

    public void addal(){
        if(name_order.equals("") || ed_qty.getText().toString().equals("")|| ed_price.getText().toString().equals("")) {
            Toast.makeText(AddAlActivity.this,"Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        }else {
            String qty= ed_qty.getText().toString();
            String price= ed_price.getText().toString();
            String date= ed_date.getText().toString();
            new ModelAL.Builder(AddAlActivity.this)
                    .fullname(name_order)
                    .phone(phone_order)
                    .quantity(qty)
                    .unit_price(price)
                    .date(date)
                    .status("0")
                    .build()
                    .insert();
            Toast.makeText(AddAlActivity.this,"Thêm đơn hàng thành công", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
