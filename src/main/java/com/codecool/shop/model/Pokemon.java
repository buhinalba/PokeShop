package com.codecool.shop.model;

import java.util.Currency;

public class Pokemon extends BaseModel {

    private float defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private String spiritImageUrl;


    public Pokemon(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, String spirit) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSpiritImageUrl(spirit);
        this.setProductCategory(productCategory);
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public String getSpiritImageUrl() {
        return spiritImageUrl;
    }

    public void setSpiritImageUrl(String spirit) {
        this.spiritImageUrl = spirit;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "spiritImageUrl: %6$s ",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.spiritImageUrl);
    }
}
