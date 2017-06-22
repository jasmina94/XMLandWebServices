/**
 * Created by Olivera on 17.6.2017..
 */

'use strict';

var app = angular.module('app', ['ui.router', 'ngMessages', 'ngMaterial', 'material.svgAssetsCache', 'md.data.table']);

app.factory('authInterceptor', ['$q', '$injector', function ($q, $injector) {
    var authInterceptor = {
        responseError: function (response) {
            var $state = $injector.get('$state');
            if ($state.current.name !== 'login' && response.status == 401) {
                $state.transitionTo('login');
            }
            return $q.reject(response);
        }
    };
    return authInterceptor;
}]);

app.config(function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, $mdThemingProvider, $mdDateLocaleProvider) {

    $mdThemingProvider.theme('default')
        .primaryPalette('indigo')
        .accentPalette('blue');

    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('authInterceptor');

    $stateProvider
        .state('login', {
            url: '/login',
            controller: 'LoginController',
            templateUrl: 'page/login.html'
        })
        .state('home', {
            url: '/home',
            controller: 'HomeController',
            templateUrl: 'page/home.html'
        })
        .state('home.fakturaDobavljac', {
            url: '/faktura',
            controller: 'FakturaController',
            templateUrl: 'page/faktura.html',
        })
        .state('home.fakturaKupac', {
            url: '/faktura',
            controller: 'FakturaController',
            templateUrl: 'page/faktura.html',
        })
});