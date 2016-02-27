'use strict';

angular.module('documentmanagementApp')
    .service('DateUtils', function ($filter) {

        this.convertLocaleDateToServer = function (date) {
            if (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            } else {
                return null;
            }
        };

        this.convertTimeToServer = function (date) {
            if (date) {
                var d = new Date();
                var dateSplit = date.split(':');
                if (dateSplit.length = 2) {
                    d.setHours(dateSplit[0], dateSplit[1]);
                } else if (dateSplit.length = 3) {
                    d.setHours(dateSplit[0], dateSplit[1], dateSplit[2]);
                } else {
                    d.setHours(dateSplit[0]);
                }
                return d;
            } else {
                return null;
            }
        };

        this.convertTimeFromServer = function (date) {
            if (date) {
                return $filter('date')(date, 'HH:mm');
            } else {
                return null;
            }
        };

        this.convertLocaleDateFromServer = function (date) {
            if (date) {
                var dateString = date.split("-");
                return new Date(dateString[0], dateString[1] - 1, dateString[2]);
            }
            return null;
        };

        this.convertDateTimeFromServer = function (date) {
            if (date) {
                return new Date(date);
            } else {
                return null;
            }
        };

        // common date format for all date input fields
        this.dateformat = function () {
            return 'yyyy-MM-dd';
        };

        this.dateTimeformat = function () {
            return 'HH:mm';
        }
    });
