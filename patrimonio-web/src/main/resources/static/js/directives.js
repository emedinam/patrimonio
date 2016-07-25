angular.module('patrimonioNatApp.directives', [])
        .directive('ngThumb', ['$window', function ($window) {
                var helper = {
                    support: !!($window.FileReader && $window.CanvasRenderingContext2D),
                    isFile: function (item) {
                        return angular.isObject(item) && item instanceof $window.File;
                    },
                    isImage: function (file) {
                        var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                        return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                    }
                };

                return {
                    restrict: 'A',
                    template: '<canvas/>',
                    link: function (scope, element, attributes) {
                        if (!helper.support)
                            return;

                        var params = scope.$eval(attributes.ngThumb);

                        if (!helper.isFile(params.file))
                            return;
                        if (!helper.isImage(params.file))
                            return;

                        var canvas = element.find('canvas');
                        var reader = new FileReader();

                        reader.onload = onLoadFile;
                        reader.readAsDataURL(params.file);

                        function onLoadFile(event) {
                            var img = new Image();
                            img.onload = onLoadImage;
                            img.src = event.target.result;
                        }

                        function onLoadImage() {
                            var width;
                            var height;
                            if (params.width || params.height) {
                                width = params.width || this.width / this.height * params.height;
                                height = params.height || this.height / this.width * params.width;
                            } else {
                                if ($(window).width() > 900) {
                                    width = 800;
                                    height = 500;
                                } else if ($(window).width() > 550 && $(window).width() <= 900) {
                                    width = 500;
                                    height = 300;
                                } else {
                                    width = 300;
                                    height = 200;
                                }

                            }
                            canvas.attr({width: width, height: height});
                            canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
                        }
                    }
                };
            }])
        




