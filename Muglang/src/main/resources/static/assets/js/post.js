
$(function() {
	$('.fileBtns').hide();

	//ajax로 이벤트 함수를 다시 빌드하는 객체를 따로 정의
	$.update_post = function() {
		console.log("새로 등록한 게시글 입력 확인 이벤트");
		$($('.uploadFileSpace')[0]).hide();
		$($('.changedFileSpace')[0]).hide();
		$("#upTitle" + $(this).val()).hide();
		$("#contentIn" + $(this).val()).hide();
		$("#fileRequest" + $(this).val()).hide();
		$($(".fileBtns")[0]).hide();
		$($(".updateBtn")[0]).click(function(e) {
			const postId = Number($(this).val());
			//const postId = e.target.postId;
			console.log("회원의 수정버튼 이벤트 함수 적용 확인.");
			flagList[0] = !flagList[0];
			if (flagList[0]) {
				$("<button type='button' id='updateButton" + $(this).val() + "' class='btn' style='float:right'>").appendTo($("#modify_content" + postId));
				$("#updateButton" + $(this).val()).text("완료");
			} else {
				$("#updateButton" + $(this).val()).remove();
			}

			$("#postContent" + $(this).val()).text();

			if (!flagList[0]) {
				$("#postContent" + $(this).val()).show();
				$("#contentIn" + $(this).val()).hide();
				$("#fileRequest" + $(this).val()).hide();
			} else {
				$("#postContent" + $(this).val()).hide();
				$("#contentIn" + $(this).val()).show();
				$("#fileRequest" + $(this).val()).show();
			}

			console.log("버튼 이벤트 html단 활성화");

			$("#fileRequest" + $(this).val()).click(function(e) {
				console.log("파일 요청 조작 활성화" + $(this).val());
				$("#updateBtnAtt" + $(this).val()).click();
			});

			$("#fileRemove" + $(this).val()).click(function(e) {
				console.log("파일 삭제 요청 활성화");
			});

			$("#updateButton" + postIdList[0]).click(function(e) {
				$($('.data')[0]).children('#postContentIn').val($("#contentIn" + postIdList[0]).val());
				console.log("update될 내용 : " + $("#contentIn" + postIdList[0]).val());
				fnUpdatePost(postIdList[0], 0);
			});
			$("#deleteButton" + postIdList[0]).click(function(e) {
				console.log("delete");
				$($('.data')[0]).submit();
			});

			//글 내용 수정하는 키입력을 받음.
			$("#contentIn" + postIdList[0]).keyup(function(e) {
				$("#postContent" + postIdList[0]).text($(this).val());
				console.log($(this).val());
				fnChangeContent(this, postId);
			});
		});
	}
	
	$(".uploadFileSpace").each(function(i, e) {
		$(this).hide();
	});
	$("div[class='changedFileSpace']").each(function(i, e) {
		$(this).hide();
	});

	//게시글 수정과 삭제를 활성화하는 버튼을 생성함. 게시글이 자기꺼인 것만 표시함.
	$(".updateBtn").each(function(i, e) {
		console.log("게시글의 수정버튼 이벤트 생성");
		//$($('.uploadFileSpace')[i]).hide();
		//$($("div[class='changedFileSpace']")[i]).hide();
		$("#upTitle" + $(this).val()).hide();
		$("#contentIn" + $(this).val()).hide();
		$("#fileRequest" + $(this).val()).hide();
		$(this).on('click', function() {
			console.log("초기 화면 수정 버튼 활성화.");
			const postId = $(this).val();
			flagList[i] = !flagList[i];
			if (flagList[i]) {
				$("#postAttZone" + postId).show();
				$("<button type='button' id='updateButton" + $(this).val() + "' class='btn' style='float:right'>").appendTo($("#modify_content" + postId));
				$("#updateButton" + $(this).val()).text("완료");
				$($(".fileBtns")[i]).show();

			} else {
				$(this).text("게시글 수정");
				$("#postAttZone" + postId).hide();
				$("#updateButton" + $(this).val()).remove();
				$($(".fileBtns")[i]).hide();

			}

			$("#postContent" + $(this).val()).text();

			if (!flagList[i]) {
				$("#postContent" + $(this).val()).show();
				$("#contentIn" + $(this).val()).hide();
				$("#fileRequest" + $(this).val()).hide();
				$("#upTitle" + $(this).val()).hide();

			} else {
				$("#postContent" + $(this).val()).hide();
				$("#contentIn" + $(this).val()).show();
				$("#fileRequest" + $(this).val()).show();
				$("#upTitle" + $(this).val()).show();
			}
			console.log("버튼 이벤트 html단 활성화");

			console.log("버튼 파일 조작 화면단 활성화" + postIdList[0]);
			//$.updateBtnAtt(postIdList[i], i);
			//내 게시글 파일 관리 버튼
			$("#fileRequest" + postId).click(function(e) {
				console.log("파일 요청 조작 활성화" + $(this).val());
				$("#updateBtnAtt" + postId).click();
			});

			$("#fileRemove" + $(this).val()).click(function(e) {
				console.log("파일 삭제 요청 활성화");
			});

			//내 게시물 수정, 삭제, 돌아가기 결정 버튼
			$("#updateButton" + postId).click(function(e) {
				//console.log("update될 내용 : " + $("#contentIn" + postIdList[i]).val());\
				fnUpdatePost(postId, i);
			});

			//글 내용 수정하는 키입력을 받음.
			$("#contentIn" + postId).keyup(function(e) {
				$("#postContent" + postId).text($(this).val());
				console.log($(this).val());
				fnChangeContent(this, postId);
			});

		});

	});
	//포스트 삭제
	$(".post_deleteButton").click(function(e) {
		console.log("삭제버튼 클릭")
		$("#delete_form" + e.target.value).submit();
	});

	$.followingEvent = function(targetIndex, postId) {
		console.log("현재 인덱스 : " + targetIndex + ", 현재 게시글의 아이디 : " + postId);
		$("#updateForm" + postId).hide();
		$("#upTitle" + postId).hide();
		$("#contentIn" + postId).hide();
		$("#fileRequest" + $(this).val()).hide();
		$("#fileRequest" + postId).hide();
	}

	//스크롤 확장시 다시 이벤트를 발생시킬 스크립트를 다시 로드함.
	$.updateBtn = function(startIndex, size) {
		for (let i = startIndex; i < startIndex + size; i++) {
			console.log("this확인- 업데이트 관련 이벤트 적용중");
			flagList[i] = false;
			$($('.uploadFileSpace')[i]).hide();
			$($('.changedFileSpace')[i]).hide();
			$("#upTitle" + $(this).val()).hide();
			$("#contentIn" + $(this).val()).hide();
			$("#fileRequest" + $(this).val()).hide();
			$($(".fileBtns")[i]).hide();
			$($(".updateBtn")[i]).on('click', function(e) {
				console.log("초기 화면 수정 버튼 활성화.");
				const postId = Number($(this).val());
				flagList[i] = !flagList[i];
				if (flagList[i]) {
					$("<button type='button' id='updateButton" + $(this).val() + "' class='btn' style='float:right'>").appendTo($("#modify_content" + postId));
					$("#updateButton" + $(this).val()).text("완료");
				} else {
					$("#updateButton" + $(this).val()).remove();
				}
				$("#postContent" + $(this).val()).text();

				if (!flagList[i]) {
					$("#postContent" + $(this).val()).show();
					$("#contentIn" + $(this).val()).hide();
					$("#fileRequest" + $(this).val()).hide();
					$("#upTitle" + $(this).val()).hide();

				} else {
					$("#postContent" + $(this).val()).hide();
					$("#contentIn" + $(this).val()).show();
					$("#fileRequest" + $(this).val()).show();
					$("#upTitle" + $(this).val()).show();
				}
				console.log("버튼 이벤트 html단 활성화");

				console.log("버튼 파일 조작 화면단 활성화" + postIdList[0]);
				//$.updateBtnAtt(postIdList[i], i);
				//내 게시글 파일 관리 버튼
				$("#fileRequest" + postId).click(function(e) {
					console.log("파일 요청 조작 활성화" + $(this).val());
					$("#updateBtnAtt" + postId).click();
				});

				$("#fileRemove" + $(this).val()).click(function(e) {
					console.log("파일 삭제 요청 활성화");
				});

				//내 게시물 수정, 삭제, 돌아가기 결정 버튼
				$("#updateButton" + postId).click(function(e) {
					//console.log("update될 내용 : " + $("#contentIn" + postIdList[i]).val());\
					fnUpdatePost(postId, i);
				});

				//글 내용 수정하는 키입력을 받음.
				$("#contentIn" + postId).keyup(function(e) {
					$("#postContent" + postId).text($(this).val());
					console.log($(this).val());
					fnChangeContent(this, postId);
				});

			});

		}
		//포스트 삭제
		$(".post_deleteButton").click(function(e) {
			console.log("삭제버튼 클릭")
			$("#delete_form" + e.target.value).submit();
		});

	}

});

