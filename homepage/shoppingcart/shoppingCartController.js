(function () {
  'use strict';

  angular
    .module('app')
    .controller('shoppingCartController', ShoppingCartController);


  /** @ngInject */
  function ShoppingCartController(shoppingCartService,cartManagement,$scope, $rootScope, $routeParams,$log ) {
    var vm = this;

    if ($rootScope.shoppingCart != null){
      vm.cart = $rootScope.shoppingCart;
    }else {
      var id = $routeParams.id;
      shoppingCartService.get({id: id}, function (data) {
        vm.cart = data;
      })
    }
    $scope.$on('$locationChangeStart', function () {
      $rootScope.cartUpdateSuccess = false;

    });

    vm.updateCart = function () {
        $rootScope.cartUpdateSuccess = true;
    }

    vm.totalEach = function (index) {
      return vm.cart.selectedProducts[index].product.totalPrice * vm.cart.selectedProducts[index].amount;
    }

    vm.saveCart = function (cart){
      cart.user = {};
      cart.user.username = $rootScope.user.name;
      cartManagement.saveCart(cart,function(returnData){
        $rootScope.shoppingCart = returnData;
        //success add cart
        $log.debug("save cart success");
      })
    }


    vm.total = function () {
      var total = 0;
      angular.forEach(vm.cart.selectedProducts, function (item) {
        total += item.amount * item.product.totalPrice;
      })

      return total;
    }

    vm.removeProduct = function(index){
      $rootScope.shoppingCart.selectedProducts.splice(index, 1);
    }
  }
})();
