angular.module('gamificationApp').controller('ChallengeCreationController',
    ['$scope', '$location', '$sce', 'CONSTANTS', 'TaskService', '$filter', 'UserApiService', 'AuthInfo',
        function ($scope, $location, $sce, CONSTANTS, TaskService, $filter, UserApiService, AuthInfo) {

            $scope.trustAsHtml = function (value) {
                return $sce.trustAsHtml(value);
            };

            UserApiService.getCurrentUserInfo(AuthInfo.getToken()).success(function (data) {
                $scope.currentUserRole = data.role;
            });


            $scope.newTask = {
                id: -100,
                name: undefined,
                subject: undefined,
                creator: undefined,
                date_from: new Date(),
                date_to: new Date(),
                category: undefined,
                coursesAndGroups: [],
                description: undefined,
                performers: [],
                maxMark: 0

            };
            $scope.attachments = [];
            $scope.errors = [];
            $scope.teachers = [];
            $scope.studyGroups = [];
            $scope.students = [];
            $scope.coursesAndGroups = [];
            $scope.performers = [];
            TaskService.getCategories().success(function (data) {
                $scope.categories = data
            });
            TaskService.getTeachers().success(function (data) {
                $scope.teachers = data;
            });
            TaskService.getDisciplines().success(function (data) {
                $scope.disciplines = data;
            });

            TaskService.getCoursesAndGroups().success(function (data) {
                $scope.coursesAndGroups = data;
            });

            TaskService.getPerformers().success(function (data) {
                $scope.performers = data;
            });


            $scope.createTask = function () {
                TaskService.createTask($scope.newTask).success(function (data) {
                    $scope.created = true;
                    $scope.attachments.forEach(function (attachment) {
                        const fileName = attachment.name;
                        TaskService.uploadAttachment(data.id, attachment).progress(function (evt) {
                            console.log(evt.loaded + " : " + evt.total);
                        }).success(function () {
                            if (fileName)
                                console.log('file ' + '\'' + fileName + '\' uploaded');
                        }).error(function (data, status, headers) {
                            if (fileName)
                                console.error('file ' + '\'' + fileName + '\' did not uploaded');
                        })
                    });

                }).error(function (data) {
                    console.error('task creation error: ' + JSON.stringify(data));
                    $scope.errors = data.errors;
                })
            }
        }
    ])
;