package com.thinkitlimited.digitalid.classes;

/**
 * Created by JOSHUA BUJO on 3/21/2017.
 */

public class Cards {
    public String id;
    public String name;
    public String number;
    public String nationality;
    public String date;
    public String tag;
    public String other;

    public Cards(String id, String name, String number, String nationality, String date, String other, String tag){
        this.id= id;
        this.name= name;
        this.number= number;
        this.nationality= nationality;
        this.date= date;
        this.other= other;
        this.tag= tag;
    }

    public void setId(String id){
        this.id= id;
    }

    public void setName(String id){
        this.name= id;
    }
    public void setNumber(String id){
        this.number= id;
    }
    public void setNationality(String id){
        this.nationality= id;
    }
    public void setDate(String id){
        this.date= id;
    }
    public void setOther(String id){
        this.other= id;
    }
    public void setTagVal(String id){
        this.tag= id;
    }

    //getters
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getNumber(){
        return this.number;
    }
    public String getNationality(){
        return this.nationality;
    }
    public String getDate(){
        return this.date;
    }
    public String getOther(){
        return this.other;
    }
    public String getTagVal(){
        return this.tag;
    }




}
