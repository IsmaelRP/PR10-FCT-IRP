package com.practica.ismael.pr10_fct_irp.data.local;

import android.content.Context;
import android.provider.ContactsContract;

import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class, Company.class, Meeting.class},/* views = {NextMeeting.class},*/ version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "database";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (ContactsContract.Data.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        }
                    }).build();
                }
            }
        }
        return instance;
    }

    public abstract StudentDao studentDao();
    public abstract CompanyDao companyDao();
    public abstract MeetingDao meetingDao();

}