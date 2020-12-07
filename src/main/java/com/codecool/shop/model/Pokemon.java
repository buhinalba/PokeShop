package com.codecool.shop.model;

import java.util.Currency;

public class Pokemon extends BaseModel {
    private float defaultPrice;
    private Currency defaultCurrency;
    private PokemonCategory pokemonCategory;
    private String spriteImageUrl;


    public Pokemon(int id, String name, float defaultPrice, String currencyString, PokemonCategory pokemonCategory, String sprite) {
        super(name);
        this.setId(id);
        this.setPrice(defaultPrice, currencyString);
        this.setSpriteImageUrl(sprite);
        this.setProductCategory(pokemonCategory);
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

    public PokemonCategory getProductCategory() {
        return pokemonCategory;
    }

    public void setProductCategory(PokemonCategory pokemonCategory) {
        this.pokemonCategory = pokemonCategory;
        this.pokemonCategory.addPokemon(this);
    }

    public String getSpriteImageUrl() {
        return spriteImageUrl;
    }

    public void setSpriteImageUrl(String spirit) {
        this.spriteImageUrl = spirit;
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "spriteImageUrl: %6$s ",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.pokemonCategory.getName(),
                this.spriteImageUrl);
    }
}
