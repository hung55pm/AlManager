package hungdn.app.almanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hungdn on 3/16/2018.
 */

public class SQLiteDB extends SQLiteOpenHelper {
    public static String CREATE_TABLE_ACOUNT_SQL;
    public static String CREATE_AL_TABLE_SQL;
    static final String DB_NAME = "smart.db";
    private static final int DB_VERSION = 1;

    private static SQLiteDB mSQLiteDB;
    public SQLiteDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public synchronized static SQLiteDB getInstance(Context context) {
        if (mSQLiteDB == null) {
            mSQLiteDB = new SQLiteDB(context.getApplicationContext());
        }
        return mSQLiteDB;
    }

    public synchronized static void destoryInstance() {
        if (mSQLiteDB != null) {
            mSQLiteDB.close();
        }
    }
    private void initTables() {
        // Device List
        this.CREATE_TABLE_ACOUNT_SQL = "CREATE TABLE " + AcountDB.TABLE_ACOUNT
                + "(" + AcountDB.PHONE_NUMBER + " TEXT PRIMARY KEY,"
                + AcountDB.FULL_NAME + " TEXT NOT NULL);";

        this.CREATE_AL_TABLE_SQL = "CREATE TABLE "
                + ModelAL.TABLE_AL + "(" + ModelAL.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ModelAL.FULL_NAME + " TEXT NOT NULL,"
                + ModelAL.QUANTITY + " TEXT NOT NULL,"
                + ModelAL.DATE_ORDER + " TEXT NOT NULL,"
                + ModelAL.UNIT_PRICE + " TEXT NOT NULL,"
                + ModelAL.STATUS + " TEXT,"
                + ModelAL.PHONE_NUMBER + " TEXT NOT NULL);";
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        initTables();
        sqLiteDatabase.beginTransaction();
        try {
            excuteTableCreate(sqLiteDatabase);

            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }
    private void excuteTableCreate(SQLiteDatabase db) {
        db.execSQL(this.CREATE_TABLE_ACOUNT_SQL);
        db.execSQL(this.CREATE_AL_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        switch (i1) {
            case 1:
//            case 2:
//                upgradeTo2(db);
        }
    }
    //upgrade when db change
//    private void upgradeTo2(SQLiteDatabase db) {
//        db.execSQL("ALTER TABLE "
//                + AcountDB.TABLE_ACOUNT
//                + " ADD " + AcountDB.ADDRESS + " TEXT;");
//
//    }
    public synchronized Cursor rawQuery(String sql, String[] selectionArgs) {
        return getReadableDatabase().rawQuery(sql, selectionArgs);
    }

    public void execSQL(String sql) {
        this.getWritableDatabase().execSQL(sql);
    }
}
