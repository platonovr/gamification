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
        register: register,
        getUser: getUser,
        getCurrentUserInfo: getCurrentUserInfo
    };

    function loginUser(param) {
        return $http.post(ApiService.api(CONSTANTS.LOGIN_URI), param);
    }

    function register(user) {
        return $http.post(ApiService.api('register'), user);
    }

    function getUser(token) {
        return $http.get(CONSTANTS.API_URI_PREFIX + '/user/current', {params: {token: token}})
    }

    function getCurrentUserInfo(token) {
        return $http.get(CONSTANTS.API_URI_PREFIX + '/user/currentUserInfo', {params: {token: token}})
    }
}