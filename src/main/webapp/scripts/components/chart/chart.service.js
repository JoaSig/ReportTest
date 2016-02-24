'use strict';

angular.module('documentmanagementApp')
    .factory('Chart', function Chart() {
        return {
            getPDChartConfig: function () {
                return pdChartConfig;
            }
        }
    });
var pdChartConfig = {
    chart: {
        "type": "historicalBarChart",
        "height": 450,
        "margin": {
            "top": 20,
            "right": 20,
            "bottom": 65,
            "left": 50
        },
        "showValues": true,
        clipEdge: true,
        duration: 500,
        x: function (d) {
            if (d) {
                return d.x;
            }

        },
        y: function (d) {
            if (d) {
                return d.y;
            }
        },
        useInteractiveGuideline: true,
        dispatch: {},
        xAxis: {
            axisLabel: "DrillingID",
            showMaxMin: false,
            tickFormat: function (d) {
                return d3.format(',f')(d);
                //return d;
                //return d3.time.format("%b %d")(new Date(d));
            }
        },
        yAxis: {
            axisLabel: "Minutes",
            axisLabelDistance: -10,
            tickFormat: function(d){
                return d3.format('1f')(d);
            }
        },
        tooltip: {
            keyFormatter: function(d) {
                return 'Hello';
            }
        },
        zoom: {
            enabled: true,
            scaleExtent: [1, 10],
            useFixedDomain: true,
            useNiceScale: false,
            horizontalOff: false,
            verticalOff: true,

            //"verticalOff": false,

            unzoomEventType: 'dblclick.zoom'
        }
        //transitionDuration: 250
    },
    title: {
        enable: true
    }
};
