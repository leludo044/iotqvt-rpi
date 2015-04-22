angular.module('domobyPi', []).value('url', 'http://localhost:9000/json');
angular.module('domobyPi').config(['$controllerProvider', function ($controllerProvider) {
    $controllerProvider.allowGlobals();
}]);

angular.module('domobyPi')
    .controller('LedController', function ($scope, $http, url) {
        $scope.led = "off";
        $scope.isOn = function() {
            return $scope.led == "on" ;
        };
        $scope.ledOn = function () {
            $http.get(url + '/led/on').success(function (data) {
                $scope.led = data.led;
            });
        };
        $scope.ledOff = function () {
            $http.get(url + '/led/off').success(function (data) {
                $scope.led = data.led;
            });
        };
        
    });