<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>cleverc.online</title>
<script src="resources/js/js.js"></script>
<link href="resources/css/joinc.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">

function dupCheck() {
	let userId = document.getElementsByName("userId")[0];
	let check = document.getElementsByName("dupCheck")[0];

	if (userId.value == "" || !isValidateCheck(userId.value,true)) {
		check.value = "check";
		if (userId.value == "") {
			alert("아이디를 입력해주세요.");
			userId.value = "";
			userId.focus();
		} else {
				userId.value = "";
				userId.focus();
				alert("아이디는 첫글자 대문자 + 나머지 대문자 or 숫자 조합, 길이는 5글자 이상 9글자 이하입니다.");
		}
		return;
	}
	if (check.value == "check") {
		console.log(userId.value);
		postAjax("IsDup","userId="+userId.value,"dupResult","text");
	}else{
		userId.readOnly = false;
		userId.value = "";
		userId.focus();
		check.value = "check";
		check.innerText = "중복확인";
	}
}

function isCheckingDup() {
	let check = document.getElementsByName("dupCheck")[0];
	let userId = document.getElementsByName("userId")[0];
	if (userId.value != "") {
		if (check.value != "checked") {
			check.focus();
		}
	} else {
		userId.focus();
	}
}

function dupResult(data){
	let jsonData = data;
	let userId = document.getElementsByName("userId")[0];
	let check = document.getElementsByName("dupCheck")[0];
	let userPwd = document.getElementsByName("userPwd")[0];

	if (jsonData == "true") {
		userId.readOnly = false;
		userId.value = "";
		userId.focus();
		check.value = "check";
		check.innerText = "중복확인";
		alert("사용 불가능한 아이디입니다.");
	} else {
		userId.readOnly = true;
		check.value = "checked";
		check.innerText = "사용가능";
		userPwd.focus();
		alert("사용 가능한 아이디입니다.\n아이디 재입력을 원하면 버튼을 다시 누르세요.");
	}
}
function pwdValidate(data){
	if(data.value != ""){
	if(!isValidateCheck(data.value,false) || !charCount(data.value, 6, 20)){
		alert("비밀번호는 대소문자, 숫자, 특수문자 중 2종류 이상 조합, 6글자이상 20글자 이하여야 합니다.");
		data.value ="";
		data.focus();
	}
	}
}
function pwdConfirm(){
	let userPwd = document.getElementsByName("userPwd");
	if(userPwd[0].value != userPwd[1].value){
		alert("비밀번호가 일치하지 않습니다.");
		userPwd[1].value = "";
		userPwd[0].focus();
	}
}


function sendJoinInfo(){
	let userId = document.getElementsByName("userId")[0];
	let userPwd = document.getElementsByName("userPwd");
	let userName = document.getElementsByName("userName")[0];
	let userEmail = document.getElementsByName("userEmail")[0];
	
	if (userId.readOnly != true) {
		userId.value = "";
		userId.focus();
		alert("아이디 중복 확인를 하세요.");
		return;
	}
	if (userPwd[0].value != userPwd[1].value || userPwd[0].value == "") {
		userPwd[0].value = ""; userPwd[1].value = "";
		userPwd[0].focus();
		
			alert("비밀번호를 확인하세요.");
		

		return;
	}
	if (userName.value == "") {
		userName.focus();
		alert("이름을 입력하세요.");
		return;
	}
	if (userEmail.value == "") {
		userEmail.focus();
		alert("이메일을 입력하세요.");
		return;
	}
	let form = makeForm("Join","post");
	form.appendChild(userId);
	form.appendChild(userPwd[0]);
	form.appendChild(userName);
	form.appendChild(userEmail);
	document.body.appendChild(form);
	form.submit();
}



</script>
<script>
	${message}
</script>
</head>
<body ${message}>
	<div id="form">

		<div id="content">
			<div id="title1">join</div>
			<div class="list">
			<div id="loginBox">
				<div>
					<input type="text" name="userId" placeholder="아이디" onKeyUp="korCheck(this, event)">
				</div>
				<div class="button1">
					<button name="dupCheck" onClick="dupCheck()" value="check">중복확인</button>
				</div>
			</div>
			</div>



			<div class="list">
				<input type="password" name="userPwd" placeholder="비밀번호"
					onclick="isCheckingDup()" onBlur="pwdValidate(this)">
			</div>
			<div class="list">
				<input type="password" name="userPwd" placeholder="비밀번호 확인"
					onclick="isCheckingDup()" onBlur="pwdConfirm()">
			</div>

			<div class="list">
				<input type="text" name="userName" placeholder="이름">
			</div>

			
			<div class="list">

				<input type="email" name="userEmail" placeholder="이메일">	

			</div>
			<div class="list">
				<div class="button">
					<button onClick="sendJoinInfo()">회원가입</button>
				</div>
			</div>

			<div id="sublist">
				<a href="/"> 로그인 페이지 </a>
				<!-- <button onClick="modalOpen()">test</button> -->
			</div>

		</div>

	</div>

<!-- 	<div id="modal" class="modal-overlay">
		<div class="modal-window">
			<div class="title">
				<h2>비지니스 회원 추가 입력</h2>
			</div>
			<div class="close-area" onClick="modalClose()">X</div>
			<div class="content">
				<div id="loginBox">
					<div>
						<input type="text" name="businessId" placeholder="비지니스 아이디">
					</div>
					<div class="button1">
						<button name="dupCheckR" onClick="dupCheckR()" value="check">중복확인</button>
					</div>
				</div>
				<div class="list">

					<select name="rType" class="selectBox2"
						onchange="selecOption(this)">
						<option value="R">가게분류</option>
						<option value="H">한식</option>
						<option value="I">분식</option>
						<option value="C">중식</option>
						<option value="A">양식</option>

					</select>

				</div>
				<div class="list">
				<input type="text" id="rName" name="rName" placeholder="가게 이름">
				</div>
				<div class="list">
				<div class="button">
					<button onClick="sendJoinInfo()">회원가입</button>
				</div>
			</div>
			</div>
		</div>
	</div>
 -->

</body>
</html>