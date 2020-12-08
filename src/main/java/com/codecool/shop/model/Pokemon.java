package com.codecool.shop.model;

import java.util.List;
import java.util.stream.Collectors;

public class Pokemon extends BaseModel {
    private int defaultPrice;
    private List<String> pokemonCategory;
    private String spriteImageUrl;


    public Pokemon(int id, String name, int defaultPrice, List<String> pokemonCategory, String pokemonSprite) {
        super(name);
        this.setId(id);
        this.setPrice(defaultPrice);
        this.setPokemonCategories(pokemonCategory);
        this.setSpriteImageUrl(pokemonSprite);
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(int price) {
        this.defaultPrice = price;
    }

    public List<String> getPokemonCategory() {
        return pokemonCategory;
    }

    public String getPokemonCategoryString() {
        return String.join(", ", this.pokemonCategory);
    }

    public void setPokemonCategories(List<String> pokemonCategory) {
        this.pokemonCategory = pokemonCategory;
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
                        "productCategory: %4$s, " +
                        "spriteImageUrl: %5$s ",
                this.id,
                this.name,
                this.defaultPrice,
                this.pokemonCategory.stream().map(Object::toString).collect(Collectors.joining(", ")),
                this.spriteImageUrl);
    }
}
