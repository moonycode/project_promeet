$(document).ready(function() {
	$.ajax({
		url: 'controller',
		type: 'GET',
		dataType: 'json',
		data: { cmd : "fileBoxProject" },
		success: function(projects) {
			const $grid = $('#fileGrid');
			$grid.empty();
			$.each(projects, function(i, p) {
            const $card = $('<div>', { 'class': 'file-card', 'data-projectno': p.project_no });
            $card.append($('<span>', { 'class': 'file-name', text: p.project_name }));
            $card.append($('<span>', { 'class': 'file-count', text: p.totalFileCount + ' Files' }));
            $grid.append($card);
			});
		}
	})
});