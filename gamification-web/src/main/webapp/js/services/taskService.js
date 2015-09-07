angular.module('gamificationApp').service('TaskService', ['$http', 'CONSTANTS', 'Upload', 'AuthInfo', function ($http, CONSTANTS, Upload, AuthInfo) {
    this.createTask = function (newTask) {
        return $http({
            url: CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI,
            params: {
                token: AuthInfo.getToken()
            },
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            data: newTask
        });
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
    this.getTasks = function (offset, limit, query) {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/my', {
            params: {
                token: AuthInfo.getToken(),
                offset: offset,
                limit: limit,
                query: query
            }
        })
    };

    this.getTeachers = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.DICTIONARIES + '/teachers');
    };

    this.getPerformers = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.DICTIONARIES + '/students');
    };

    this.getCategories = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/categories', {
            params: {
                token: AuthInfo.getToken()
            }
        })
    };

    this.getCoursesAndGroups = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.DICTIONARIES + '/getCoursesAndGroups');
    };

    this.getDisciplines = function () {
        return $http.get(CONSTANTS.API_URI_PREFIX + CONSTANTS.DICTIONARIES + '/disciplines');
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


