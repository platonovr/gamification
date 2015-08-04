angular
    .module('gamificationApp')
    .controller('LoginController', LoginController);

LoginController.$inject = ['$scope', '$http', '$location', '$timeout', 'AuthInfo'];
function LoginController($scope, $http, $location, $timeout, AuthInfo) {
    var vm = this;
    vm.login = login;
    vm.user = {};
    console.log("success");
    function login() {
        AuthInfo.login(vm.user).then(function (data) {
            //$scope.$emit('Auth');
            $location.path('#/challenges');
        }, function (error) {
            vm.error = error;
        });
    }
}