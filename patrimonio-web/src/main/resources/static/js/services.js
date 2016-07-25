angular.module('patrimonioNatApp.services', ['ngResource'])

        .factory('Login', ['$resource',
            function ($resource) {
                return $resource("/login", {}, {
                    login: {method: 'POST', cache: false, isArray: false}
                });
            }])
        .factory('Fotos', ['$resource',
            function ($resource) {
                return $resource("/api/trees/:idTree/fotos/:idFoto ", {}, {
                    delete: {method: 'DELETE', cache: false, isArray: false}
                });
            }])
        .factory('Seguimiento', function () {
            return {
                add: function (seguim) {
                    console.log("delete bd");
                },
                delete: function (seguim) {
                    console.log("delete bd");
                },
                get: function (treeId) {
                    for (var i = 0; i < trees.length; i++) {
                        if (trees[i].id === parseInt(treeId)) {
                            return trees[i];
                        }
                    }
                    return null;
                }
            }
        })
        .factory('TreeReg', function () {
            // Some fake testing data
            var treeReg = [{
                    id: 0,
                    cedula: '821224',
                    tipo: {
                        id: 0,
                        nombreComun: 'Ceibas',
                        nombreCientifico: 'Ceibas opticus',
                        familia: 'frondosos',
                        tipo: 'maderable',
                        contenidoInformativo: 'para hacer muebles',
                        foto: 'img/adam.jpg'
                    },
                    observaciones: 'esta verde',
                    patrimonio: 'humanidad',
                    latitud: '115',
                    longitud: '120',
                    foto: 'img/adam.jpg',
                    seguimientos: [{
                            id: 0,
                            fecha: '03/02/2016',
                            ejecutor: 'Unesco',
                            acciones: 'Limpia. Corte',
                            foto: 'img/adam.jpg'
                        }]
                }];
            return {
                delete: function (tree) {
                    console.log("delete bd");
                },
                get: function (treeRegId) {
                    for (var i = 0; i < treeReg.length; i++) {
                        if (treeReg[i].id === parseInt(treeRegId)) {
                            return treeReg[i];
                        }
                    }
                    return null;
                }
            }
        })
        .factory('TreesRegList', function () {
            // Some fake testing data
            var treesReg = [{
                    id: 0,
                    cedula: '821224',
                    tipo: {
                        id: 0,
                        nombreComun: 'Ceibas',
                        nombreCientifico: 'Ceibas opticus',
                        familia: 'frondosos',
                        tipo: 'maderable',
                        contenidoInformativo: 'para hacer muebles',
                        foto: 'img/adam.jpg'
                    },
                    observaciones: 'esta verde',
                    patrimonio: 'humanidad',
                    latitud: '115',
                    longitud: '120',
                    foto: 'img/adam.jpg'
                }];
            return {
                all: function () {
                    return treesReg;
                },
                delete: function (tree) {
                    console.log("delete bd");
                },
                get: function (treeRegId) {
                    for (var i = 0; i < treesReg.length; i++) {
                        if (treesReg[i].id === parseInt(treeRegId)) {
                            return treesReg[i];
                        }
                    }
                    return null;
                }
            }
        })
        .factory('TreesList', function () {
            // Some fake testing data
            var trees = [{
                    id: 0,
                    nombreComun: 'Ceibas',
                    nombreCientifico: 'Ceibas opticus',
                    familia: 'frondosos',
                    tipo: 'maderable',
                    contenidoInformativo: 'para hacer muebles',
                    foto: 'img/adam.jpg'
                }, {
                    id: 1,
                    nombreComun: 'Mango',
                    nombreCientifico: 'Mangus opticus',
                    familia: 'frutrales',
                    tipo: 'frutos',
                    contenidoInformativo: 'para comida',
                    foto: 'img/ben.png'
                }];
            return {
                all: function () {
                    return trees;
                },
                delete: function (tree) {
                    console.log("delete bd");
                },
                get: function (treeId) {
                    for (var i = 0; i < trees.length; i++) {
                        if (trees[i].id === parseInt(treeId)) {
                            return trees[i];
                        }
                    }
                    return null;
                }
            }
        })
        .factory('FamiliaList', function () {
            // Some fake testing data
            var familia = [{
                    id: 0,
                    nombreFamilia: 'frondosos',
                    descripcionFamilia: 'para hacer muebles. bla bla'
                }, {
                    id: 1,
                    nombreFamilia: 'frutrales',
                    descripcionFamilia: 'para comida',
                }];
            return {
                all: function () {
                    return familia;
                }
            }
        })
        .factory('TipoList', function () {
            // Some fake testing data
            var tipos = [{
                    id: 0,
                    nombreTipo: 'maderable',
                    descripcionTipo: 'para hacer muebles. bla bla'
                }, {
                    id: 1,
                    nombreTipo: 'frutos',
                    descripcionTipo: 'para comida',
                }];
            return {
                all: function () {
                    return tipos;
                }
            };
        })


