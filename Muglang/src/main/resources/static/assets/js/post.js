	let title;
	
	$(function() {
		//ajax로 이벤트 함수를 다시 빌드하는 객체를 따로 정의
		$.update_post = function() {
			$($(".updateBtn")[0]).click(function() {
				console.log("회원의 수정버튼 이벤트 함수 적용 확인.");
				flagList[0] = !flagList[0];
				if (flagList[0]) {
					$(this).text("돌아가기");
					$("<button id='deleteButton" + $(this).val() + "'>").appendTo($(this).parent());
					$("#deleteButton" + $(this).val()).text("글 삭제");
					$("<button id='updateButton" + $(this).val() + "'>").appendTo($(this).parent());
					$("#updateButton" + $(this).val()).text("게시글 수정하기");
				} else {
					$(this).text("게시글 수정")
					$("#deleteButton" + $(this).val()).remove();
					$("#updateButton" + $(this).val()).remove();
				}
	
				$("#postContent" + $(this).val()).text();
	
				if (!flagList[0]) {
					$("#postContent" + $(this).val()).show();
					$("#contentIn" + $(this).val()).hide();
				} else {
					$("#postContent" + $(this).val()).hide();
					$("#contentIn" + $(this).val()).show();
				}
	
				console.log("버튼 이벤트 html단 활성화");
				$("#updateButton" + postIdList[0]).click(function (e) {
					$($('.data')[0]).children('#postContentIn').val($("#contentIn" + postIdList[0]).val());
					console.log("update될 내용 : " + $("#contentIn" + postIdList[0]).val());
					fnUpdatePost(postIdList[0], 0);
				});
				$("#deleteButton" + postIdList[0]).click(function (e) {
					console.log("delete");
					$($('.data')[0]).submit();
				});
		
				//글 내용 수정하는 키입력을 받음.
				$("#contentIn" + postIdList[0]).keyup(function (e) {
					$("#postContent" + postIdList[0]).text($(this).val());
					console.log($(this).val());
					fnChangeContent(this);
				});
			});
		}
		//게시글 수정과 삭제를 활성화하는 버튼을 생성함. 게시글이 자기꺼인 것만 표시함.
		$(".updateBtn").each(function(i, e) {
			$(this).on('click', function() {
				console.log("초기 화면 수정 버튼 활성화.");
				flagList[i] = !flagList[i];
				if (flagList[i]) {
					$(this).text("돌아가기");
					$("<button id='deleteButton" + $(this).val() + "'>").appendTo($(this).parent());
					$("#deleteButton" + $(this).val()).text("글 삭제");
					$("<button id='updateButton" + $(this).val() + "'>").appendTo($(this).parent());
					$("#updateButton" + $(this).val()).text("게시글 수정하기");
				} else {
					$(this).text("게시글 수정")
					$("#deleteButton" + $(this).val()).remove();
					$("#updateButton" + $(this).val()).remove();
				}

				$("#postContent" + $(this).val()).text();

				if (!flagList[i]) {
					$("#postContent" + $(this).val()).show();
					$("#contentIn" + $(this).val()).hide();
				} else {
					$("#postContent" + $(this).val()).hide();
					$("#contentIn" + $(this).val()).show();
				}

				console.log("버튼 이벤트 html단 활성화");
				$("#updateButton" + postIdList[i]).click(function (e) {
					$($('.data')[i]).children('#postContentIn').val($("#contentIn" + postIdList[i]).val());
					console.log("update될 내용 : " + $("#contentIn" + postIdList[i]).val());
					fnUpdatePost(postIdList[i], i);
				});
				$("#deleteButton" + postIdList[i]).click(function (e) {
					console.log("delete");
					$($('.data')[i]).submit();
				});
		
				//글 내용 수정하는 키입력을 받음.
				$("#contentIn" + postIdList[i]).keyup(function (e) {
					$("#postContent" + postIdList[i]).text($(this).val());
					console.log($(this).val());
					fnChangeContent(this);
				});
	
			});
		});
	
	});
	
	function inputTitle(title) {
		$('#inputRestNm').val(title)
	}
	
	//포스팅 html단에 표시하는 함수. 문자열 값을 반환
	function post(item) {
		console.log("게시글 작성자 Id : " + item.loginUser.userId);
		let text = "";
		text += `<div class="col-12 post">`;
		text += `<input type="hidden" value="${item.insertPost.betweenDate}">`;
		text += `<div class="card recent-sales">`;
		text += `<div class="card-body">`;
		text += `<div class="filter" style="margin-top: 15px;">`;
		text += `<a style="margin-right: 20px;">`;
		if(item.insertPost.betweenDate / 60 / 60 / 24 / 30 / 12 >= 1)
			text += `<span>${Math.floor(item.insertPost.betweenDate / 60 / 60 / 24 / 30 / 12)}년 전</span>`;
		else if(item.insertPost.betweenDate / 60 / 60 / 24 / 30 / 12 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 / 30 >= 1)
			text += `<span>${Math.floor(item.insertPost.betweenDate / 60 / 60 / 24 / 30 )}달 전</span>`;
		else if(item.insertPost.betweenDate / 60 / 60 / 24 / 30 / 12 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 / 30 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 >= 1)
			text += `<span>${Math.floor(item.insertPost.betweenDate / 60 / 60 / 24 )}일 전</span>`;
		else if(item.insertPost.betweenDate / 60 / 60 / 24 / 30 / 12 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 / 30 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 < 1 && item.insertPost.betweenDate / 60 / 60 >= 1)
			text += `<span>${Math.floor(item.insertPost.betweenDate / 60 / 60)}시간 전</span>`;
		else if(item.insertPost.betweenDate / 60 / 60 / 24 / 30 / 12 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 / 30 < 1 && item.insertPost.betweenDate / 60 / 60 / 24 < 1 && item.insertPost.betweenDate / 60 / 60 < 1)
			text += `<span>${Math.floor(item.insertPost.betweenDate / 60 )}분 전</span>`;
		
		text += `</a>`;
		text += `<a class="icon" href="#" data-bs-toggle="dropdown"><i
							class="bi bi-three-dots"></i></a>
					<ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
						<li><a class="dropdown-item" href="#"><i
									class="ri-alarm-warning-line"></i>&emsp;신고하기</a></li>
					</ul>`;
		text += `</div>`;
		text += `<div class="card-title">
					<img class="img-fluid rounded-circle" src="../assets/img/messages-1.jpg"
						style="width: 40px;">
					<a href="#" class="card-title">${item.insertPost.restNm}</a>
				</div>`;
		text += `<div class="activity" style="margin-bottom: 10px;" id="restImgBox">`;
		//text += `<img src="../assets/img/news-1.jpg" style="width: 100%;">`;
		//text +=	`</div>`;
		text += `<div class="box" id="imageBox${post.postId}">
					<img src="../assets/img/news-1.jpg" style="width: 100%;">
				</div>
				<div class="buttons" id="buttonBox${post.postId}">
					<button id="fileRequest${post.postId}">파일 관리창 열기</button>&emsp;
					<button id="fileRemove${post.postId}">파일 삭제</button>
				</div></div>`;
		text += `<div class="activity">`;
		text += `<div id="postContent${item.insertPost.postId}">${item.insertPost.postContent}</div>
				<textarea id="contentIn${item.insertPost.postId}" name="contentIn" col="200"
				row="40" style="display: none; resize: none;">${item.insertPost.postContent}</textarea><br>`;
		text += `<a href="#" style="color: blue;">&emsp;#강남맛집</a>
				<a href="#" style="color: blue;">&emsp;#˘ᗜ˘</a>
				<a href="#" style="color: blue;">&emsp;#(๑╹ワ╹)</a></div>`;
		text += `<div class="activity">`;
		text += `<i class="ri-heart-3-line post_like" id="${item.insertPost.postId}"
					style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>
					<i class="ri-message-3-line msg_icon" id="${item.insertPost.postId}"
					style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>
					<i class="ri-send-plane-2-line"
					style="font-size: 30px; margin-right: 5px; color:black; cursor: pointer;"></i>
					<br>`;
		text += `<a>좋아요 <span id="like_num${item.insertPost.postId}">21</span>개</a><hr></div>`;
		text += `<form class="data" action="/post/deletePost" method="post">
					<input type="hidden" id="restNmIn" name="restNm" value="${item.insertPost.restNm}">
					<input type="hidden" id="postContentIn" name="postContent"
					value="${item.insertPost.postContent}">
					<input type="hidden" id="userId" name="userId" value="${item.insertPost.userId}">
					<input type="hidden" id="postId" name="postId" value="${item.insertPost.postId}">
					<input type="hidden" id="postDate" name="postDate"
					value="${item.insertPost.postDate}">
				</form>`;
		text += `<div class="activity" style="text-align: center;">
					<a href="#"><span>six</span>님 외 16명이 <span>서울 강남구 강남대로 36</span>
					<span>감성타코</span>에서 식사하셨어요!</a>
					<input type="hidden" class="test" value="">`;
		if(item.insertPost.userId == item.loginUser.userId) {
			text += `<button class="updateBtn" id="updateButton" value="${item.insertPost.postId}">게시글 수정</button></div>`;
		}
		text += `</div></div></div></div>`;
		return text; 
	}