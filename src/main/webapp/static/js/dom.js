import {dataHandler} from "./data_handler.js";

export let dom = {

    init: function () {
        dom.loadButtonFunctions();
    },

    loadButtonFunctions: function () {
        dom.loadSelectorButton();
        dom.loadPaginationButtons();
        this.initAddToCartButton();
        this.addReviewCartListener();
    },

    loadSelectorButton: function () {
        let selectorButton = document.querySelector("#submit-search");
        let selectedType = document.querySelector("#select-type");
        selectorButton.addEventListener('click', () => {
            document.querySelector(".page-title").dataset.offset = "0";
            dom.loadPokemonsByType(selectedType.value, document.querySelector(".page-title").dataset.offset);
        });
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
                            <div class="card" data-pokemon-id="${pokemon.id}">
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
                                        <button class="btn btn-success">Add to cart</button>
                                    </div>
                                </div>
                            </div>
                                `;
        }

        pokemonsContainer.insertAdjacentHTML('beforeend', loadedPokemons);
        this.initAddToCartButton();
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

    initAddToCartButton: function () {
        let addToCartButtons = document.querySelectorAll(".card-text .btn-success");

        for (let button of addToCartButtons) {
            button.addEventListener('click', this.addToCart)
        }
    },

    addReviewCartListener: function () {
        let reviewCartButton = document.querySelector(".navbar #review-cart-button");
        reviewCartButton.addEventListener("click", this.initCartModal);

        let closeButton = document.querySelector(".modal .close");
        closeButton.addEventListener("click", dom.closeModal);
    },

    addToCart: function (event) {
        let button = event.currentTarget;
        let card = button.closest(".card")
        let pokemonId = card.dataset.pokemonId

        dataHandler.addPokemonToCart(pokemonId, (response) => console.log(response));
    },

    initCartModal: function (event) {
        console.log("button pushed");
        let cartButton = event.target;

        cartButton.classList.add("active")      // todo remove when closing modal
        dataHandler.getCartContent(dom.showCartModal);
    },

    showCartModal: function (response) {
        let modal = document.querySelector(".modal");
        modal.classList.remove("hidden");
        let modalBody = modal.querySelector(".modal-body");

        let cartList = "";
        let totalPrice = response.totalPrice;
        let cartContent = response.cartContent;
        for (let element of cartContent) {
            let pokemon = element.pokemon;
            cartList += `
                <div class="list-element" data-pokemon-id="${pokemon.id}">
                    <table>
                        <tr>
                            <th>
                                <button class="delete-button" type="button">&#x1F5D1;</button>
                            </th>                        
                            <th>
                                <p class="name">Name: ${pokemon.name}</p>
                            </th>
                            <th>
                                <p class="categories">Types: ${pokemon.pokemonCategory.join(", ")}</p>
                            </th>
                            <th>
                                <p class="price">Single price: ${pokemon.defaultPrice}</p>
                            </th>
                            <th>
                                <img src="${pokemon.spriteImageUrl}"/>
                            </th>
                            <th>
                                <button class="increase-count" type="button">&#43;</button>
                            </th>
                            <th>
                                <p class="count">${element.count}</p>
                            </th>
                            <th>
                                <button class="decrease-count" type="button">&#45;</button>
                            </th>
                        </tr>
                    </table>
                </div>`;
        }
        cartList += `<div class="total-price"><p>Total Price: ${totalPrice}</p></div>`;
        cartList += `<div id="checkout-cart-button"><a href="/checkout">Checkout</a></div>`
        modalBody.innerHTML = cartList;
    },

    closeModal: function (event) {
        let closeButton = event.target;
        let modal = closeButton.closest(".modal");
        modal.classList.add("hidden");
    }
}
