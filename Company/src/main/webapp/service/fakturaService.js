/**
 * Created by JELENA on 20.6.2017.
 */

app.service('fakturaService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/fakture').then(onSuccess, onError);
        },
        readFaktura: function (id, onSuccess, onError) {
          $http.get('/api/fakture/' + id).then(onSuccess, onError);
        },
        readDobavljac: function (onSuccess, onError) {
            $http.get('api/fakture/firmaDobavljac/').then(onSuccess, onError);
        },
        readKupac: function (onSuccess, onError) {
            $http.get('api/fakture/firmaKupac').then(onSuccess, onError);
        },
        create: function(faktura, onSuccess, onError){
            $http.post('/api/fakture', faktura).then(onSuccess, onError);
        },
        update: function(faktura, onSuccess, onError){//mozda i nece trebati
            $http.patch('/api/fakture/' + faktura.id, faktura).then(onSuccess, onError);
        },
        generisiPdf: function(faktura, onSuccess, onError){
            $http.post('/api/fakture/generisiPdf', faktura).then(onSuccess, onError);
        }
    }
});
