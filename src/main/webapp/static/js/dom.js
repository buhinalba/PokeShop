import {dataHandler} from "./data_handler.js";

export let dom = {

    init: function () {
        // need to initialize event listeners for type selection
        dom.loadButtonFunctions();
    },

    loadButtonFunctions: function () {
        dom.loadSelectorButton();
        dom.loadPaginationButtons();
    },

    loadSelectorButton: function () {
        let selectorButton = document.querySelector("#submit-search");
        let selectedType = document.querySelector("#select-type");
        let offset = document.querySelector(".page-title").dataset.offset
        selectorButton.addEventListener('click', () => {
            dom.loadPokemonsByType(selectedType.value, offset);
        })
    },


    loadPaginationButtons: function () {
        let search = document.querySelector(".page-title").dataset.search;
        let prevButton = document.querySelector("#prev-page");
        let nextButton = document.querySelector("#next-page");
        prevButton.addEventListener('click', () => {
            if(search.length === 0) {
                dom.loadPage(-20);
            } else {
                dom.loadPageByType(-20);
            }
        });
        nextButton.addEventListener('click', () => {
            if(search.length === 0) {
                dom.loadPage(20);
            } else {
                dom.loadPageByType(20);
            }
        })
    },

    loadPage: function (offset) {
        let tempOffset = document.querySelector(".page-title");
        if(parseInt(tempOffset.dataset.offset) + offset >= 0) {
            tempOffset.dataset.offset = parseInt(tempOffset.dataset.offset) + offset;
        }
        dataHandler.getPage(tempOffset.dataset.offset, (pokemons) => {
            if(pokemons.length > 0) {
                dom.showPokemons(pokemons);
            } else{
                tempOffset.dataset.offset = parseInt(tempOffset.dataset.offset) - offset;
            }
        });
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

    loadPokemonsByType: function (type, offset) {
        let dataSearch = document.querySelector(".page-title");
        dataSearch.dataset.search = "type";
        dataHandler.getPokemonsByType(type, offset,function (pokemons) {
            console.log(pokemons)
            dom.showPokemons(pokemons);
        });
    },

    showPokemons: function (pokemons) {
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
                                        <p class="card-text">${pokemon.pokemonCategory.length > 1 ?
                "Types: " + pokemon.pokemonCategory.join(", ") :
                "Type: " + pokemon.pokemonCategory}</p>
                                        <p class="lead">Price: ${pokemon.defaultPrice}</p>
                                    </div>
                                    <div class="card-text">
                                        <a class="btn btn-success" href="#">Add to cart</a>
                                    </div>
                                </div>
                            </div>
                                `;
        }

        pokemonsContainer.insertAdjacentHTML('beforeend', loadedPokemons);
    },

    loadPageByType: function(offset){
        let tempOffset = document.querySelector(".page-title");
        if(parseInt(tempOffset.dataset.offset) + offset >= 0) {
            tempOffset.dataset.offset = parseInt(tempOffset.dataset.offset) + offset;
        }
        dataHandler.getPokemonsByType(tempOffset.dataset.offset, (pokemons) => {
            if(pokemons.length > 0) {
                dom.showPokemons(pokemons);
            } else{
                tempOffset.dataset.offset = parseInt(tempOffset.dataset.offset) - offset;
            }
        });
    },
}