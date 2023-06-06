package com.example.termproject.UserManagement;
import com.google.firebase.firestore.CollectionReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MemberInfo {
    // 없어서 만듦. 회원이 가진 정보 내역 - ojs
    private String Uid;
    private String name;
    private String schoolNum;
    private String department;
    private String phoneNum;
    private ArrayList<String> bookMark;
    private ArrayList<String> adminCategory;
    private ArrayList<String> adminClub;

    public MemberInfo(){}

    public MemberInfo(String uid, String name, String schoolNum, String department, String phoneNum,
                      ArrayList<String> bookMark, ArrayList<String> adminCategory,
                      ArrayList<String> adminClub) {
        this.Uid = uid;
        this.name = name;
        this.schoolNum = schoolNum;
        this.department = department;
        this.phoneNum = phoneNum;
        this.bookMark = bookMark;
        this.adminCategory = adminCategory;
        this.adminClub = adminClub;
    }

    public MemberInfo(String uid, String name, String schoolNum, String department, String phoneNum) {
        this.Uid = uid;
        this.name = name;
        this.schoolNum = schoolNum;
        this.department = department;
        this.phoneNum = phoneNum;

        this.bookMark = new ArrayList<>();
        this.adminCategory = new ArrayList<>();
        this.adminClub = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public ArrayList<String> getBookMark() {return bookMark;}
    public void setBookMark(ArrayList<String> bookMark) {
        this.bookMark = bookMark;
    }


    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }


    public ArrayList<String> getadminCategory() {
        return adminCategory;
    }

    public void setadminCategory(ArrayList<String> adminCategory) {
        this.adminCategory = adminCategory;
    }

    public ArrayList<String> getadminClub() {
        return adminClub;
    }
    public void setadminClub(ArrayList<String> adminClub) { this.adminClub = adminClub;}
}

