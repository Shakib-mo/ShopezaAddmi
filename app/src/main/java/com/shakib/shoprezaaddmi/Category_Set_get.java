package com.shakib.shoprezaaddmi;

public class Category_Set_get {
    public String image;
    public String name;
    public String catid;
    public String b_id;
    public String b_name;
    public String b_kg;
    public String b_price;
    public String b_pic;



    public Category_Set_get(String catid, String name){
        this.name = name;
        this.catid=catid;
    }
    public Category_Set_get(String catid, String name, String image){
        this.image = image;
        this.name = name;
        this.catid=catid;
    }
    public Category_Set_get(String b_id,String b_name,String b_kg,String b_price,String b_pic){
        this.b_id = b_id;
        this.b_name = b_name;
        this.b_kg = b_kg;
        this.b_price = b_price;
        this.b_pic = b_pic;


    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getB_kg() {
        return b_kg;
    }

    public void setB_kg(String b_kg) {
        this.b_kg = b_kg;
    }

    public String getB_price() {
        return b_price;
    }

    public void setB_price(String b_price) {
        this.b_price = b_price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String cid) {
        this.catid = cid;
    }


}