function inputTitle(title) {
	$('#inputRestNm').val(title)
}

//수정모드에서  내용 변경시 실행될 메소드
function fnChangeContent(input, postId) {
	console.log("내용 변경 이벤트 발생");
	//글을 변경하기위해 텍스트박스를 수정할 경우 해당 텍스트박스의 내용을 숨겨진 태그들에 뿌려줌.
	//$("#postContent").text($(input).text() + "");
	$("#postContentIn" + postId).val($(input).val());
	//console.log($("#post").val($(input).val()));
	$("#postContent" + postId).val()
}


//수정작업을 진행한후 이미지 갱신을 위한 태그 생성. 수정작업때만 사용되는 html단 텍스트 이므로 로그인 여부를 사용하지 않아도됨.
function imageTag(item, fileLength) {
	let tag = "";
	for (let i = 0; i < fileLength; i++) {
		tag += `<div class="fileList${item.getPost.postId}" value="${item.updateFileList[i].postFileId}">`;
		tag += `<div style="position: relative;">`;
		tag += `<input type="hidden" id="postFileId${item.updateFileList[i].postFileId}" 
					class="postFileId${item.updateFileList[i].postId}" name="postFileId" value="${item.updateFileList[i].postFileId}">`;
		tag += `<input type="hidden" id="postFileNm${item.updateFileList[i].postFileId}" 
					class="postFileNm" name="postFileNm" value="${item.updateFileList[i].postFileNm}">`;
		tag += `<input type="hidden" id="postId${item.updateFileList[i].postFileId}" 
					class="postId${item.getPost.postId}" name="postId" value="${item.updateFileList[i].postId}">`;
		tag += `<input type="file" id="changedFile${item.updateFileList[i].postFileId}" name="changedFile${item.updateFileList[i].postFileId}" style="display: none;" 
					onchange="fnGetChangedFileInfo(${item.updateFileList[i].postFileId}, ${i}, event)">`;
		if (item.updateFileList[i].postFileCate == 'img') {
			tag += `<img id="img${item.updateFileList[i].postFileId}" 
				src="/upload/${item.updateFileList[i].postFileNm}" 
		 		style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
				class="fileImg" 
				onclick="fnImgChange(${item.updateFileList[i].postFileId})">`;
		} else {
			tag += `<img id="img${item.updateFileList[i].postFileId}" 
				src="/assets/img/defaultFileImg.png" 
				style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
				class="fileImg" 
			 	onclick="fnImgChange(${item.updateFileList[i].postFileId})">`;
		}
		tag += `<input type="button" class="btnDel" value="x" data-del-file="${item.updateFileList[i].postFileId}"
				   style="width: 30px; height: 30px; position: absolute; right: 0px; bottom: 0px; 
				   z-index: 999; background-color: rgba(255, 255, 255, 0.1); color: #f00;"
				   onclick="fnPostImgDel(event)">`;
		tag += `<p id="fileNm${item.updateFileList[i].postFileId}" style="display: none; font-size: 8px; cursor: pointer;" 
					onclick="fnFileDown(${item.updateFileList[i].postId}, ${item.updateFileList[i].postFileId})"
					>${item.updateFileList[i].postFileOriginNm}</div>`;
		tag += `</div>`;
	}

	return tag;
}

