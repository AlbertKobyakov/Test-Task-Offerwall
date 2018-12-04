package ua.kobyakov.offerwall.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ServerResponse {

    @SerializedName("allow")
    @Expose
    @PrimaryKey
    private boolean allow;

    public boolean isAllow() {
        return allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

    @NonNull
    @Override
    public String toString() {
        return "ServerResponse{" +
                "allow=" + allow +
                '}';
    }
}