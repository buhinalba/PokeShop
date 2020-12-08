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


    getPokemonsByType: function (pokemonType, callback) {
        this._api_get(`http://localhost:8080/filter-by-type?type=${pokemonType}`, (pokemonType, response) => {
            console.log(response);
            this._data['pokemons'] = response;
            callback(pokemonType, response);
        });
    }
}