/**
 * Created by Olivera on 20.6.2017..
 */
app.controller('StavkeFaktureController', function ($scope, $http, $state, $mdDialog, fakturaService, faktura) {

    var loadData = function () {
        fakturaService.readFaktura(faktura.id, function (response) {
            $scope.fakturaNova = response.data;
            $scope.stavkeFakture = $scope.fakturaNova.stavkaFakture;
        });

    };

    loadData();

    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});