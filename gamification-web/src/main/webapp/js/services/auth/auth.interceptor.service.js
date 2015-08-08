/**
 * Created by ElessarST on 14.04.2015.
 */
angular
    .module('gamificationApp')
    .factory('AuthInterceptor', AuthInterceptor);

AuthInterceptor.$inject = ['$q', '$injector','$location', 'localStorageService'];
function AuthInterceptor($q, $injector, $location, localStorageService){
    var authInterceptorFactory = {};

    var request = function (config) {
        config.headers = config.headers || {};

        var authData = localStorageService.get('token');
        if (authData) {
            config.headers.Authorization = authData.token;
        }

        return config;
    };

    var response = function (rejection) {
        if (rejection.status === 401) {
            var AuthInfo = $injector.get('AuthInfo');
            var authData = localStorageService.get('authorizationData');

            if (authData) {
                if (authData.useRefreshTokens) {
                    $location.path('/refresh');
                    return $q.reject(rejection);
                }
            }
            AuthInfo.logout();
            //$location.path('/login');
        }
        return $q.reject(rejection);
    };

    authInterceptorFactory.request = request;
    authInterceptorFactory.responseError = response;

    return authInterceptorFactory;
}