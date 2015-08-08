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

        $scope.autoLoadingDisabled = false;
        $scope.isLoading = false;
        $scope.loadMore = function () {
            if (!$scope.isLoading) {
                $scope.isLoading = true;
                var length = $scope.challenges.length;
                TaskService.getTasks(length, maxResult).success(function (data) {
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
            $scope.mark_dialog.challenge = challenge;
            $scope.mark_dialog.performer = performer;
            addMarkDialogBox.dialog("open");
        };


        $scope.checkTask = function () {
            var dialog_model = $scope.mark_dialog;
            TaskService.check(dialog_model.challenge, dialog_model.performer, dialog_model.mark).success(function (data) {
                addMarkDialogBox.dialog("close");
                var i = 0;
                var j = 0;
                for (i = 0; i < $scope.challenges.length; i++) {
                    var challenge2 = $scope.challenges[i];
                    if (challenge2.id == challenge.id) {
                        var performersArray = challenge2.performers;
                        for (j = 0; j < performersArray.length; j++) {
                            if (performersArray[j].id == performer.id) {
                                break;
                            }

                        }
                        break;
                    }
                }
                $scope.challenges[i].performers.splice(j, 1);
            });
        };
    }]);