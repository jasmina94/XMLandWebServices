/**
 * Created by JELENA on 20.6.2017.
 */

app.controller('FakturaFormController', function ($scope, $http, $state, $mdDialog, $filter, fakturaService, tPodaciSubjektService, authenticationService, faktura) {

    $scope.zaposleni = authenticationService.getUser();

    function getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    $scope.faktura = {
        "podaciODobavljacu": $scope.zaposleni.tpodaciSubjektDTO,
        "vrednostRobe": 0.0,
        "vrednostUsluga": 0.0,
        "ukupnoRobaIUsluga": 0.0,
        "ukupanRabat": 0.0,
        "ukupanPorez": 0.0,
        "oznakaValute": "RSD",
        "iznosZaUplatu": 0.0,
        "uplataNaRacun": "000-0000000000000-00",
        "stavkaFakture": [],
        "idPoruke": 0,
        "brojRacuna": getRandomInt(100000, 999999),
        "datumRacuna": $filter('date')(new Date(),'yyyy-MM-dd'),
        "datumValute": $filter('date')(new Date(),'yyyy-MM-dd')
    };

    tPodaciSubjektService.readPoslovniPartneri($scope.zaposleni.tpodaciSubjektDTO.id,  function (response) {
        $scope.poslovniPartneri = response.data;
    });

    $scope.queryPoslovniPartneri = function (searchText) {
        if (searchText === null) {
            return $scope.poslovniPartneri;
        }

        var queryResults = [];
        for (var i = 0; i < $scope.poslovniPartneri.length; i++) {
            if ($scope.poslovniPartneri[i].naziv.toLowerCase().match(searchText.toLowerCase())
                || $scope.poslovniPartneri[i].adresa.toLowerCase().match(searchText.toLowerCase())
            || $scope.poslovniPartneri[i].pib.match(searchText)) {
                queryResults.push($scope.poslovniPartneri[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        fakturaService.create($scope.faktura, function () {
            $scope.close();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});