/* global moduleCita, FullCalendar */

'use strict';

moduleCita.controller('citaCalendarioController', ['$scope', 'toolService', '$anchorScroll',
    function ($scope, toolService, $anchorScroll) {
        $anchorScroll();
        $scope.isActive = toolService.isActive;

        var calendarEl = $('#calendar')[0];

        var fuenteEventos = {
            events: [
                {"resourceId": 2, "id": 1, "title": "event 1", "start": "2019-04-05T12:00:00+02:00", "end": "2019-04-05T14:00:00+02:00"},
                {"resourceId": 3, "id": 2, "title": "event 2", "start": "2019-04-05T09:30:00+02:00", "end": "2019-04-05T10:30:00+02:00"}
            ],
            id: 1
        };

        var calendar = new FullCalendar.Calendar(calendarEl, {
            schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
            plugins: ['interaction', 'resourceDayGrid', 'resourceTimeGrid'],
            locale: 'es',
            timeZone: 'local',
            firstDay: 1,
            defaultView: 'resourceTimeGridDay',
            datesAboveResources: true,
            editable: true,
            selectable: true,
            eventLimit: true,
            nowIndicator: true,
            unselectAuto: true,
            eventOverlap: false,
            slotEventOverlap: false,
            allDaySlot: false,
            scrollTime: '09:00',
            height: 1291,
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
            header: {
                left: 'anterior,siguiente hoy',
                center: 'title',
                right: 'dia,semana,mes'
            },
            customButtons: {
                anterior: {
                    text: '<',
                    click: function () {
                        calendar.prev();
                        console.log(calendar.getDate());
                    }
                },
                siguiente: {
                    text: '>',
                    click: function () {
                        calendar.next();
                        console.log(calendar.getDate());
                    }
                },
                hoy: {
                    text: 'Hoy',
                    click: function () {
                        var fecha = calendar.getDate().toString().split(" ", 4);
                        var hoy = new Date().toString().split(" ", 4);
                        if (fecha[0] !== hoy[0] || fecha[1] !== hoy[1] || fecha[2] !== hoy[2] || fecha[3] !== hoy[3]) {
                            calendar.today();
                            console.log(calendar.getDate());
                        }
                    }
                },
                dia: {
                    text: 'D\u00EDa',
                    click: function () {
                        if (calendar.view.type !== 'resourceTimeGridDay') {
                            calendar.changeView('resourceTimeGridDay');
                            console.log(calendar.getDate());
                        }
                    }
                },
                semana: {
                    text: 'Semana',
                    click: function () {
                        if (calendar.view.type !== 'resourceTimeGridWeek') {
                            calendar.changeView('resourceTimeGridWeek');
                            console.log(calendar.getDate());
                        }
                    }
                },
                mes: {
                    text: 'Mes',
                    click: function () {
                        if (calendar.view.type !== 'dayGridMonth') {
                            calendar.changeView('dayGridMonth');
                            console.log(calendar.getDate());
                        }
                    }
                }
            },
            resources: [
                {"id": "2", "title": "Osc.", "eventColor": "red"},
                {"id": "3", "title": "Cri.", "eventColor": "blue"}
            ],
            eventSources: [
                fuenteEventos
            ],
            dateClick: function (info) {
                if (calendar.view.type === 'dayGridMonth') {
                    alert('clicked = ' + info.dateStr);
                    calendar.changeView('resourceTimeGridDay', info.dateStr);
                }
            },
            select: function (info) {
                if (calendar.view.type !== 'dayGridMonth') {
                    /*alert('resourceId = ' + info.resource.id);
                     alert('selectedDateStartString = ' + info.startStr);
                     alert('selectedDateEndString = ' + info.endStr);
                     alert('selectedDateStart = ' + info.start);
                     alert('selectedDateEnd = ' + info.end);*/
                    var newEvent = createEvent(info, fuenteEventos.events);
                    fuenteEventos.events.push(newEvent);
                    calendar.getEventSources()[0].refetch();
                    console.log(fuenteEventos.events);
                }
            },
            eventClick: function (info) {
                if (calendar.view.type === 'dayGridMonth') {
                    /*alert('clicked = ' + info.start);
                     calendar.changeView('resourceTimeGridDay', info.start);*/
                } else {
                    /*var event = info.event;
                     alert('Id_cita = ' + event.id);
                     alert('Id_usuario = ' + event._def.resourceIds[0]);*/


                    /*var eventId = parseInt(info.event.id);
                     info.event.remove();
                     for (var i = 0; i < fuenteEventos.events.length; i++) {
                     if(eventId === fuenteEventos.events[i].id){
                     fuenteEventos.events.splice(i, 1);
                     }
                     }
                     console.log(fuenteEventos.events);*/


                    var eventId = parseInt(info.event.id);
                    for (var i = 0; i < fuenteEventos.events.length; i++) {
                        if (eventId === fuenteEventos.events[i].id) {
                            fuenteEventos.events.splice(i, 1);
                        }
                    }
                    fuenteEventos.events.push(editEvent(info));
                    calendar.getEventSources()[0].refetch();
                }
            }
        });

        function createEvent(info, events) {
            var newId = events.length + 1;
            var resourceId = parseInt(info.resource.id);
            var title = "event " + newId;
            var startDate = info.startStr;
            var endDate = info.endStr;
            var newEvent = {"resourceId": resourceId, "id": newId, "title": title, "start": startDate, "end": endDate};
            return newEvent;
        }

        function editEvent(info) {
            var eventId = parseInt(info.event.id);
            var resourceId = parseInt(info.event._def.resourceIds[0]);
            var startDate = info.event.start;
            var endDate = info.event.end;
            var editedEvent = {"resourceId": resourceId, "id": eventId, "title": "Prueba editar", "start": startDate, "end": endDate};
            return editedEvent;
        }

        calendar.render();
    }
]);