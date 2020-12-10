export let dataHandler = {
    _data: {}, // it is a "cache for all data received: boards, cards and statuses. It is not accessed from outside.
    _api_get: function (url, callback) {
        // it is not called from outside
        // loads data from API, parses it and calls the callback with it

        fetch(url, {
            method: 'GET',
            credentials: 'same-origin'
        })
            .then(response => response.json())  // parse the response as JSON
            .then(json_response => callback(json_response));  // Call the `callback` with the returned object
    },
    _api_post: function (url, data, callback) {
        // it is not called from outside
        // sends the data to the API, and calls callback function

        fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data),
            credentials: "same-origin"
        })
            .then(response => response.json())  // parse the response as JSON
            .then(json_response => callback(json_response))
            .catch(error => {
                console.log("Fetch error: " + error);
            });
    },

    getPokemons: function (callback) {
        this._api_get('localhost:8080', (response) => {
            this._data['pokemons'] = response;
            callback(response);
        });
    },

    getPokemonById: function (pokemonId, callback) {
        this._api_get(`/https://pokeapi.co/api/v2/pokemon/${pokemonId}`, (pokemonId, response) => {
            this._data['pokemon'] = response;
            callback(pokemonId, response);
        });
    },


    getPokemonsByType: function (pokemonType, offset, callback) {
        this._api_get(`http://localhost:8080/filter-by-type?type=${pokemonType}&offset=${offset}`, (response) => {
            this._data['pokemons'] = response;
            callback(response);
        });
    },

    getPage: function (offset, callback) {
        this._api_get(`http://localhost:8080/pokemons/?offset=${offset}`, (response) => {
            console.log("getPage offset: " + offset);
            this._data['pokemons'] = response;
            callback(response);
        });
    },

    addPokemonToCart: function (pokemonID, callback) {
        this._api_get(`http://localhost:8080/add-to-cart?pokemon-id=${pokemonID}`, (response) => {
            callback(response);
        })
    },

    getCartContent: function (callback) {
        this._api_get(`http://localhost:8080/cart-content`, (response) => {
            callback(response);
        })
    },

    postCheckoutInformation: function (data, callback) {
        this._api_post('http://localhost:8080/valid-checkout', {checkout_information: data}, (response) => {
            callback(response);
        })
    },

    deletePokemon: function (pokemonId, callback) {
        this._api_get(`http://localhost:8080/edit/delete?pokemon-id=${pokemonId}`, (response) => {
            callback(response);
        })
    },

    decreasePokemon: function (pokemonId, callback) {
        this._api_get(`http://localhost:8080/edit/decrease?pokemon-id=${pokemonId}`, (response) => {
            callback(response);
        })
    },

    increasePokemon: function (pokemonId, callback) {
        this._api_get(`http://localhost:8080/edit/increase?pokemon-id=${pokemonId}`, (response) => {
            callback(response);
        })
    }
}