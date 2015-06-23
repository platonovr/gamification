/**
 * Created by timur on 19.06.15.
 */
angular.module('gamificationApp').controller('TaskController',
    ['$scope', 'API_URI_PREFIX', 'TaskService', 'Upload',
        function ($scope, apiUriPrefix, TaskService, Upload) {
            $scope.task = {};
            $scope.attachments = [];
            $scope.createTask = function () {
                TaskService.createTask($scope.task).success(function (data) {
                    console.log(data);
                    var taskId;
                    if (taskId = data.id) {
                        var fileUploadUrl = apiUriPrefix + '/tasks/' + taskId + '/attachments';
                        $scope.attachments.forEach(function (attachment) {
                            const fileName = attachment.name;
                            Upload.upload({
                                url: fileUploadUrl,
                                //fields: {taskId: taskId},
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
                    console.error('task creation error: ' + data);
                })
            }
        }]);