angular.module('gamificationApp').service('TaskService', ['$http', 'CONSTANTS', 'Upload', 'AuthInfo', function ($http, CONSTANTS, Upload, AuthInfo) {
    this.createTask = function (newTask) {
        return $http.post(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + "?token=" + AuthInfo.getToken(), newTask)
    };
    this.uploadAttachment = function (taskId, attachment) {
        return Upload.upload({
            url: CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/' + taskId + '/attachments',
            file: attachment,
            params: {
                token: AuthInfo.getToken()
            }
        })
    };
    this.getTasks = function (offset, limit) {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/my', {
            params: {
                token: AuthInfo.getToken(),
                offset: offset,
                limit: limit
            }
        })
    };
    this.getCategories = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/categories', {
            params: {
                token: AuthInfo.getToken()
            }
        })
    };
    this.check = function (challenge, performer, mark) {
        var params = {
            token: AuthInfo.getToken(),
            mark: mark
        };
        return $http.post(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/' + challenge.id +
            '/user/' + performer.id, params);
    };
}]);