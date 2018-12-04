package ua.kobyakov.offerwall.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import ua.kobyakov.offerwall.model.ServerResponse;

@Dao
public interface ResponseDao {

    @Query("SELECT * FROM ServerResponse")
    LiveData<ServerResponse> getLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ServerResponse serverResponse);
}
