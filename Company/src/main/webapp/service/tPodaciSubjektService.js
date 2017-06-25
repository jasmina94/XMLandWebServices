/**
 * Created by JELENA on 20.6.2017.
 */

app.service('tPodaciSubjektService', function ($http) {
    return {
        read: function (onSuccess, onError) {
            $http.get('/api/tPodaciSubjekti').then(onSuccess, onError);
        },
        readPoslovniPartneri: function (id, onSuccess, onError) {
        $http.get('api/tPodaciSubjekti/poslovniPartneri/' + id).then(onSuccess, onError);
        }
    }
});
