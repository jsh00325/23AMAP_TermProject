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
    private Map<String, List<String>> bookMark;
    private String filteringBinary;
    private ArrayList<Boolean> checked;
    private ArrayList<String> scrap;
    private ArrayList<String> questionList;
    private ArrayList<String> alarmList;
    private ArrayList<String> filtering;

    public MemberInfo(){}

    public MemberInfo(String uid, String name, String schoolNum, String department, String phoneNum,
                      Map<String, List<String>> bookMark, String filteringBinary, ArrayList<Boolean> checked,
                      ArrayList<String> scrap, ArrayList<String> questionList, ArrayList<String> alarmList,
                      ArrayList<String> filtering) {
        this.Uid = uid;
        this.name = name;
        this.schoolNum = schoolNum;
        this.department = department;
        this.phoneNum = phoneNum;
        this.bookMark = bookMark;
        this.filteringBinary = filteringBinary;
        this.checked = checked;
        this.scrap = scrap;
        this.questionList = questionList;
        this.alarmList = alarmList;
        this.filtering = filtering;
    }

    public MemberInfo(String uid, String name, String schoolNum, String department, String phoneNum) {
        this.Uid = uid;
        this.name = name;
        this.schoolNum = schoolNum;
        this.department = department;
        this.phoneNum = phoneNum;

        this.bookMark = new HashMap<>();
        this.filteringBinary = null;
        this.checked = new ArrayList<Boolean>(Arrays.asList(false, false));
        this.scrap = new ArrayList<>();
        this.questionList = new ArrayList<>();
        this.alarmList = new ArrayList<>();
        this.filtering = new ArrayList<>();
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

    public Map<String, List<String>> getBookMark() {return bookMark;}
    public void setBookMark(Map<String, List<String>> bookMark) {
        this.bookMark = bookMark;
    }

    public String getFilteringBinary() {
        return filteringBinary;
    }
    public void setFilteringBinary(String filteringBinary) { this.filteringBinary = filteringBinary;}

    public ArrayList<Boolean> getChecked(){return checked;}
    public void setChecked(ArrayList<Boolean> checked) {this.checked = checked;}

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public ArrayList<String> getScrap() {
        return scrap;
    }

    public void setScrap(ArrayList<String> scrap) {
        this.scrap = scrap;
    }

    public ArrayList<String> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<String> questionList) {
        this.questionList = questionList;
    }

    public ArrayList<String> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(ArrayList<String> alarmList) {
        this.alarmList = alarmList;
    }

    public ArrayList<String> getFiltering() {
        return filtering;
    }
    public void setFiltering(ArrayList<String> filtering) { this.filtering = filtering;}
}

