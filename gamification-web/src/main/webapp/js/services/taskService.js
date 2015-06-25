/**
 * Created by timur on 19.06.15.
 */
angular.module('gamificationApp').service('TaskService', function ($http, CONSTANTS) {
    this.createTask = function (task) {
        return $http.post(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI, task)
    };
    this.getCategories = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/categories')
    }
});