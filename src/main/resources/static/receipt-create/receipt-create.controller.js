angular.module('receipt-create')
.controller('ReceiptCreateController', function(products) {
    var vm = this;

    vm.products = products;
});