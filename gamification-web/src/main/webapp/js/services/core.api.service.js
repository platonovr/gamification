angular
    .module('gamificationApp')
    .service('ApiService', ApiService);

ApiService.$inject = ['$http', 'CONSTANTS'];
function ApiService($http, CONSTANTS) {
    return {
        api: function (partUrl) {
            return CONSTANTS.API_URI_PREFIX + partUrl;
        }
    };
}