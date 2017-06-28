app.controller('IzvodController', function ($scope, $state, $window, $rootScope, $mdDialog, zahtevZaIzvodService) {

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

    $scope.generisiPdf = function(izvod) {
        zahtevZaIzvodService.generisiPdf(izvod, function (response) {
            if(response.data != null) {
                izvod.link = response.data;
                 $mdDialog.show(
                     $mdDialog.alert()
                         .parent(angular.element(document.body))
                         .title('Uspeh')
                         .content('Izabrani izvod je zapisan kao pdf.')
                         .ok('Ok')
                 );
            }
        });
    }


    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});