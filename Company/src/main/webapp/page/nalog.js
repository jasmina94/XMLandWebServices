/**
 * Created by Olivera on 23.6.2017..
 */
app.controller('NalogController', function ($scope, $state, $rootScope, $mdDialog, nalogZaPrenosService, authenticationService) {

    $scope.page.current = 3.3;

    var loadData = function () {
        zaposleni = authenticationService.getUser();
        if($state.current.name === "home.nalogPoverilac") {
            nalogZaPrenosService.readPoverilac(zaposleni.tpodaciSubjektDTO.naziv,  function (response) {
                $scope.uloga = "poverilac";
                $scope.nalozi = response.data;
            });
        } else if ($state.current.name === "home.nalogDuznik") {
            nalogZaPrenosService.readDuznik(zaposleni.tpodaciSubjektDTO.naziv, function (response) {
                $scope.uloga = "duznik";
                $scope.nalozi = response.data;
            });
        }
    };

    loadData();

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});