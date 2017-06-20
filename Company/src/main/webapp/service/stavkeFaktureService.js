/**
 * Created by Olivera on 20.6.2017..
 */
app.service('stavkeFaktureService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/stavkeFakture').then(onSuccess, onError);
        },
        read: function (fakturaId, onSuccess, onError) {
            $http.get('api/stavkeFakture/fakture/' + fakturaId).then(onSuccess, onError);
        },
    }
});

