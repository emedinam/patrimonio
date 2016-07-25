angular.module('patrimonioNatApp.businessServices', [])
/*
        .factory('checkCreds', ['$cookies', function ($cookies) {
                return function () {
                    var returnVal = false;
                    var patriNatCreds = $cookies.patriNatCreds;
                    if (patriNatCreds !== undefined && patriNatCreds !== "") {
                        returnVal = true;
                    }
                    return returnVal;
                };
            }])
        .factory('getToken', ['$cookies', function ($cookies) {
                return function () {
                    var returnVal = "";
                    var patriNatCreds = $cookies.patriNatCreds;
                    if (patriNatCreds !== undefined && patriNatCreds !== "") {
                        returnVal = btoa(patriNatCreds);
                    }
                    return returnVal;
                };
            }])
        .factory('getUsername', ['$cookies', function ($cookies) {
                return function () {
                    var returnVal = "";
                    var patriNatUsername = $cookies.patriNatUsername;
                    if (patriNatUsername !== undefined && patriNatUsername !== "") {
                        returnVal = patriNatUsername;
                    }
                    return returnVal;
                };
            }])
        .factory('setCreds', ['$cookies', function ($cookies) {
                return function (un, pw) {
                    var token = un.concat(":", pw);
                    $cookies.patriNatCreds = token;
                    $cookies.patriNatUsername = un;
                };
            }])
        .factory('deleteCreds', ['$cookies', function ($cookies) {
                return function () {
                    $cookies.patriNatCreds = "";
                    $cookies.patriNatUsername = "";
                };
            }]);*/