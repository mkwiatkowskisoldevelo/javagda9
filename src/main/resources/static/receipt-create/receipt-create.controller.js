angular.module('receipt-create')
.controller('ReceiptCreateController', function(products, $resource) {
    var vm = this;

    var resource = $resource('http://localhost:8081/receipts');

    vm.products = products;

    vm.statuses = [
        'INITIATED',
        'DEVIVERED',
        'SUBMITTED'
    ];

    vm.receipt = {
        products: [],
        status: vm.statuses[0]
    };

    vm.addProduct = addProduct;
    vm.addReceipt = addReceipt;

    function addProduct() {
        vm.receipt.products.push(vm.selectedProduct);
    }

    function addReceipt() {
        vm.receipt.date = new Date();
        resource.save({}, vm.receipt);
    }
});