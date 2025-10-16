$(document).ready(function() {
	var keyword;
	function renderFileBoxList() {
        $.ajax({
            url: 'controller',
            method: 'GET',
			dataType: 'json',
            data: {
            	cmd : "fileBox"
			},
		success: function(projects) {
			var $grid = $('#fileGrid');
			$grid.empty();
			$('.filebox-h2').text("모든 프로젝트");
			for (var i=0; i < projects.length; i++) {
				var item = projects[i];
				
var card = '<a class="file-card" href="/project/detail?projectNo=' + item.projectNo + '">' +
    '<span class="file-name">' + item.projectName + '</span>' +
    '<span class="file-client">' + item.client + '</span>' +
    '<span class="file-description">' + item.description + '</span>' +
'</a>';
$grid.append(card);

			}
		}
		});
	}
	

	
	$('.search-btn').click(function() {
		keyword = $('.search-input').val();
		searchFiles(keyword);
		
	});
	
	$('.search-input').keydown(function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			keyword = $(this).val();
			searchFiles(keyword);
		}
	});
	
	function searchFiles(keyword) {
		console.log("searFiles테스트용",keyword);
	    $.ajax({
	        url: 'controller',
	        method: 'GET',
	        data: {
	            cmd: "searchFileBox",
	            keyword: keyword
	        },
	        success: function(testResponse) {
	        	console.log("서버 응답:", testResponse);
	        	var jsonResponse = JSON.parse(testResponse);
	        	   if (jsonResponse.status === "success") {
	                   var list = jsonResponse.data;
	                   var $tbody = $('.file-table tbody');
	                   $tbody.empty();
	        	
	            
	            var $grid = $('#fileGrid');
	            $grid.empty();
	            var $tbody = $('.file-table tbody');
	            $tbody.empty(); 

	            list.forEach(function(file) {
	                var row = '<tr>' +
	                            '<td class="left">' + "[프로젝트이름]: " + (file.projectName || '') + '</td>' +
	                            '<td class="left">' + "[업무이름]: " + (file.taskName || '') + '</td>' +
	                            '<td class="left">' + "[파일이름]: " + (file.fileName || '') + '</td>' +
	                          '</tr>';
	                $tbody.append(row);
	            });
	        	   } else {
	        		   console.log("검색 실패: " + response.message);
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