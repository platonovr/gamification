angular.module('gamificationApp').controller('ChallengesController',
    ['$scope', '$window', 'CONSTANTS', 'TaskService', function ($scope, $window, CONSTANTS, TaskService) {
        var maxResult = 10;
        $scope.challenges = [];
        $scope.showedBlocks = [];
        $scope.mark_dialog = {
            challenge: null,
            performer: null,
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
            $scope.mark_dialog.mark = 0;
            var groups = [];
            for (var i = 0; i < challenge.groups.length; i++) {
                groups[i] = challenge.groups[i].id;
            }
            TaskService.getStudentsByGroups(groups).success(function (data) {
                $scope.students = data;
            });
            addMarkDialogBox.dialog("open");
            return false;
        };

        $scope.getMark = function (challenge, performer) {
            if (challenge.status_map[performer.id]) {
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


        $scope.checkTask = function () {
            var dialog_model = $scope.mark_dialog;
            var challenge = dialog_model.challenge;
            var performer = dialog_model.performer;
            var mark = dialog_model.mark;
            TaskService.check(challenge, performer, mark).success(function (data) {
                addMarkDialogBox.dialog("close");
                challenge.status_map[performer.id].status = 'COMPLETED';
                challenge.status_map[performer.id].mark = mark;
            });
        };

        $scope.searchChallenges = function () {
            $scope.autoLoadingDisabled = false;
            $scope.isLoading = false;
            $scope.challenges = [];
            $scope.loadMore();
        }
    }]);