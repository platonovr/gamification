angular.module('gamificationApp').controller('ChallengeCreationController',
    ['$scope', '$location', 'CONSTANTS', 'TaskService',
        function ($scope, $location, CONSTANTS, TaskService) {
            $scope.newTask = {};
            $scope.attachments = [];
            $scope.errors = [];
            TaskService.getCategories().success(function (data) {
                $scope.categories = data
            });
            $scope.createTask = function () {
                TaskService.createTask($scope.newTask).success(function (data) {
                    $scope.created = true;
                    var taskId;
                    if (taskId = data.id) {
                        //var uploaded = 0;
                        $scope.attachments.forEach(function (attachment) {
                            const fileName = attachment.name;
                            TaskService.uploadAttachment(taskId, attachment).progress(function (evt) {
                                console.log(evt.loaded + " : " + evt.total);
                            }).success(function () {
                                if (fileName)
                                    console.log('file ' + '\'' + fileName + '\' uploaded');
                            }).error(function (data, status, headers) {
                                if (fileName)
                                    console.error('file ' + '\'' + fileName + '\' did not uploaded');
                            })
                        });
                        //$location.url(CONSTANTS.TASK_URI+'s') - on success
                    }
                }).error(function (data) {
                    console.error('task creation error: ' + JSON.stringify(data));
                    $scope.errors = data.errors;
                })
            }
        }]);