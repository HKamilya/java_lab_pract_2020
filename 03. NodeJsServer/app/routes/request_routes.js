bodyParser = require('body-parser').json();

module.exports = function (app) {
    app.post('/request', bodyParser, (request, response) => {
        const {Client} = require('pg');

        const client = new Client({
            user: 'postgres',
            host: 'localhost',
            database: 'postgres',
            password: 'qAzXcvbnm',
        });
        client.connect();
        let name = `${request.body.name}`;
        let email = `${request.body.email}`;
        let message = `${request.body.message}`;
        let back = '<br><a href="/summary.html"><button>Return</button></a>';
        let query = 'Insert into postgres.public.message (name, email, message) values ($1,$2,$3);'
        client.query(query, [name, email, message], (err) => {
            if (err != null)
                response.send("Your request was rejected, please try again." + back);
            else
                response.send("Thanks! I'll answer as soon as possible :)" + back);
            client.end();
        });
    });
};