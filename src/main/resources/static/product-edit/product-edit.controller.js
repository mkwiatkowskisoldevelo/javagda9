angular.module('product-edit')
.controller('ProductEditController', function(product, productService, $location) {
    var vm = this;

    vm.product = product;

    vm.update = update;

    function update() {
        productService.update(vm.product)
        .then(function() {
            $location.path('/products');
        })
        .catch(function(response) {
            vm.errors = response.data;
        });
    }
});