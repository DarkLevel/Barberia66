/* global moduleCita, FullCalendar, moment */

'use strict';

moduleCita.controller('citaCalendarioController', ['$scope', '$http', 'toolService', '$anchorScroll', '$mdDialog',
    function ($scope, $http, toolService, $anchorScroll, $mdDialog) {
        $anchorScroll();
        $scope.isActive = toolService.isActive;

        $scope.calendar = null;
        $scope.empleados = null;
        $scope.citas = null;
        $scope.recursos = null;
        $scope.fuenteEventos = null;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=getresources'
        }).then(function (response) {
            if (response.data.status === 200) {
                $scope.empleados = response.data.message;
                var datarecursos = [];
                response.data.message.forEach(element => {
                    var resource = {
                        id: element.id,
                        title: (element.nombre).substring(0, 3) + '.'
                    };
                    datarecursos.push(resource);
                });
                $scope.recursos = datarecursos;

                $http({
                    method: 'GET',
                    url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=getpage&modo=dayGridMonth&fecha=' + moment(new Date()).format('YYYY-MM-DD')
                }).then(function (response) {
                    if (response.data.status === 200) {
                        $scope.citas = response.data.message;
                        var dataeventos = [];
                        response.data.message.forEach(element => {
                            var evento = {
                                resourceId: element.obj_usuario.id,
                                id: element.id,
                                title: element.obj_tipocita.descripcion,
                                start: new Date(element.fecha_inicio),
                                end: new Date(element.fecha_fin)
                            };
                            dataeventos.push(evento);
                        });
                        $scope.fuenteEventos = [{events: dataeventos, id: 1}];
                        $('#calendar').empty();
                        renderCalendar();
                    }
                }, function (response) {
                    $scope.status = response.status;
                    response.data.message = response.data.message || 'Request failed';
                });
            }
        }, function (response) {
            $scope.status = response.status;
            response.data.message = response.data.message || 'Request failed';
        });

        function renderCalendar() {
            $scope.calendar = new FullCalendar.Calendar($('#calendar')[0], {
                schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
                plugins: ['interaction', 'resourceDayGrid', 'resourceTimeGrid'],
                locale: 'es',
                timeZone: 'local',
                firstDay: 1,
                defaultView: 'dayGridMonth',
                datesAboveResources: true,
                editable: true,
                selectable: true,
                eventLimit: true,
                nowIndicator: true,
                unselectAuto: true,
                eventOverlap: false,
                slotEventOverlap: false,
                allDaySlot: false,
                scrollTime: '07:00',
                height: 750,
                slotLabelFormat: {
                    hour: 'numeric',
                    minute: '2-digit',
                    omitZeroMinute: false,
                    meridiem: 'false'
                },
                businessHours: {
                    daysOfWeek: [1, 2, 3, 4, 5],
                    startTime: '09:00',
                    endTime: '20:00'
                },
                eventConstraint: "businessHours",
                resources: $scope.recursos,
                eventSources: $scope.fuenteEventos,
                header: {
                    left: 'anterior,siguiente hoy',
                    center: 'title',
                    right: 'dia,semana,mes'
                },
                customButtons: {
                    anterior: {
                        text: '<',
                        click: function () {
                            $scope.calendar.prev();
                            switch ($scope.calendar.view.type) {
                                case 'resourceTimeGridDay':
                                    moveDay($scope.calendar.getDate());
                                    break;
                                case 'resourceTimeGridWeek':
                                    break;
                                case 'dayGridMonth':
                                    break;
                            }
                            console.log(moment($scope.calendar.getDate()).format('YYYY-MM-DD'));
                        }
                    },
                    siguiente: {
                        text: '>',
                        click: function () {
                            $scope.calendar.next();
                            switch ($scope.calendar.view.type) {
                                case 'resourceTimeGridDay':
                                    moveDay($scope.calendar.getDate());
                                    break;
                                case 'resourceTimeGridWeek':
                                    break;
                                case 'dayGridMonth':
                                    break;
                            }
                            console.log(moment($scope.calendar.getDate()).format('YYYY-MM-DD'));
                        }
                    },
                    hoy: {
                        text: 'Hoy',
                        click: function () {
                            var fecha = $scope.calendar.getDate().toString().split(" ", 4);
                            var hoy = new Date().toString().split(" ", 4);
                            if (fecha[0] !== hoy[0] || fecha[1] !== hoy[1] || fecha[2] !== hoy[2] || fecha[3] !== hoy[3]) {
                                $scope.calendar.today();
                                switch ($scope.calendar.view.type) {
                                    case 'resourceTimeGridDay':
                                        moveDay($scope.calendar.getDate());
                                        break;
                                    case 'resourceTimeGridWeek':
                                        break;
                                    case 'dayGridMonth':
                                        break;
                                }
                                console.log(moment($scope.calendar.getDate()).format('YYYY-MM-DD'));
                            }
                        }
                    },
                    dia: {
                        text: 'D\u00EDa',
                        click: function () {
                            if ($scope.calendar.view.type !== 'resourceTimeGridDay') {
                                $scope.calendar.changeView('resourceTimeGridDay');
                                moveDay($scope.calendar.getDate());
                            }
                        }
                    },
                    semana: {
                        text: 'Semana',
                        click: function () {
                            if ($scope.calendar.view.type !== 'resourceTimeGridWeek') {
                                $scope.calendar.changeView('resourceTimeGridWeek');
                                console.log(moment($scope.calendar.getDate()).format('YYYY-MM-DD'));
                            }
                        }
                    },
                    mes: {
                        text: 'Mes',
                        click: function () {
                            if ($scope.calendar.view.type !== 'dayGridMonth') {
                                $scope.calendar.changeView('dayGridMonth');
                                console.log(moment($scope.calendar.getDate()).format('YYYY-MM-DD'));
                            }
                        }
                    }
                },
                dateClick: function (info) {
                    if ($scope.calendar.view.type !== 'resourceTimeGridDay') {
                        $scope.calendar.changeView('resourceTimeGridDay', moment(info.date).format('YYYY-MM-DD'));
                    }
                },
                select: function (info) {
                    if ($scope.calendar.view.type === 'resourceTimeGridDay') {
                        $scope.resourceId = info.resource.id;
                        $scope.fecha_inicio = moment(info.start).format('YYYY-MM-DD HH:mm:ss');
                        $scope.fecha_fin = moment(info.end).format('YYYY-MM-DD HH:mm:ss');
                        abrirDialog();
                    }
                },
                eventClick: function (info) {
                    if ($scope.calendar.view.type === 'dayGridMonth') {
                        $scope.calendar.changeView('resourceTimeGridDay', moment(info.start).format('YYYY-MM-DD'));
                    }
                }
            });

            $scope.calendar.render();
        }

        function abrirDialog() {
            $mdDialog.show({
                controller: DialogController,
                templateUrl: 'js/app/cita/citaDialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: DialogController.customFullscreen,
                scope: $scope,
                preserveScope: true
            }).then(function () {
                $scope.statusDialog = 'ok';
            }, function () {
                $scope.statusDialog = 'cancel';
            });
        }

        function DialogController($scope, $mdDialog) {
            $scope.cancel = function () {
                $mdDialog.cancel();
            };
            $scope.answer = function () {
                var json = {
                    id_usuario: $scope.resourceId,
                    fecha_inicio: $scope.fecha_inicio,
                    fecha_fin: $scope.fecha_fin,
                    id_estadocita: 1,
                    descripcion: "prueba",
                    id_tipocita: 1
                };
                $http({
                    method: 'GET',
                    url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=create',
                    params: {json: JSON.stringify(json)}
                }).then(function (response) {
                    if (response.data.status === 200) {
                        $scope.citas = response.data.message;
                        var dataeventos = [];
                        response.data.message.forEach(element => {
                            var evento = {
                                resourceId: element.obj_usuario.id,
                                id: element.id,
                                title: element.obj_tipocita.descripcion,
                                start: new Date(element.fecha_inicio),
                                end: new Date(element.fecha_fin)
                            };
                            dataeventos.push(evento);
                        });
                        $scope.fuenteEventos = [{events: dataeventos, id: 1}];
                        $('#calendar').empty();
                        renderCalendar();
                        $scope.calendar.changeView('resourceTimeGridDay', moment($scope.fecha_inicio).format('YYYY-MM-DD'));
                    }
                }, function (response) {
                    $scope.status = response.status;
                    response.data.message = response.data.message || 'Request failed';
                });
                $mdDialog.hide();
            };
        }

        function moveDay(fecha) {
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=getpage&modo=resourceTimeGridDay&fecha=' + moment(fecha).format('YYYY-MM-DD')
            }).then(function (response) {
                if (response.data.status === 200) {
                    $scope.citas = response.data.message;
                    var dataeventos = [];
                    response.data.message.forEach(element => {
                        var evento = {
                            resourceId: element.obj_usuario.id,
                            id: element.id,
                            title: element.obj_tipocita.descripcion,
                            start: new Date(element.fecha_inicio),
                            end: new Date(element.fecha_fin)
                        };
                        dataeventos.push(evento);
                    });
                    $scope.fuenteEventos = [{events: dataeventos, id: 1}];
                    $('#calendar').empty();
                    renderCalendar();
                    $scope.calendar.changeView('resourceTimeGridDay', fecha);
                }
            }, function (response) {
                $scope.status = response.status;
                response.data.message = response.data.message || 'Request failed';
            });
        }

    }
]);