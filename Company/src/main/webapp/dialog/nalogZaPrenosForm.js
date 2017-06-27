app.controller('NalogZaPrenosFormController', function ($scope, $http, $state, $mdDialog, faktura, authenticationService, nalogZaPrenosService) {

    $scope.faktura = faktura;
    $scope.podaciZaNalog = {};
    $scope.firma = authenticationService.getUser().tpodaciSubjektDTO;
    $scope.obradaUToku = false;

    $scope.posaljiNalog = function () {
        $scope.obradaUToku = true;
        $scope.podaciZaNalog.faktura = faktura;
        $scope.podaciZaNalog.racunDuznika = $scope.firma.racunFirme;
        nalogZaPrenosService.kreirajNalog($scope.podaciZaNalog,  function (response) {
            $scope.close();
            if(response.data != "") {
                prikaziUspeh();
            } else {
                prikaziNeuspeh();
            }
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };

    var prikaziUspeh = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Uspeh')
                .content('Uspesno je kreiran nalog za izabranu fakturu.')
                .ok('Ok')
        );
    }

    var prikaziNeuspeh = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Neuspeh')
                .content('Doslo je do greske pri kreiranju naloga.')
                .ok('Ok')
        );
    }
});