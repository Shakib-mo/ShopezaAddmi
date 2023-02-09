package com.shakib.shoprezaaddmi;

public class SubCotegory_Get_Set {
    public String subcatid;
    public String subname;
    public String cat_id;
    public String pic;

    public SubCotegory_Get_Set(String subcatid,String subname,String cat_id,String pic) {
        this.subcatid = subcatid;
        this.subname = subname;
        this.cat_id = cat_id;
        this.pic = pic;
    }

    public String getSubcatid() {
        return subcatid;
    }

    public void setSubcatid(String subcatid) {
        this.subcatid = subcatid;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }
    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getCat_id() {
        return cat_id;
    }

}
