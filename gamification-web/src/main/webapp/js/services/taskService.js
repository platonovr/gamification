/**
 * Created by timur on 19.06.15.
 */
angular.module('gamificationApp').service('TaskService', function ($http, API_URI_PREFIX) {
    this.createTask = function (task) {
        return $http.post(API_URI_PREFIX + '/tasks', task)
    };
});