$(document).ready(function(){
	const taskNo = 3;
	const employeeId = window.employeeId;
	let isEditMode = false;


	function getChecklist() {
		$.ajax({
			url: "controller",
			data: { cmd: "checkList", taskNo  },
			success: function(res) {
				const list = JSON.parse(res);
				loadChecklist(list.data);  
			}
		});
	}

	function loadChecklist(list) {
		const $todo = $("#todoList");
		$todo.empty(); 
		const activeList = list.filter(function(item) {
			return !item.delDate;
		});
		activeList.forEach(function(item) {
			const checked = item.completeFlag ? "checked" : "";
			const disabled = isEditMode ? "" : "disabled";
			$todo.append(`
					<div class="todo-line" data-id="${item.checkListNo}">
			<input type="checkbox" class="chk" ${checked} ${disabled}/>
			<input type="text" class="todo-input" value="${item.contents}" ${disabled}/>
			${isEditMode ? '<button class="del-ico">×</button>' : ''}
			</div>
			`);
		});
	}

	function saveChecklist(){
		$(".todo-line").each(function(){
			const checkListNo = $(this).data("id");
			const contents = $(this).find(".todo-input").val().trim();
			const completeFlag = $(this).find(".chk").is(":checked") ? 1 : 0;
			if(!contents) return;
			const type = checkListNo
			? { cmd: "updateCheckList", checkListNo, contents, completeFlag }
			: { cmd: "addCheckList", taskNo, contents };
			$.ajax({  
				url: "controller",
				data: type,
				success: function() {
					getChecklist();
				}
			});
		}); 
	}

	function deleteChecklist(checkListNo){
		$.ajax({
			url: "controller",
			data: { cmd: "deleteCheckList", checkListNo },
			success: function(){
				getChecklist();
			}
		});
	}

	$("#btnToggleEdit").on("click", function(){
		if(!isEditMode){
			$(this).text("저장");
			$("#btnAddLine").show();
			$("#btnCancel").show();
			$(".todo-card").removeClass("view");
			getChecklist();
		}else{
			$("#btnToggleEdit").text("수정");
			$("#btnAddLine").hide();
			$("#btnCancel").hide();
			$(".todo-card").addClass("view");
			$(".todo-line[data-delete='true']").each(function(){
				const checkListNo = $(this).data("id");
				if(checkListNo) deleteChecklist(checkListNo);
			});
			saveChecklist();
		}
		isEditMode = !isEditMode;

	});

	$("#btnCancel").on("click", function(){
		$("#btnToggleEdit").text("수정");
		$("#btnAddLine").hide();
		$("#btnCancel").hide();
		$(".todo-card").addClass("view");
		isEditMode = !isEditMode;
		getChecklist();
	});

	$("#btnAddLine").on("click", function(){
		$("#todoList").append(`
				<div class="todo-line new-item">
		<input type="checkbox" class="chk" disabled />
		<input type="text" class="todo-input" placeholder="새 항목 입력..." />
			<button class="del-ico">×</button>
		</div>
		`); 
	});

	$(document).on("click", ".del-ico", function(){
		if(!confirm("정말 삭제하시겠습니까?")) return;
		const $line = $(this).closest(".todo-line");
		const checkListNo = $line.data("id");
		if(!checkListNo){
			$line.remove();
			return;
		}
		$line.attr("data-delete", "true");
		$line.hide();
	});

	function getComments(){
		$.ajax({
			url : "controller",
			data : {cmd : "commentReply", taskNo},
			success : function(res){
				const list = JSON.parse(res);
				loadComments(list.data)
			}
		});
	}

	function loadComments(list) {
		  const $commentList = $("#commentList");
		  $commentList.find("article.cmt4").remove();
		  const grouped = {};
		  list.forEach(function(item) {
		    if (!grouped[item.commentNo]) grouped[item.commentNo] = { comment: item, replies: [] };
		    if (item.replyNo) grouped[item.commentNo].replies.push(item);
		  });
		  Object.values(grouped).forEach(function({ comment, replies }) {
		    const cDel = comment.commentDeldate;
		    const cClass = cDel ? "cmt4 is-deleted" : "cmt4";
		    const cText = comment.commentContents;
		    const isMine = (comment.commentWriterId == employeeId);
		    let html = `
		      <article class="${cClass}">
		        <div class="line1">
		          <div class="name">${comment.commentWriter}</div>
		        </div>
		        <div class="line2">${cDel ? "" : cText}</div>
		        <div class="line3">
		          <div class="meta-row">
		            <time>${comment.commentIndate}</time>`;
		    if (!cDel && isMine) {
		      html += `
		            <div class="meta-actions">
		              <a href="#" class="btnEdit" data-id="${comment.commentNo}" data-type="comment">수정</a>
		              <a href="#" class="btnDel" data-id="${comment.commentNo}" data-type="comment">삭제</a>
		            </div>`;
		    }
		    html += `
		          </div>`;
		    if (comment.commentFilename && !cDel) {
		      html += `<div class="filename">${comment.commentFilename}</div>`;
		    }
		    html += `
		        </div>
		        <div class="line4">
		          ${!cDel ? `<button class="btn-reply" data-id="${comment.commentNo}">답글</button>` : ""}
		        </div>
		        <div class="reply-stack">`;
		    replies.forEach(function(r) {
		      const rDel = r.repliesDeldate;
		      const rText = rDel ? "삭제된 답글입니다." : (r.repliesContents || "");
		      const rClass = rDel ? "r3 is-deleted" : "r3";
		      const isMyReply = (r.replyWriterId == employeeId);
		      html += `
		          <div class="${rClass}">
		            <div class="rline1">
		              <span class="arrow">ㄴ</span>
		              <span class="name">${r.replyWriter}</span>
		            </div>
		            <div class="rline2">${rText}</div>
		            <div class="rline3">
		              <div class="meta-row">
		                <time>${r.repliesIndate}</time>`;
		      if (!rDel && isMyReply) {
		        html += `
		                <div class="meta-actions">
		                  <a href="#" class="btnEdit" data-id="${r.replyNo}" data-type="reply">수정</a>
		                  <a href="#" class="btnDel" data-id="${r.replyNo}" data-type="reply">삭제</a>
		                </div>`;
		      }
		      html += `
		              </div>`;
		      if (r.repliesFilename && !rDel) {
		        html += `<div class="filename">${r.repliesFilename}</div>`;
		      }
		      html += `
		            </div>
		          </div>`;
		    });
		    html += `
		        </div>
		      </article>`;
		    $commentList.prepend(html);
		  });
		}


	function addComment(contents,fileName){
		$.ajax({
			url: "controller",
			data: { cmd: "addComment", taskNo, employeeId, contents, fileName  },
			success: function(){
				$("#newComment").val("");
				$("#attachedFileName").empty();
				getComments();
			}
		});
	}

	$("#btnAddComment").on("click", function(){
		const contents = $("#newComment").val().trim();
		if(!contents) return alert("내용을 입력하세요");
		const fileNameText = $("#attachedFileName").text().trim();
		const fileName = fileNameText.replace("첨부 파일:", "").replace("✕", "").trim() || null;
		addComment(contents,fileName);
	});

	function deleteComment(commentNo){
		$.ajax({
			url: "controller",
			data: { cmd : "deleteComment", commentNo },
			success: function(){
				getComments();
			} 
		});
	}

	function deleteReply(replyNo){
		$.ajax({
			url: "controller",
			data: { cmd : "deleteReply", replyNo  },
			success: function(){
				getComments();
			} 
		});
	}

	$(document).on("click", ".btnDel", function(){
		if(!confirm("정말 삭제하시겠습니까?")) return;
		const id = $(this).data("id");
		const type = $(this).data("type");
		if(type == "comment"){deleteComment(id);}
		if(type == "reply"){deleteReply(id);}

	});

	$(document).on("click", ".btnEdit", function(e){
		e.preventDefault();
		const id = $(this).data("id");
		const type = $(this).data("type");
		const $article = $(this).closest(type === "reply" ? ".r3" : ".cmt4");
		const $content = type === "reply" ? $article.find(".rline2") : $article.find(".line2");
		const originalText = $content.text();
		$(".meta-actions").hide();
		if($article.find("textarea").length) return;
		$content.html(`
				<textarea class="editArea">${originalText}</textarea>
				<button class="btn-small btn-pink btnSaveEdit" data-id="${id}" data-type="${type}">등록</button>
				<button class="btn-small btn-gray btnCancelEdit">취소</button>
				`);
	});

	$(document).on("click", ".btnCancelEdit", function(){
		$(".meta-actions").show();
		const $area = $(this).closest(".cmt4, .r3");
		const $textArea = $area.find("textarea");
		const prevText = $textArea.val();
		$area.find(".line2, .rline2").text(prevText);
	});

	function updateComment(commentNo, contents){
		$.ajax({
			url: "controller",
			data: { cmd : "updateComment", commentNo, contents },
			success: function(){
				getComments();
			}
		});
	}

	function updateReply(replyNo, contents){
		$.ajax({
			url: "controller",
			data: { cmd : "updateReply", replyNo, contents },
			success: function(){
				getComments();
			}
		});
	}

	$(document).on("click", ".btnSaveEdit", function(){
		const $area = $(this).closest(".cmt4, .r3");
		const contents = $area.find("textarea").val().trim();
		if(!contents) return alert("내용을 입력하세요");
		const id = $(this).data("id");
		const type = $(this).data("type");
		if(type == "comment"){updateComment(id, contents);}
		if(type == "reply"){updateReply(id, contents);}
		$(".meta-actions").show();
	});

	$(document).on("click", ".btn-reply", function() {
		const commentNo = $(this).data("id");
		const $article = $(this).closest(".cmt4");
		if ($article.find(".reply-editor").length > 0) return;
		const replyEditor = `
		<div class="reply-editor inline">
		<input type="text" class="reply-input" placeholder="답글을 입력하세요" />
			<button class="btn-small btn-pink btnSaveReply" data-id="${commentNo}">등록</button>
		<button class="btn-small btn-gray btnCancelReply">취소</button>
		</div>
		`;
		$article.find(".reply-stack").first().append(replyEditor);
	});


	function addReply(commentNo,contents){
		$.ajax({
			url: "controller",
			data: { cmd: "addReply",taskNo, commentNo, employeeId, contents },

			success: function() {
				getComments(); 
			}
		});
	}
	$(document).on("click", ".btnSaveReply", function() {
		const contents = $(this).siblings(".reply-input").val().trim();
		if (!contents) return alert("내용을 입력하세요");
		const commentNo = $(this).data("id");
		addReply(commentNo, contents);
		$(".reply-editor").remove(); 
	});

	$(document).on("click", ".btnCancelReply", function() {
		$(this).closest(".reply-editor").remove();
	});


	$(document).on("click", ".remove-file", function(){
		$("#attachedFileName").empty();
	});

	$(function(){
		const dialog = document.getElementById("fileDialog");
		$("#btnAddFile").on("click", function(){
			$("#fileNameInput").val("");
			dialog.showModal();
		});
		dialog.addEventListener("close", function(){
			if(dialog.returnValue === "confirm"){
				const fileName = $("#fileNameInput").val().trim();
				if(!fileName) return alert("파일 이름을 입력하세요");
				$("#attachedFileName").html(
						`첨부 파일: ${fileName} <span class="remove-file">✕</span>`
				);
			}
		});
	});

	getChecklist();
	getComments();
});