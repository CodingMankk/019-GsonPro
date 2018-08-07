package com.codingmankk.www.gsonpro.Entity;

import com.codingmankk.www.gsonpro.UserJsonAdapterTypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * ================================================
 * 版    本：
 * 创建日期：
 * 描    述：
 * 修订历史：
 * ================================================
 */
@JsonAdapter(UserJsonAdapterTypeAdapter.class)
public class UserJsonAdapter {

    public String name;
    public int age;
    @SerializedName(value = "emailAddress",alternate = {"email_address","email"})
    public String emailAddress;

    public UserJsonAdapter(String name, int age, String emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }


    public UserJsonAdapter(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserJsonAdapter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
