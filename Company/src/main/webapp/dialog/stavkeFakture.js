/**
 * Created by Olivera on 20.6.2017..
 */
app.controller('StavkeFaktureController', function ($scope, $http, $state, $mdDialog, stavkeFaktureService, faktura) {

    var loadData = function () {
        stavkeFaktureService.read(faktura.id, function (response) {
            $scope.stavkeFakture = response.data;
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