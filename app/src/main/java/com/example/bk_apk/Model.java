package com.example.bk_apk;

public class Model {
    private int id;
    private String product_name;
    private String product_price;
    private String product_description;
    private String product_category;
    private String product_mfgdate;
    private byte[] image;

public Model(int id, String product_name,String product_price,String product_description,String product_category,String product_mfgdate,byte[]  image)
{
    this.id = id;
    this.product_name = product_name;
    this.product_price = product_price;
    this.product_description =product_description;
    this.product_category =product_category;
    this.product_mfgdate =product_mfgdate;
    this.image =image;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_mfgdate() {
        return product_mfgdate;
    }

    public void setProduct_mfgdate(String product_mfgdate) {
        this.product_mfgdate = product_mfgdate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
