app.controller('NalogZaPrenosFormController', function ($scope, $http, $state, $mdDialog, faktura, nalogZaPrenosService) {

    $scope.faktura = faktura;

    $scope.podaciZaNalog = {};

    $scope.posaljiNalog = function () {
        $scope.podaciZaNalog.faktura = faktura;
        nalogZaPrenosService.kreirajNalog($scope.podaciZaNalog,  function (response) {
            $scope.close();
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .title('Uspeh!')
                    .content('Uspesno je kreiran nalog za izabranu fakturu.')
                    .ok('Ok')
            );
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