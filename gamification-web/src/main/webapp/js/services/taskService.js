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
    this.getTasks = function (offset, limit) {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI +
            '/my?offset=' + offset + '&limit=' + limit)
    };
    this.getCategories = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/categories')
    };
    this.check = function (challenge, performer, mark) {
        return $http.post(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/' + challenge.id +
            '/user/' + performer.id, mark);
    };
});