function followerPostlisu(item) {
	console.log("대상 게시글 : " + item.followerPost.postId + ", 대상 유저 : " + item.followerPost.userId);
	let textHtml = "";
	let now = new Date();


}


//포스팅 html단에 표시하기 위한 html단을 만드는 문자열 String을 반환하는 함수
//해당 문자열들은 (선택자).html()함수를 통해 태그로 바뀌게 됨. 이벤트는 따로 함수로 빼놓고  다시 호출해야함.
function callPost(post) {
	console.log(" 게시글 표시 시작!@#$^^7 : ");
	console.log(post);
	let htmlText = "";
	let now = new Date();
	let post_date = new Date(post.postDate);
	post_date = now - post_date;

	htmlText += `<div class="col-12 post">`;
	htmlText += `<input type="hidden" value="${post.betweenDate}">`;
	htmlText += `<input type="text" id="fileList${post.postId}" value="${post.file_length}">`;
	htmlText += `<div class="card recent-sales">`
	htmlText += `<div class="card-body">`
	htmlText += `<div class="filter" style="margin-top: 15px;">`
	if (post_date / (1000 * 60 * 60 * 24 * 30 * 12) > 1) {
		htmlText += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60 * 24 * 30 * 12))}년 전</a>`
	}
	else if (post_date / (1000 * 60 * 60 * 24 * 30) > 1) {
		htmlText += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60 * 24 * 30))}달 전</a>`
	}
	else if (post_date / (1000 * 60 * 60 * 24) > 1) {
		htmlText += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60 * 24))}일 전</a>`
	}
	else if (post_date / (1000 * 60 * 60) > 1) {
		htmlText += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60))}시간 전</a>`
	}
	else {
		htmlText += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60))}분전</a>`
	}
	htmlText += `<a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>`
	htmlText += `<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">`
	htmlText += `<li><a class="dropdown-item" href="#"><iclass="ri-alarm-warning-line"></i>&emsp;신고하기</a></li>`
	htmlText += `</ul></div>`
	htmlText += `<div class="card-title" id="otherUser${post.userId}" value="${post.userId}">`;
	htmlText += `<img class="img-fluid rounded-circle" src="/upload/${post.profile.userProfileNm}"
					style="width: 40px;">
					<a href="#" class="card-title">${post.userNick}</a>`;
	htmlText += `</div>`;
	htmlText += `<form id="updateForm${post.postId}" enctype="multipart/form-data">`;
	//<!-- 게시글 사진 부분 -->
	//<!-- imgArea는 반복문을 사용해 2차원 배열 처럼 사용되어 파일의 내용을 표시하게됨. -->
	htmlText += `<div class="activity" style="margin-bottom: 10px;"
					id="restImgBox${post.postId}">`;
	htmlText += `<div id="imgArea${post.postId}">`;
	if (loginUserId == post.userId) {
		for (let i = 0; i < post.fileLength; i++) {
			htmlText += `<div class="fileList${post.postId}" value="${post.fileList[i].postFileId}">`;
			//<!--<input type="text" th:id="'postFileNm' + ${post.postId}" value="">
			//<input type="text" th:id="'postFileId' + ${post.postId}" value="">-->

			htmlText += `<div style="position: relative;">`;
			htmlText += `<input type="hidden" id="postFileId${post.fileList[i].postFileId}"
							class="postFileId${post.fileList[i].postId}" name="postFileId"
							value="${post.fileList[i].postFileId}">`;
			htmlText += `<input type="hidden"id="postFileNm${post.fileList[i].postFileId}"
							class="postFileNm" name="postFileNm"
							value="${post.fileList[i].postFileNm}">`;
			htmlText += `<input type="hidden" id="postId${post.fileList[i].postFileId}"
							class="postId${post.fileList[i].postId}" name="postId"
							value="${post.fileList[i].postId}">`;
			htmlText += `<input type="file" id="changedFile${post.fileList[i].postFileId}"
							name="changedFile${post.fileList[i].postFileId}"
							style="display: none;"
							onchange="fnGetChangedFileInfo(${post.fileList[i].postFileId}, ${i}, event)">`;
			if (post.fileList[i].postFileCate == "img") {
				htmlText += `<img id="img${post.fileList[i].postFileId}"
								src="/upload/${post.fileList[i].postFileNm}"
								style="width: 100%; height: 100%; z-index: none; cursor: pointer;"
								class="fileImg"
								onclick="fnImgChange(${post.fileList[i].postFileId})">`;

			} else {
				htmlText += `<img id="img${post.fileList[i].postFileId}"
								src="/assets/img/defaultFileImg.png"
								style="width: 100%; height: 100%; z-index: none; cursor: pointer;"
								class="fileImg"
								onclick="fnImgChange(${post.fileList[i].postFileId})">`;
			}

			htmlText += `<input type="button" class="btnDel" value="x"
							data-del-file="${post.fileList[i].postFileId}" style="width: 30px; height: 30px; position: absolute; right: 0px; bottom: 0px; 
							z-index: 999; background-color: rgba(255, 255, 255, 0.1); color: #f00;" 
							onclick="fnPostImgDel(event)">`;
			htmlText += `<p id="fileNm${post.fileList[i].postFileId}"
							style="display: none; font-size: 8px; cursor: pointer;"
							onclick="fnFileDown(${post.fileList[i].postId}, ${post.fileList[i].postFileId})">
							${post.fileList[i].postFileOriginNm}</p>`;
			htmlText += `</div></div>`;
		}
	} else {
		for (let i = 0; i < post.fileLength; i++) {
			if (post.fileList[i].postFileCate == "img") {
				htmlText += `<img id="img${post.fileList[i].postFileId}" 
					 src="/upload/${post.fileList[i].postFileNm}"
				 	 style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
					 class="fileImg" 
					 onclick="fnImgChange(${post.fileList[i].postFileId})">`;
			} else {
				htmlText += `<img id="img${post.fileList[i].postFileId}"
					 src="/assets/img/defaultFileImg.png"
					 style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
					 class="fileImg" 
					 onclick="fnImgChange(${post.fileList[i].postFileId})">`;
			}
		}
	}
	htmlText += `</div>`;
	htmlText += `</div>`;
	htmlText += `<div class="uploadFileSpace">
					<input type="file" id="updateBtnAtt${post.postId}"
						class="updateBtnAtt" data-post-id="${post.postId}" name="uploadFiles"
						multiple="multiple">
					</div>`;
	htmlText += `<div class="changedFileSpace">
					<input type="file" id="changedFiles${post.postId}"
						name="changedFiles" value="" multiple="multiple">
					</div>`;
	htmlText += `<div id="postAttZone${post.postId}"
					data-placeholder="파일을 첨부하려면 파일선택 버튼을 누르세요."></div>`;
	htmlText += `<input type="hidden" name="postId" value="${post.postId}">`;
	htmlText += `<input type="hidden" name="originFiles" id="originFiles${post.postId}">`;
	htmlText += `<input type="hidden" id="userId" name="userId" value="${post.userId}">`;
	htmlText += `<input type="hidden" id="postContentIn${post.postId}" name="postContent" value="${post.postContent}">`;
	htmlText += `<input type="hidden" name="postDate" value="${post.postDate}">`;
	htmlText += `<input type="hidden" name="restNm" value="${post.restNm}"></form>`;

	if (loginUserId == post.userId) {
		htmlText += `<div class="buttons fileBtns" id="buttonBox${post.postId}" value="${post.postId}">
						<button type="button" id="fileRequest${post.postId}"
						value="${post.postId}">파일 관리창 열기</button></div>`;

	}

	htmlText += `<div class="activity" style="margin-bottom: 10px;">`
	//htmlText += `<img src="../assets/img/news-1.jpg" style="width: 100%;">`

	htmlText += `</div><div class="activity">`
	htmlText += `<div id="postContent${post.postId}">${post.postContent}</div>`
	htmlText += `<div id="upTitle${post.postId}">수정할 게시글 내용</div>`;
	htmlText += `<textarea id="contentIn${post.postId}" name="contentIn"
					row="40" style="display: none; resize: none; width: 85%;">${post.postContent}</textarea>`;
	//여기에 텍스트 에어리어 투명한거 들어가는자리(일단 빼놨음)
	htmlText += `<br>`
	htmlText += `</div>`
	htmlText += `<div class="activity">`
	//<!--좋아요 댓글 지도-->	
	if (post.postLike == "Y")
		htmlText += `<i class="ri-heart-3-line post_like" id="${post.postId}" style="font-size: 30px; margin-right: 5px; color:red; cursor: pointer;"></i>`
	else if (post.postLike == "N")
		htmlText += `<i class="ri-heart-3-line post_like" id="${post.postId}" style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>`
	htmlText += `<i class="ri-message-3-line msg_icon" id="${post.postId}" style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>`
	//식당 관련 내용을 적용하는 버튼	
	if (post.restaurant == "Y")
		htmlText += `<i class="ri-map-pin-2-line map_icon" id="${post.postId}" style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>`
	htmlText += `<br>`
	htmlText += `<a>좋아요 <span id="likeCnt${post.postId}">${post.likeCnt}</span>개</a>`;

	htmlText += `<hr></div>`
	//내부 서버로 옮기는 데이터를 모음. 추후에 이미지도 다룸. 
	htmlText += `<form class="data" action="/post/deletePost" method="post">`
	htmlText += `<input type="hidden" id="restNmIn" name="restNm" value="${post.restNm}">`
	htmlText += `<input type="hidden" id="postContentIn" name="postContent" value="${post.postContent}">`
	htmlText += `<input type="hidden" id="userId" name="userId" value="''+${post.userId}">`
	htmlText += `<input type="hidden" id="postId" name="postId" value="''+${post.postId}">`
	htmlText += `<input type="hidden" id="postDate" name="postDate" value="''+${post.postDate}">`
	htmlText += `</form>`
	//<!-- 친구 식사 했는지 확인 필드 -->
	if (post.resCnt != 0) {
		htmlText += `<div class="activity" style="text-align: center;"><hr>
			<a href="#"><span th:text="${item.loginUser.userName}"></span>님의 친구 <span>${post.resCnt}</span>명이
				<span>${post.resName}</span> 에서 식사하셨어요!</a></div>`;
	}
	if (loginUserId == post.userId)
		htmlText += `<button type="button" class="updateBtn" id="updateButton"
					value="${post.postId}">게시글 수정</button>`;
	htmlText += `</div></div></div></div></div>`;

	return htmlText;
}



