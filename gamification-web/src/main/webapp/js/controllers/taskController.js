/**
 * Created by timur on 19.06.15.
 */
angular.module('gamificationApp').controller('TaskController', ['$scope', 'TaskService', function ($scope, TaskService) {
    console.log(TaskService.createTask);
    $scope.task = {};
    $scope.createTask = function () {
        TaskService.createTask($scope.task)
    }
}]);