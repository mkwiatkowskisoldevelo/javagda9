angular.module('product-create')
.config(function($routeProvider) {
    $routeProvider.when('/products/add', {
        templateUrl: '/product-create/product-create.html',
        controller: 'ProductCreateController',
        controllerAs: 'vm'
    });
});