//작성글을 등록할 때 사용하는 게시글 1개를 호출하는 post 태그
//포스팅 html단에 표시하는 함수. 문자열 값을 반환
function post(item, insertIndex) {
	console.log("게시글 작성자 Id : " + item.loginUser.userId);
	let text = "";
	let now = new Date();
	let post_date = new Date(item.insertPost.postDate);
	post_date = now - post_date;
	text += `<div class="col-12 post">`;
	text += `<input type="hidden" value="${item.insertPost.betweenDate}">`;
	text += `<div class="card recent-sales">`;
	text += `<div class="card-body">`;
	text += `<div class="filter" style="margin-top: 15px;">`;
	if (post_date / (1000 * 60 * 60 * 24 * 30 * 12) > 1) {
		text += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60 * 24 * 30 * 12))}년 전</a>`
	}
	else if (post_date / (1000 * 60 * 60 * 24 * 30) > 1) {
		text += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60 * 24 * 30))}달 전</a>`
	}
	else if (post_date / (1000 * 60 * 60 * 24) > 1) {
		text += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60 * 24))}일 전</a>`
	}
	else if (post_date / (1000 * 60 * 60) > 1) {
		text += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60 * 60))}시간 전</a>`
	}
	else {
		text += `<a style="margin-right: 20px;">${parseInt(post_date / (1000 * 60))}분전</a>`
	}
	text += `<a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>`
	text += `<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">`
	text += `<li><a class="dropdown-item" href="#"><iclass="ri-alarm-warning-line"></i>&emsp;신고하기</a></li>`
	text += `</ul></div>`;
	text += `<div class="card-title">
					<img class="img-fluid rounded-circle" src="../assets/img/messages-1.jpg"
						style="width: 40px;">
					<a href="#" class="card-title">${item.restaurant.restName}</a>
			</div>`;
	text += `<form id="updateForm${item.insertPost.postId}" enctype="multipart/form-data">`;
	text += `<div class="activity" style="margin-bottom: 10px;" id="restImgBox${item.insertPost.postId}">`;
	//text += `<img src="../assets/img/news-1.jpg" style="width: 100%;">`;
	//text +=	`</div>`;
	text += `<div id="imgArea${item.insertPost.postId}">`;
	//item.postFileList[i].postFileNm
	//item.postFileList[i].postFileId
	if (item.loginUser.userId == item.insertPost.userId) {
		for (let i = 0; i < item.postFileList.length; i++) {
			text += `<div class="fileList${item.insertPost.postId}" value="${item.postFileList[i].postFileId}">`;

			text += `<div style="position: relative;">`;
			text += `<input type="hidden" id="postFileId${item.postFileList[i].postFileId}"
							class="postFileId${item.postFileList[i].postId}" name="postFileId"
							value="${item.postFileList[i].postFileId}">`;
			text += `<input type="hidden"id="postFileNm${item.postFileList[i].postFileId}"
							class="postFileNm" name="postFileNm"
							value="${item.postFileList[i].postFileNm}">`;
			text += `<input type="hidden" id="postId${item.postFileList[i].postFileId}"
							class="postId${item.postFileList[i].postId}" name="postId"
							value="${item.postFileList[i].postId}">`;
			text += `<input type="file" id="changedFile${item.postFileList[i].postFileId}"
							name="changedFile${item.postFileList[i].postFileId}"
							style="display: none;"
							onchange="fnGetChangedFileInfo(${item.postFileList[i].postFileId}, ${i}, event)">`;
			if (item.postFileList[i].postFileCate == "img") {
				text += `<img id="img${item.postFileList[i].postFileId}"
								src="/upload/${item.postFileList[i].postFileNm}"
								style="width: 100%; height: 100%; z-index: none; cursor: pointer;"
								class="fileImg"
								onclick="fnImgChange(${item.postFileList[i].postFileId})">`;

			} else {
				text += `<img id="img${item.postFileList[i].postFileId}"
								src="/assets/img/defaultFileImg.png"
								style="width: 100%; height: 100%; z-index: none; cursor: pointer;"
								class="fileImg"
								onclick="fnImgChange(${item.postFileList[i].postFileId})">`;
			}

			text += `<input type="button" class="btnDel" value="x"
							data-del-file="${item.postFileList[i].postFileId}" style="width: 30px; height: 30px; position: absolute; right: 0px; bottom: 0px; 
							z-index: 999; background-color: rgba(255, 255, 255, 0.1); color: #f00;" 
							onclick="fnPostImgDel(event)">`;
			text += `<p id="fileNm${item.postFileList[i].postFileId}"
							style="display: none; font-size: 8px; cursor: pointer;"
							onclick="fnFileDown(${item.postFileList[i].postId}, ${item.postFileList[i].postFileId})">
							${item.postFileList[i].postFileOriginNm}</p>`;
			text += `</div></div>`;
		}
	} else {
		for (let i = 0; i < item.postFileList.fileLength; i++) {
			if (item.postFileList[i].postFileCate == "img") {
				text += `<img id="img${item.postFileList[i].postFileId}" 
					 src="/upload/${item.postFileList[i].postFileNm}"
				 	 style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
					 class="fileImg" 
					 onclick="fnImgChange(${item.postFileList[i].postFileId})">`;
			} else {
				text += `<img id="img${item.postFileList[i].postFileId}"
					 src="/assets/img/defaultFileImg.png"
					 style="width: 100%; height: 100%; z-index: none; cursor: pointer;" 
					 class="fileImg" 
					 onclick="fnImgChange(${item.postFileList[i].postFileId})">`;
			}
		}
	}

	text += `</div></div>`;
	text += `<div class="uploadFileSpace">
					<input type="file" id="updateBtnAtt${item.insertPost.postId}"
						class="updateBtnAtt" data-post-id="${item.insertPost.postId}" name="uploadFiles"
						multiple="multiple">
					</div>`;
	text += `<div class="changedFileSpace">
					<input type="file" id="changedFiles${item.insertPost.postId}"
						name="changedFiles" value="" multiple="multiple">
					</div>`;
	text += `<div id="postAttZone${item.insertPost.postId}"
					data-placeholder="파일을 첨부하려면 파일선택 버튼을 누르세요."></div>`;
	text += `<input type="hidden" name="postId" value="${item.insertPost.postId}">`;
	text += `<input type="hidden" name="originFiles" id="originFiles${item.insertPost.postId}">`;
	text += `<input type="hidden" id="userId" name="userId" value="${item.insertPost.userId}">`;
	text += `<input type="hidden" id="postContentIn${item.insertPost.postId}" name="postContent" value="${item.insertPost.postContent}">`;
	text += `<input type="hidden" name="postDate" value="${item.insertPost.postDate}">`;
	text += `<input type="hidden" name="restNm" value="${item.insertPost.restNm}"></form>`;
	if (item.loginUser.userId == item.insertPost.userId) {
		text += `<div class="buttons fileBtns" id="buttonBox${item.insertPost.postId}" value="${item.insertPost.postId}">
				<button type="button" id="fileRequest${item.insertPost.postId}"
				value="${item.insertPost.postId}">파일 관리창 열기</button>&emsp;
				<button type="button" id="fileRemove${item.insertPost.postId}">파일 전부 삭제</button>
				</div>`;
	}
	//text += `<img class="img-fluid rounded-circle" src="../assets/img/messages-1.jpg"style="width: 40px;">`
	text += `<div class="activity" style="margin-bottom: 10px;">`
	//text += `<img src="../assets/img/news-1.jpg" style="width: 100%;">`

	text += `</div><div class="activity">`
	text += `<div id="postContent${item.insertPost.postId}">${item.insertPost.postContent}</div>`
	text += `<div id="upTitle${item.insertPost.postId}">수정할 게시글 내용</div>`;
	text += `<textarea id="contentIn${item.insertPost.postId}" name="contentIn"
					row="40" style="display: none; resize: none; width: 85%;">${item.insertPost.postContent}</textarea>`;
	//<!-- 해시태그 -->
	if (item.insertPost.hashTag1 != "") {
		text += `<a href="#" style="color: blue;">&emsp;#<span>${item.insertPost.hashTag1}</span></a>`;
	}
	if (item.insertPost.hashTag2 != "") {
		text += `<a href="#" style="color: blue;">&emsp;#<span>${item.insertPost.hashTag2}</span></a>`;
	}
	if (item.insertPost.hashTag3 != "") {
		text += `<a href="#" style="color: blue;">&emsp;#<span>${item.insertPost.hashTag3}</span></a>`;
	}
	if (item.insertPost.hashTag4 != "") {
		text += `<a href="#" style="color: blue;">&emsp;#<span>${item.insertPost.hashTag4}</span></a>`;
	}
	if (item.insertPost.hashTag5 != "") {
		text += `<a href="#" style="color: blue;">&emsp;#<span>${item.insertPost.hashTag5}</span></a>`;
	}
	text += `<br></div>`;
	text += `<div class="activity">`
	//<!--좋아요 댓글 지도-->	
	if (item.insertPost.postLike == "Y")
		text += `<i class="ri-heart-3-line post_like" id="${item.insertPost.postId}" style="font-size: 30px; margin-right: 5px; color:red; cursor: pointer;"></i>`
	else if (item.insertPost.postLike == "N")
		text += `<i class="ri-heart-3-line post_like" id="${item.insertPost.postId}" style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>`
	text += `<i class="ri-message-3-line msg_icon" id="${item.insertPost.postId}" style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>`
	//식당 관련 내용을 적용하는 버튼	
	if (item.insertPost.restaurant == "Y")
		text += `<i class="ri-map-pin-2-line map_icon" id="${item.insertPost.postId}" style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>`
	text += `<br>`
	text += `<a>좋아요 <span id="likeCnt${item.insertPost.postId}">${item.insertPost.likeCnt}</span>개</a>`;

	text += `<hr></div>`;
	text += `<form class="data" action="/post/deletePost" method="post">
					<input type="hidden" id="restNmIn" name="restNm" value="${item.insertPost.restNm}">
					<input type="hidden" id="postContentIn" name="postContent"
					value="${item.insertPost.postContent}">
					<input type="hidden" id="userId" name="userId" value="${item.insertPost.userId}">
					<input type="hidden" id="postId" name="postId" value="${item.insertPost.postId}">
					<input type="hidden" id="postDate" name="postDate"
					value="${item.insertPost.postDate}">
				</form>`;
	//<!-- 친구 식사 했는지 확인 필드 -->
	if (item.insertPost.restCnt > 0) {
		text += `<div class="activity" style="text-align: center;"><hr>
		<a href="#"><span>${item.loginUser.userName}</span>님의 친구 <span>${item.insertPost.resCnt}</span>명이
			<span>${item.restaurant.resName}</span> 에서 식사하셨어요!</a>
		</div>`;
	}
	if (item.insertPost.userId == item.loginUser.userId) {
		text += `<button class="updateBtn" id="updateButton" value="${item.insertPost.postId}">게시글 수정</button>`;
	}
	text += `</div></div></div></div></div>`;
	return text;
}