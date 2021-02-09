package com.example.myapplication;

public class NameModel {
    String name;
    String meaning;
    String nameDate;

    public NameModel(String name ,String meaning ,String nameDate){
        this.name = name;
        this.meaning = meaning;
        this.nameDate = nameDate;
    }

    public void pushNameDate(String meaning){
        this.nameDate += " " + meaning;
    }

    public String getName() {
        return name;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getNameDate() {
        return nameDate;
    }
}
