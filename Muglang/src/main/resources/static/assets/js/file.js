/*
//전역 변수로 담는 배열들을 미리 선언해두고, 계속 사용할 것임.
//추가된 파일들을 담아줄 배열. File객체로 하나씩 담음
let uploadFiles = [];

//기존 첨부파일 배열 - 새 게시글에서 사용하는 첨부파일 배열.
let originFiles = [];
//각 게시글들의 첨부파일들을 모은 것을 따로 담을 것임..
let originFileList = [];
//변경된 첨부파일 배열
let changedFiles = [];
*/
//스크립트에서 설정한 파일은 스크립트에서 다시 사용하는 것이 불가능하며, 따로 만들어서 초기화 해줘야함.
	$(function() {
		let formData;
		let flagList = false;
		//파일 추가 입력단 생성.
		let fileFlagList = false;
		
		//게시글 작성 부분의 파일 입력 관리 이벤트.
		$("#postFileRequest").click(function(e) {
			e.preventDefault();
			console.log("파일의 등록 요청.")
			//숨어있던 파일 조작 메뉴 등장.
			//이벤트 새로 생성.
			if(!flagList) {
				$.btnAtt();
				$("#imagePreview").show();
				$(this).text("파일 업로드 닫기");
				$("#postFileUpdate").show();
				flagList = !flagList;
			} else {
				$(this).text("파일 업로드 열기");
				$("#imagePreview").hide();
				$("#postFileUpdate").hide();
				flagList = !flagList;
			}
		});
		
		$("#imagePreview").hide();
		$("#postFileUpdate").hide();
		//$("#btnAttForm").hide();
		//파일 리스트 변경 버튼 이벤트 대신 처리.
		$("#postFileUpdate").click(function(e) {
			e.preventDefault();
			$("#btnAtt").click();
		});

		
		console.log("게시글 아이디 리스트 표시 : ");
		
		//이미 등록된 게시글의 수정 과정에서 사용하는 이벤트 처리 버튼.
		
		
		//게시글 수정에서 파일을 수정하는 버튼의 파일관리 이벤트 처리 함수.
		$(".updateBtnAtt").on("change", function(e) {
			//console.log(postId + "번 게시글의 업데이트 파일 변경을 감지.");
			//input type=file에 추가된 파일들을 변수로 받아옴
			const files = e.target.files;
			//변수로 받아온 파일들을 배열 형태로 변환
			const fileArr = Array.prototype.slice.call(files);
			
			const postId = $(e.target).attr("data-post-id");
			console.log(postId);
			
			//배열에 있는 파일들을 하나씩 꺼내서 처리
			for(f of fileArr) {
				postImageLoader(f, postId);
			}
		});

		$.btnAtt = function() {
			$("#btnAtt").on("change", function(e) {
				console.log("파일 변경을 감지.");
				//input type=file에 추가된 파일들을 변수로 받아옴
				const files = e.target.files;
				//변수로 받아온 파일들을 배열 형태로 변환
				const fileArr = Array.prototype.slice.call(files);
				
				//배열에 있는 파일들을 하나씩 꺼내서 처리
				for(f of fileArr) {
					imageLoader(f);
				}
			});
		}
		/*
		//게시글 조회에서 사용함. 해당 게시글의 업로드된 파일을 확인.
		//업로드된 파일의 개수만큼 반복해서 originFileObj 맵에 파일 정보를 배열에 저장함.
		//수업 때 했던 게시글은 1개이고, 지금 이건 여러개를 뿌려야하기 때문에 게시된 데이터를 다 가지고 와야함.
		for(let i = 0; i < postIdList.length; i++) {
			console.log("파일 정보 입력 시작하기 : " + i);
			for(let j = 0; j < $("#fileListSize" + postIdList[i]).val(); j++) {
				const originFileObj = {
					postId: postIdList[i],
					postFileId: $("#postFileId" + postIdList[i]).val(),
					postFileNm: $("#postFileNm" + postIdList[i]).val(),
					//업로드 파일 경로가 각각 다를때는 boardFilePath 속성도 추가
					//파일 상태값(수정되거나 삭제된 파일은 변경) - 파일의 상태 값을 표시함.
					postFileStatus: "N"
				};
				console.log("파일의 정보 : " + originFileObj);
				originFiles.push(originFileObj);
			}
			//1 게시글의 내용을 모아두는 배열에 담음. - 2차원 배열.
			originFileList.push(originFiles);
		}
		*/
		/*
		//게시글을 수정하는 로직 함수.
		$.fnUpdateBtn = function(postId, index) {   //파일 입출력이나 수정을 위한 ajax 데이터 묶음 처리
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
			//바뀐 파일의 정보를 임시로 저장
			$("#changedFiles")[0].files = dt2.files;
		    
			//변경된 파일정보와 삭제된 파일정보를 담고있는 배열 전송
			//배열 형태로 전송 시 백단(Java)에서 처리불가
			//JSON String 형태로 변환하여 전송한다.
			$("#originFiles").val(JSON.stringify(originFileList[index]));
			
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
					flagList[index] = !flagList[index];
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
		*/
	});
	
	//파일 추가창을 활성화하는 이벤트 생성 함수
	function fileTag() {
		let text = "";
		text += `<div id="image_preview">
					<input type="file" id="btnAtt" name="uploadFiles" multiple="multiple">
					<div id="attZone" data-placeholder="파일을 첨부하려면 파일선택 버튼을 누르세요."></div>
				</div>`;
		return text;
	}
	
	//게시글 작성 영역의 파일 이미지 미리보기 로드하는 함수.
	//미리보기 영역에 들어갈 img태그 생성 및 선택된 파일을 Base64 인코딩된 문자열 형태로 변환하여
	//미리보기가 가능하게 해줌
	function imageLoader(file) {
		uploadFiles.push(file);
		console.log(uploadFiles);
		let reader = new FileReader();
		
		reader.onload = function(e) {
			//이미지를 표출해줄 img태그 선언
			let img = document.createElement("img");
			img.setAttribute("style", "width: 100%; height: 100%; z-index: none;");
			
			//이미지 파일인지 아닌지 체크
			if(file.name.toLowerCase().match(/(.*?)\.(jpg|jpeg|png|gif|svg|bmp)$/)) {
				//이미지 파일 미리보기 처리
				img.src = e.target.result;
			} else {
				//일반 파일 미리보기 처리
				img.src = "../img/defaultFileImg.png";
			}
			
			//미리보기 영역에 추가
			//미리보기 이미지 태그와 삭제 버튼 그리고 파일명을 표출하는 p태그를 묶어주는 div 만들어서
			//미리보기 영역에 추가
			//미리보기 영역을 다른거로 대체해야함.
			//$("#imagePrview").append(makeDiv(img,file));
			$("#attZone").append(makeDiv(img, file));
		};
		console.log("파일 이미지 로드 중...");
		//파일을 Base64 인코딩된 문자열로 변경
		reader.readAsDataURL(file);
	}
	
	//게시글 작성이 아닌 이미 게시된 게시글의 파일 입력 미리보기 로드.
	//배열안에 배열을 생성.
	function postImageLoader(file, postId) {
		uploadFiles.push(file);
		//console.log(postId + "번의 게시글의 해당 게시글의 파일 목록을 확인함.");
		//console.log(uploadPostFileList[index]);
		//uploadFiles.push(file);
		
		let reader = new FileReader();
		
		reader.onload = function(e) {
			//이미지를 표출해줄 img태그 선언
			let img = document.createElement("img");
			img.setAttribute("style", "width: 100%; height: 100%; z-index: none;");
			
			//이미지 파일인지 아닌지 체크
			if(file.name.toLowerCase().match(/(.*?)\.(jpg|jpeg|png|gif|svg|bmp)$/)) {
				//이미지 파일 미리보기 처리
				img.src = e.target.result;
			} else {
				//일반 파일 미리보기 처리
				img.src = "../img/defaultFileImg.png";
			}
			console.log(postId + "번 게시글에 이미지 로드중......");
			//미리보기 영역에 추가
			//미리보기 이미지 태그와 삭제 버튼 그리고 파일명을 표출하는 p태그를 묶어주는 div 만들어서
			//미리보기 영역에 추가
			//미리보기 영역을 다른거로 대체해야함.
			$("#postAttZone" + postId).append(makePostDiv(img, file, postId));
			//로딩된 이미지의 이벤트를 추가함.
		};
		//파일을 Base64 인코딩된 문자열로 변경
		reader.readAsDataURL(file);
	}
	
	/*
	function fnGetChangedFileInfo(postFileId, e) {
		console.log("바꾸는 파일 아이디 : " + postFileId);
		//변경된 파일 받아오기
		const files = e.target.files;
		//받아온 파일 배열 형태로 변경(싱글파일 업로드여서 파일배열 한개의 인자만 담김)
		const fileArr = Array.prototype.slice.call(files);

		//변경된 파일들은 변경된 파일 배열에 담아준다.
		changedFiles.push(fileArr[0]);

		//미리보기 화면에서 변경된 파일의 이미지 출력
		const reader = new FileReader();

		reader.onload = function (ee) {
			const img = document.getElementById("img" + postFileId);
			const p = document.getElementById("fileNm" + postFileId);

			p.textContent = fileArr[0].name;

			//이미지인지 체크
			if (fileArr[0].name.match(/(.*?)\.(jpg|jpeg|png|gif|bmp|svg)$/))
				img.src = ee.target.result;
			else
				img.src = "/assets/img/defaultFileImg.png";
		}

		reader.readAsDataURL(fileArr[0]);

		//기존 파일을 담고있는 배열에서 변경이 일어난 파일 수정
		console.log("게시글의 개수 : " + originFiles.length);
		for (let i = 0; i < originFiles.length; i++) {
			if (postFileId == originFiles[i].postFileId) {
				originFiles[i].postFileStatus = "U";
				originFiles[i].newFileNm = fileArr[0].name;
			}
		}

		console.log("변경된 파일 다시 정렬 중");
	}
	*/
	//게시글의 인덱스 번호를 가져온뒤 해당 이미지들의 이벤트를 작동시킬수 있게 해주는 함수를 jquery단계에서 이벤트를 설정해주는 함수다.
	
	//게시글의 인덱스 번호를 매개변수로 더 가져온 뒤 수정하려는 게시글만 따로 저장한다.
	//게시글의 아이디를 가지고 온다음, 그것을 저장한 postIdList에서 인덱스 값을 가지고 온다음, 임시로 저장한 인덱스 번호를 사용한다.
	function fnGetChangedFileInfo(postFileId, index, e) {
		console.log("바꾸는 파일의 게시글 자리 : " + index);
		console.log("바꾸는 파일 아이디 : " + postFileId);
		//변경된 파일 받아오기
		const files = e.target.files;
		//받아온 파일 배열 형태로 변경(싱글파일 업로드여서 파일배열 한개의 인자만 담김)
		const fileArr = Array.prototype.slice.call(files);
	
		//변경된 파일들은 변경된 파일 배열에 담아준다.
		changedFiles.push(fileArr[0]);
		console.log("바꾼 파일의 변경사항을 저장하였습니다. 몇번째 게시글의 : "  + index);
		console.log(changedFiles);
		//미리보기 화면에서 변경된 파일의 이미지 출력
		const reader = new FileReader();

		reader.onload = function (ee) {
			const img = document.getElementById("img" + postFileId);
			const p = document.getElementById("fileNm" + postFileId);

			p.textContent = fileArr[0].name;

			//이미지인지 체크
			if (fileArr[0].name.match(/(.*?)\.(jpg|jpeg|png|gif|bmp|svg)$/))
				img.src = ee.target.result;
			else
				img.src = "/assets/img/defaultFileImg.png";
		}

		reader.readAsDataURL(fileArr[0]);

		//기존 파일을 담고있는 배열에서 변경이 일어난 파일 수정
		console.log("게시글의 개수 : " + originFileList.length);
		
		for(let i = 0; i < originFiles.length; i++) {
			if(postFileId == originFiles[i].postFileId) {
				originFiles[i].postFileStatus = "U";
				originFiles[i].newFileNm = fileArr[0].name;
			}
		}
		/*for (let i = 0; i < originFileList.length; i++) {
			for (let j = 0; j < originFileList[i].length; j++) {
				if (postFileId == originFileList[i][j].postFileId) {
					originFileList[i][j].postFileStatus = "U";
					originFileList[i][j].newFileNm = fileArr[0].name;
				}
			}
		}*/

		console.log("변경된 파일 다시 정렬 중");
	}
	
	
	//x버튼 클릭시 동작하는 메소드
	function fnImgDel(e) {
		//클릭된 태그 가져오기
		let ele = e.srcElement;
		//delFile속성 값 가져오기(boardFileNo)
		let delFile = ele.getAttribute("data-del-file");	
		console.log("삭제할 이미지 파일 번호 : " + delFile);
		
		for(let i = 0; i < originFiles.length; i++) {
			if(delFile == originFiles[i].postFileId) {
				originFiles[i].postFileStatus = "D";
			}
		}
		
		//부모 요소인 div 삭제
		let div = ele.parentNode;
		$(div).remove();
	}	
	
	//이미 게시된 포스트의 x버튼 클릭시 일어나는 이벤트
	function fnPostImgDel(e) {
		//클릭된 태그 가져오기
		let ele = e.currentTarget;
		//delFile속성 값 가져오기(boardFileNo)
		let delFile = ele.getAttribute("data-del-file");	
		console.log("삭제할 이미지 파일 번호 : " + delFile);
		
		for(let i = 0; i < originFiles.length; i++) {
			if(delFile == originFiles[i].postFileId) {
				originFiles[i].postFileStatus = "D";
			}
		}
		//부모 요소인 div 삭제
		let div = ele.parentNode;
		$(div).remove();
	}
	
	function fnImgChange(postFileId) {
		//기존 파일의 이미지를 클릭했을 때 같은 레벨의 input type="file"을 가져온다.
		let changedFile = document.getElementById("changedFile" + postFileId);
		//위에서 가져온 input 강제클릭 이벤트 발생시킴
		changedFile.click();
	}

	//이미 게시된 자신의 게시글을 수정하는 로직 함수.
	//함수에 있는 스크립트는 jQuery선택자가 아닌 배열 내의 데이터를 가지고다루면 됨.
	//한개의 게시글에 대한 수정작업
	function fnUpdatePost(postId, index) {   //파일 입출력이나 수정을 위한 ajax 데이터 묶음 처리
		dt = new DataTransfer();
		console.log(postId + "번의 업로드 되는 파일 목록 : ");
		console.log(uploadFiles);
		for (f in uploadFiles) {
		   let file = uploadFiles[f];
		   dt.items.add(file);
		}

		$("#updateBtnAtt" + postId)[0].files = dt.files;

		//dt.clearData();
		//clearData() 사용하면 배열의 모든 내용이 담기지 않고
		//파일 하나씩만 담기는 현상이 발생해서 dt를 두 개로 분리하여 사용
		dt2 = new DataTransfer();

		for (f in changedFiles) {
		   let file = changedFiles[f];
		   dt2.items.add(file);
		}

		$("#changedFiles" + postId)[0].files = dt2.files;
		console.log(postId + "번 게시글의 파일 수정을 진행하고 있습니다.");
		console.log(changedFiles);
		
		//해당 postId에 대한 originsFiles만 남김
		originFiles = originFiles.filter(file => file.postId === postId)
		
		//변경된 파일정보와 삭제된 파일정보를 담고있는 배열 전송
		//배열 형태로 전송 시 백단(Java)에서 처리불가
		//JSON String 형태로 변환하여 전송한다.
		$("#originFiles" + postId).val(JSON.stringify(originFiles));
		console.log("원래 파일의 이름 : " + $("#originFiles").val());
		//ajax에서 multipart/form-data형식을 전송하기 위해서는
		//new FormData()를 사용하여 직접 폼데이터 객체를 만들어준다.
		//form.serialize()는 multipart/form-data 전송불가
		//let formData = new FormData($("#updateForm")[0]);
		let formData = new FormData($("#updateForm" + postId)[0]);

		//ajax에 enctype: multipart/form-data, 
		//processData: false, contentType: false로 설정               
		console.log("updating-service 실행");
		//console.log("수정 할 내용 : " + $("#contentIn" + postId).val());
		//console.log(originFileList[index]);
		$.ajax({
			enctype: 'multipart/form-data',
			url: '/post/updatePost',
			type: 'put',
			data: formData,
			processData: false,
			contentType: false,
			success: function (obj) {
				console.log(obj.item);
				console.log(obj.items);
				alert("수정작업을 성공하였습니다.");
				console.log("현재 파일의 개수는 ? : " + obj.item.fileSize);

				$("#imgArea" + postId).html(imageTag(obj.item, obj.item.fileSize));

				console.log("변경된 파일을 토대로 갱신을 완료하였습니다.");
				$("#postId").val('' + obj.item.getPost.postId);
				$("#userId").val('' + obj.item.getPost.userId);
				$("#postContentIn").val(obj.item.getPost.userId);
				$("#postContent").text(obj.item.getPost.postContent);
				$("#postContent" + postId).text(obj.item.getPost.postContent);
				$("#contentIn" + postId).text(obj.item.getPost.postContent);
				$("#restNmIn").val(obj.item.getPost.restNm);
				//수정 다하면 태그들을 다시 원래대로 돌린다.
				$("#postAttZone" + postId).html('');
				$("#buttonBox" + postId).hide();
				$("#postContent" + postId).show();
				$("#contentIn" + postId).hide();
				$("#deleteButton" + postId).remove();
				$("#updateButton" + postId).remove();
				flagList[index] = false;
				$($(".updateBtn")[index]).text("게시글 수정");
			},
			error: function (e) {
				console.log(e);
			},
			done: function (result) {
				console.log(result);
				$("#postAttZone" + postId).replaceWith(result);
			}
		});
		return false;
	}
	
	//미리보기 영역에 들어가 div(img+button+p)를 생성하고 리턴
	function makeDiv(img, file) {
		//div 생성
		let div = document.createElement("div");
		div.setAttribute("style", "display: inline-block; position: relative;"
		+ " width: 150px; height: 120px; margin: 5px; border: 1px solid #00f; z-index: 1;");
		
		//button 생성
		let btn = document.createElement("input");
		btn.setAttribute("type", "button");
		btn.setAttribute("value", "x");
		btn.setAttribute("data-del-file", file.name);
		btn.setAttribute("style", "width: 30px; height: 30px; position: absolute;"
		+ " right: 0px; bottom: 0px; z-index: 999; background-color: rgba(255, 255, 255, 0.1);"
		+ " color: #f00;");
		
		//버튼 클릭 이벤트
		//버튼 클릭 시 해당 파일이 삭제되도록 설정
		btn.onclick = function(e) {
			//클릭된 버튼
			const ele = e.srcElement;
			//delFile(파일이름) 속성 꺼내오기: 삭제될 파일명
			const delFile = ele.getAttribute("data-del-file");
			
			for(let i = 0; i < uploadFiles.length; i++) {
				//배열에 담아놓은 파일들중에 해당 파일 삭제
				if(delFile == uploadFiles[i].name) {
					//배열에서 i번째 한개만 제거
					uploadFiles.splice(i, 1);
				}
			}
			
			//버튼 클릭 시 btnAtt에 첨부된 파일도 삭제
			//input type=file은 첨부된 파일들을 fileList 형태로 관리
			//fileList에 일반적인 File객체를 넣을 수 없고
			//DataTransfer라는 클래스를 이용하여 완전한 fileList 형태로 만들어서
			//input.files에 넣어줘야 된다.
			dt = new DataTransfer();
			
			for(f in uploadFiles) {
				const file = uploadFiles[f];
				dt.items.add(file);
			}
			
			$("#attZone")[0].files = dt.files;
			
			//해당 img를 담고있는 부모태그인 div 삭제
			const parentDiv = ele.parentNode;
			$(parentDiv).remove();
		}
		
		//파일명 표출할 p태그 생성
		const fName = document.createElement("p");
		fName.setAttribute("style", "display: inline-block; font-size: 8px;");
		fName.textContent = file.name;
		
		//div에 하나씩 추가
		div.appendChild(img);
		div.appendChild(btn);
		div.appendChild(fName);
		
		//완성된 div 리턴
		return div;
	}
	
	//이미 게시된 게시글의 미리보기 영역에 들어가 div(img+button+p)를 생성하고 리턴
	function makePostDiv(img, file, postId) {
		//div 생성
		let div = document.createElement("div");
		div.setAttribute("style", "display: inline-block; position: relative;"
		+ " width: 150px; height: 120px; margin: 5px; border: 1px solid #00f; z-index: 1;");
		
		//button 생성
		let btn = document.createElement("input");
		btn.setAttribute("type", "button");
		btn.setAttribute("value", "x");
		btn.setAttribute("data-del-file", file.name);
		btn.setAttribute("style", "width: 30px; height: 30px; position: absolute;"
		+ " right: 0px; bottom: 0px; z-index: 999; background-color: rgba(255, 255, 255, 0.1);"
		+ " color: #f00;");
		
		//버튼 클릭 이벤트
		//버튼 클릭 시 해당 파일이 삭제되도록 설정
		btn.onclick = function(e) {
			//클릭된 버튼
			const ele = e.currentTarget;
			console.log(e.currentTarget);
			//delFile(파일이름) 속성 꺼내오기: 삭제될 파일명
			const delFile = ele.getAttribute("data-del-file");

			for(let i = 0; i < uploadFiles.length; i++) {
				//배열에 담아놓은 파일들중에 해당 파일 삭제
				if(delFile == uploadFiles[i].name) {
					console.log("del-file : " + delFile + ", uploadFiles : " + uploadFiles[i].name);
					//배열에서 i번째 한개만 제거
					let del = uploadFiles.splice(i, 1);
					console.log(del[0].name);
				}
				
			}
			//console.log(uploadPostFileList[index]);
			//버튼 클릭 시 btnAtt에 첨부된 파일도 삭제
			//input type=file은 첨부된 파일들을 fileList 형태로 관리
			//fileList에 일반적인 File객체를 넣을 수 없고
			//DataTransfer라는 클래스를 이용하여 완전한 fileList 형태로 만들어서
			//input.files에 넣어줘야 된다.
			dt = new DataTransfer();
			
			for(f in uploadFiles) {
				const file = uploadFiles[f];
				dt.items.add(file);
			}
			
			$("#postAttZone" + postId)[0].files = dt.files;
			
			//해당 img를 담고있는 부모태그인 div 삭제
			const parentDiv = ele.parentNode;
			$(parentDiv).remove();
			console.log("게시글의 업로드할 파일이 제거 됨.");
		};
		
		//파일명 표출할 p태그 생성
		const fName = document.createElement("p");
		fName.setAttribute("style", "display: inline-block; font-size: 8px;");
		fName.textContent = file.name;
		
		//div에 하나씩 추가
		div.appendChild(img);
		div.appendChild(btn);
		div.appendChild(fName);
		
		//완성된 div 리턴
		return div;
	}

