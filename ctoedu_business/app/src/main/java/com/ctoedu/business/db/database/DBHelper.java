package com.ctoedu.business.db.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ctoedu.business.application.CtoeduApplication;

import java.util.ArrayList;

/**
 * 工程数据库助手类, 我們在自己封裝一些東西的時候可以以Manager類的型式，也可以以Helper的形式
 */
public final class DBHelper extends SQLiteOpenHelper {

    private static Context mContext;
    private static DBHelper helper = null;
    private static final String DATABASE_NAME = "ctoedu.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * 字段公共类型
     */
    public static final String INTEGER_TYPE = " integer";
    public static final String TEXT_TYPE = " TEXT";
    public static final String DESC = " DESC";
    /**
     * 表公共字段
     */
    public static final String ID = "_id";
    public static final String TIME = "time";

    /**
     * 基金记录表
     */
    public static final String FUND_LIST_TABLE = "fundListTable";
    public static final String FUND_CODE = "fdcode";
    public static final String FUND_ABBREV = "abbrev";
    public static final String FUND_SPELL = "spell";
    public static final String FUND_TYPE = "type";

    /**
     * 基金浏览历史记录表，字段与基金记录完全一样
     */
    public static final String FUND_BROWSE_TABLE = "fundBrowseTable";

    /**
     * 第三方用户登陆信息表
     */
    public static final String THIRD_LOGIN_TABLE = "thirdLoginTable";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String PHOTO_URL = "photoUrl";
    public static final String TICK = "tick"; //用户签名
    public static final String PLATFORM = "platform"; //来自那个平台

    private DBHelper() {
        super(CtoeduApplication.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 此构造方法用于ContentProvider的初始化
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance() {
        if (helper == null) {
            /**
             * 这里用全局Application即可
             */
            helper = new DBHelper();
        }
        return helper;
    }

    /**
     * 调用getInstance时先为其初始化一个Context
     *
     * @param context
     */
    public static void setContext(Context context) {

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            createAllTables(db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //根据老的数据库版本名来升级数据库,增量升级
        switch (oldVersion) {
            case 1:
                //此处不应该有break; 保证版本跳跃时也可以全部更新，例如:由oldVersion为1直接到version 5
            case 2:
                //老版本为2时,说明一定执行过了老版本为1时的更新,再执行以后所有版本的更新即可,不需要使用网上的那种循环的方式。
            case 3:

            default:
                break;
        }
    }

    private void createAllTables(SQLiteDatabase db) {
        /**
         * 创建基金列表
         */
        String[] cloumns = new String[]{FUND_CODE + TEXT_TYPE, FUND_ABBREV + TEXT_TYPE, FUND_SPELL + TEXT_TYPE,
            FUND_TYPE + INTEGER_TYPE};
        createTable(db, FUND_LIST_TABLE, cloumns);

        /**
         * 创建基金浏览列表
         */
        cloumns = new String[]{FUND_CODE + TEXT_TYPE, FUND_ABBREV + TEXT_TYPE, FUND_SPELL + TEXT_TYPE,
            FUND_TYPE + INTEGER_TYPE};
        createTable(db, FUND_BROWSE_TABLE, cloumns);

        /**
         * 创建第三方用户信息表
         */
        cloumns = new String[]{USER_ID + TEXT_TYPE, USER_NAME + TEXT_TYPE, MOBILE + TEXT_TYPE,
            PLATFORM + INTEGER_TYPE};
        createTable(db, THIRD_LOGIN_TABLE, cloumns);
    }

    /**
     * @param sqliteDatabase
     * @param table          要创建的数据表名
     * @param columns        列名
     */
    private void createTable(SQLiteDatabase sqliteDatabase, String table, String[] columns) {
        String createTable = "create table if not exists ";
        String primaryKey = " Integer primary key autoincrement";
        String text = " text";
        char leftBracket = '(';
        char rightBracket = ')';
        char comma = ',';
        int stringBufferSize = 170;
        StringBuffer sql = new StringBuffer(stringBufferSize); // StringBuffer的效率会更高一些
        sql.append(createTable).append(table).append(leftBracket).append(ID).append(primaryKey).append(comma);
        for (String column : columns) {
            sql.append(column);
            sql.append(comma);
        }
        sql.append(TIME).append(text).append(rightBracket);
        try {
            sqliteDatabase.execSQL(sql.toString());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDatabase(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * 插入数据
     *
     * @param table
     * @param nullColumnHack
     * @param values
     * @return
     */
    public synchronized long insert(final String table, final String nullColumnHack, final ContentValues values) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.insert(table, nullColumnHack, values);
        } catch (Exception e) {
            return -1;
        } finally {
            closeDatabase(database);
        }
    }

    /**
     * 批量插入数据，显示使用事物,事物的效率在批量插入时非常的明显
     */
    public synchronized boolean insert(final String table, final String nullColumnHack,
                                       final ArrayList<ContentValues> values) {
        boolean result = true;
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            database.beginTransaction();
            for (ContentValues value : values) {
                if (database.insert(table, nullColumnHack, value) < 0) {
                    result = false;
                    break;
                }
            }
            if (result) {
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            return false;
        } finally {
            database.endTransaction();
            closeDatabase(database);
        }
        return result;
    }

    /**
     * 删除数据
     *
     * @param table
     * @param whereClause //删除条件
     * @param whereArgs   //删除条件值
     * @return
     */
    public int delete(final String table, final String whereClause, final String[] whereArgs) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.delete(table, whereClause, whereArgs);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeDatabase(database);
        }
    }

    /**
     * 更新数据
     *
     * @param table
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public int update(final String table, final ContentValues values, final String whereClause,
                      final String[] whereArgs) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.update(table, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeDatabase(database);
        }
    }
}
