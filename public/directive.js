angular.module('domobyPi').directive('statsTemperature', function () {
    return {
        restrict: 'E',
        replace: 'true',
        template: '<div id="chart" />',
        link: function (scope, element, attrs) {
            console.log("Generating chart...");

            scope.chart = new Highcharts.Chart({
                chart: {
                    renderTo: 'chart',
                },
                title: {
                    text: 'Stats Temperature',
                    x: -20 //center
                },
                subtitle: {
                    text: 'Source: my home',
                    x: -20
                },
                yAxis: {
                    title: {
                        text: 'Temperature (°C)'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
            }]
                },
                tooltip: {
                    valueSuffix: '°C'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: 'Home',
                    data: scope.temps
        }]
            });
        }
    };
});