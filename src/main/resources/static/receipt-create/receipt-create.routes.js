angular.module('receipt-create')
.config(function($routeProvider) {
    $routeProvider.when('/receipts/add', {
        templateUrl: '/receipt-create/receipt-create.html',
        controller: 'ReceiptCreateController',
        controllerAs: 'vm',
        resolve: {
            products: function(productService) {
                return productService.search()
                .then(function(productPage) {
                    return productPage.content;
                });
            }
        }
    });
});