package hungdn.app.almanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hungdn on 4/10/2018.
 */

public class ModelAL {

    public static final String TABLE_AL = "acount_AL";
    public static final String ID = "id";
    public static final String FULL_NAME = "fullname";
    public static final String PHONE_NUMBER = "phone";
    public static final String DATE_ORDER = "date_order";
    public static final String QUANTITY = "quantity";
    public static final String UNIT_PRICE = "unit_price";
    public static final String STATUS = "status";
    private SQLiteDB sqliteDBHelper;
    private String fullname, phone, date, quantity, unit_price, status;
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelAL(String fullname, String phone, String date, String quantity, String unit_price, String status, int id) {
        this.fullname = fullname;
        this.phone = phone;
        this.date = date;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.status = status;
        this.id = id;
    }

    public ModelAL(Builder builder) {
        fullname = builder.fullname;
        phone = builder.phone;
        date = builder.date;
        quantity = builder.quantity;
        unit_price = builder.unit_price;
        status = builder.status;
        sqliteDBHelper = builder.sqliteDBHelper;

    }

    public void insert() {
        ContentValues values = packAcountData();
        sqliteDBHelper.getWritableDatabase().insert(TABLE_AL, null, values);
    }

    public void update(String whereClause, String[] whereArgs) {
        ContentValues values = packAcountData();
        int update = sqliteDBHelper.getWritableDatabase().update(TABLE_AL, values, whereClause, whereArgs);
        Log.e("de_update", "" + update);
    }

    public void delete(String whereClause, String[] whereArgs) {
        sqliteDBHelper.getWritableDatabase().delete(TABLE_AL, whereClause, whereArgs);
    }

    public ModelAL queryAL(String phones, String start, String end) {
        Cursor query = null;
        try {
            query = sqliteDBHelper.getWritableDatabase().query(TABLE_AL, null, PHONE_NUMBER + "=?",
                    new String[]{phones}, null, null, null);
            if (query != null) {
                boolean moveToFirst = query.moveToFirst();
                if (moveToFirst) {
                    fullname = query.getString(query.getColumnIndex(FULL_NAME));
                    phone = query.getString(query.getColumnIndex(PHONE_NUMBER));
                    date = query.getString(query.getColumnIndex(DATE_ORDER));
                    quantity = query.getString(query.getColumnIndex(QUANTITY));
                    unit_price = query.getString(query.getColumnIndex(UNIT_PRICE));
                    status = query.getString(query.getColumnIndex(STATUS));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
        }
        return this;
    }

    public ArrayList<ModelAL> queryDateOld(String phonenumber, String start, String end) {
        ArrayList<ModelAL> list = new ArrayList<ModelAL>();
        String sql = "";
        if (phonenumber.equals("")) {
            sql = "select * from " + TABLE_AL;
        } else {
            sql = "select * from " + TABLE_AL + " where " + PHONE_NUMBER + "='" + phonenumber + "'";
        }
        Cursor mCursor = sqliteDBHelper.getReadableDatabase().rawQuery(sql, null);
        if (mCursor.getCount() >= 1) {
            while (mCursor.moveToNext()) {
                id = mCursor.getInt(mCursor.getColumnIndex(ID));
                fullname = mCursor.getString(mCursor.getColumnIndex(FULL_NAME));
                phone = mCursor.getString(mCursor.getColumnIndex(PHONE_NUMBER));
                date = mCursor.getString(mCursor.getColumnIndex(DATE_ORDER));
                quantity = mCursor.getString(mCursor.getColumnIndex(QUANTITY));
                unit_price = mCursor.getString(mCursor.getColumnIndex(UNIT_PRICE));
                status = mCursor.getString(mCursor.getColumnIndex(STATUS));
                long start_from = FomartDate.convertdatetolong(start);
                long start_to = FomartDate.convertdatetolong(end);
                long start_date = FomartDate.convertdatetolong(date);
                if (start_date >= start_from && start_date <= start_to) {
                    list.add(new ModelAL(fullname, phone, date, quantity, unit_price, status,id));
                }


            }
        }
        mCursor.close();
        return list;
    }

    public String getAlbyID(String phones) {
        Cursor query = null;
        try {
            query = sqliteDBHelper.getWritableDatabase().query(TABLE_AL, null, ID + "=?",
                    new String[]{phones}, null, null, null);
            if (query != null) {
                boolean moveToFirst = query.moveToFirst();
                if (moveToFirst) {
                    return query.getString(query.getColumnIndex(ID));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
        }
        return "";
    }

    public static Cursor selectWithQuery(Context context, String[] columns, String selection, String[] selectionArgs, String orderby) {
        return SQLiteDB.getInstance(context).getReadableDatabase().query(TABLE_AL, columns, selection, selectionArgs, null, null, orderby);
    }

    private ContentValues packAcountData() {
        ContentValues values = new ContentValues();
        if (this.fullname != null)
            values.put(FULL_NAME, fullname);
        if (this.phone != null)
            values.put(PHONE_NUMBER, phone);
        if (this.date != null)
            values.put(DATE_ORDER, date);
        if (this.quantity != null)
            values.put(QUANTITY, quantity);
        if (this.unit_price != null)
            values.put(UNIT_PRICE, unit_price);
        if (this.status != null)
            values.put(STATUS, status);
        return values;
    }

    public static class Builder {
        private String fullname, phone, date, quantity, unit_price, status;
        private SQLiteDB sqliteDBHelper;

        public Builder(Context context) {
            sqliteDBHelper = SQLiteDB.getInstance(context);
        }

        public ModelAL.Builder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public ModelAL.Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ModelAL.Builder date(String date) {
            this.date = date;
            return this;
        }

        public ModelAL.Builder quantity(String quantity) {
            this.quantity = quantity;
            return this;
        }

        public ModelAL.Builder unit_price(String unit_price) {
            this.unit_price = unit_price;
            return this;
        }

        public ModelAL.Builder status(String status) {
            this.status = status;
            return this;
        }

        public ModelAL build() {
            return new ModelAL(this);
        }
    }
}
