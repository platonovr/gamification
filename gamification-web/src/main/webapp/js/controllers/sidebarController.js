angular
    .module('gamificationApp')
    .controller('SideBarController', SideBarController);

SideBarController.$inject = ['$scope', '$http', '$location', '$timeout', 'AuthInfo'];
function SideBarController($scope, $http, $location, $timeout, AuthInfo) {
    $scope.logout = function () {
        AuthInfo.logout();
    };
}