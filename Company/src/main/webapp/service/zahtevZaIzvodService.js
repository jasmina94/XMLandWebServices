app.service('zahtevZaIzvodService', function ($http) {
    return {
        read: function(onSuccess, onError){
            $http.get('/api/izvodi').then(onSuccess, onError);
        },
        generisiPdf: function(izvod, onSuccess, onError){
            $http.get('/api/izvodi/generisiPdf/' + izvod.id).then(onSuccess, onError);
        },
        posaljiZahtev: function (zahtev, onSuccess, onError) {
            $http.post('/api/zahtevZaIzvod/posaljiZahtev', zahtev).then(onSuccess, onError);
        }
    }
});
