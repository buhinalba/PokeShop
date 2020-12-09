import {dataHandler} from "./data_handler.js";

export let dom = {
    init: function () {
       // need to initialize event listeners for type selection
        let selectorButton = document.querySelector("#submit-search");
        let selectedType = document.querySelector("#select-type");
        selectorButton.addEventListener('click', () => {
            dom.loadPokemonsByType(selectedType.value);
        });
        this.initAddToCartButton();
        this.addReviewCartListener();
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
                    <p class="name">Name: ${pokemon.name}</p>
                    <p class="categories">Types: ${pokemon.pokemonCategory.join(", ")}</p>
                    <p class="price">Single price: ${pokemon.defaultPrice}</p>
                    <img src="${pokemon.spriteImageUrl}"/>
                    <div class="counter">
                        <button class="increase-count" type="button">&#43;</button>
                        <p class="count">${element.count}</p>
                        <button class="decrease-count" type="button">&#45;</button>
                    </div>
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
