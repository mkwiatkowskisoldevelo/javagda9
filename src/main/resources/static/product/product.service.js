angular.module('product')
.service('productService', function($resource) {
    var service = this;

    var productResource = $resource('http://localhost:8081/products/:productId', {}, {
        query: {
            method: 'GET',
            isArray: false
        }
    });

    service.search = function(params) {
        return productResource.query(params).$promise;
    };

    service.create = function(product) {
        return productResource.save({}, product).$promise;
    };

    service.remove = function(id) {
        return productResource.remove({
            productId: id
        }).$promise;
    };

    service.get = function(id) {
        return productResource.get({
            productId: id
        }).$promise;
    };
});