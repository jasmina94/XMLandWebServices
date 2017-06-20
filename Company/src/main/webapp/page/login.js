app.controller('LoginController', function ($scope, $state, $http, $mdDialog, authenticationService) {

    $scope.submit = function () {
        authenticationService.login($scope.zaposleni, function () {
            $http.get('/api/employees')
                .success(function (response) {
                    authenticationService.setUser(response);
                    $state.transitionTo('home');
                })
                .error(error);
        }, error);
    };

    var error = function () {
        $mdDialog.show(
            $mdDialog.alert()
            .parent(angular.element(document.body))
            .title('Neuspesno logovanje!')
            .content('Pogresno korisnicko ime ili lozinka.')
            .ok('Ok')
    );
    };
});