import {dataHandler} from "./data_handler.js";

export let dom = {
    init: function () {
        dom.loadPokemons()
    },

    loadPokemons: function () {
        dataHandler.getPokemons(function (pokemons) {
            dom.showPokemons(pokemons);
        });
    },

    showPokemons: function (pokemons){
        //TODO print pokemons
    }
}