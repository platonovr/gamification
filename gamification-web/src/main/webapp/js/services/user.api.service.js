/**
 * Created by ElessarST on 14.04.2015.
 */
angular
    .module('gamificationApp')
    .service('UserApiService', UserApiService);


UserApiService.$inject = ['$http', 'ApiService', 'CONSTANTS'];
function UserApiService($http, ApiService, CONSTANTS) {
    return {
        loginUser: loginUser,
        register: register
    };

    function loginUser(param) {
        return $http.post(ApiService.api(CONSTANTS.LOGIN_URI), param);
    }

    function register(user) {
        return $http.post(ApiService.api('register'), user);
    }
}