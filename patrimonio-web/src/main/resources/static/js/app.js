angular.module('patrimonioNatApp', [
    'ionic', 
    'patrimonioNatApp.controllers', 
    'patrimonioNatApp.services', 
    'patrimonioNatApp.businessServices',
    'patrimonioNatApp.directives',
    'angularFileUpload',
    'ngTable'])

        .run(function ($ionicPlatform) {
            $ionicPlatform.ready(function () {
                // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
                // for form inputs)
                if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
                    cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
                    cordova.plugins.Keyboard.disableScroll(true);

                }
                if (window.StatusBar) {
                    // org.apache.cordova.statusbar required
                    StatusBar.styleDefault();
                }
            });
        })

        .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

            var csrfToken = $('meta[name=csrf-token]').attr('content');
            var csrfName = $('meta[name=csrf-name]').attr('content');
            $httpProvider.defaults.headers.common[csrfName] = csrfToken;
            
            $stateProvider
                    .state('signin', {
                        url: '/sign-in',
                        templateUrl: 'templates/sign-in.html',
                        controller: 'SignInCtrl'
                    })

                    .state('tab', {
                        url: '/tab',
                        abstract: true,
                        templateUrl: 'templates/tabs.html'
                    })


                    .state('tab.trees', {
                        url: '/trees',
                        views: {
                            'tab-trees': {
                                templateUrl: 'templates/tab-trees.html',
                                controller: 'TreesRegCtrl',
                                controllerAs: 'treesRegList'
                            }
                        }
                    })
                    .state('tab.treeReg-detail', {
                        url: '/treesReg/:treeRegId',
                        views: {
                            'tab-trees': {
                                templateUrl: 'templates/treeReg-detail.html',
                                controller: 'TreeDetailCtrl',
                                controllerAs: 'treeDetail'
                            }
                        }
                    })

                    .state('tab.dictionary', {
                        url: '/dictionary',
                        views: {
                            'tab-dictionary': {
                                templateUrl: 'templates/tab-dictionary.html',
                                controller: 'DictionaryCtrl',
                                controllerAs: 'dictionaryList'
                            }
                        }
                    })
                    
                    .state('tab.report', {
                        url: '/report',
                        views: {
                            'tab-report': {
                                templateUrl: 'templates/report.html',
                                controller: 'ReportCtrl',
                                controllerAs: 'reportList'
                            }
                        }
                    });

            $urlRouterProvider.otherwise('/sign-in');

        })
        
        
