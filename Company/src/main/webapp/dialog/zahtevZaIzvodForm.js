app.controller('ZahtevZaIzvodFormController', function ($scope, $http, $state, $mdDialog, firma) {

    $scope.firma = firma;
    $scope.zahtevZaIzvod = {};
    $scope.danasnjiDatum = new Date();

    $scope.close = function () {
        $mdDialog.hide();
    };
    
    $scope.posaljiZahtev = function () {
        //TODO: Implementirati
    }

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});