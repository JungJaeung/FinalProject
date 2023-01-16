
$.like_button = function(loginUserId) {
	$(".post_like").click(function(e) {
		//하트 아이콘의 아이디를 게시물 아이디로 만들었음
		postId = e.target.id;
		postId = Number(postId);
		if ($(e.target).css("color") == "rgb(0, 0, 0)") {
			console.log(loginUserId);
			console.log("좋아요 누른 포스트 id: " + postId + "좋아요 누른 유저 : " + loginUserId);
			console.log("타입 : " + typeof(postId));
			
			$(e.target).css("color", "red");
			//좋아요를 눌렀을때 ajax
			$.ajax({
				url: '/post/likeUp',
				type: 'get',
				//포스트 아이디를 전송
				//일단 생각한 방법으로 만들어뒀음(like 좋아요 1, 해제 0)
				data: {
					userId: loginUserId,
					postId: postId
				},
				success: function(obj) {
					console.log(obj)
					//좋아요 개수를 likeCnt +아이디 로 만들었음

					$("#likeCnt" + postId).html(obj);
				},
				error: function(e) {
					console.log(e);
				}
			});
		} else {
			$(e.target).css("color", "black");
			console.log("좋아요 해제한 포스트 id: " + postId);
			$.ajax({
				url: '/post/likeDown',
				type: 'get',
				data: {
					userId: loginUserId,
					postId: postId
				},
				success: function(obj) {
					console.log(obj)
					//좋아요 개수를 likeCnt +아이디 로 만들었음
					$("#likeCnt" + postId).html(obj);
				},
				error: function(e) {
					console.log(e);
				}
			});
		}
	});
}
