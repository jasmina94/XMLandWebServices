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

    $scope.goToFaktureDobavljac = function () {
        $state.transitionTo('home.fakturaDobavljac');
    };

    $scope.goToFaktureKupac = function () {
        $state.transitionTo('home.fakturaKupac');
    };

    $scope.goToNaloziPoverilac = function () {
        $state.transitionTo('home.nalogPoverilac');
    };

    $scope.goToNaloziDuznik = function () {
        $state.transitionTo('home.nalogDuznik');
    };

    $scope.kreirajZahtevZaIzvod = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/zahtevZaIzvodForm.html',
            controller: 'ZahtevZaIzvodFormController',
            locals: {firma: $scope.zaposleni.tpodaciSubjektDTO}
        });
    }

    $scope.goToIzvodi = function () {
        $state.transitionTo('home.izvod');

    }
});