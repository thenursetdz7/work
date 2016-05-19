(function () {
  'use strict';
  angular
    .module('app')
    .controller('LanguageController', languageController);


  /** @ngInject */
  function languageController($translate, $location, $locale) {
    var vm = this;
    var currentLocal = $locale.id.substring(0, 2);

    vm.currentLocale = currentLocal;
    vm.changeLanguage = function (locale) {
      $translate.use(locale);
      $location.search('lang', locale);
      vm.currentLocale = locale;
    }
  }
})();
