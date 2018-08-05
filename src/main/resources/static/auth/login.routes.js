angular.module('auth')
.config(function route($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: '/auth/login.html',
        controllerAs: 'vm',
        controller: 'LoginController'
    });
});
