/**
 * Created by JELENA on 20.6.2017.
 */
app.controller('FakturaController', function ($scope, $state, $rootScope, $mdDialog, fakturaService) {

    $scope.page.current = 3.2;


    var openForm = function (faktura) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/fakturaForm.html',
            controller: 'FakturaFormController',
            locals: { faktura: faktura}
        }).finally(function () {
           // loadData();
        });
    };

    $scope.addFaktura = function() {
        openForm(null);
    };






});
