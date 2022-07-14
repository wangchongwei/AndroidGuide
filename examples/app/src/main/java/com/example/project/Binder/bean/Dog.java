package com.example.project.Binder.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Dog implements Parcelable {

    private int gender;
    private String name;

    public Dog(){

    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Dog(Parcel in) {
        this.gender = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gender);
        dest.writeString(this.name);
    }
}
