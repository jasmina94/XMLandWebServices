/**
 * Created by JELENA on 20.6.2017.
 */
app.controller('FakturaController', function ($scope, $state, $rootScope, $mdDialog, fakturaService, nalogZaPrenosService,robaUslugaService) {

    $scope.page.current = 3.2;

    var loadData = function () {
        if($state.current.name === "home.fakturaDobavljac") {
            fakturaService.readDobavljac(function (response) {
                $scope.uloga = "dobavljac";
                $scope.fakture = response.data;
            });
        } else if ($state.current.name === "home.fakturaKupac") {
            fakturaService.readKupac(function (response) {
                $scope.uloga = "kupac";
                $scope.fakture = response.data;
            });
        }
        };

    loadData();

    var openForm = function (faktura) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/fakturaForm.html',
            controller: 'FakturaFormController',
            locals: { faktura: faktura}
        }).finally(function () {
            loadData();
        });
    };

    $scope.addFaktura = function() {
        openForm(null);
    };


    $scope.showStavke = function(faktura) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/stavkeFakture.html',
            controller: 'StavkeFaktureController',
            locals: {faktura: faktura}
        });
    }

    $scope.addStavke = function (faktura) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/robaUslugaForm.html',
            controller: 'RobaUslugaFormController',
            locals: {faktura: faktura}
        }).finally(function () {
            loadData();
        });
    };

    $scope.popuniNalog = function (faktura) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/nalogZaPrenosForm.html',
            controller: 'NalogZaPrenosFormController',
            locals: {faktura: faktura}
        }).finally(function () {
            loadData();
        });
    };

    $scope.prikaziDetalje = function(faktura) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/fakturaDetalji.html',
            controller: 'FakturaDetaljiController',
            locals: {faktura: faktura}
        });
    }

    $scope.sendFaktura = function(faktura) {
        faktura.poslato = true;
        fakturaService.update(faktura, function (response) {
            if(response.data != "") {
                loadData();
                prikaziUspeh('Faktura je uspešno poslata.');
            } else {
                prikaziNeuspeh();
            }

        });
    }

    var prikaziUspeh = function (tekstPoruke) {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Uspeh')
                .content(tekstPoruke)
                .ok('Ok')
        );
    }

    var prikaziNeuspeh = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Neuspeh')
                .content('Došlo je do greške pri slanju fakture.')
                .ok('Ok')
        );
    }

    $scope.generisiPdf = function(faktura) {
        fakturaService.generisiPdf(faktura, function (response) {
            if(response.data != null)
                prikaziUspeh('Izabrana faktura je zapisana kao pdf.');
        });
    }

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});
