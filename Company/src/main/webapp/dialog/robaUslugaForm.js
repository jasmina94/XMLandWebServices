app.controller('RobaUslugaFormController', function ($scope, $http, $state, $mdDialog, robaUslugaService) {

    //da saljem id fakture, id robe i kolicinu
    //prilikom dodavanja
    $scope.kolicina = 0;

    var loadData = function () {
        robaUslugaService.read(function (response) {
            $scope.robeUsluge = response.data;
        });
    };

    loadData();

    $scope.addRobaUsluga = function(robaUslugaId) {
        console.log("dodaj robu");
        $scope.kolicina = 0;
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