package com.example.l.mathquiz;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lorenz on 26.12.2016.
 */


public class MyDatabase {

    /**
     * Beschreibt die Tabelle
     */
    public static abstract class FeedEntry implements BaseColumns{

        // Erste Datenbank
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_GOAL = "goal";
        public static final String COLUMN_NAME_POINTS = "points";

        // Zeite Datenbank
        public static final String SECOND_TABLE_NAME = "rewards";
        public static final String SECOND_COLUMN_GOAL= "goals";
        public static final String SECOND_COLUMN_DATETIME= "datetime";

    }

    // create First Database
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_GOAL + " TEXT," +
                    FeedEntry.COLUMN_NAME_POINTS + " INTEGER" +
                    " )";

    // create second Database
    private static final String SQL_CREATE_SECOND_TABLE=
            "CREATE TABLE " + FeedEntry.SECOND_TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.SECOND_COLUMN_GOAL + " TEXT," +
                    FeedEntry.SECOND_COLUMN_DATETIME + " INTEGER"+
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_SECOND=
            "DROP TABLE IF EXISTS " +FeedEntry.SECOND_TABLE_NAME;




    public class FeedGoalsDbHelper extends SQLiteOpenHelper {
        // Version
        public static final int DATABASE_VERSION = 1;
        // Dateinmame
        public static final String DATABASE_NAME = "FeedGoals.db";

        public FeedGoalsDbHelper( Context context){
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
            }

