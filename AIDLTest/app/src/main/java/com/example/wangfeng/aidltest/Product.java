package com.example.wangfeng.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangfeng on 15/8/5.
 */
public final class Product implements Parcelable {

  private int id;
  private String name;
  private String desc;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Product() {
  }

  public Product(Parcel source) {
    readFromParcel(source);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.name);
    dest.writeString(this.desc);
  }

  public void readFromParcel(Parcel source) {
    this.id = source.readInt();
    this.name = source.readString();
    this.desc = source.readString();
  }

  public static final Creator<Product> CREATOR = new Creator<Product>() {
    @Override
    public Product createFromParcel(Parcel source) {
      return new Product(source);
    }

    @Override
    public Product[] newArray(int size) {
      return new Product[size];
    }
  };

}
