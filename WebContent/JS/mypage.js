$(document).ready(function() {
    const $msgArea = $('#message-area');
    const $currentPass = $('#current-password');
    const $newPass = $('#new-password');
    const $confirmPass = $('#confirm-password');

    function clearMessage() {
        $msgArea.removeClass('msg-success msg-error').text('');
    }

    function displayMessage(msg, isSuccess) {
        clearMessage();
        $msgArea.addClass(isSuccess ? 'msg-success' : 'msg-error').text(msg);
    }
    
    function clearInputs() {
        $currentPass.val('');
        $newPass.val('');
        $confirmPass.val('');
        clearMessage();
    }

    $('#cancel-btn').on('click', function() {
    	location.href = "controller?cmd=projectUI";
    });// clear: clearInputs();

    
    $('#change-password-btn').on('click', function() {
        clearMessage();
        
        const currentPassword = $currentPass.val().trim();
        const newPassword = $newPass.val().trim();
        const confirmPassword = $confirmPass.val().trim();

        if (currentPassword === '' || newPassword === '' || confirmPassword === '') {
            displayMessage('현재/새 비밀번호를 모두 입력해주세요.', false);
            return;
        }
        if (newPassword !== confirmPassword) {
            displayMessage('새 비밀번호와 확인 비밀번호가 일치하지 않습니다.', false);
            return;
        }
        if (currentPassword === newPassword) {
            displayMessage('현재 비밀번호와 새 비밀번호가 같습니다. 다른 비밀번호를 설정해주세요.', false);
            return;
        }
        
        $.ajax({
        	url: 'controller',
        	method: 'POST',
            data: {
                cmd: 'changePassword',
                currentPassword: currentPassword,
                newPassword: newPassword
            },
            success: function(responseString) {
            	console.log("서버 응답:", responseString);
            	var jsonResponse = JSON.parse(responseString);
            	const isSuccess = jsonResponse.status === 'success';
        		displayMessage(jsonResponse.message, isSuccess)
            	if(isSuccess){
            		clearInputs();
            	}
            },
            error: function(xhr, status, error) {
                console.error("Error:", status, error, xhr.responseText);
                displayMessage('비밀번호 변경을 실패했습니다.', false);
            }
        });
    });
});