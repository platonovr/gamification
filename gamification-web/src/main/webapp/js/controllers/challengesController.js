/**
 * Created by timur on 30.06.15.
 */
angular.module('gamificationApp').controller('ChallengesController',
    ['$scope', '$window', 'CONSTANTS', 'TaskService', function ($scope, $window, CONSTANTS, TaskService) {
        var maxResult = 10;
        $scope.challenges = [];
        $scope.showedBlocks = [];
        $scope.autoLoadingDisabled = false;
        $scope.loadMore = function () {
            var length = $scope.challenges.length;
            TaskService.getAvailableTasks(length, maxResult).success(function (data) {
                if (data.length != 0) {
                    for (var i = 0; i < data.length; i++)
                        $scope.showedBlocks[i + length] = false;
                    $scope.challenges = $scope.challenges.concat(data)
                } else
                    $scope.autoLoadingDisabled = true;
            })
        }
    }]);