app.controller('RobaUslugaFormController', function ($scope, $http, $state, $mdDialog, robaUslugaService,stavkeFaktureService, faktura) {

    //da saljem id fakture, id robe i kolicinu
    //prilikom dodavanja

    var loadData = function () {
        robaUslugaService.read(function (response) {
            $scope.robeUsluge = response.data;
        });
    };

    loadData();

    $scope.addRobaUsluga = function(robaUsluga) {
        stavkeFaktureService.createStavka(robaUsluga, faktura.id, this.kolicina, function (response) {
            console.log("uspesno dodata stavka");
        });
        /*console.log(this.kolicina);
        console.log("dodaj robu " + robaUsluga.id + "za fakturu " + faktura.id + "kolicina " + this.kolicina);
        this.kolicina = null ;*/
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