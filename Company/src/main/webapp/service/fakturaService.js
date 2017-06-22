/**
 * Created by JELENA on 20.6.2017.
 */

app.service('fakturaService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/fakture').then(onSuccess, onError);
        },
        readDobavljac: function (pib, onSuccess, onError) {
            $http.get('api/fakture/firmaDobavljac/' + pib).then(onSuccess, onError);
        },
        readKupac: function (pib, onSuccess, onError) {
            $http.get('api/fakture/firmaKupac/' + pib).then(onSuccess, onError);
        },
        create: function(faktura, onSuccess, onError){
            $http.post('/api/fakture', faktura).then(onSuccess, onError);
        },
        update: function(faktura, onSuccess, onError){//mozda i nece trebati
            $http.patch('/api/fakture/' + faktura.id, faktura).then(onSuccess, onError);
        }
    }
});
