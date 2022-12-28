	let title;
	
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
		text += `<div class="activity" style="margin-bottom: 10px;">
					<img src="../assets/img/news-1.jpg" style="width: 100%;">
				</div>`;
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