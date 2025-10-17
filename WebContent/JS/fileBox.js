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
                            '<span class="project-title">' + (item.projectName || '') + '<br></span>' +
                            '<span class="file-client">' + (item.client || '') + '<br></span>' +
                            '<span class="small">' + (item.description || '') + '</span>' +
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
                if (jsonResponse.status === "success") {
                var list = jsonResponse.data;
                var $grid = $('#fileGrid');
                $grid.empty();
                
                var breadcrumbHtml = '';
                if(list.length > 0){
                	breadcrumbHtml =
                		'<div class="breadcrumbs">' +
                		(list[0].projectName || '') +
                		'<span class="sep"> / </span>' +
                		(list[0].taskName || '') +
                		'</div>';
                }
                
                $grid.append(breadcrumbHtml);
                
                var $fileTableDiv = $('#fileTableDiv');
                $fileTableDiv.empty();

          
                var tableHead =
                    '<table class="file-table">' +
                        '<thead>' +
                            '<tr>' +
                                '<th class="left">파일명</th>' +
                                '<th>작성자</th>' +
                                '<th>등록일</th>' +
                            '</tr>' +
                        '</thead>' +
                        '<tbody></tbody>' +
                    '</table>';

         
            $fileTableDiv.append(tableHead);

            var $tbody = $fileTableDiv.find('.file-table tbody');
            $tbody.empty();

            for (var i = 0; i < list.length; i++) {
                var file = list[i];
                var row =
                    '<tr>' +
                        '<td class="left">' + (file.fileName || '') + '</td>' +
                        '<td>' + (file.writerName || '') + '</td>' +
                        '<td>' + (file.inDate || '') + '</td>' +
                    '</tr>';
                $tbody.append(row);
            }
        } else {
            console.log("검색 실패: " + jsonResponse.message);
        }
    },
    error: function(xhr, status, error) {
        console.error("에러 발생:", error);
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

    function searchFiles(keyword) {
        $.ajax({
            url: 'controller',
            method: 'GET',
            data: {
                cmd: "searchFileBox",
                keyword: keyword
            },
            success: function(textResponse) {
            	$('.filebox-h2').text("검색내용: " + keyword);
                var jsonResponse = JSON.parse(textResponse);
                if (jsonResponse.status === "success") {
                    var list = jsonResponse.data;
                    var $grid = $('#fileGrid');
                    $grid.empty();
                    
                    var breadcrumbHtml = '';
                    if(list.length > 0){
                    	breadcrumbHtml =
                    		'<div class="breadcrumbs">' +
                    		(list[0].projectName || '') +
                    		'<span class="sep"> / </span>' +
                    		(list[0].taskName || '') +
                    		'</div>';
                    }
                    
                    $grid.append(breadcrumbHtml);
                    
                    var $fileTableDiv = $('#fileTableDiv');
                    $fileTableDiv.empty();
                    

                    var tableHead =
                            '<table class="file-table">' +
                                '<thead>' +
                                    '<tr>' +
                                        '<th class="left">파일명</th>' +
                                        '<th>작성자</th>' +
                                        '<th>등록일</th>' +
                                    '</tr>' +
                                '</thead>' +
                                '<tbody></tbody>' +
                            '</table>';

                 
                    $fileTableDiv.append(tableHead);

                    var $tbody = $fileTableDiv.find('.file-table tbody');
                    $tbody.empty();

                    for (var i = 0; i < list.length; i++) {
                        var file = list[i];
                        var row =
                            '<tr>' +
                                '<td class="left">' + (file.fileName || '') + '</td>' +
                                '<td>' + (file.writerName || '') + '</td>' +
                                '<td>' + (file.inDate || '') + '</td>' +
                            '</tr>';
                        $tbody.append(row);
                    }
                } else {
                    console.log("검색 실패: " + jsonResponse.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("에러 발생:", error);
            }
        });
    }
    
    $('.fileBox').on('click', function() {
    	$('#fileTableDiv').empty();
    	renderFileBoxList();
    });


    renderFileBoxList();
});