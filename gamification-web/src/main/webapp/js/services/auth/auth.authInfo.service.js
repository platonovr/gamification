/**
 * Created by vladislav on 10.04.2015.
 */
angular.module('gamificationApp')
    .service('AuthInfo', AuthInfo);

AuthInfo.$inject = ['$http', '$location', 'UserApiService', '$q', '$rootScope'];
function AuthInfo($http, $location, UserApiService, $q, $rootScope) {
    var user = null;

    return {
        updateCurrentUser: function (token) {
            var self = this;
            UserApiService.getUser(token).then(function (response) {
                self.setUser(response.data);
            }, function (error) {
                return $q.reject(error);
            });
        },
        login: function (loginForm) {
            var self = this;
            var params = {username: loginForm.username, password: loginForm.password};
            return UserApiService.loginUser(params).then(function (response) {
                var token = response.data.token;
                self.setToken(token);
                self.updateCurrentUser(token);
                $rootScope.$broadcast('authStateChanged', true);
            }, function (error) {
                return $q.reject(error);
            });
        },
        logout: function () {
            localStorage.removeItem('token');
            localStorage.removeItem('auth');
            user = null;
            $rootScope.$broadcast('authStateChanged', false);
            $location.path('login');
        },
        setToken: function (token) {
            localStorage.setItem('token', JSON.stringify(token));
        },
        getToken: function () {
            return JSON.parse(localStorage.getItem('token'));
        },
        setUser: function (data) {
            user = data;
            localStorage.setItem('auth', JSON.stringify(data));
        },
        isLogin: function () {
            this.checkUser();
            return !(user == null);
        },
        getUser: function () {
            this.checkUser();
            return user;
        },
        checkUser: function () {
            if (user == null && localStorage.getItem('auth') != null)
                this.setUser(JSON.parse(localStorage.getItem('auth')));
        }

    };
}