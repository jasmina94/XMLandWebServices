app.controller('IzvodController', function ($scope, $state, $rootScope, $mdDialog, zahtevZaIzvodService) {

    $scope.page.current = 3.4;

    var loadData = function () {
        zahtevZaIzvodService.read(function (response) {
            $scope.izvodi = response.data;
        });
    };

    loadData();

    $scope.$on('refresh', function() {
        loadData();
    });
    
    $scope.prikaziStavke = function (izvod) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/stavkeIzvoda.html',
            controller: 'StavkeIzvodaController',
            locals: {izvod: izvod}
        });
    }


    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});