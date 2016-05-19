(function () {
  'use strict'
  angular
    .module('app')
    .factory('shoppingCartService', shoppingCartService)
    .factory('cartManagement', cartManagement);

  /** @ngInject */
  function shoppingCartService($resource) {
    return $resource('http://localhost:8080/shoppingcart/:id', {id: '@_id'}, {
      update: {
        method: 'PUT' // this method issues a PUT request
      }
    });

  }

  /** @ngInject */
  function cartManagement($resource) {
    return $resource('/shoppingcart/:action/:id', {id:'@_id'}, {
      addToCart: {
        method: 'POST',
        params: {'action': 'addToCart'}
      },
      saveCart:{
        method: 'POST',
        params: {'action' : 'saveCart'}
      }
    })
  }
})();
