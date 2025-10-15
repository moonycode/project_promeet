$(document).ready(function() {
    var today = new Date();
    var todayYear = today.getFullYear();
    var todayMonth = today.getMonth() + 1;
    var todayDate = today.getDate();

    var year = todayYear;
    var month = todayMonth;
    var selectedDate = null;
    var dotList = [];
    var MAX_DOTS = 3;

    $('.sched-add-form, .add-btn').hide();

    function updateCalendar() {
        $('.year').text(year + '년');
        $('.month').text(month + '월');

        var firstDay = new Date(year, month - 1, 1).getDay();
        var lastDate = new Date(year, month, 0).getDate();
        var tbody = $('.calendar tbody').empty();

        var tr = $('<tr>');
        var dayCount = 0;

        addEmptyDays(tr, firstDay, function() { dayCount++; });

        for (var date = 1; date <= lastDate; date++) {
            var todayClass = (year === todayYear && month === todayMonth && date === todayDate) ? ' today' : '';
            var dateStr = year + '-' + ('0' + month).slice(-2) + '-' + ('0' + date).slice(-2);
            var spanDot = buildDotSpans(date);

            tr.append('<td><div class="day' + todayClass + '" data-date="' + dateStr + '">' + date + spanDot + '</div></td>');
            dayCount++;

            if (dayCount % 7 === 0) {
                tbody.append(tr);
                tr = $('<tr>');
            }
        }

        fillRemainingCells(tr, dayCount);
        tbody.append(tr);
    }

    function addEmptyDays(tr, count, cb) {
        for (var i = 0; i < count; i++) {
            tr.append('<td><div class="day empty"></div></td>');
            if (cb) cb();
        }
    }

    function fillRemainingCells(tr, dayCount) {
        if (dayCount % 7 !== 0) {
            for (var k = dayCount % 7; k < 7; k++) {
                tr.append('<td><div class="day empty"></div></td>');
            }
        }
    }

    function buildDotSpans(date) {
        var count = 0;
        var spanDot = '';

        for (var j = 0; j < dotList.length; j++) {
            var schedule = dotList[j];
            var ymd = schedule.schedule_date.split('-');
            var y = parseInt(ymd[0], 10);
            var m = parseInt(ymd[1], 10);
            var d = parseInt(ymd[2], 10);

            if (y === year && m === month && d === date) {
                count++;
                if (count <= MAX_DOTS) {
                    spanDot += '<span class="dot ' + schedule.schedule_type + '" title="' + schedule.title + '"></span>';
                }
            }
        }
        if (count > MAX_DOTS) {
            spanDot += '<span class="more">+' + (count - MAX_DOTS) + '</span>';
        }
        return spanDot;
    }

    function renderScheduleList(list, selectedDate) {
        var ul = $('.sched-ul').empty();
        var filteredList = filterListByDate(list, selectedDate);

        if (filteredList.length === 0) {
            $('<li>일정이 없습니다.</li>').appendTo(ul);
            return;
        }

        $.each(filteredList, function(i, item) {
        	var li = $('<li>')
            .data('schedule-no', item.schedule_no)
            .data('color', item.schedule_type)
            .data('date', item.schedule_date);
        	
            var dot = $('<span>').addClass('dot ' + item.schedule_type);
            var when = $('<div>').addClass('when').text(
                formatDate(item.schedule_date) + ' ' + 
                (item.start_time ? item.start_time + (item.end_time ? ' ~ ' + item.end_time : '') : '')
            );
            var what = $('<div>').addClass('what').text(item.title);
            var deleteBtn = $('<button>').addClass('sched-delete-btn').html('<i class="fas fa-times"></i>');
            li.append(dot, $('<div>').addClass('sched-line').append(when, what), deleteBtn).appendTo(ul);
        });
    }

    function filterListByDate(list, selectedDate) {
        var currentMonth = ('0' + month).slice(-2);
        var currentYear = year.toString();

        return list.filter(function(item) {
            if (selectedDate) {
                $('.add-btn').show();
                $('.panel-title').text("일정목록");
                return item.schedule_date === selectedDate;
            } else {
                $('.add-btn').hide();
                $('.panel-title').text("현재 월 전체일정목록");
                var parts = item.schedule_date.split('-');
                return parts[0] === currentYear && parts[1] === currentMonth;
            }
        });
    }

    function fetchDotList(callback) {
        $.ajax({
            url: "controller",
            method: 'GET',
            dataType: "json",
            data: { cmd: "dotSchedule" },
            success: function(response) {
                dotList = response;
                if (callback) callback();
            },
            error: function() {
                showAlert('일정 dot 객체를 가져오는데 실패했습니다.');
            }
        });
    }

    function createTimeSelect(selector, limit) {
        var $select = $(selector);
        for (var i = 0; i < limit; i++) {
            var value = ('0' + i).slice(-2);
            $select.append($('<option>', { value: value, text: value }));
        }
    }

    createTimeSelect('#startHour', 24);
    createTimeSelect('#endHour', 24);
    createTimeSelect('#startMin', 60);
    createTimeSelect('#endMin', 60);

    function formatDate(dateStr) {
        var date = new Date(dateStr);
        var day = date.getDate();
        var dayNames = ['일', '월', '화', '수', '목', '금', '토'];
        return day + '일 (' + dayNames[date.getDay()] + ')';
    }

    function showAlert(msg) {
        $('#alertMessage').text(msg);
        $('#customAlert').show();
    }

    $(document).on('click', '.calendar .day:not(.empty)', function() {
        $('.calendar .day').removeClass('selected');
        $(this).addClass('selected');
        selectedDate = $(this).data('date');
        $('.add-btn').show();
        renderScheduleList(dotList, selectedDate);
    });

    $('#prevMonth, #nextMonth').click(function() {
        selectedDate = null;
        month += ($(this).attr('id') === 'nextMonth') ? 1 : -1;
        if (month < 1) { month = 12; year--; }
        if (month > 12) { month = 1; year++; }
        updateCalendar();
        renderScheduleList(dotList);
    });

    $('.add-btn').click(function() {
        $(this).toggleClass('active');
        $('.sched-add-form').slideToggle();
    });

    $(document).on('click', '.cancel-xs', function() {
        $('.sched-add-form').slideUp();
        $('.add-btn').removeClass('active');
        $('.sched-add-form .input-s').val('');
        $('.sched-add-form input[name="color"]').prop('checked', false);
    });

    $('.save-xs').click(function() {
        var title = $('.sched-add-form .input-s').val().trim();
        var color = $('.sched-add-form input[name="color"]:checked').val();
        var startTime = $('#startHour').val() + ':' + $('#startMin').val();
        var endTime = $('#endHour').val() + ':' + $('#endMin').val();

        if (!title || !color || !selectedDate) {
            return showAlert('일정명과 색상을 선택해주세요.');
        }

        $.ajax({
            url: 'controller',
            type: 'POST',
            dataType: 'json',
            data: {
                cmd: "addSchedule",
                scheduleDate: selectedDate,
                scheduleType: color,
                title: title,
                startTime: startTime,
                endTime: endTime
            },
            success: function(response) {
                if (response) {
                    fetchDotList(function() {
                        updateCalendar();
                        renderScheduleList(dotList, selectedDate);
                    });
                    $('.add-btn').removeClass('active');
                    $('.sched-add-form').hide();
                } else {
                    showAlert('일정 등록에 실패했습니다.');
                }
            },
            error: function() {
                showAlert('서버 통신 오류가 발생했습니다.');
            }
        });
    });

    $('.sched-ul').on('click', '.sched-delete-btn', function() {
        var li = $(this).closest('li');
        var scheduleNo = li.data('schedule-no');

        $.ajax({
            url: 'controller',
            type: 'POST',
            data: { cmd: 'deleteSchedule', scheduleNo: scheduleNo },
            success: function(response) {
                if (response) {
                    li.remove();
                    fetchDotList(function() {
                        updateCalendar();
                        renderScheduleList(dotList, selectedDate);
                    });
                } else {
                    showAlert('삭제 실패했습니다.');
                }
            },
            error: function() {
                showAlert('서버 통신 오류');
            }
        });
    });
    
    $('.sched-ul').on('click', 'li', function() {
        var li = $(this);
        var selectedDate = $(this).data('date');
        
        console.log("선택된 날짜:", selectedDate);
        
        $('.sched-edit-form').each(function() {
            var prevLi = $(this).prev('li');
            prevLi.css('opacity', '1');
            $(this).remove();
        });
        
        var scheduleNo = li.data('schedule-no');
        var liText = li.text().trim();
     var timeMatch = liText.match(/(\d{1,2}:\d{2})\s*~\s*(\d{1,2}:\d{2})/);
     var startHour = 0, startMin = 0, endHour = 0, endMin = 0;

     if(timeMatch) {
         var start = timeMatch[1].split(':');
         var end = timeMatch[2].split(':');

         startHour = parseInt(start[0], 10);
         startMin = parseInt(start[1], 10);
         endHour = parseInt(end[0], 10);
         endMin = parseInt(end[1], 10);
     }

     var title = liText.replace(/.*(\d{1,2}:\d{2}\s*~\s*\d{1,2}:\d{2})\s*/, '');
     var color = li.data('color');
     
        $('.sched-edit-form').remove();

        var h,m,selected;
        var startHourOptions = '';
        for (h = 0; h <= 23; h++) {
            selected = (h === startHour) ? ' selected' : '';
            startHourOptions += '<option value="' + h + '"' + selected + '>' + (h < 10 ? '0'+h : h) + '</option>';
        }

        var startMinOptions = '';
        for (m = 0; m <= 59; m++) {
            selected = (m === startMin) ? ' selected' : '';
            startMinOptions += '<option value="' + m + '"' + selected + '>' + (m < 10 ? '0'+m : m) + '</option>';
        }
        
        var endHourOptions = '';
        for (h = 0; h <= 23; h++) {
            selected = (h === endHour) ? ' selected' : '';
            endHourOptions += '<option value="' + h + '"' + selected + '>' + (h < 10 ? '0'+h : h) + '</option>';
        }

        var endMinOptions = '';
        for (m = 0; m <= 59; m++) {
            selected = (m === endMin) ? ' selected' : '';
            endMinOptions += '<option value="' + m + '"' + selected + '>' + (m < 10 ? '0'+m : m) + '</option>';
        }
        
        

        var formHtml = '<div class="sched-edit-form">' +
        '<input type="text" class="input-s" placeholder="일정명 입력" value="' + title + '" />' +
        '<div class="dot-picker">' +
        '<label><input type="radio" name="color" value="red"' + (color==='red'?' checked':'') + '><span class="dot red"></span></label>' +
        '<label><input type="radio" name="color" value="orange"' + (color==='orange'?' checked':'') + '><span class="dot orange"></span></label>' +
        '<label><input type="radio" name="color" value="yellow"' + (color==='yellow'?' checked':'') + '><span class="dot yellow"></span></label>' +
        '<label><input type="radio" name="color" value="green"' + (color==='green'?' checked':'') + '><span class="dot green"></span></label>' +
        '<label><input type="radio" name="color" value="blue"' + (color==='blue'?' checked':'') + '><span class="dot blue"></span></label>' +
        '<label><input type="radio" name="color" value="navy"' + (color==='navy'?' checked':'') + '><span class="dot navy"></span></label>' +
        '<label><input type="radio" name="color" value="purple"' + (color==='purple'?' checked':'') + '><span class="dot purple"></span></label>' +
        '<label><input type="radio" name="color" value="black"' + (color==='black'?' checked':'') + '><span class="dot black"></span></label>' +
        '<label><input type="radio" name="color" value="white"' + (color==='white'?' checked':'') + '><span class="dot white"></span></label>' +
        '</div>' +
        '<div class="time-picker">' +
        '<select class="sched-edit-startHour">' + startHourOptions + '</select> :' +
        '<select class="sched-edit-startMin">' + startMinOptions + '</select> ~ ' +
        '<select class="sched-edit-endHour">' + endHourOptions + '</select> :' +
        '<select class="sched-edit-endMin">' + endMinOptions + '</select>' +
        '</div>' +
        '<div class="actions">' +
        '<button class="save-xs">저장</button> ' +
        '<button class="cancel-xs">취소</button>' +
        '</div>' +
        '</div>';


        li.after(formHtml);
        li.css('opacity', '0.5');
    });



    
    $('.sched-ul').on('click', '.sched-edit-form .cancel-xs', function() {
        var form = $(this).closest('.sched-edit-form');
        var li = form.prev('li');
        li.css('opacity', '1');
        form.remove();
    });

    $('.sched-ul').on('click', '.sched-edit-form .save-xs', function() {
        var form = $(this).closest('.sched-edit-form');
        var li = form.prev('li');
        var scheduleNo = li.data('schedule-no');
        var title = $('.sched-edit-form .input-s').val().trim();
        var color = $('.sched-edit-form input[name="color"]:checked').val();
        var startHour = parseInt(form.find('.sched-edit-startHour').val(), 10);
        var startMin  = parseInt(form.find('.sched-edit-startMin').val(), 10);
        var endHour   = parseInt(form.find('.sched-edit-endHour').val(), 10);
        var endMin    = parseInt(form.find('.sched-edit-endMin').val(), 10);

        var startTime = (startHour < 10 ? '0' + startHour : startHour) + ':' +
        (startMin < 10 ? '0' + startMin : startMin);

        var endTime = (endHour < 10 ? '0' + endHour : endHour) + ':' +
        (endMin < 10 ? '0' + endMin : endMin);
        
        var selectedDate = li.data('date');
        
        if (!title || !color || !selectedDate) {
            return showAlert('일정명과 색상을 선택해주세요.');
        }
        
        $.ajax({
            url: 'controller',
            method: 'POST',
            data: {
            	cmd : "updateSchedule",
                scheduleNo: scheduleNo,
                scheduleDate: selectedDate,
                scheduleType: color,
                title: title,
                startTime: startTime,
                endTime: endTime
            },
            success: function() {
                fetchDotList(function() {
                    updateCalendar();
                    renderScheduleList(dotList);
                });
                form.remove();
            }
        });
    });


    fetchDotList(function() {
        updateCalendar();
        renderScheduleList(dotList);
    });
});
