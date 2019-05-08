/* global moduleCita, FullCalendar, moment */

'use strict';

moduleCita.controller('citaCalendarioController', ['$scope', '$http', 'toolService', '$anchorScroll', '$mdDialog',
    function ($scope, $http, toolService, $anchorScroll, $mdDialog) {
        $anchorScroll();
        $scope.isActive = toolService.isActive;

        $http({
            method: 'GET',
            url: 'http://localhost:8081/barberia66/barberia66?ob=tipocita&op=getpage&rpp=10000&page=1&order=descripcion,asc'
        }).then(function (response) {
            $scope.ajaxDataTipoCita = response.data.message;
            var listatipocitas = [];
            $scope.ajaxDataTipoCita.forEach(element => {
                var tipocitas = {
                    obj_tipocita: element
                };
                listatipocitas.push(tipocitas);
            });
            $scope.tipocita = listatipocitas[0].obj_tipocita.id;
        }, function (response) {
            $scope.status = response.status;
            $scope.ajaxDataTipoCita = response.data.message || 'Request failed';
        });

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
                scrollTime: '08:00',
                height: 739,
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
                                    getpageCalendar('resourceTimeGridDay', $scope.calendar.getDate());
                                    break;
                                case 'resourceTimeGridWeek':
                                    getpageCalendar('resourceTimeGridWeek', $scope.calendar.getDate());
                                    break;
                                case 'dayGridMonth':
                                    getpageCalendar('dayGridMonth', $scope.calendar.getDate());
                                    break;
                            }
                        }
                    },
                    siguiente: {
                        text: '>',
                        click: function () {
                            $scope.calendar.next();
                            switch ($scope.calendar.view.type) {
                                case 'resourceTimeGridDay':
                                    getpageCalendar('resourceTimeGridDay', $scope.calendar.getDate());
                                    break;
                                case 'resourceTimeGridWeek':
                                    getpageCalendar('resourceTimeGridWeek', $scope.calendar.getDate());
                                    break;
                                case 'dayGridMonth':
                                    getpageCalendar('dayGridMonth', $scope.calendar.getDate());
                                    break;
                            }
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
                                        getpageCalendar('resourceTimeGridDay', $scope.calendar.getDate());
                                        break;
                                    case 'resourceTimeGridWeek':
                                        getpageCalendar('resourceTimeGridWeek', $scope.calendar.getDate());
                                        break;
                                    case 'dayGridMonth':
                                        getpageCalendar('dayGridMonth', $scope.calendar.getDate());
                                        break;
                                }
                            }
                        }
                    },
                    dia: {
                        text: 'D\u00EDa',
                        click: function () {
                            if ($scope.calendar.view.type !== 'resourceTimeGridDay') {
                                $scope.calendar.changeView('resourceTimeGridDay');
                                getpageCalendar('resourceTimeGridDay', $scope.calendar.getDate());
                            }
                        }
                    },
                    semana: {
                        text: 'Semana',
                        click: function () {
                            if ($scope.calendar.view.type !== 'resourceTimeGridWeek') {
                                $scope.calendar.changeView('resourceTimeGridWeek');
                                getpageCalendar('resourceTimeGridWeek', $scope.calendar.getDate());
                            }
                        }
                    },
                    mes: {
                        text: 'Mes',
                        click: function () {
                            if ($scope.calendar.view.type !== 'dayGridMonth') {
                                $scope.calendar.changeView('dayGridMonth');
                                getpageCalendar('dayGridMonth', $scope.calendar.getDate());
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
                        var datosEmpleado = null;
                        for (var i = 0; i < $scope.empleados.length && Boolean(datosEmpleado === null); i++) {
                            if ($scope.empleados[i].id === parseInt(info.resource.id)) {
                                datosEmpleado = $scope.empleados[i];
                            }
                        }
                        $scope.accion = "Crear";
                        $scope.id_usuario = datosEmpleado.id;
                        $scope.nombreEmpleado = datosEmpleado.nombre;
                        $scope.fecha_inicio = moment(info.start).format('HH:mm:ss DD-MM-YYYY');
                        $scope.fecha_fin = moment(info.end).format('HH:mm:ss DD-MM-YYYY');
                        $scope.descripcion = "";
                        $scope.id_estadocita = 1;
                        $scope.tipocita = 1;
                        abrirDialog();
                    }
                },
                eventClick: function (info) {
                    if ($scope.calendar.view.type === 'dayGridMonth') {
                        $scope.calendar.changeView('resourceTimeGridDay', moment(info.event.start).format('YYYY-MM-DD'));
                    } else {
                        var datosEmpleado = null;
                        for (var i = 0; i < $scope.empleados.length && Boolean(datosEmpleado === null); i++) {
                            if ($scope.empleados[i].id === parseInt(info.event._def.resourceIds[0])) {
                                datosEmpleado = $scope.empleados[i];
                            }
                        }
                        $scope.datosCita = null;
                        for (var i = 0; i < $scope.citas.length && Boolean($scope.datosCita === null); i++) {
                            if ($scope.citas[i].id === parseInt(info.event.id)) {
                                $scope.datosCita = $scope.citas[i];
                            }
                        }
                        $scope.accion = "Guardar";
                        $scope.id_cita = $scope.datosCita.id;
                        $scope.id_usuario = datosEmpleado.id;
                        $scope.nombreEmpleado = datosEmpleado.nombre;
                        $scope.fecha_inicio = moment(new Date($scope.datosCita.fecha_inicio)).format('HH:mm:ss DD-MM-YYYY');
                        $scope.fecha_fin = moment(new Date($scope.datosCita.fecha_fin)).format('HH:mm:ss DD-MM-YYYY');
                        $scope.id_estadocita = $scope.datosCita.obj_estadocita.id;
                        $scope.descripcion = $scope.datosCita.descripcion;
                        $scope.tipocita = $scope.datosCita.obj_estadocita.id;
                        abrirDialog();
                    }
                }
            });
            $scope.calendar.render();
        }

        function getpageCalendar(formato, fecha) {
            $http({
                method: 'GET',
                url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=getpage&modo=' + formato + '&fecha=' + moment(fecha).format('YYYY-MM-DD')
            }).then(function (response) {
                renderEvents(response, formato, fecha);
            }, function (response) {
                $scope.status = response.status;
                response.data.message = response.data.message || 'Request failed';
            });
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
                if ($scope.accion === "Crear") {
                    var json = {
                        id_usuario: $scope.id_usuario,
                        fecha_inicio: formatDate($scope.fecha_inicio),
                        fecha_fin: formatDate($scope.fecha_fin),
                        id_estadocita: $scope.id_estadocita,
                        descripcion: $scope.descripcion,
                        id_tipocita: $scope.tipocita
                    };
                    $http({
                        method: 'GET',
                        url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=create',
                        params: {json: JSON.stringify(json)}
                    }).then(function (response) {
                        renderEvents(response, 'resourceTimeGridDay', formatDate($scope.fecha_inicio));
                    }, function (response) {
                        $scope.status = response.status;
                        response.data.message = response.data.message || 'Request failed';
                    });
                }
                else if ($scope.accion === "Guardar") {
                    var json = {
                        id: $scope.id_cita,
                        id_usuario: $scope.id_usuario,
                        fecha_inicio: formatDate($scope.fecha_inicio),
                        fecha_fin: formatDate($scope.fecha_fin),
                        id_estadocita: $scope.id_estadocita,
                        descripcion: $scope.descripcion,
                        id_tipocita: $scope.tipocita
                    };
                    $http({
                        method: 'GET',
                        url: 'http://localhost:8081/barberia66/barberia66?ob=cita&op=update&modo=' + $scope.calendar.view.type,
                        params: {json: JSON.stringify(json)}
                    }).then(function (response) {
                        renderEvents(response, $scope.calendar.view.type, formatDate($scope.fecha_inicio));
                    }, function (response) {
                        $scope.status = response.status;
                        response.data.message = response.data.message || 'Request failed';
                    });
                }
                $mdDialog.hide();
            };
        }

        function renderEvents(response, formato, fecha) {
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
                $scope.calendar.changeView(formato, fecha);
            }
        }

        function formatDate(fecha) {
            return fecha.split(" ")[1].split("-")[2] + '-' + fecha.split(" ")[1].split("-")[1] + '-' + fecha.split(" ")[1].split("-")[0] + ' ' + fecha.split(" ")[0];
        }

    }
]);