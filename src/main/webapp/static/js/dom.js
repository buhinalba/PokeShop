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
        let reviewCartButton = document.querySelector("#review-cart-button");
        reviewCartButton.addEventListener("click", this.initCartModal);

        let closeButton = document.querySelector(".modal .close");
        closeButton.addEventListener("click", dom.closeModal);
    },

    addToCart: function (event) {
        event.preventDefault();
        let button = event.currentTarget;
        let card = button.closest(".card")
        let pokemonId = card.dataset.pokemonId

        dataHandler.addPokemonToCart(pokemonId, (response) => {
            // todo change cart size (next to cart there is an icon)
        });
    },


    initCartModal: function (event) {
        let cartButton = event.target;

        dataHandler.getCartContent(dom.showCartModal);
    },

    showCartModal: function (response) {
        let modal = document.querySelector(".modal");
        modal.classList.remove("hidden");
        let modalBody = modal.querySelector(".modal-body");

        let cartList = "";
        let totalPrice = response.totalPrice;
        let cartContent = response.cartContent;
        if (cartContent.length === 0) {
            cartList = "Your cart is empty"
        } else {
        cartList += `
            <table>
                <th></th>
                <th>Name</th>
                <th>Types</th>
                <th>Single Price</th>
                <th></th>
                <th></th>
                <th>Count</th>
                <th></th>`
        for (let element of cartContent) {
            let pokemon = element.pokemon;
            cartList += `
                        <tr class="list-element" data-pokemon-id="${pokemon.id}">
                            <td>
                                <button class="delete-button" type="button">&#x1F5D1;</button>
                            </td>                        
                            <td>
                                <p class="name">${pokemon.name}</p>
                            </td>
                            <td>
                                <p class="categories">${pokemon.pokemonCategory.join(", ")}</p>
                            </td>
                            <td>
                                <p class="price">${pokemon.defaultPrice}</p>
                            </td>
                            <td>
                                <img src="${pokemon.spriteImageUrl}"/>
                            </td>
                            <td>
                                <button class="decrease-count" type="button">&#45;</button>
                            </td>
                            
                            <td>
                                <p class="count">${element.count}</p>
                            </td>
                            <td>
                                <button class="increase-count" type="button">&#43;</button>
                            </td>
                            
                        </tr>
                        `;
        }
        cartList += "</table>"
        }
        cartList += `<div class="total-price"><p>Total Price: ${totalPrice}</p></div>`;

        if (cartContent.length !== 0) {cartList += `<div id="checkout-cart-button"><a href="/checkout">Checkout</a></div>`}
        modalBody.innerHTML = cartList;

        dom.initEditCartButtons();
    },



    closeModal: function (event) {
        let closeButton = event.target;
        let modal = closeButton.closest(".modal");
        modal.classList.add("hidden");
    },

    initEditCartButtons: function () {
        let deleteButtons = document.querySelectorAll(".delete-button");
        let decreaseButtons = document.querySelectorAll(".decrease-count");
        let increaseButtons = document.querySelectorAll(".increase-count");

        for (let deleteButton of deleteButtons) {
            deleteButton.addEventListener('click', dom.deletePokemon)
        }

        for (let decreaseButton of decreaseButtons) {
            decreaseButton.addEventListener('click', dom.decreasePokemon)
        }

        for (let increaseButton of increaseButtons) {
            increaseButton.addEventListener('click', dom.increasePokemon)
        }
    },

    deletePokemon: function (event) {
        let button = event.target;
        let pokemonId = button.closest("tr").dataset.pokemonId;
        dataHandler.deletePokemon(pokemonId, dom.removePokemonFromList)
    },

    decreasePokemon: function (event) {
        let button = event.target;
        let pokemonId = button.closest("tr").dataset.pokemonId;
        dataHandler.decreasePokemon(pokemonId, dom.updateCount)
    },

    increasePokemon: function (event) {
        let button = event.target;
        let pokemonId = button.closest("tr").dataset.pokemonId;
        dataHandler.increasePokemon(pokemonId, dom.updateCount)
    },

    updateCount: function (response) {
        let count = response.count;
        let pokemonId = response.pokemonId;

        let affectedRow = document.querySelector(`tr[data-pokemon-id="${pokemonId}"]`)
        let countDisplayer = affectedRow.querySelector(".count");

        countDisplayer.innerHTML = count;

        document.querySelector(".modal .total-price p").innerHTML = "Total price: " + response.totalPrice;
    },

    removePokemonFromList: function (response) {
        let pokemonId = response.pokemonId;
        let affectedRow = document.querySelector(`tr[data-pokemon-id="${pokemonId}"]`)
        affectedRow.remove();
        document.querySelector(".modal .total-price p").innerHTML = "Total price: " + response.totalPrice;

    }
}