angular.module('patrimonioNatApp.controllers', ['ionic'])

        .controller('SignInCtrl', function ($scope, $state, $ionicModal) {

            $scope.showValidation = false;
            $scope.signIn = function () {
                console.log("username", $scope.username);
                console.log("password", $scope.password);
                $state.go('tab.trees'); //quitar despues
                if ($scope.username && $scope.password) {
                    var postData = {
                        "username": $scope.username,
                        "password": $scope.password
                    };
                    $state.go('tab.trees');
                } else {
                    $scope.showValidation = true;
                }
            };
        })

        .controller('TreesRegCtrl', function ($scope, TreesRegList, NgTableParams, $ionicPopup, $ionicModal) {
            $scope.idTreeRegEdit = -1;
            $scope.type = 0; //tipo de opciones  0-show 1-edit 2-create
            $scope.treesReg = TreesRegList.all();

            var self = this;
            var data = $scope.treesReg;

            $scope.filters = {
                cedula: {'cedula': 'text'},
                nombreComun: {'nombreComun': 'text'},
                observaciones: {'observaciones': 'text'},
                patrimonio: {'patrimonio': 'text'}
            };
            self.tableParams = new NgTableParams({count: 1}, {counts: [1, 5, 10, 25], dataset: data});
            $scope.showConfirmDelete = function (treeReg) {
                $scope.idTreeRegEdit = treeReg.id;
                var confirmPopup = $ionicPopup.confirm({
                    title: '<h2>Eliminar Arbol Registrado&nbsp;<span class="icon ion-information-circled"></span></h2>',
                    template: '¿Esta seguro de eliminar el árbol seleccionado?',
                    okText: 'Eliminar<div class="icon ion-ios-trash assertive-bg"></div>',
                    okType: 'button-assertive',
                    cancelText: 'Cancelar<div class="icon ion-ios-close-outline balanced-bg"></div>',
                    cancelType: 'button-balanced'
                });
                confirmPopup.then(function (res) {
                    if (res) {
                        $scope.deleteTreeReg(treeReg);
                    }
                });
            };
            $scope.deleteTreeReg = function (treeReg) {
                TreesRegList.delete({id: treeReg.id});
                $scope.treesReg.splice($scope.treesReg.indexOf(treeReg), 1);
                data = $scope.treesReg;
                var count = self.tableParams.count();
                var page = self.tableParams.page() * count >= data.length ? self.tableParams.page() - 1 : self.tableParams.page();
                self.tableParams = new NgTableParams({count: count, page: page === 0 ? page + 1 : page}, {counts: [2, 5, 10, 25], dataset: data});
            };
            $scope.changeFilters = function (filter) {
                if (filter === "cedula")
                    $scope.filters.cedula = $scope.filters.cedula !== false ? false : {'cedula': 'text'};
                if (filter === "nombreComun")
                    $scope.filters.nombreComun = $scope.filters.nombreComun !== false ? false : {'nombreComun': 'text'};
                if (filter === "observaciones")
                    $scope.filters.observaciones = $scope.filters.observaciones !== false ? false : {'observaciones': 'text'};
                if (filter === "patrimonio")
                    $scope.filters.patrimonio = $scope.filters.patrimonio !== false ? false : {'patrimonio': 'text'};
            };
            $ionicModal.fromTemplateUrl('filtersModal', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function (modal) {
                $scope.filtersModal = modal;
            });
            $scope.openModal = function () {
                $scope.filtersModal.show();
            };
            $scope.closeModal = function () {
                $scope.filtersModal.hide();
            };
            $scope.$on('$destroy', function () {
                $scope.filtersModal.remove();
            });
        })
        
        .controller('ReportCtrl', function ($scope) {
        var map = new ol.Map({
                target: 'map',
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.MapQuest({layer: 'sat'})
                    })
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([-78.507751, -0.208946]),
                    zoom: 4
                })
            });
        })
        
        .controller('TreeDetailCtrl', function ($scope, $stateParams, TreeReg, TreesList, Seguimiento, $ionicPopup, $ionicModal, FileUploader, NgTableParams) {
            $scope.showValidation = false;
            var self = this;
            var data = {};
            var csrfToken = $('meta[name=csrf-token]').attr('content');
            var uploader = $scope.uploader = new FileUploader({
                url: '/api/fichas/id/fotos',
                headers: {
                    'X-CSRF-TOKEN': csrfToken
                }
            });

            uploader.filters.push({
                name: 'imageFilter',
                fn: function (item /*{File|FileLikeObject}*/, options) {
                    var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                }
            });
            
            var uploaderSeguim = $scope.uploaderSeguim = new FileUploader({
                url: '/api/fichas/id/fotos',
                headers: {
                    'X-CSRF-TOKEN': csrfToken
                }
            });

            uploaderSeguim.filters.push({
                name: 'imageFilter',
                fn: function (item /*{File|FileLikeObject}*/, options) {
                    var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                }
            });
            
            uploaderSeguim.onAfterAddingFile = function (fileItem) {
                console.info('onAfterAddingFile', fileItem);
            };
            uploaderSeguim.onAfterAddingAll = function (addedFileItems) {
                if (uploaderSeguim.queue.length > 1)
                    uploaderSeguim.queue[0].remove();
                console.info('onAfterAddingAll', addedFileItems);
            };

            uploader.onAfterAddingFile = function (fileItem) {
                console.info('onAfterAddingFile', fileItem);
            };
            uploader.onAfterAddingAll = function (addedFileItems) {
                if (uploader.queue.length > 1)
                    uploader.queue[0].remove();
                console.info('onAfterAddingAll', addedFileItems);
            };

            if ($stateParams.treeRegId && $stateParams.treeRegId > -1) {
                $scope.treeReg = TreeReg.get($stateParams.treeRegId);
                //debe ser en la lectura
                data = $scope.treeReg.seguimientos;
                var fileItems = [];
                fileItems[0] = new FileUploader.FileItem($scope.uploader, {
                    "id": $scope.treeReg.id,
                    "name": $scope.treeReg.cedula + ".jpg",
                    "type": "image/jpeg",
                });
                fileItems[0].id = $scope.treeReg.id;
                fileItems[0].urlHttp = $scope.treeReg.foto; //cambiar por el api
                fileItems[0].progress = 100;
                fileItems[0].isUploaded = true;
                fileItems[0].isSuccess = true;
                $scope.uploader.queue.push(fileItems[0]);
            } else
                $scope.treeReg = {};
            $scope.trees = TreesList.all();

            $scope.saveUpdatedTreeReg = function () {
                if ($scope.treeReg.cedula && $scope.treeReg.tipo) {
                    if ($scope.treeReg.id) {
                        console.log("updated tree");
                    } else
                        console.log("created tree");
                    var confirmPopup = $ionicPopup.confirm({
                        title: '<h2>Datos Guardados&nbsp;<span class="icon ion-ios-cloud-upload-outline"></span></h2>',
                        template: 'Los datos han sido Guardados satisfactoriamente. ¿Desea agregar un nuevo seguimiento? ',
                        okText: 'Si<div class="icon ion-ios-navigate-outline balanced-bg"></div>',
                        okType: 'button-balanced',
                        cancelText: 'No<div class="icon ion-ios-close-outline balanced-bg"></div>',
                        cancelType: 'button-balanced',
                    });
                    confirmPopup.then(function (res) {
                        if (res) {
                            alert("segui")
                        }
                        else
                            alert("NO")
                    });
                } else {
                    $scope.showValidation = true;
                    var alertPopup = $ionicPopup.alert({
                        title: '<h2>Datos Incompletos&nbsp;<span class="icon ion-information-circled"></span></h2>',
                        template: 'Existen datos sin completar!!!',
                    });
                }
            };

            var map = new ol.Map({
                target: 'map',
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.MapQuest({layer: 'sat'})
                    })
                ],
                view: new ol.View({
                    center: ol.proj.fromLonLat([-78.507751, -0.208946]),
                    zoom: 4
                })
            });

            $scope.idSeguimientoEdit = -1;
            $scope.type = 0; //tipo de opciones  0-show 1-edit 2-create

            $scope.filters = {
                fecha: {'fecha': 'text'},
                ejecutor: {'ejecutor': 'text'},
                acciones: {'acciones': 'text'}
            };
            self.tableParams = new NgTableParams({count: 1}, {counts: [1, 5, 10, 25], dataset: data});
            $scope.showConfirmDelete = function (seguim) {
                $scope.idSeguimientoEdit = seguim.id;
                var confirmPopup = $ionicPopup.confirm({
                    title: '<h2>Eliminar Seguimiento&nbsp;<span class="icon ion-information-circled"></span></h2>',
                    template: '¿Esta seguro de eliminar el seguimiento seleccionado?',
                    okText: 'Eliminar<div class="icon ion-ios-trash assertive-bg"></div>',
                    okType: 'button-assertive',
                    cancelText: 'Cancelar<div class="icon ion-ios-close-outline balanced-bg"></div>',
                    cancelType: 'button-balanced'
                });
                confirmPopup.then(function (res) {
                    if (res) {
                        $scope.deleteSeguimiento(seguim);
                    }
                });
            };
            $scope.deleteSeguimiento = function (seguim) {
                Seguimiento.delete({id: seguim.id});
                $scope.treeReg.seguimientos.splice($scope.treeReg.seguimientos.indexOf(seguim), 1);
                data = $scope.treeReg.seguimientos;
                var count = self.tableParams.count();
                var page = self.tableParams.page() * count >= data.length ? self.tableParams.page() - 1 : self.tableParams.page();
                self.tableParams = new NgTableParams({count: count, page: page === 0 ? page + 1 : page}, {counts: [2, 5, 10, 25], dataset: data});
            };
            $scope.changeFilters = function (filter) {
                if (filter === "fecha")
                    $scope.filters.fecha = $scope.filters.fecha !== false ? false : {'fecha': 'text'};
                if (filter === "ejecutor")
                    $scope.filters.ejecutor = $scope.filters.ejecutor !== false ? false : {'ejecutor': 'text'};
                if (filter === "acciones")
                    $scope.filters.acciones = $scope.filters.acciones !== false ? false : {'acciones': 'text'};
            };
            
            $ionicModal.fromTemplateUrl('filtersModal', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function (modal) {
                $scope.filtersModal = modal;
            });
            $scope.openModal = function () {
                $scope.filtersModal.show();
            };
            $scope.closeModal = function () {
                $scope.filtersModal.hide();
            };
            $scope.$on('$destroy', function () {
                $scope.filtersModal.remove();
            });
            
            $ionicModal.fromTemplateUrl('seguimientoShowModal', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function (modal) {
                $scope.seguimientoShowModal = modal;
            });
            $scope.openSeguimientoShowModal = function (seguim, type) {
                for (var i = 0; i < uploader.queue.length; i++) {
                    uploader.queue[0].remove();
                }
                if (seguim) {
                    $scope.idSeguimEdit = seguim.id;
                    $scope.seguimEdit = {
                        fecha: seguim.fecha,
                        ejecutor: seguim.ejecutor,
                        acciones: seguim.acciones,
                        foto: seguim.foto
                    };
                    var fileItems = [];
                    fileItems[0] = new FileUploader.FileItem($scope.uploaderSeguim, {
                        "id": $scope.seguimEdit.id,
                        "name": $scope.seguimEdit.fecha + ".jpg",
                        "type": "image/jpeg",
                    });
                    fileItems[0].id = $scope.seguimEdit.id;
                    fileItems[0].urlHttp = $scope.seguimEdit.foto; //cambiar por el api
                    fileItems[0].progress = 100;
                    fileItems[0].isUploaded = true;
                    fileItems[0].isSuccess = true;
                    $scope.uploaderSeguim.queue.push(fileItems[0]);
                } else {
                    $scope.idSeguimEdit = -1;
                    $scope.seguimEdit = {};
                }
                $scope.type = type;
                $scope.seguimientoShowModal.show();
            };
            $scope.$on('$destroy', function () {
                $scope.seguimientoShowModal.remove();
            });
            $scope.closeSeguimientoShowModal = function () {
                $scope.seguimientoShowModal.hide();
            };
            
            $scope.showValidationSeguim = false;
            $scope.saveUpdatedSeguim = function () {
                if ($scope.seguimEdit.ejecutor) {
                    if ($scope.type === 1) {
                        $scope.treeReg.seguimientos[$scope.idSeguimEdit].ejecutor = $scope.seguimEdit.ejecutor;
                        $scope.treeReg.seguimientos[$scope.idSeguimEdit].acciones = $scope.seguimEdit.acciones;
                        console.log("updated tree")
                    } else
                        console.log("created tree")
                    $scope.closeSeguimientoShowModal();
                } else {
                    $scope.showValidationSeguim = true;
                    var alertPopup = $ionicPopup.alert({
                        title: '<h2>Datos Incompletos&nbsp;<span class="icon ion-information-circled"></span></h2>',
                        template: 'Existen datos sin completar!!!',
                    });
                }
            };

        })

        .controller('DictionaryCtrl', function ($scope, TreesList, FamiliaList, TipoList, NgTableParams, $ionicPopup, $ionicModal, FileUploader) {
            $scope.showValidation = false;
            $ionicModal.fromTemplateUrl('filtersModal', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function (modal) {
                $scope.filtersModal = modal;
            });
            $scope.openModal = function () {
                $scope.filtersModal.show();
            };
            $scope.closeModal = function () {
                $scope.filtersModal.hide();
            };
            $scope.$on('$destroy', function () {
                $scope.filtersModal.remove();
            });
            $ionicModal.fromTemplateUrl('treeShowModal', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function (modal) {
                $scope.treeShowModal = modal;
            });
            $scope.openTreeShowModal = function (tree, type) {
                for (var i = 0; i < uploader.queue.length; i++) {
                    uploader.queue[0].remove();
                }
                if (tree) {
                    $scope.idTreeEdit = tree.id;
                    $scope.treeEdit = {
                        nombreComun: tree.nombreComun,
                        nombreCientifico: tree.nombreCientifico,
                        familia: tree.familia,
                        tipo: tree.tipo,
                        contenidoInformativo: tree.contenidoInformativo,
                        foto: tree.foto
                    };
                    var fileItems = [];
                    fileItems[0] = new FileUploader.FileItem($scope.uploader, {
                        "id": $scope.treeEdit.id,
                        "name": $scope.treeEdit.nombreComun + ".jpg",
                        "type": "image/jpeg",
                    });
                    fileItems[0].id = $scope.treeEdit.id;
                    fileItems[0].urlHttp = $scope.treeEdit.foto; //cambiar por el api
                    fileItems[0].progress = 100;
                    fileItems[0].isUploaded = true;
                    fileItems[0].isSuccess = true;
                    $scope.uploader.queue.push(fileItems[0]);
                } else {
                    $scope.treeEdit = {};
                    $scope.idTreeEdit = -1;
                }
                $scope.type = type;
                $scope.treeShowModal.show();
            };
            $scope.$on('$destroy', function () {
                $scope.treeShowModal.remove();
            });
            $scope.closeTreeShowModal = function () {
                $scope.treeShowModal.hide();
            };
            $scope.saveUpdatedTree = function () {
                if ($scope.treeEdit.nombreComun) {
                    if ($scope.type == 1) {
                        $scope.trees[$scope.idTreeEdit].nombreComun = $scope.treeEdit.nombreComun;
                        $scope.trees[$scope.idTreeEdit].nombreCientifico = $scope.treeEdit.nombreCientifico;
                        $scope.trees[$scope.idTreeEdit].familia = $scope.treeEdit.familia;
                        $scope.trees[$scope.idTreeEdit].tipo = $scope.treeEdit.tipo;
                        $scope.trees[$scope.idTreeEdit].contenidoInformativo = $scope.treeEdit.contenidoInformativo;
                        $scope.trees[$scope.idTreeEdit].foto = $scope.treeEdit.foto;
                        console.log("updated tree")
                    } else
                        console.log("created tree")
                    $scope.closeTreeShowModal();
                } else {
                    $scope.showValidation = true;
                    var alertPopup = $ionicPopup.alert({
                        title: '<h2>Datos Incompletos&nbsp;<span class="icon ion-information-circled"></span></h2>',
                        template: 'Existen datos sin completar!!!',
                    });
                }
            };

            $scope.idTreeEdit = -1;
            $scope.treeEdit = {};
            $scope.type = 0; //tipo de opciones  0-show 1-edit 2-create
            $scope.trees = TreesList.all();
            $scope.familias = FamiliaList.all();
            $scope.tipos = TipoList.all();
            var self = this;
            var data = $scope.trees;

            $scope.filters = {
                nombreComun: {'nombreComun': 'text'},
                nombreCientifico: {'nombreCientifico': 'text'},
                familia: {'familia': 'text'},
                tipo: {'tipo': 'text'},
                contenidoInformativo: {'contenidoInformativo': 'text'}
            };
            self.tableParams = new NgTableParams({count: 1}, {counts: [1, 5, 10, 25], dataset: data});
            $scope.showConfirmDelete = function (tree) {
                $scope.idTreeEdit = tree.id;
                var confirmPopup = $ionicPopup.confirm({
                    title: '<h2>Eliminar Arbol&nbsp;<span class="icon ion-information-circled"></span></h2>',
                    template: '¿Esta seguro de eliminar el árbol seleccionado?',
                    okText: 'Eliminar<div class="icon ion-ios-trash assertive-bg"></div>',
                    okType: 'button-assertive',
                    cancelText: 'Cancelar<div class="icon ion-ios-close-outline balanced-bg"></div>',
                    cancelType: 'button-balanced',
                });
                confirmPopup.then(function (res) {
                    if (res) {
                        $scope.deleteTree(tree);
                    }
                });
            };
            $scope.deleteTree = function (tree) {
                TreesList.delete({id: tree.id});
                $scope.trees.splice($scope.trees.indexOf(tree), 1);
                data = $scope.trees;
                var count = self.tableParams.count();
                var page = self.tableParams.page() * count >= data.length ? self.tableParams.page() - 1 : self.tableParams.page();
                self.tableParams = new NgTableParams({count: count, page: page == 0 ? page + 1 : page}, {counts: [2, 5, 10, 25], dataset: data});
            };
            $scope.changeFilters = function (filter) {
                if (filter == "nombreComun")
                    $scope.filters.nombreComun = $scope.filters.nombreComun != false ? false : {'nombreComun': 'text'};
                if (filter == "nombreCientifico")
                    $scope.filters.nombreCientifico = $scope.filters.nombreCientifico != false ? false : {'nombreCientifico': 'text'};
                if (filter == "familia")
                    $scope.filters.familia = $scope.filters.familia != false ? false : {'familia': 'text'};
                if (filter == "tipo")
                    $scope.filters.tipo = $scope.filters.tipo != false ? false : {'tipo': 'text'};
                if (filter == "contenidoInformativo")
                    $scope.filters.contenidoInformativo = $scope.filters.contenidoInformativo != false ? false : {'contenidoInformativo': 'text'};
            };

            var csrfToken = $('meta[name=csrf-token]').attr('content');
            var uploader = $scope.uploader = new FileUploader({
                url: '/api/fichas/id/fotos',
                headers: {
                    'X-CSRF-TOKEN': csrfToken
                }
            });

            uploader.filters.push({
                name: 'imageFilter',
                fn: function (item /*{File|FileLikeObject}*/, options) {
                    var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                }
            });

            uploader.onAfterAddingFile = function (fileItem) {
                console.info('onAfterAddingFile', fileItem);
            };
            uploader.onAfterAddingAll = function (addedFileItems) {
                if (uploader.queue.length > 1)
                    uploader.queue[0].remove();
                console.info('onAfterAddingAll', addedFileItems);
            };

        });
