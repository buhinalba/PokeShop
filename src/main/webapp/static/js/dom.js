import {dataHandler} from "./data_handler.js";

export let dom = {

    init: function () {
        dom.loadButtonFunctions();
    },

    loadButtonFunctions: function () {
        dom.loadSelectorButton();
        dom.loadPaginationButtons();
    },

    loadSelectorButton: function () {
        let selectorButton = document.querySelector("#submit-search");
        let selectedType = document.querySelector("#select-type");
        selectorButton.addEventListener('click', () => {
            document.querySelector(".page-title").dataset.offset = "0";
            dom.loadPokemonsByType(selectedType.value, document.querySelector(".page-title").dataset.offset);
        })
    },


    loadPaginationButtons: function () {
        let prevButton = document.querySelector("#prev-page");
        let nextButton = document.querySelector("#next-page");
        prevButton.addEventListener('click', () => {
            if(document.querySelector(".page-title").dataset.search.length === 0) {
                dom.loadPage(-20);
            } else {
                dom.loadPageByType(-20);
            }
        });
        nextButton.addEventListener('click', () => {
            if(document.querySelector(".page-title").dataset.search.length === 0) {
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


    loadPokemonsByType: function (type, offset) {
        let dataSearch = document.querySelector(".page-title");
        let selectedType = document.querySelector("#select-type");
        dataSearch.dataset.search = selectedType.value;
        dataHandler.getPokemonsByType(type, offset,function (pokemons) {
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
        let selectedType = document.querySelector("#select-type");
        let type = selectedType.value;
        if(parseInt(tempOffset.dataset.offset) + offset >= 0) {
            tempOffset.dataset.offset = parseInt(tempOffset.dataset.offset) + offset;
        }
        dataHandler.getPokemonsByType(type, tempOffset.dataset.offset, (pokemons) => {
            if(pokemons.length === 20) {
                dom.showPokemons(pokemons);
            } else {
                tempOffset.dataset.offset = parseInt(tempOffset.dataset.offset) - offset;
            }
        });
    },
}