angular.module('domobyPi', []);
angular.module('domobyPi').config(['$controllerProvider', function ($controllerProvider) {
    $controllerProvider.allowGlobals();
}]);

function MessageController($scope) {
    $scope.message = "This is an Angular message.";
}