/**
 * Created by timur on 19.06.15.
 */
angular.module('gamificationApp').service('TaskService', function ($http, CONSTANTS, Upload) {
    this.createTask = function (newTask) {
        return $http.post(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI, newTask)
    };
    this.uploadAttachment = function (taskId, attachment) {
        return Upload.upload({
            url: CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/' + taskId + '/attachments',
            file: attachment
        })
    };
    this.getAvailableTasks = function (offset, maxResult) {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI+'?offset='+offset+'&maxResult='+maxResult)
    };
    this.getCategories = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/categories')
    }
});