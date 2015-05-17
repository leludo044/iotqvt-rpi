var testConnectivity = {
    serveur: "ludo1:9000",
    ledstate: "/mock/ledstate",
    led: "/mock/led",
    websocket: "/mock/socket"
};

var piConnectivity = {
    serveur: "pi:9000",
    ledstate: "/json/ledstate",
    led: "/json/led",
    websocket: "/socket"
};

var domobypi = angular.module('domobyPi', [])

domobypi.value("connect", piConnectivity);

domobypi.config(
  ['$controllerProvider', function ($controllerProvider) {
        $controllerProvider.allowGlobals();
  }]);

angular.module('domobyPi').controller('LedController',
    function ($scope, $http, connect, temperatureService) {

        ledState = function () {
            $http.get('http://' + connect.serveur + connect.ledstate).success(function (data) {
                $scope.led = data.led;
            });
        };
        ledState();
        $scope.isOn = function () {
            return $scope.led == "on";
        };
        $scope.ledOn = function () {
            $http.get('http://' + connect.serveur + connect.led + '/on').success(function (data) {
                $scope.led = data.led;
            });
        };
        $scope.ledOff = function () {
            $http.get('http://' + connect.serveur + connect.led + '/off').success(function (data) {
                $scope.led = data.led;
            });
        };

        sensor = function () {
            $http.get(url + '/sensor').success(function (data) {
                $scope.temp = Math.round((data.temp / 1000) * 100) / 100;
            });
        };
        //sensor();

        $scope.temps = [];

        $scope.updateTemp = function (sensor) {
            $scope.temp = Math.round(sensor.temp / 1000 * 100) / 100;
            $scope.temps.push($scope.temp);
            if ($scope.minTemp == null) {
                $scope.minTemp = $scope.temp;
            }
            if ($scope.temp < $scope.minTemp) {
                $scope.minTemp = $scope.temp;
            } else if ($scope.temp > $scope.maxTemp) {
                $scope.maxTemp = $scope.temp;
            }
            $scope.message = "titi";
            console.log($scope.temps);
            $scope.updateChart($scope.temp);
        }
        $scope.temp = 0;
        $scope.date = null;
        $scope.minTemp = null;
        $scope.maxTemp = 0;

        temperatureService.start($scope);

        $scope.message = "toto";

        $scope.chart;

        $scope.updateChart = function (value) {
            console.log("updating chart...");
            $scope.chart.series[0].addPoint(value);
            $scope.chart.redraw();
        }

    });

angular.module('domobyPi').factory('temperatureService', function ($q, connect) {

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
            ws = new WebSocket("ws://" + connect.serveur + connect.websocket);

            ws.onopen = function () {
                console.log("Socket has been opened!");
            }

            ws.onclose = function () {
                console.log("Socket has benn closed!");
            }

            ws.onmessage = function (message) {

                sensor = JSON.parse(message.data);
                date = new Date(sensor.date);
                registeredScope.$apply(function () {
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