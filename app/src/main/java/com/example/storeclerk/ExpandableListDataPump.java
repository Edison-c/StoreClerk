package com.example.storeclerk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListDataPump {

    public static HashMap<Groupname, List<Groupname>> getData() {
        HashMap<Groupname, List<Groupname>> expandableListDetail = new HashMap<>();

        List<Groupname> cricket = new ArrayList<Groupname>();

        Groupname Cricket1 = new Groupname("Pen","All","20","20");

        Groupname Cricket11 = new Groupname("Pen","iss","5","1");
        Groupname Cricket12 = new Groupname("Pen","soc","5","2");
        Groupname Cricket13 = new Groupname("Pen","ee","10","3");

        cricket.add(Cricket11);
        cricket.add(Cricket12);
        cricket.add(Cricket13);

        List<Groupname> cricket3 = new ArrayList<Groupname>();

        Groupname Cricket2 = new Groupname("Rule","All","20","15");

        Groupname Cricket22 = new Groupname("Rule","ISS","10","4");
        Groupname Cricket23 = new Groupname("Rule","SOC","5","5");
        Groupname Cricket24 = new Groupname("Rule","EE","3","6");
        Groupname Cricket25 = new Groupname("Rule","EE","2","7");


        cricket3.add(Cricket22);
        cricket3.add(Cricket23);
        cricket3.add(Cricket24);
        cricket3.add(Cricket25);

        expandableListDetail.put(Cricket1, cricket);
        expandableListDetail.put(Cricket2, cricket3);

        return expandableListDetail;
    }
}


class Groupname {
    // It is specified while object creation.
    // Cannot be changed once object is created. No setter for this field.
    private  String desc;
    private  String dept;
    private  String needed;
    private  String actual;

    public Groupname(String desc, String dept, String needed, String actual) {
        this.desc = desc;
        this.dept = dept;
        this.needed = needed;
        this.actual = actual;
    }


    public final String getDesc() {
        return desc;
    }

    public final void setDesc(String desc) {
        this.desc = desc;
    }


    public final String getDept() {
        return dept;
    }

    public final void setDept(String dept) {
        this.dept = dept;
    }

    public final String getNeeded() {
        return needed;
    }

    public final void setNeeded(String needed) {
        this.needed = needed;
    }

    public final String getActual() {
        return actual;
    }

    public final void setActual(String actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groupname groupname = (Groupname) o;
        return desc.equals(groupname.desc) &&
                dept.equals(groupname.dept) &&
                needed.equals(groupname.needed) &&
                actual.equals(groupname.actual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(desc, dept, needed, actual);
    }
}

