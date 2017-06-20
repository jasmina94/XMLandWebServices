app.controller('HomeController', function ($scope, $state, $location, $log, $rootScope, $mdDialog, $interval, authenticationService) {

    $scope.page = {
        title: 'Pocetna',
        current: -1
    };

    $scope.zaposleni = authenticationService.getUser();
    $scope.authService = authenticationService;

    $scope.logout = function () {
        authenticationService.logout(function () {
            $state.transitionTo('login');
        }, function () {

        });
    };

    $scope.isActive = function (pageIndex) {
        return $scope.page.current === pageIndex;
    };

    $scope.goToFakture = function () {
        $state.transitionTo('home.faktura');
    };

});