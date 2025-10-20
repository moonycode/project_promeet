$(document).ready(function() {
    const $msgArea = $('#message-area');
    const $currentPass = $('#current-password');
    const $newPass = $('#new-password');
    const $confirmPass = $('#confirm-password');

    function clearMessage() {
        $msgArea.removeClass('msg-success msg-error').text('');
    }

    function displayMessage(msg, isSuccess) {
    	console.log("displayMessage 실행:", msg, isSuccess);
        clearMessage();
        $msgArea.addClass(isSuccess ? 'msg-success' : 'msg-error').text(msg);
    }
    
    function clearInputs() {
        $currentPass.val('');
        $newPass.val('');
        $confirmPass.val('');
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
            success: function(responseText) {
            	console.log("서버 응답:", responseText);
            	
            	let jsonResponse;
                try {
                    jsonResponse = JSON.parse(responseText);
                } catch (e) {
                    console.error("JSON 파싱 오류:", e);
                    displayMessage('서버 응답 형식이 올바르지 않습니다.', false);
                    return;
                }
            	
            	const isSuccess = jsonResponse.status === 'success';
            	const message = jsonResponse.message || (isSuccess ? '비밀번호를 변경했습니다.' : '비밀번호 변경 실패');
            	displayMessage(message, isSuccess);
            	
            	if(isSuccess){
            		clearInputs();
            	}
            },
            error: function(xhr, status, error) {
                console.error("Error:", status, error, xhr.responseText);
                displayMessage('비밀번호 변경 중 오류가 발생했습니다.', false);
            }
        });
    });
});