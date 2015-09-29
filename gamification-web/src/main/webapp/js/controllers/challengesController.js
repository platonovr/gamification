angular.module('gamificationApp').controller('ChallengesController',
    ['$scope', '$sce', '$window', 'CONSTANTS', 'TaskService', function ($scope, $sce, $window, CONSTANTS, TaskService) {
        var maxResult = 10;
        $scope.challenges = [];
        $scope.showedBlocks = [];
        $scope.mark_dialog = {
            challenge: null,
            performer: null,
            temp_performer: null,
            mark: 0
        };
        $scope.queryString = null;
        $scope.autoLoadingDisabled = false;
        $scope.isLoading = false;
        $scope.students = [];


        $scope.trustAsHtml = function (value) {
            return $sce.trustAsHtml(value);
        };

        $scope.loadMore = function () {
            if (!$scope.isLoading) {
                $scope.isLoading = true;
                var length = $scope.challenges.length;
                TaskService.getTasks(length, maxResult, $scope.queryString).success(function (data) {
                    if (data.length != 0) {
                        for (var i = 0; i < data.length; i++)
                            $scope.showedBlocks[i + length] = false;
                        $scope.challenges = $scope.challenges.concat(data);
                        $scope.isLoading = false;
                    } else
                        $scope.autoLoadingDisabled = true;
                })
            }
        };

        var addMarkDialogBox = $("#dialog-message").dialog({
            autoOpen: false,
            modal: true,
            buttons: {
                "Подтвердить": function () {
                    $scope.checkTask();
                }
            }
        });

        $scope.showChangeBlock = function (challenge, performer) {
            //if ($scope.checkConfirmed(challenge, performer.id)) {
            //    return false;
            //}
            $scope.mark_dialog.challenge = challenge;
            $scope.mark_dialog.performer = performer;
            $scope.mark_dialog.temp_performer = null;
            if (challenge.status_map[performer.id]) {
                $scope.mark_dialog.mark = challenge.status_map[performer.id].mark;
            } else {
                $scope.mark_dialog.mark = 0;
            }
            addMarkDialogBox.dialog("open");
            return false;
        };

        $scope.showNewPerformerChangeBlock = function (challenge) {
            $scope.mark_dialog.challenge = challenge;
            $scope.mark_dialog.performer = null;
            $scope.mark_dialog.temp_performer = null;
            $scope.mark_dialog.mark = 0;
            var groups = [];
            for (var i = 0; i < challenge.groups.length; i++) {
                groups[i] = challenge.groups[i];
            }

            if (groups.length != 0) {
                TaskService.getStudentsByGroups(groups).success(function (data) {
                    $scope.students = removeArray(data, challenge.users);
                });
            } else {
                TaskService.getPerformers().success(function (data) {
                    $scope.students = data;
                });
            }
            addMarkDialogBox.dialog("open");
            return false;
        };

        $scope.getMark = function (challenge, performer) {
            if (challenge.status_map[performer.id] && challenge.status_map[performer.id].mark != null) {
                return "(" + challenge.status_map[performer.id].mark + ")";
            } else {
                return "";
            }
        };
        $scope.checkConfirmed = function (challenge, performer_id) {
            if (challenge.status_map && challenge.status_map[performer_id]) {
                return challenge.status_map[performer_id].status == 'COMPLETED';
            }
            return false;
        };

        function removeArray(firstArray, secondArray) {
            var firstArrayCopy = firstArray.slice();
            var indexOffset = 0;
            for (var i = 0; i < firstArray.length; i++) {
                for (var j = 0; j < secondArray.length; j++) {
                    if (firstArray[i].id == secondArray[j].id) {
                        firstArrayCopy.splice(i - indexOffset, 1);
                        indexOffset++;
                    }
                }
            }
            return firstArrayCopy;
        }


        $scope.checkTask = function () {
            var dialog_model = $scope.mark_dialog;
            if (dialog_model.performer == null) {
                dialog_model.performer = dialog_model.temp_performer;
            }
            var challenge = dialog_model.challenge;
            var performer = dialog_model.performer;
            var mark = dialog_model.mark;
            TaskService.check(challenge, performer, mark).success(function (data) {
                addMarkDialogBox.dialog("close");
                if (challenge.status_map == null || challenge.status_map == undefined) {
                    challenge.status_map = [];
                }
                if (challenge.status_map[performer.id] == null || challenge.status_map[performer.id] == undefined) {
                    challenge.status_map[performer.id] = {
                        status: null,
                        mark: null
                    }
                }
                challenge.status_map[performer.id].status = 'COMPLETED';
                challenge.status_map[performer.id].mark = mark;
                $scope.challenges.forEach(function (item, i, arr) {
                    if (item.id == challenge.id) {
                        var isExist = false;
                        for (var i = 0; i < item.users; i++) {
                            if (item.users[i].id = performer.id) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            item.users.push({
                                first_name: performer.firstName,
                                group: performer.fullNameWithGroup.split("\n").pop(),
                                id: performer.id,
                                last_name: performer.lastName,
                                time_back: null
                            });
                        }
                    }
                });
            });
        };

        $scope.searchChallenges = function () {
            $scope.autoLoadingDisabled = false;
            $scope.isLoading = false;
            $scope.challenges = [];
            $scope.loadMore();
        }
    }]);