var productListModule = angular.module('product-list');

productListModule.controller('ProductListController', function(productService) {
    var vm = this;

    vm.sortOptions = [
        {
            displayName: 'Name ASC',
            value: 'name,asc'
        },
        {
            displayName: 'Name DESC',
            value: 'name,desc'
        },
        {
            displayName: 'Price ASC',
            value: 'price,asc'
        },
        {
            displayName: 'Price DESC',
            value: 'price,desc'
        }
    ];

    vm.params = {};

    vm.search = search;

    vm.remove = remove;

    search();

    function search() {
        productService.search(vm.params)
        .then(function(response) {
            vm.products = response.content;
        })
        .catch(function(response) {
            alert(response.data.message);
            vm.error = response.data.message;
        });
    }

    function remove(productId) {
        productService.remove(productId)
        .then(function() {
            search();
        });
    }
});