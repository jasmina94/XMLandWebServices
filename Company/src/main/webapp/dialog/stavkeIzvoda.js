app.controller('StavkeIzvodaController', function ($scope, $http, $state, $mdDialog, izvod) {

    $scope.izvod = izvod;
    
    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});