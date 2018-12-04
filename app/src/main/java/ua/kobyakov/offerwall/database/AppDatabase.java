package ua.kobyakov.offerwall.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ua.kobyakov.offerwall.model.ServerResponse;
import ua.kobyakov.offerwall.dao.ResponseDao;

@Database(entities = {
        ServerResponse.class,
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ResponseDao responseDao();
}
