package com.dlq.mybatis.bean;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-14 17:02
 */
public class Dept {

    private String id;
    private String name;
    private String tele;

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tele='" + tele + '\'' +
                '}'+'\n';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
}
