			function fnGetChangedFileInfo(boardFileNo, e) {
				//변경된 파일 받아오기
				const files = e.target.files;
				//받아온 파일 배열 형태로 변경(싱글파일 업로드여서 파일배열 한개의 인자만 담김)
				const fileArr = Array.prototype.slice.call(files);

				//변경된 파일들은 변경된 파일 배열에 담아준다.
				changedFiles.push(fileArr[0]);

				//미리보기 화면에서 변경된 파일의 이미지 출력
				const reader = new FileReader();

				reader.onload = function (ee) {
					const img = document.getElementById("img" + boardFileNo);
					const p = document.getElementById("fileNm" + boardFileNo);

					p.textContent = fileArr[0].name;

					//이미지인지 체크
					if (fileArr[0].name.match(/(.*?)\.(jpg|jpeg|png|gif|bmp|svg)$/))
						img.src = ee.target.result;
					else
						img.src = "/images/defaultFileImg.png";
				}

				reader.readAsDataURL(fileArr[0]);

				//기존 파일을 담고있는 배열에서 변경이 일어난 파일 수정
				for (let i = 0; i < originFiles.length; i++) {
					if (boardFileNo == originFiles[i].boardFileNo) {
						originFiles[i].boardFileStatus = "U";
						originFiles[i].newFileNm = fileArr[0].name;
					}
				}
			}

			function fnUpdatePost(postId, index) {   //파일 입출력이나 수정을 위한 ajax 데이터 묶음 처리


				/*
				dt = new DataTransfer();
	
				for (f in uploadFiles) {
				   let file = uploadFiles[f];
				   dt.items.add(file);
				}
	
				$("#btnAtt")[0].files = dt.files;
	
				//dt.clearData();
				//clearData() 사용하면 배열의 모든 내용이 담기지 않고
				//파일 하나씩만 담기는 현상이 발생해서 dt를 두 개로 분리하여 사용
				dt2 = new DataTransfer();
	
				for (f in changedFiles) {
				   let file = changedFiles[f];
				   dt2.items.add(file);
				}
	
				$("#changedFiles")[0].files = dt2.files;
			    
				//변경된 파일정보와 삭제된 파일정보를 담고있는 배열 전송
				//배열 형태로 전송 시 백단(Java)에서 처리불가
				//JSON String 형태로 변환하여 전송한다.
				$("#originFiles").val(JSON.stringify(originFiles));
				*/
				//ajax에서 multipart/form-data형식을 전송하기 위해서는
				//new FormData()를 사용하여 직접 폼데이터 객체를 만들어준다.
				//form.serialize()는 multipart/form-data 전송불가
				//let formData = new FormData($("#updateForm")[0]);
				let formData = new FormData($($(".data")[index])[0]);

				//ajax에 enctype: multipart/form-data, 
				//processData: false, contentType: false로 설정               
				console.log("updating-service");
				console.log("수정 할 내용 : " + $("#contentIn" + postId).val());
				$.ajax({
					enctype: 'multipart/form-data',
					url: '/post/updatePost',
					type: 'put',
					data: formData,
					processData: false,
					contentType: false,
					success: function (obj) {
						console.log(obj.item);
						alert("수정작업을 성공하였습니다.");

						$("#postId").val('' + obj.item.getPost.postId);
						$("#userId").val('' + obj.item.getPost.userId);
						$("#postContentIn").val(obj.item.getPost.userId);
						$("#postContent").text(obj.item.getPost.postContent);
						$("#postContent" + postId).text(obj.item.getPost.postContent);
						$("#contentIn" + postId).text(obj.item.getPost.postContent);
						$("#restNmIn").val(obj.item.getPost.restNm);
						//수정 다하면 태그들을 다시 원래대로 돌린다.
						$("#postContent" + postId).show();
						$("#contentIn" + postId).hide();
						$("#deleteButton" + postId).remove();
						$("#updateButton" + postId).remove();
						$($(".updateBtn")[index]).text("게시글 수정");
					},
					error: function (e) {
						console.log(e);
					},
					done: function (result) {
						console.log(result);
						$("#attZone").replaceWith(result);
					}
				});
				return false;
			}