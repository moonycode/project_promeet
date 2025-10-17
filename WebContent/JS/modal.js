(function() {
    if (!$('#confirmModal').length) {
        var modalHTML = ''
            + '<div id="confirmModal" class="modal">'
            + '  <div class="modal-content">'
            + '    <p id="confirmMessage">정말 진행하시겠습니까?</p>'
            + '    <div class="modal-buttons">'
            + '      <button id="confirmYes">예</button>'
            + '      <button id="confirmNo">아니오</button>'
            + '    </div>'
            + '  </div>'
            + '</div>';
        $('body').append(modalHTML);
    }

    window.showConfirm = function(message, callback) {
        var $modal = $('#confirmModal');
        $('#confirmMessage').text(message);
        $modal.fadeIn(150);

        $('#confirmYes').off('click').on('click', function() {
            $modal.fadeOut(150);
            callback(true);
        });

        $('#confirmNo').off('click').on('click', function() {
            $modal.fadeOut(150);
            callback(false);
        });
    };
})();
