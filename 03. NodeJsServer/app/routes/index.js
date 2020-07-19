const usersRoutes = require('./users_routes');
const requestRoutes = require('./request_routes');
const requestsRoutes = require('./requests_routes');
module.exports = function (app) {
    usersRoutes(app);
    requestRoutes(app);
    requestsRoutes(app);
};
