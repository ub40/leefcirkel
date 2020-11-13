(function(){

    /**
     * @author - Urfan Beijlerbeijli
     * */

var myApp = angular.module('myApp', ['ngMap']);

myApp.controller("mapController", ['$window','$scope','$http','$log','NgMap', function($window, $scope, $http, $log, NgMap) {

        var vm = this;
        var infoWindow = new google.maps.InfoWindow();
        $scope.users = [];
        $scope.coordinates = [];

        window.initMap = function(){}
        $scope.reloadRoute = function() {
            $window.location.reload();
        };

        NgMap.getMap().then(function(map) {
            vm.map = map;

            drawPolygon();

            function drawPolygon() {
                $http({
                    responseType: 'json',
                    method: 'GET',
                    url: "/maps/list"
                }).then(function successCallback(response) {
                    var coords = [];
                    for (var i = 0; i < response.data.length; i++) {
                        coords.push(new google.maps.LatLng(response.data[i].lat, response.data[i].lng));
                    }
                    var polygon = new google.maps.Polygon({
                        path: coords,
                        strokeColor: '#FF0000',
                        strokeOpacity: 1.0,
                        strokeWeight: 2,
                        fillColor: '#57acff'
                    });
                    polygon.setMap(map);
                }, function errorCallback(response) {
                    console.log(response.statusText);
                });
            }


            /*USERs LOCATION*/
            $http({
                responseType: 'json',
                method: 'POST',
                url: '/maps/checkinside'
            }).then(function successCallback(response) {

                var location = new google.maps.LatLng(response.data.lat, response.data.lng);

                var userLocation = new google.maps.Marker({
                    position: location,
                    title: "U"
                });

                userLocation.setMap(map);
            }, function errorCallback(response) {
                console.log(response.statusText);
            });

            /*Locate User
            * */
            if ($scope.isChecked) {
                $scope.showHistory();
            } else {

            $http({
                contentType: 'application/json',
                responseType: 'json',
                method: 'GET',
                url: '/maps/locateusers',
            }).then(function successCallback(response) {

                var markers = [];
                for (var i = 0; i < response.data.length; i++) {
                    markers[i] = new google.maps.Marker({
                        title: response.data[i].name,
                        position: {
                            lat: response.data[i].history.lat,
                            lng: response.data[i].history.lng
                        },
                        map: vm.map
                    });

                    markers[i].setMap(vm.map);
                }

            }, function errorCallback(response) {
                console.log(response.statusText);
            });
                // reloadRoute();
            };



            /* History of all users */
            $scope.showHistory = function() {
                $http({
                    contentType: 'application/json',
                    responseType: 'json',
                    method: 'GET',
                    url: '/maps/clientshistory',
                }).then(function successCallback(response) {

                    var markers = [];

                    for (var i = 0; i < response.data.length; i++) {
                        markers[i] = new google.maps.Marker({
                            url: "/images/profile.png",
                            scaledSize: new google.maps.Size(32, 40),
                            origin: new google.maps.Point(0, 0),
                            anchor: new google.maps.Point(16, 40),

                            title: response.data[i].name,

                            position: ({
                                lat: response.data[i].lat + i / 550000,
                                lng: response.data[i].lng + i / 550000
                            }),

                            /*position: {
                                lat : response.data[i].history.lat,
                                lng : response.data[i].history.lng
                            },
                            // icon : "{url:'profile_mini.png'}",*/
                            map: vm.map
                        });
                        markers[i].setMap(vm.map);
                    }

                }, function errorCallback(response) {
                    console.log(response.statusText);
                });
            };

            /* Draq Polyline */
            vm.placeMarker = function(e) {
                vm.map.panTo(e.latLng);
                /*Add point*/
                $http({
                    contentType: 'application/json',
                    responseType:'json',
                    method : 'POST',
                    url : '/maps/add',
                    data: e.latLng
                }).then(function successCallback(response) {
                        $scope.reloadRoute();
                },function errorCallback(response) {
                    console.log(response.statusText);
                });
            };


            vm.deleteMap = function(){
                $http({
                    contentType: 'application/json',
                    responseType:'json',
                    method : 'POST',
                    url : '/maps/deleteAll'
                }).then(function successCallback() {
                    $scope.reloadRoute();
                },function errorCallback(response) {
                    console.log(response.statusText);
                });
            };


        });

        infoWindow.open(vm.map);

    }]);


/*Clients data */
    myApp.controller("appController", function ($http, $scope,$window) {

        $scope.users = [];

        $scope.reloadRoute = function() {
            $window.location.reload();
        };


        getListOfUsers();

        function getListOfUsers(){
            $http({
                method: 'GET',
                url: "/leefcirkel/getListOfUsers"
            }).then(function successCallback(response) {
                $scope.users = response.data;

            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };


        $scope.saveUser = function() {
            $http({
                contentType: 'application/json',
                responseType:'json',
                method : 'POST',
                url : '/leefcirkel/add',
                data : $scope.user
            }).then(function successCallback() {
                $scope.user = null;
                $scope.reloadRoute();
            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.removeUser =  function(user){
            $http({
                method : 'POST',
                url : '/leefcirkel/delete',
                data : user
            }).then(function successCallback() {
                $scope.user = null;
                $scope.reloadRoute();
                getListOfUsers();
            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };


        /*$http({
            method: 'GET',
            url: "/leefcirkel/listAll"
        }).then(function successCallback(response) {
            $scope.users = response.data;
        },function errorCallback(response) {
            console.log(response.statusText);
        });*/

        $scope.updateData = function(user) {
            $http({
                method : 'POST',
                url : '/update/{id}',
                data : user
            }).then(function successCallback() {
                $scope.user = user;
                $scope.reloadRoute();
                getListOfUsers();
            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.updateUser = function(user) {
            $scope.user = user;
            getListOfUsers();
        };

        $scope.reloadRoute = function() {
            $window.location.reload();
        };


    });


    /*Get Zones */
    myApp.controller("zoneController", function ($http, $scope,$window) {

        $scope.zones = [];

        $scope.reloadRoute = function() {
            $window.location.reload();
        };

        showAllZonnes();

        function showAllZonnes() {
            $http({
                contentType: 'application/json',
                responseType: 'json',
                method: 'GET',
                url: '/zone/zones'
            }).then(function successCallback(response) {
                $scope.zones = response.data;
            }, function errorCallback(response) {
                console.log(response.statusText);
            });
        };


        $scope.saveCliZone = function() {
            $http({
                contentType: 'application/json',
                responseType: 'json',
                method : 'PUT',
                url : '/zone/addZone',
                data : $scope.zone
            }).then(function successCallback() {
                // $scope.zone = name;
                $scope.reloadRoute();
            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };


        if ($scope.zoneChecked) {

            scope.updateZoneStatus(zone);
        };


        $scope.updateZoneStatus = function(zone) {
            $http({
                method : 'PUT',
                url : '/zone/updateZone/{id}/zone',
                data : zone
            }).then(function successCallback() {
                $scope.zone = zone;
            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };

        $scope.deleteZone =  function(zone){
            $http({
                method : 'POST',
                url : '/zone/deleteZone',
                data : zone
            }).then(function successCallback() {
                $scope.zone = null;
                $scope.reloadRoute();
                showAllZonnes();
            },function errorCallback(response) {
                console.log(response.statusText);
            });
        };


    });



}());

