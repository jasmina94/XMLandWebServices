app.controller('NalogZaPrenosFormController', function ($scope, $http, $state, $mdDialog, faktura, authenticationService, nalogZaPrenosService) {

    $scope.faktura = faktura;
    $scope.podaciZaNalog = {};
    $scope.firma = authenticationService.getUser().tpodaciSubjektDTO;

    $scope.posaljiNalog = function () {
        $scope.podaciZaNalog.faktura = faktura;
        nalogZaPrenosService.kreirajNalog($scope.podaciZaNalog,  function (response) {
            $scope.close();
            if(response.data != "") {
                $mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .title('Uspeh')
                        .content('Uspesno je kreiran nalog za izabranu fakturu.')
                        .ok('Ok')
                );
            } else {
                $mdDialog.show(
                    $mdDialog.alert()
                        .parent(angular.element(document.body))
                        .title('Greska')
                        .content('Doslo je do greske pri kreiranju naloga.')
                        .ok('Ok')
                );
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
});