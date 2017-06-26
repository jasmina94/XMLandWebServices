app.service('zahtevZaIzvodService', function ($http) {
    return {
        posaljiZahtev: function (zahtev, onSuccess, onError) {
            $http.post('/api/zahtevZaIzvod/posaljiZahtev', zahtev).then(onSuccess, onError);
        }
    }
});
