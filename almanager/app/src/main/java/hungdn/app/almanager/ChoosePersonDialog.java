package hungdn.app.almanager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Hungdn on 4/10/2018.
 */

public class ChoosePersonDialog extends Dialog {
    Context context;
    EditText ed_filll;
    ListView listView;
    PersonAdapters adapters;
    EditText bt;
    Class cls;
    public static ArrayList<AcountDB> list, listfill;
    public ChoosePersonDialog(@NonNull Context context, EditText bt, Class cls) {
        super(context);
        this.context = context;
        this.bt = bt;
        this.cls = cls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        ed_filll = (EditText) findViewById(R.id.ed_fill);
        listView = (ListView) findViewById(R.id.listviewdialog);
        adapters = new PersonAdapters(context);
        listView.setAdapter(adapters);
        AcountDB.Builder builder = new AcountDB.Builder(context);
        list = new AcountDB(builder).queryAllPerson();
        adapters.updatedata(list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (cls == MainActivity.class) {
                    MainActivity.phone_order = list.get(i).getPhone();
                    MainActivity.name_order = list.get(i).getFullname();
                    bt.setText(list.get(i).getFullname());
                } else {
                    AddAlActivity.phone_order = list.get(i).getPhone();
                    AddAlActivity.name_order = list.get(i).getFullname();
                    bt.setText(list.get(i).getFullname() + ":" + list.get(i).getPhone());
                }
                dismiss();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Cảnh báo");
                alertDialog.setMessage("Mày có chắc chắn muốn xóa không nếu xóa người mua này thì tất cả các đơn hàng của người mua này sẽ mất hết");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Xóa",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new AcountDB.Builder(context).build().delete(AcountDB.PHONE_NUMBER + "=?", new String[]{list.get(i).getPhone()});
                                new ModelAL.Builder(context).build().delete(ModelAL.PHONE_NUMBER + "=?", new String[]{list.get(i).getPhone()});
                                AcountDB.Builder builder = new AcountDB.Builder(context);
                                final ArrayList<AcountDB> list = new AcountDB(builder).queryAllPerson();
                                adapters.updatedata(list);
                                dismiss();
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

//        ed_filll.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                listfill.clear();
//                for (int i = 0; i < list.size(); i++) {
//                    if (removeAccent(list.get(i).getFullname()).contains(removeAccent(ed_filll.getText().toString()))) {
//                        listfill.add(list.get(i));
//                    }
//                }
//                adapters.updatedata(listfill);
//            }
//        });
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase();
    }

}
