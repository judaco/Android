package com.example.juda.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juda on 31/01/2017.
 */

public class Database {

    static final int DATABASE_VERSION = 1;


    enum Type{
        INTEGER, REAL, TEXT, BLOB
    }

    static class Column{
        String name;
        Type type;
        boolean primaryKey, autoIncrement, notNull;

        public Column(String name, Type type) {
            this(name, type, false, false, false);
        }

        public Column(String name, Type type, boolean primaryKey,
                      boolean autoIncrement, boolean notNull) {
            this.name = name;
            this.type = type;
            this.primaryKey = primaryKey;
            this.autoIncrement = autoIncrement;
            this.notNull = notNull;
        }

        String getCreate(){
            String s = name + " ";
            switch (type){
                case INTEGER:
                    s += "integer";
                    break;
                case REAL:
                    s += "real";
                    break;
                case TEXT:
                    s += "text";
                    break;
                case BLOB:
                    s += "blob";
                    break;
            }
            if (primaryKey)
                s += " primary key";
            if(autoIncrement)
                s += " autoincrement";
            if(notNull)
                s += " not null";
            return s;
        }
    }

    public static abstract class Table{
        static Column[] columns;
        static String name;



        public static String getCreate(){
            String s = "CREATE TABLE " + name + "(";
            for (int i = 0; i < columns.length; i++) {
                s += columns[i].getCreate();
                if(i<columns.length-1)
                    s += ",";
            }
            s += ")";
            return s;
        }

        public static String getDrop(){
            return "DROP TABLE IF EXISTS " + name;
        }

        public static String[] getAllColumns(){
            String[] columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                columnNames[i] = columns[i].name;
            }
            return columnNames;
        }

    }

    public static class TableProducts extends Table{


        static {
            columns = new Column[]{
                    new Column("ProductID",Type.INTEGER, true, true, false),
                    new Column("ProductName", Type.TEXT, false, false, true),
                    new Column("CategoryID", Type.INTEGER, false, false, true),
                    new Column("UnitPrice", Type.REAL, false, false, true),
                    new Column("UnitsInStock", Type.INTEGER, false, false, true),
                    new Column("Discontinued", Type.INTEGER, false, false, true)
            };
            name = "Products";
        }

    }

    public static class TableOrders extends Table{


        static {
            columns = new Column[]{
                    new Column("OrderID", Type.INTEGER, true, true, false),
                    new Column("OrderDate", Type.INTEGER, false, false, true),
                    new Column("CustomerID", Type.INTEGER, false, false, true)
            };
            name = "Orders";

        }

    }

    public static class TableOrderDetails extends Table{


        static {
            columns = new Column[]{
                    new Column("OrderID", Type.INTEGER, false, false, true),
                    new Column("ProductID", Type.INTEGER, false, false, true),
                    new Column("UnitPrice", Type.REAL, false, false, true),
                    new Column("Quantity", Type.REAL, false, false, true),
                    new Column("Discount", Type.REAL, false, false, true)
            };
            name = "OrderDetails";
        }

    }

    private static SQLiteDatabase db;
    private static DatabaseHelper helper;
    private static boolean isWritable = false;

    public static void start(Context context){
        if(helper == null)
            helper = new DatabaseHelper(context);
    }

    public static void finish(){
        if(db != null){
            db.close();
            db = null;
        }
        helper.close();
        helper = null;
    }


    public static void openRead(){
        if(helper == null || db != null)
            throw new IllegalStateException();
        db = helper.getReadableDatabase();
        isWritable = false;
    }

    public static void openWrite(){
        if(helper == null || db != null)
            throw new IllegalStateException();
        db = helper.getWritableDatabase();
        isWritable = true;
    }

    public static void close(){
        if(db == null)
            throw new IllegalStateException();
        db.close();
        db = null;
    }

    public static long addProduct(String productName, int categoryId,
                                  float unitPrice, int unitsInStock,
                                  boolean discontinued){
        if(helper == null || db == null || isWritable == false)
            throw new IllegalStateException();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableProducts.columns[1].name, productName);
        contentValues.put(TableProducts.columns[2].name, categoryId);
        contentValues.put(TableProducts.columns[3].name, unitPrice);
        contentValues.put(TableProducts.columns[4].name, unitsInStock);
        contentValues.put(TableProducts.columns[5].name, discontinued ? 1 : 0);
        return db.insert(TableProducts.name, null, contentValues);
    }

    public static boolean deleteProduct(long productId){
        if(helper == null || db == null || isWritable == false)
            throw new IllegalStateException();
        return db.delete(TableProducts.name,
                TableProducts.columns[0].name + "=" + productId, null) > 0;

    }

    public static boolean updateProduct(long productId, String productName, int categoryId,
                                        float unitPrice, int unitsInStock,
                                        boolean discontinued){
        if(helper == null || db == null || isWritable == false)
            throw new IllegalStateException();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableProducts.columns[1].name, productName);
        contentValues.put(TableProducts.columns[2].name, categoryId);
        contentValues.put(TableProducts.columns[3].name, unitPrice);
        contentValues.put(TableProducts.columns[4].name, unitsInStock);
        contentValues.put(TableProducts.columns[5].name, discontinued ? 1 : 0);
        return db.update(TableProducts.name, contentValues,
                TableProducts.columns[0].name + "=" + productId, null) > 0;
    }

    public static Cursor getAllProducts(){
        if(helper == null || db == null)
            throw new IllegalStateException();
        return db.query(TableProducts.name, TableProducts.getAllColumns(), null, null, null, null, null);
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context, "sales.db", null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TableProducts.getCreate());
            db.execSQL(TableOrders.getCreate());
            db.execSQL(TableOrderDetails.getCreate());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TableProducts.getDrop());
            db.execSQL(TableOrders.getDrop());
            db.execSQL(TableOrderDetails.getDrop());
            onCreate(db);
        }
    }
}
