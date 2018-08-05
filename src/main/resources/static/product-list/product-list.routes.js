var productListModule = angular.module('product-list');

productListModule.config(function($routeProvider) {
    $routeProvider.when('/products', {
        templateUrl: '/product-list/product-list.html',
        controller: 'ProductListController',
        controllerAs: 'vm'
    });
});