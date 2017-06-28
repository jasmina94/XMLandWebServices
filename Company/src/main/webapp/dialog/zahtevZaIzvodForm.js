app.controller('ZahtevZaIzvodFormController', function ($scope, $http, $state, $mdDialog, firma, zahtevZaIzvodService) {

    $scope.firma = firma;
    $scope.zahtevZaIzvod = {};
    $scope.danasnjiDatum = new Date();
    $scope.obradaUToku = false;

    $scope.close = function () {
        $mdDialog.hide();
    };
    
    $scope.posaljiZahtev = function () {
        $scope.obradaUToku = true;
        $scope.zahtevZaIzvod.brojRacuna = $scope.firma.racunFirme;
        $scope.zahtevZaIzvod.redniBrojPreseka = 1;
        zahtevZaIzvodService.posaljiZahtev($scope.zahtevZaIzvod, function (response) {
            if(response.data) {
                $scope.$broadcast('refresh');
                prikaziUspeh();
            } else {
                prikaziNeuspeh();
            }
        });
    }

    var prikaziUspeh = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Uspeh')
                .content('Zahtev je poslat banci.')
                .ok('Ok')
        );
    }

    var prikaziNeuspeh = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Neuspeh')
                .content('Došlo je do greške pri slanju zahteva.')
                .ok('Ok')
        );
    }

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});