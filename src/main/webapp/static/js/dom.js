import {dataHandler} from "./data_handler.js";

export let dom = {
    init: function () {
       // need to initialize event listeners for type selection
        let selectorButton = document.querySelector("#submit-search");
        let selectedType = document.querySelector("#select-type");
        selectorButton.addEventListener('click', () => {
            dom.loadPokemonsByType(selectedType.value);
        })
    },

    loadPokemons: function () {
        dataHandler.getPokemons(function (pokemons) {
            dom.showPokemons(pokemons);
        });
    },

    loadPokemonById: function (pokemonId) {
        dataHandler.getPokemonById(pokemonId, function (pokemons) {
            dom.showPokemons(pokemons);
        });
    },

    loadPokemonsByType: function (type) {
        dataHandler.getPokemonsByType(type, function (pokemons) {
            dom.showPokemons(pokemons);
        });
    },

    showPokemons: function (pokemons){
        let pokemonsContainer = document.querySelector(".card-container");
        pokemonsContainer.innerHTML = "";
        let loadedPokemons = "";
        for (let pokemon of pokemons) {
            loadedPokemons += `
                            <div class="card">
                                <img src="${pokemon.spriteImageUrl}" alt=""/>
                                <div class="card-header">
                                    <h4 class="card-title"> ${pokemon.name}</h4>
                                </div>
                                <div class="card-body">
                                    <div class="card-text">
                                        <p class="card-text">${pokemon.pokemonCategory}</p>
                                        <p class="lead"> ${pokemon.defaultPrice}</p>
                                    </div>
                                    <div class="card-text">
                                        <a class="btn btn-success" href="#">Add to cart</a>
                                    </div>
                                </div>
                            </div>
                                `;
        }

        pokemonsContainer.insertAdjacentHTML('beforeend', loadedPokemons);
    }
}