        public void onCreateSecond(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_SECOND_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_DELETE_SECOND);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion){
            onUpgrade(db,oldVersion,newVersion);
        }



    }

    private Context context;

    public MyDatabase(Context context){
        this.context = context;
    }



    // insert in FIRST Database
    public void insert(String goal,int points){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_GOAL,goal);
            values.put(FeedEntry.COLUMN_NAME_POINTS,points);

            long newRowId;
            newRowId = db.insert(FeedEntry.TABLE_NAME,null,values);
        } finally {
            db.close();
        }
    }

    public void deleteAllEntriesonSecond(){
        FeedGoalsDbHelper feedGoalsDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = feedGoalsDbHelper.getWritableDatabase();

        db.delete(FeedEntry.SECOND_TABLE_NAME,null,null);
        db.close();
    }

    // insert in SECOND Database
    public void insertSecond(String goal,long datetime){

        FeedGoalsDbHelper feedGoalsDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = feedGoalsDbHelper.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FeedEntry.SECOND_COLUMN_GOAL,goal);
            contentValues.put(FeedEntry.SECOND_COLUMN_DATETIME,datetime);

            long insert = db.insert(FeedEntry.SECOND_TABLE_NAME,null,contentValues);
        } finally {
            db.close();
        }

    }

    // get all from Second DB
    public List<Rewards> getAllFromSecond(){

        List<Rewards> rewardsList = new ArrayList<>();

        String SQLQuery = "SELECT * FROM " + FeedEntry.SECOND_TABLE_NAME;

        FeedGoalsDbHelper feedGoalsDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = feedGoalsDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(SQLQuery,null);

        if(cursor.moveToFirst()) {
            try {
                do {
                    Rewards rewards = new Rewards();
                    rewards.setGoal(cursor.getString(cursor.getColumnIndex(FeedEntry.SECOND_COLUMN_GOAL)));
                    rewards.setDatetime(cursor.getLong(cursor.getColumnIndex(FeedEntry.SECOND_COLUMN_DATETIME)));

                    rewardsList.add(rewards);

                } while (cursor.moveToNext());
            } finally {
                db.close();
            }
        }

        return rewardsList;

    }

    public List<Rewards> getInTimespan(long beginn,long end){

        List <Rewards> rewardsList = new ArrayList<>();

        String SQLQuery = "SELECT * FROM "+FeedEntry.SECOND_TABLE_NAME + " WHERE "+FeedEntry.SECOND_COLUMN_DATETIME+ " between "+ beginn +" and " + end ;

        FeedGoalsDbHelper feedGoalsDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = feedGoalsDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(SQLQuery,null);

        if(cursor.moveToFirst()) {
            try {
                do {
                    Rewards rewards = new Rewards();
                    rewards.setGoal(cursor.getString(cursor.getColumnIndex(FeedEntry.SECOND_COLUMN_GOAL)));
                    rewards.setDatetime(cursor.getLong(cursor.getColumnIndex(FeedEntry.SECOND_COLUMN_DATETIME)));

                    rewardsList.add(rewards);

                } while (cursor.moveToNext());
            } finally {
                db.close();
            }
        }

        return rewardsList;


    }

    public List<String> getAllGoals(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            ArrayList<String> result = new ArrayList<>();
           // Cursor c = db.query(FeedEntry.TABLE_NAME,new String[] { FeedEntry.COLUMN_NAME_GOAL},null,null,null,null,FeedEntry.COLUMN_NAME_GOAL+" ASC");
            Cursor c = db.rawQuery("SELECT " + FeedEntry.COLUMN_NAME_GOAL + " FROM " + FeedEntry.TABLE_NAME + " ORDER BY " + FeedEntry.COLUMN_NAME_POINTS + " ASC" , null);
            try {
                while (c.moveToNext()){
                    result.add(c.getString(0));
                }
                return result;
            } finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }

    public List<Integer> getPointbyGoals(String name){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<Integer>  result = new ArrayList<Integer>();
        try {

            Cursor c =db.query(FeedEntry.TABLE_NAME,new String[] {FeedEntry.COLUMN_NAME_POINTS},FeedEntry.COLUMN_NAME_GOAL+"=?",new String[]{name},null,null,null);
            try {
                while (c.moveToNext()){
                    result.add(c.getInt(0));
                }
                return result;
            }finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }

    public List<String> getGoalName(String name){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<String>  result = new ArrayList<String>();
        try {

            Cursor c =db.query(FeedEntry.TABLE_NAME,new String[] {FeedEntry.COLUMN_NAME_GOAL},FeedEntry.COLUMN_NAME_GOAL+"=?",new String[]{name},null,null,null);
            try {
                while (c.moveToNext()){
                    result.add(c.getString(0));
                }
                return result;
            }finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }


    public List<Integer> getIDbyName(String name){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ArrayList<Integer>  result = new ArrayList<Integer>();
        try {

            Cursor c =db.query(FeedEntry.TABLE_NAME,new String[] {FeedEntry._ID},FeedEntry.COLUMN_NAME_GOAL+"=?",new String[]{name},null,null,null);
            try {
                while (c.moveToNext()){
                    result.add(c.getInt(0));
                }
                return result;
            }finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }

    public List<Integer> getAllPoints(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            ArrayList<Integer> result = new ArrayList<>();
            Cursor c = db.query(FeedEntry.TABLE_NAME,new String[] { FeedEntry.COLUMN_NAME_POINTS},null,null,null,null,FeedEntry.COLUMN_NAME_POINTS+" ASC");
            try {
                while (c.moveToNext()){
                    result.add(c.getInt(0));
                }
                return result;
            } finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }

    public List<Integer> getAllIDs(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        try {
            ArrayList<Integer> result = new ArrayList<>();
            Cursor c = db.query(FeedEntry.TABLE_NAME,new String[] { FeedEntry._ID},null,null,null,null,null);
            try {
                while (c.moveToNext()){
                    result.add(c.getInt(0));
                }
                return result;
            } finally {
                c.close();
            }
        }finally {
            db.close();
        }
    }




    public void deleteWholeDB(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
        }finally {
            db.close();
        }


    }

    public void deleteSecondDB(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            db.execSQL(SQL_DELETE_SECOND);
        }finally {
            db.close();
        }


    }


    public void createDB(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mDbHelper.onCreate(db);
    }

    public void createSecondDB(){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mDbHelper.onCreateSecond(db);

    }

    public void updateDB(String name,String update,String column){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(FeedEntry.COLUMN_NAME_GOAL,update);
            db.update(FeedEntry.TABLE_NAME,contentValues,column+"=?",new String[]{name});
        }finally {
            db.close();
        }

    }

    public void deleteEntryOnDB(String name,String column){
        FeedGoalsDbHelper mDbHelper = new FeedGoalsDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            db.delete(FeedEntry.TABLE_NAME,column+"=?",new String[]{name});
        }finally {
            db.close();
        }

    }



}
