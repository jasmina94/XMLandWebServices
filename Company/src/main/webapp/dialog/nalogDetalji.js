
app.controller('NalogDetaljiController', function ($scope, $http, $state, $mdDialog, nalog) {

    $scope.nalog = nalog;

    $scope.close = function () {
        $mdDialog.hide();
    };
});