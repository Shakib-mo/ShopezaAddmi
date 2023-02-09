package com.shakib.shoprezaaddmi;

public class Product_Get_Set {
    public String pr_productid;
    public String pr_productname;
    public String pr_price;
    public String pr_offer_price;
    public String pr_SubCatid;
    public String pr_description;
    public String pr_image;

    public Product_Get_Set(String pr_productid,String pr_productname,String pr_price,String pr_offer_price,String pr_SubCatid,
                           String pr_description,String pr_image){
        this.pr_productid = pr_productid;
        this.pr_productname = pr_productname;
        this.pr_price = pr_price;
        this.pr_offer_price = pr_offer_price;
        this.pr_SubCatid = pr_SubCatid;
        this.pr_description = pr_description;
        this.pr_image = pr_image;
    }

    public String getPr_productid() {
        return pr_productid;
    }

    public void setPr_productid(String pr_productid) {
        this.pr_productid = pr_productid;
    }

    public String getPr_productname() {
        return pr_productname;
    }

    public void setPr_productname(String pr_productname) {
        this.pr_productname = pr_productname;
    }

    public String getPr_price() {
        return pr_price;
    }

    public void setPr_price(String pr_price) {
        this.pr_price = pr_price;
    }

    public String getPr_offer_price() {
        return pr_offer_price;
    }

    public void setPr_offer_price(String pr_offer_price) {
        this.pr_offer_price = pr_offer_price;
    }

    public String getPr_SubCatid() {
        return pr_SubCatid;
    }

    public void setPr_SubCatid(String pr_SubCatid) {
        this.pr_SubCatid = pr_SubCatid;
    }

    public String getPr_description() {
        return pr_description;
    }

    public void setPr_description(String pr_description) {
        this.pr_description = pr_description;
    }

    public String getPr_image() {
        return pr_image;
    }

    public void setPr_image(String pr_image) {
        this.pr_image = pr_image;
    }
}
