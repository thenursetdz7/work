(function () {
  'use strict';

  angular
    .module('app')
    .controller('addProductController', addProductController)
    .controller('listProductController', listProductController)
    .controller('editProductController', editProductController);


  /** @ngInject */
  function addProductController($scope, $location, $rootScope, productService, $timeout) {
    var vm = this;
    vm.product = {};
    vm.addProduct = true;
    vm.editProduct = false;
    vm.addProduct = function (flowFiles) {
      productService.save(vm.product, function (data) {
        // after adding the object, add a new picture
        // get the product id which the image will be addded
        var productid = data.id;
        // set location
        flowFiles.opts.target = 'http://localhost:8080/productImage/add';
        flowFiles.opts.testChunks = false;
        flowFiles.opts.query = {productid: productid};
        flowFiles.upload();
        $timeout(function () {
          // $message is the json response from the post

        })

      });
    }

    vm.redirectToHome = function () {
      $rootScope.addSuccess = true;
      $location.path("listProduct");
    }

  }


  /** @ngInject */
  function listProductController($scope,$location, $rootScope, productService, $route, queryProductService,cartManagement) {
    var vm = this;
    //$http.get("/product/").success(function (data) {
    vm.queryPromise = productService.query(function (data) {
      // $scope.totalNetPrice= totalCalService.getTotalNetPrice(data);
      vm.products = data;
    }).$promise;


    $scope.$on('$locationChangeStart', function () {
      $rootScope.addSuccess = false;
      $rootScope.editSuccess = false;
      $rootScope.deleteSuccess = false;
    });

    vm.deleteProduct = function (id) {
      var answer = confirm("Do you want to delete the product?");
      if (answer) {
        productService.delete({id: id}, function () {
          $rootScope.deleteSuccess = true;
          $route.reload();
        })
      }
    }

    vm.searchProduct = function (name) {
      queryProductService.query({name: name}, function (data) {
        vm.products = data;
      });
    }

    vm.addToCart = function (product) {
      product.images = null;
      cartManagement.addToCart({id:product.id},$rootScope.shoppingCart, function (shoppingCart) {
        //success event
        $rootScope.shoppingCart = shoppingCart;
        $location.path("shoppingCart")

      }, function () {
        // fail event
      })

    }


  }


  /** @ngInject */
  function editProductController($http, $routeParams, $location, $rootScope, productService) {
    var vm = this;
    vm.addProduct = false;
    vm.editProduct = true;
    var id = $routeParams.id;
    productService.get({id: id},
      // success function
      function (data) {
        vm.product = data;
      }
    )


    vm.editProduct = function () {
      //$http.put("/product", $scope.product).then(function () {
      productService.update({id: vm.product.id}, vm.product, function () {
        $rootScope.editSuccess = true;
        $location.path("listProduct");
      });
    }
  }
})();
