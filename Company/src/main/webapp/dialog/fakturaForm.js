/**
 * Created by JELENA on 20.6.2017.
 */

app.controller('FakturaFormController', function ($scope, $http, $state, $mdDialog, fakturaService, faktura) {



    $scope.submit = function () {
            fakturaService.create($scope.faktura, function () {
                $scope.close();
            });


    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});