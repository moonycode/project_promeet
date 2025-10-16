$(document).ready(function() {
    var keyword;

    function renderFileBoxList() {
        $.ajax({
            url: 'controller',
            method: 'GET',
            data: {
                cmd: "fileBox"
            },
            success: function(textResponse) {
                var jsonResponse = JSON.parse(textResponse);
                var $grid = $('#fileGrid');
                $grid.empty();
                $('.filebox-h2').text("모든 프로젝트");

                if (jsonResponse.status === "success") {
                    var files = jsonResponse.data;
                    for (var i = 0; i < files.length; i++) {
                        item = files[i];
                        var card = '<a class="file-card" href="#" data-projectno="' + item.projectNo + '">' +
                            '<span class="file-name">' + (item.projectName || '') + '</span>' +
                            '<span class="file-client">' + (item.client || '') + '</span>' +
                            '<span class="file-description">' + (item.description || '') + '</span>' +
                            '</a>';
                        $grid.append(card);
                    }
                } else {
                    alert("검색 실패: " + jsonResponse.message);
                }
            }
        });
    }

    $('#fileGrid').on('click', '.file-card', function(e) {
        e.preventDefault();
        var $this = $(this);
        if ($this.data('projectno')) {
            var projectNo = $this.data('projectno');
            loadProjectFiles(projectNo);
        } else if ($this.data('taskno')) {
            var taskNo = $this.data('taskno');
            loadTaskFiles(taskNo);
        }
    });

    function loadProjectFiles(projectNo) {
        console.log('클릭된 프로젝트 번호:', projectNo);
        $.ajax({
            url: 'controller',
            method: 'GET',
            data: {
                cmd: 'projectFileBox',
                projectNo: projectNo
            },
            success: function(textResponse) {
                var jsonResponse = JSON.parse(textResponse);
                $('.filebox-h2').text("프로젝트 번호: " + projectNo + " 파일 목록");
                var $grid = $('#fileGrid');
                $grid.empty();

                if (jsonResponse.status === "success") {
                    var files = jsonResponse.data;
                    for (var i = 0; i < files.length; i++) {
                        var item = files[i];
                        var card = '<a class="file-card" data-taskno="' + item.taskNo + '">' +
                            '<span class="file-name">' + (item.taskName || '') + '</span>' +
                            '<span class="file-client">' + (item.totalFileCount || '') + '</span>' +
                            '</a>';
                        $grid.append(card);
                    }
                } else {
                    alert("검색 실패: " + jsonResponse.message);
                }
            }
        });
    }

    function loadTaskFiles(taskNo) {
        console.log('클릭된 업무 번호:', taskNo);
        $.ajax({
            url: 'controller',
            method: 'GET',
            data: {
                cmd: 'filesTaskFileBox',
                taskNo: taskNo
            },
            success: function(textResponse) {
                var jsonResponse = JSON.parse(textResponse);
                $('.filebox-h2').text("업무 번호: " + taskNo + " 파일 목록");
                var $grid = $('#fileGrid');
                $grid.empty();

                if (jsonResponse.status === "success") {
                    var taskFiles = jsonResponse.data;
                    for (var i = 0; i < taskFiles.length; i++) {
                        var item = taskFiles[i];
                        var card = '<a class="file-card">' +
                            '<span class="file-name">코멘트번호:' + (item.commentNo || '') + '</span>' +
                            '<span class="file-client">코멘트파일:' + (item.commentFile || '') + '</span>' +
                            '<span class="file-client">코멘트파일경로:' + (item.commentPath || '') + '</span>' +
                            '<span class="file-name">답글번호:' + (item.replyNo || '') + '</span>' +
                            '<span class="file-client">답글파일:' + (item.replyFile || '') + '</span>' +
                            '<span class="file-client">답글파일경로:' + (item.replyPath || '') + '</span>' +
                            '</a>';
                        $grid.append(card);
                    }
                } else {
                    alert("검색 실패: " + jsonResponse.message);
                }
            }
        });
    }

    $('.search-btn').click(function() {
        keyword = $('.search-input').val();
        searchFiles(keyword);
    });

    $('.search-input').keydown(function(e) {
        if (e.key === 'Enter') {
            var keyword = $(this).val().trim();
            if (keyword === '') {
                renderFileBoxList();
            } else {
                searchFiles(keyword);
            }
        }
    });

    $('.filebox-h2').click(function() {
        renderFileBoxList();
    });

    function searchFiles(keyword) {
        $.ajax({
            url: 'controller',
            method: 'GET',
            data: {
                cmd: "searchFileBox",
                keyword: keyword
            },
            success: function(textResponse) {
                var jsonResponse = JSON.parse(textResponse);
                if (jsonResponse.status === "success") {
                    var list = jsonResponse.data;
                    var $tbody = $('.file-table tbody');
                    $tbody.empty();
                    var $grid = $('#fileGrid');
                    $grid.empty();

                    for (var i = 0; i < list.length; i++) {
                        var file = list[i];
                        var row = '<tr>' +
                            '<td class="left">[프로젝트이름]: ' + (file.projectName || '') + '</td>' +
                            '<td class="left">[업무이름]: ' + (file.taskName || '') + '</td>' +
                            '<td class="left">[파일이름]: ' + (file.fileName || '') + '</td>' +
                            '</tr>';
                        $tbody.append(row);
                    }
                } else {
                    console.log("검색 실패: " + jsonResponse.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("에러 발생:", error);
                alert("서버 오류 발생!");
            }
        });
    }

    renderFileBoxList();
});