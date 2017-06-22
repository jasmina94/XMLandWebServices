/**
 * Created by Olivera on 22.6.2017..
 */

app.service('nalogZaPrenosService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/naloziZaPrenos').then(onSuccess, onError);
        },
        kreirajNalog: function (faktura, onSuccess, onError) {
            alert("Bla")
            $http.post('api/naloziZaPrenos/kreirajNalog', faktura).then(onSuccess, onError);
        }
    }
});
