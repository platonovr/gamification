/**
 * Created by timur on 19.06.15.
 */
angular.module('gamificationApp').controller('TaskController',
    ['$scope', 'CONSTANTS', 'TaskService', 'Upload',
        function ($scope, CONSTANTS, TaskService, Upload) {
            $scope.task = {};
            $scope.attachments = [];
            $scope.errors = [];
            TaskService.getCategories().success(function (data) {
                $scope.categories = data
            });
            $scope.createTask = function () {
                TaskService.createTask($scope.task).success(function (data) {
                    $scope.created = true;
                    console.log(data);
                    var taskId;
                    if (taskId = data.id) {
                        var fileUploadUrl = CONSTANTS.API_URI_PREFIX + CONSTANTS.TASK_URI + '/' + taskId + '/attachments';
                        $scope.attachments.forEach(function (attachment) {
                            const fileName = attachment.name;
                            Upload.upload({
                                url: fileUploadUrl,
                                file: attachment
                            }).progress(function (evt) {
                                console.log(evt.loaded + " : " + evt.total);
                            }).success(function () {
                                if (fileName)
                                    console.log('file ' + '\'' + fileName + '\' uploaded');
                            }).error(function (data, status, headers) {
                                if (fileName)
                                    console.error('file ' + '\'' + fileName + '\' did not uploaded');
                            })
                        });
                    }
                }).error(function (data) {
                    console.error('task creation error: ' + JSON.stringify(data));
                    $scope.errors = data.errors;
                })
            }
        }]);