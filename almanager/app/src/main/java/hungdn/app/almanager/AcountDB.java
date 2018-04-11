package hungdn.app.almanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hungdn on 3/16/2018.
 */

public class AcountDB {

    public static final String TABLE_ACOUNT = "acount_table";
    public static final String FULL_NAME = "fullname";
    public static final String PHONE_NUMBER = "phone";

    private SQLiteDB sqliteDBHelper;
    private String fullname, phone;

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

    public AcountDB(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }

    public AcountDB(Builder builder) {
        fullname = builder.fullname;
        phone = builder.phone;
        sqliteDBHelper = builder.sqliteDBHelper;

    }

    public void insert() {

        ContentValues values = packAcountData();
        sqliteDBHelper.getWritableDatabase().insert(TABLE_ACOUNT, null, values);
    }

    public void update(String whereClause, String[] whereArgs) {
        ContentValues values = packAcountData();
        int update = sqliteDBHelper.getWritableDatabase().update(TABLE_ACOUNT, values, whereClause, whereArgs);
        Log.e("de_update", "" + update);
    }

    public void delete(String whereClause, String[] whereArgs) {
        sqliteDBHelper.getWritableDatabase().delete(TABLE_ACOUNT, whereClause, whereArgs);
    }

    public AcountDB queryAcount(String phones) {
        Cursor query = null;
        try {
            query = sqliteDBHelper.getWritableDatabase().query(TABLE_ACOUNT, null, PHONE_NUMBER + "=?",
                    new String[]{phones}, null, null, null);
            if (query != null) {
                boolean moveToFirst = query.moveToFirst();
                if (moveToFirst) {
                    fullname = query.getString(query.getColumnIndex(FULL_NAME));
                    phone = query.getString(query.getColumnIndex(PHONE_NUMBER));

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

    public ArrayList<AcountDB> queryAllPerson() {
        ArrayList<AcountDB> list = new ArrayList<AcountDB>();
        String sql = "select * from " + TABLE_ACOUNT;

        Cursor mCursor = sqliteDBHelper.getReadableDatabase().rawQuery(sql, null);
        if (mCursor.getCount() >= 1) {
            while (mCursor.moveToNext()) {
                fullname = mCursor.getString(mCursor.getColumnIndex(FULL_NAME));
                phone = mCursor.getString(mCursor.getColumnIndex(PHONE_NUMBER));
                list.add(new AcountDB(fullname, phone));
            }
        }
        mCursor.close();
        return list;
    }

    public boolean isPhoneExist(String phones) {
        Cursor query = null;
        try {
            query = sqliteDBHelper.getWritableDatabase().query(TABLE_ACOUNT, null, PHONE_NUMBER + "=?",
                    new String[]{phones}, null, null, null);
            if (query != null) {
                return query.moveToFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (query != null) {
                query.close();
            }
        }
        return false;
    }

    public String getAcountbyphone(String phones) {
        Cursor query = null;
        try {
            query = sqliteDBHelper.getWritableDatabase().query(TABLE_ACOUNT, null, PHONE_NUMBER + "=?",
                    new String[]{phones}, null, null, null);
            if (query != null) {
                boolean moveToFirst = query.moveToFirst();
                if (moveToFirst) {
                    return query.getString(query.getColumnIndex(PHONE_NUMBER));
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
        return SQLiteDB.getInstance(context).getReadableDatabase().query(TABLE_ACOUNT, columns, selection, selectionArgs, null, null, orderby);
    }

    private ContentValues packAcountData() {
        ContentValues values = new ContentValues();
        if (this.fullname != null)
            values.put(FULL_NAME, fullname);
        if (this.phone != null)
            values.put(PHONE_NUMBER, phone);
        return values;
    }

    public static class Builder {
        private String fullname, phone;
        private SQLiteDB sqliteDBHelper;

        public Builder(Context context) {
            sqliteDBHelper = SQLiteDB.getInstance(context);
        }

        public Builder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public AcountDB build() {
            return new AcountDB(this);
        }
    }
}
