package com.example.storeclerk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListDataPump {

    public static HashMap<Groupname, List<Groupname>> getData(List<Groupname> data) {
        HashMap<Groupname, List<Groupname>> expandableListDetail = new HashMap<>();

        List<Groupname> cricket = new ArrayList<Groupname>();

        Groupname Cricket1 = new Groupname("","","","","Pen","All","20","20");

        Groupname Cricket11 = new Groupname("","","","","Pen","iss","5","1");


        cricket.add(Cricket11);


        expandableListDetail.put(Cricket1, cricket);

        return expandableListDetail;
    }
}


class Groupname {
    // It is specified while object creation.
    // Cannot be changed once object is created. No setter for this field.

    private  String formnumber;
    private  String formdetailnumber;
    private  String itemnumber;
    private  String binnumber;
    private  String desc;
    private  String dept;
    private  String needed;
    private  String actual;

    public Groupname(String formnumber, String formdetailnumber, String itemnumber, String binnumber, String desc, String dept, String needed, String actual) {
        this.formnumber = formnumber;
        this.formdetailnumber = formdetailnumber;
        this.itemnumber = itemnumber;
        this.binnumber = binnumber;
        this.desc = desc;
        this.dept = dept;
        this.needed = needed;
        this.actual = actual;
    }

    public String getFormnumber() {
        return formnumber;
    }

    public void setFormnumber(String formnumber) {
        this.formnumber = formnumber;
    }

    public String getFormdetailnumber() {
        return formdetailnumber;
    }

    public void setFormdetailnumber(String formdetailnumber) {
        this.formdetailnumber = formdetailnumber;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getBinnumber() {
        return binnumber;
    }

    public void setBinnumber(String binnumber) {
        this.binnumber = binnumber;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNeeded() {
        return needed;
    }

    public void setNeeded(String needed) {
        this.needed = needed;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groupname groupname = (Groupname) o;
        return formnumber.equals(groupname.formnumber) &&
                formdetailnumber.equals(groupname.formdetailnumber) &&
                itemnumber.equals(groupname.itemnumber) &&
                binnumber.equals(groupname.binnumber) &&
                desc.equals(groupname.desc) &&
                dept.equals(groupname.dept) &&
                needed.equals(groupname.needed) &&
                actual.equals(groupname.actual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formnumber, formdetailnumber, itemnumber, binnumber, desc, dept, needed, actual);
    }
}

