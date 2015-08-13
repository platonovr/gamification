angular
    .module('gamificationApp')
    .controller('LoginController', LoginController);

LoginController.$inject = ['$scope', '$http', '$location', '$timeout', 'AuthInfo'];
function LoginController($scope, $http, $location, $timeout, AuthInfo) {
    var vm = this;
    vm.login = login;
    vm.user = {};
    if (AuthInfo.isLogin()) {
        $location.path(encodeURI('challenges'));
    }

    function shakeForm() {
        var l = 20;
        for (var i = 0; i < 10; i++)
            $("form").animate({'margin-left': "+=" + ( l = -l ) + 'px'}, 50);
    }

    function login() {
        AuthInfo.login(vm.user).then(function (data) {
            //$scope.$emit('Auth');
            $location.path(encodeURI('challenges'));
        }, function (error) {
            vm.error = error;
            shakeForm();
        });
    }
}