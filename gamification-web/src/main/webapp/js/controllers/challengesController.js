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
            if ($scope.checkConfirmed(challenge, performer.id)) {
                return false;
            }
            $scope.mark_dialog.challenge = challenge;
            $scope.mark_dialog.performer = performer;
            addMarkDialogBox.dialog("open");
            return false;
        };
        $scope.checkConfirmed = function (challenge, performer_id) {
            if (challenge.status_map && challenge.status_map[performer_id]) {
                return challenge.status_map[performer_id] == 'COMPLETED';
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
                challenge.status_map[performer.id] = 'COMPLETED';
            });
        };

        $scope.searchChallenges = function () {
            $scope.autoLoadingDisabled = false;
            $scope.isLoading = false;
            $scope.challenges = [];
            $scope.loadMore();
        }
    }]);