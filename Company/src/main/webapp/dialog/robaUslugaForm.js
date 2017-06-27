app.controller('RobaUslugaFormController', function ($scope, $http, $state, $mdDialog, robaUslugaService,stavkeFaktureService, faktura) {

    $scope.tipovi = ["roba", "usluga"];

    var loadData = function () {
        robaUslugaService.read(function (response) {
            $scope.robeUsluge = response.data;
        });
    };

    loadData();

    $scope.addRobaUsluga = function(robaUsluga) {
        console.log("dodaj robu " + robaUsluga.id + "za fakturu " + faktura.id + "kolicina " + this.kolicina);

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