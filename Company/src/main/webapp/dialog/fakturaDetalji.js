
app.controller('FakturaDetaljiController', function ($scope, $http, $state, $mdDialog, faktura) {

    $scope.faktura = faktura;

    $scope.close = function () {
        $mdDialog.hide();
    };
});