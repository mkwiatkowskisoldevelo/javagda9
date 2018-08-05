angular.module('product-create')
.controller('ProductCreateController', function(productService, $location) {
    var vm = this;

    vm.product = {};

    vm.create = create;

    function create() {
        productService.create(vm.product)
        .then(function() {
            $location.path('/products');
        })
        .catch(function(response) {
            vm.errors = response.data;
        });
    }
});