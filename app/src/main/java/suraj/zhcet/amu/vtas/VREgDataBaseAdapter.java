package suraj.zhcet.amu.vtas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Suraj on 2/24/2017.
 */
public class VREgDataBaseAdapter {
    static final String DATABASE_NAME = "VehicleDataBase.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final String V_Reg="V_Reg";
    public static final String V_Type="V_type";
    public static final String ID="ID";

    public static final String UserName="UserName";


    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "VREG" +
            "( " + "ID" + " integer primary key autoincrement," + "V_Type  text,V_Reg text,UserName text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private VehicleREgDataBaseHelper dbHelper;

    public VREgDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new VehicleREgDataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public VREgDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String vtype, String regno, String username) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("V_Type", vtype);
        newValues.put("V_Reg", regno);
        newValues.put("UserName", username);

        // Insert the row into your table
        db.insert("VREG", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String vtype, String vreg) {
        //String id=String.valueOf(ID);
        String where = "V_Type=?";
        int numberOFEntriesDeleted = db.delete("VREG", where, new String[]{vtype});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public Cursor getSinlgeEntry(String username) {
        Cursor cursor = db.query("VREG", null, " UserName=?", new String[]{username}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            //return "Vehicle Do'nt Exist ";
        }
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getString(1) + "\t" + cursor.getString(2));

        }
        //cursor.moveToFirst();
        //String usernam = cursor.getString(cursor.getColumnIndex("V_Reg"));
        cursor.close();
        //return usernam;
        return cursor;
    }
    public void  updateEntry(String vtype,String regno)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("V_Type", vtype);
        updatedValues.put("V_Reg",regno);

        String where="V_ Type = ?";
        db.update("VREG",updatedValues, where, new String[]{vtype});
    }
}
