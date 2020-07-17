module.exports = function (app) {
    app.get('/users', (request, response) => {
        var result = {
            "id": 1,
            "name": "Kamilya",
            "lastname": "Khayrullina",
            "age": 19,
            "city": "Kazan",
            "university": "Kazan Federal University",
            "faculty": "ITIS"
        };
        response.send(JSON.stringify(result));
    });
};