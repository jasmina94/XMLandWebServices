app.service('zaposleniService', function($http){
    return {
        read: function (onSuccess, onError) {
            $http.get('/api/employees').then(onSuccess, onError);
        }
    }
});