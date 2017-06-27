app.controller('StavkeIzvodaController', function ($scope, $http, $state, $mdDialog) {

    
    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});