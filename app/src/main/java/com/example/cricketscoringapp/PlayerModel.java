package com.example.cricketscoringapp;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerModel implements Parcelable {
    private String name;
    private boolean isBaller;

    public PlayerModel(String name, boolean isBaller) {
        this.name = name;
        this.isBaller = isBaller;
    }

    protected PlayerModel(Parcel in) {
        name = in.readString();
        isBaller = in.readByte() != 0;
    }

    public static final Creator<PlayerModel> CREATOR = new Creator<PlayerModel>() {
        @Override
        public PlayerModel createFromParcel(Parcel in) {
            return new PlayerModel(in);
        }

        @Override
        public PlayerModel[] newArray(int size) {
            return new PlayerModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBaller() {
        return isBaller;
    }

    public void setBaller(boolean baller) {
        isBaller = baller;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isBaller ? 1 : 0));
    }
}
