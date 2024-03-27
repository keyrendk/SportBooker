package com.example.sportbooker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // database
    private static final String DB_NAME = "sportbooker";
    private static final int DB_VERSION = 1;

    // users table
    private static final String USERS_TABLE = "users";
    private static final String COL_USERS_ID = "user_id";
    private static final String COL_USERS_NAME = "name";
    private static final String COL_USERS_GENDER = "gender";
    private static final String COL_USERS_EMAIL = "email";
    private static final String COL_USERS_PASSWORD = "password";

    // facilities table
    private static final String FACILITIES_TABLE = "facilities";
    private static final String COL_FACILITIES_ID = "facility_id";
    private static final String COL_FACILITIES_NAME = "facility_name";
    private static final String COL_FACILITIES_TYPE = "type";
    private static final String COL_FACILITIES_OPENING_HOUR = "opening_hour";
    private static final String COL_FACILITIES_CLOSING_HOUR = "closing_hour";
    private static final String COL_FACILITIES_PRICE = "price";

    // bookings table
    private static final String BOOKINGS_TABLE = "bookings";
    private static final String COL_BOOKINGS_ID = "booking_id";
    private static final String COL_BOOKINGS_ORDER_DATE = "order_date";
    private static final String COL_BOOKINGS_USE_DATE = "use_date";
    private static final String COL_BOOKINGS_START_TIME = "start_time";
    private static final String COL_BOOKINGS_FINISH_TIME = "finish_time";
    private static final String COL_BOOKINGS_PRICE = "price";
    private static final String COL_BOOKINGS_STATUS = "status";

    // schedules table
    private static final String SCHEDULES_TABLE = "schedules";
    private static final String COL_SCHEDULES_DATE = "date";
    private static final String COL_SCHEDULES_OPENING_HOUR = "opening_hour";
    private static final String COL_SCHEDULES_CLOSING_HOUR = "closing_hour";
    private static final String COL_SCHEDULES_PRICE = "price";

    // transactions table
    private static final String TRANSACTIONS_TABLE = "transactions";
    private static final String COL_TRANSACTIONS_BOOKING_DATE = "booking_date";
    private static final String COL_TRANSACTIONS_TIME = "time";
    private static final String COL_TRANSACTIONS_STATUS = "status";
    private static final String COL_TRANSACTIONS_AMOUNT = "amount";
    private static final String COL_TRANSACTIONS_METHOD = "method";

    // constructor DBHandler
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // users table
        String createUsersTable = "CREATE TABLE " + USERS_TABLE + " (" +
                COL_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERS_NAME + " TEXT, " +
                COL_USERS_GENDER + " TEXT, " +
                COL_USERS_EMAIL + " TEXT, " +
                COL_USERS_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // facilities table
        String createFacilitiesTable = "CREATE TABLE " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FACILITIES_NAME + " TEXT, " +
                COL_FACILITIES_TYPE + " TEXT, " +
                COL_FACILITIES_OPENING_HOUR + " TEXT, " +
                COL_FACILITIES_CLOSING_HOUR + " TEXT, " +
                COL_FACILITIES_PRICE + " TEXT)";
        String insertFacility1 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F01', 'Footbal Field A', 'Outside', '08:00', '21:00', '50000')";
        String insertFacility2 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F02', 'Footbal Field B', 'Outside', '08:00', '21:00', '75000')";
        String insertFacility3 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F03', 'Basketball Field A', 'Inside', '08:00', '21:00', '150000')";
        String insertFacility4 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F04', 'Basketball Field B', 'Inside', '08:00', '21:00', '175000')";
        String insertFacility5 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F05', 'Badminton Field A', 'Inside', '08:00', '21:00', '25000')";
        String insertFacility6 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F06', 'Badminton Field B', 'Inside', '08:00', '21:00', '50000')";
        String insertFacility7 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F07', 'Badminton Field C', 'Inside', '08:00', '21:00', '75000')";
        String insertFacility8 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F08', 'Futsal Field A', 'Inside', '08:00', '21:00', '100000')";
        String insertFacility9 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F09', 'Futsal Field B', 'Inside', '08:00', '21:00', '150000')";
        String insertFacility10 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F10', 'Volleyball Field A', 'Outside', '08:00', '21:00', '100000')";
        String insertFacility11 = "INSERT INTO " + FACILITIES_TABLE + " (" +
                COL_FACILITIES_ID + ", " +
                COL_FACILITIES_NAME + ", " +
                COL_FACILITIES_TYPE + ", " +
                COL_FACILITIES_OPENING_HOUR + ", " +
                COL_FACILITIES_CLOSING_HOUR + ", " +
                COL_FACILITIES_PRICE + ") VALUES ('F11', 'Volleyball Field B', 'Inside', '08:00', '21:00', '175000')";
        db.execSQL(createFacilitiesTable);
        db.execSQL(insertFacility1);
        db.execSQL(insertFacility2);
        db.execSQL(insertFacility3);
        db.execSQL(insertFacility4);
        db.execSQL(insertFacility5);
        db.execSQL(insertFacility6);
        db.execSQL(insertFacility7);
        db.execSQL(insertFacility8);
        db.execSQL(insertFacility9);
        db.execSQL(insertFacility10);
        db.execSQL(insertFacility11);

        // bookings table
        String createBookingsTable = "CREATE TABLE " + BOOKINGS_TABLE + " (" +
                COL_BOOKINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BOOKINGS_ORDER_DATE + " TEXT, " +
                COL_BOOKINGS_USE_DATE + " TEXT, " +
                COL_BOOKINGS_START_TIME + " TEXT, " +
                COL_BOOKINGS_FINISH_TIME + " TEXT, " +
                COL_BOOKINGS_PRICE + " TEXT, " +
                COL_BOOKINGS_STATUS + " TEXT)";
        db.execSQL(createBookingsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }
}
