angular.module('domobyPi', []).value('url', 'http://leludo.dtdns.net:9000/json');
angular.module('domobyPi').config(['$controllerProvider', function ($controllerProvider) {
    $controllerProvider.allowGlobals();
}]);

angular.module('domobyPi')
    .controller('LedController', function ($scope, $http, url) {
    	
    	ledState = function () {
            $http.get(url + '/ledstate').success(function (data) {
                $scope.led = data.led;
            });
        };
        ledState() ;
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