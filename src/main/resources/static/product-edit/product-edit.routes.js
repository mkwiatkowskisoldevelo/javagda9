angular.module('product-edit')
.config(function($routeProvider) {
    $routeProvider.when('/products/edit/:productId', {
        templateUrl: '/product-edit/product-edit.html',
        controller: 'ProductEditController',
        controllerAs: 'vm',
        resolve: {
            product: function(productService, $route) {
                return productService.get($route.current.params.productId);
            }
        }
    });
});