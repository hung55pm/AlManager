package hungdn.app.almanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hungdn on 4/10/2018.
 */

public class AddPersonDialog extends Dialog {
    Button bt;
    EditText ed_name,ed_phone;
    Context context;
    public AddPersonDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_person);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        ed_name=(EditText)findViewById(R.id.add_name);
        ed_phone=(EditText)findViewById(R.id.add_phone);
        bt=(Button)findViewById(R.id.bt_add_person);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= ed_name.getText().toString();
                String phone = ed_phone.getText().toString();
                if(name.equals("") || phone.equals("")){
                    Toast.makeText(context,"Bạn phải nhập đầy đủ tên và số điên thoại", Toast.LENGTH_LONG).show();
                }else {
                    if (new AcountDB.Builder(context).build().isPhoneExist("0984302192")) {
                        Toast.makeText(context,"Đã có người mua hàng với số điện thoại này kiểm tra lại", Toast.LENGTH_LONG).show();
                    } else {
                        new AcountDB.Builder(context)
                                .fullname(name)
                                .phone(phone)
                                .build()
                                .insert();
                        Toast.makeText(context,"Thêm nguời mua thành công", Toast.LENGTH_LONG).show();
                        dismiss();
                    }
                }
            }
        });

    }
    @Override
    public void dismiss() {
        super.dismiss();
    }

}
