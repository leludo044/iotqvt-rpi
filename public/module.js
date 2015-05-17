angular.module('domobyPi', [])
    .value('url', 'http://pi:9000/json');
angular.module('domobyPi').config(
  ['$controllerProvider', function ($controllerProvider) {
        $controllerProvider.allowGlobals();
  }]);

angular.module('domobyPi').controller('LedController',
    function ($scope, $http, url, temperatureService) {

        ledState = function () {
            $http.get(url + '/ledstate').success(function (data) {
                $scope.led = data.led;
            });
        };
        ledState();
        $scope.isOn = function () {
            return $scope.led == "on";
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

        sensor = function () {
            $http.get(url + '/sensor').success(function (data) {
                $scope.temp = Math.round((data.temp / 1000) * 100) / 100;
            });
        };
        //sensor();

        $scope.updateTemp = function (sensor) {
            $scope.temp = Math.round(sensor.temp / 1000 * 100) / 100;
            if ($scope.minTemp == null) {
                $scope.minTemp = $scope.temp;
            }
            if ($scope.temp < $scope.minTemp) {
                $scope.minTemp = $scope.temp;
            } else if ($scope.temp > $scope.maxTemp) {
                $scope.maxTemp = $scope.temp;
            }
        }
        $scope.temp = 0;
        $scope.date = null;
        $scope.minTemp = null;
        $scope.maxTemp = 0;

        temperatureService.start($scope);

    });

angular.module('domobyPi').factory('temperatureService', function ($q) {

    function temperatureService() {
        var self = this;

        var temp = 0;
        var deffered = $q.defer();
        var registeredScope = null;
        var ws = null;
        var isStarted = false;

        self.start = function (scope) {
            registeredScope = scope;
            console.log("starting with " + registeredScope);
            ws = new WebSocket("ws://pi:9000/socket");

            ws.onopen = function () {
                console.log("Socket has been opened!");
            }

            ws.onclose = function () {
                console.log("Socket has benn closed!");
            }

            ws.onmessage = function (message) {

                sensor = JSON.parse(message.data);
                date = new Date(sensor.date);
                //                self.temp = message.data.temp;
                registeredScope.$apply(function () {
                    //                    registeredScope.temp = Math.round(sensor.temp / 1000 * 100) / 100;
                    //                    registeredScope.date = date;
                    registeredScope.updateTemp(sensor);
                });
                deffered.resolve(message);
                console.log(sensor.temp + " at " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
            }

            isStarted = true;
        }

        self.stop = function () {
            ws.close();
            isStarted = false;
        }

        self.isStarted = function () {
            return isStarted;
        }

        self.getTemp = function () {
            //            return temp ;
            return deffered.promise;
        }
    }

    return new temperatureService();
});