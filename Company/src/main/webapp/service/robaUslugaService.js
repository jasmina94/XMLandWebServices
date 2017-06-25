/**
 * Created by JELENA on 23.6.2017.
 */

app.service('robaUslugaService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/robeUsluge').then(onSuccess, onError);
        }
    }
});
