/**
 * Created by timur on 19.06.15.
 */
Array.prototype.remove = function (from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest)
};

angular
    .module('gamificationApp', ['ngRoute', 'ngFileUpload', 'infinite-scroll'])
    .constant('CONSTANTS', {
        API_URI_PREFIX: "/api/v1",
        TASK_URI: "/challenge"
    }).config(function ($routeProvider, $locationProvider) {
        $routeProvider.when('/', {
            redirectTo: '/login'
        }).when('/login', {
            templateUrl: '/parts/login.html'
        }).when('/challenges', {
            templateUrl: '/parts/challenges.html',
            controller: 'ChallengesController'
        }).when('/challenges/create', {
            templateUrl: '/parts/taskForm.html',
            controller: 'ChallengeCreationController'
        }).otherwise({
            redirectTo: '/login'
        })
    });
angular.module('infinite-scroll').value('THROTTLE_MILLISECONDS', 250);
