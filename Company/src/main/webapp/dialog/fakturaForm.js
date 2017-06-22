/**
 * Created by JELENA on 20.6.2017.
 */

app.controller('FakturaFormController', function ($scope, $http, $state, $mdDialog, fakturaService, tPodaciSubjektService, faktura) {

    $scope.faktura = {
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
        "brojRacuna": 100000,
        "datumRacuna": "2017-01-01",
        "datumValute": "2017-01-01"
    };

    tPodaciSubjektService.read(function (response) {
        $scope.tPodaciSubjekti = response.data;
    });

    $scope.queryTPodaciSubjekti = function (searchText) {
        if (searchText === null) {
            return $scope.tPodaciSubjekti;
        }

        var queryResults = [];
        for (var i = 0; i < $scope.tPodaciSubjekti.length; i++) {
            if ($scope.tPodaciSubjekti[i].naziv.toLowerCase().match(searchText.toLowerCase())
                || $scope.tPodaciSubjekti[i].adresa.toLowerCase().match(searchText.toLowerCase())
            || $scope.tPodaciSubjekti[i].pib.match(searchText)) {
                queryResults.push($scope.tPodaciSubjekti[i]);
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