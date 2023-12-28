package Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.MachineLearning;
import Utils.Utils;
public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Utils.DATABASE_NAME, factory, Utils.DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_NAME + " (" +
                Utils.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Utils.COLUMN_MPG + " INTEGER, " +
                Utils.COLUMN_DISPLACEMENT + " INTEGER, " +
                Utils.COLUMN_HORSEPOWER + " INTEGER, " +
                Utils.COLUMN_WEIGHT + " INTEGER, " +
                Utils.COLUMN_ACCELERATION + " INTEGER, " +
                Utils.COLUMN_ORIGIN + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public MachineLearning getData(long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        MachineLearning machineLearning = new MachineLearning();

        Cursor cursor = database.query(
                Utils.TABLE_NAME,
                null,
                Utils.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            machineLearning.setId(cursor.getLong(cursor.getColumnIndex(Utils.COLUMN_ID)));
            machineLearning.setMpg(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_MPG)));
            machineLearning.setAcceleration(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_ACCELERATION)));
            machineLearning.setDisplacement(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_DISPLACEMENT)));
            machineLearning.setHorsePower(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_HORSEPOWER)));
            machineLearning.setWeight(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_WEIGHT)));
            machineLearning.setOrigin(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_ORIGIN)));
        }

        cursor.close();
        database.close();

        return machineLearning;
    }

    public void addData(MachineLearning machineLearning){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(String.valueOf(Utils.COLUMN_MPG), machineLearning.getMpg());
        contentValues.put(String.valueOf(Utils.COLUMN_DISPLACEMENT), machineLearning.getDisplacement());
        contentValues.put(String.valueOf(Utils.COLUMN_HORSEPOWER), machineLearning.getHorsePower());
        contentValues.put(String.valueOf(Utils.COLUMN_WEIGHT), machineLearning.getWeight());
        contentValues.put(String.valueOf(Utils.COLUMN_ACCELERATION), machineLearning.getAcceleration());
        contentValues.put(Utils.COLUMN_ORIGIN, machineLearning.getOrigin());

        database.insert(Utils.TABLE_NAME, null, contentValues);
        database.close();
    }

    public List<MachineLearning> getAllData(){

        SQLiteDatabase database = this.getReadableDatabase();

        List<MachineLearning> machineLearningList = new ArrayList<>();

        String getAll = "SELECT * FROM " + Utils.TABLE_NAME;

        Cursor cursor = database.rawQuery(getAll, null);

        if (cursor.moveToFirst()) {
            do {
                MachineLearning machineLearning = new MachineLearning();
                machineLearning.setId(cursor.getLong(cursor.getColumnIndex(Utils.COLUMN_ID)));
                machineLearning.setMpg(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_MPG)));
                machineLearning.setAcceleration(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_ACCELERATION)));
                machineLearning.setDisplacement(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_DISPLACEMENT)));
                machineLearning.setHorsePower(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_HORSEPOWER)));
                machineLearning.setWeight(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_WEIGHT)));
                machineLearning.setOrigin(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_ORIGIN)));

                machineLearningList.add(machineLearning);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return machineLearningList;
    }


    public int getNumRows(){
        String getAll = "SELECT * FROM "+ Utils.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(getAll, null);

        return cursor.getCount();
    }


    public void deleteData(long id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Utils.TABLE_NAME, Utils.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
    }


    public void updateData(long id, MachineLearning updatedData) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.COLUMN_MPG, updatedData.getMpg());
        contentValues.put(Utils.COLUMN_ACCELERATION, updatedData.getAcceleration());
        contentValues.put(Utils.COLUMN_DISPLACEMENT, updatedData.getDisplacement());
        contentValues.put(Utils.COLUMN_HORSEPOWER, updatedData.getHorsePower());
        contentValues.put(Utils.COLUMN_WEIGHT, updatedData.getWeight());
        contentValues.put(Utils.COLUMN_ORIGIN, updatedData.getOrigin());

        database.update(Utils.TABLE_NAME, contentValues, Utils.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        database.close();
    }


    public List<MachineLearning> searchByAnyAtt(String searchTerm) {
        SQLiteDatabase database = this.getReadableDatabase();
        List<MachineLearning> searchResults = new ArrayList<>();

        // You can customize the query based on your specific needs
        String searchQuery = "SELECT * FROM " + Utils.TABLE_NAME +
                " WHERE " + Utils.COLUMN_MPG + " LIKE ? OR " +
                Utils.COLUMN_ACCELERATION + " LIKE ? OR " +
                Utils.COLUMN_DISPLACEMENT + " LIKE ? OR " +
                Utils.COLUMN_HORSEPOWER + " LIKE ? OR " +
                Utils.COLUMN_WEIGHT + " LIKE ? OR " +
                Utils.COLUMN_ORIGIN + " LIKE ?";

        // Add "%" to the searchTerm to make it a fuzzy search
        String[] searchArgs = new String[6];
        for (int i = 0; i < 6; i++) {
            searchArgs[i] = "%" + searchTerm + "%";
        }

        Cursor cursor = database.rawQuery(searchQuery, searchArgs);

        if (cursor.moveToFirst()) {
            do {
                MachineLearning machineLearning = new MachineLearning();
                machineLearning.setId(cursor.getLong(cursor.getColumnIndex(Utils.COLUMN_ID)));
                machineLearning.setMpg(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_MPG)));
                machineLearning.setAcceleration(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_ACCELERATION)));
                machineLearning.setDisplacement(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_DISPLACEMENT)));
                machineLearning.setHorsePower(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_HORSEPOWER)));
                machineLearning.setWeight(cursor.getInt(cursor.getColumnIndex(Utils.COLUMN_WEIGHT)));
                machineLearning.setOrigin(cursor.getString(cursor.getColumnIndex(Utils.COLUMN_ORIGIN)));

                searchResults.add(machineLearning);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return searchResults;
    }
}
