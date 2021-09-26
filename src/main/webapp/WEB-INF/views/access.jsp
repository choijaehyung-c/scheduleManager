<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>cleverc.online</title>
<script>${msg}</script>
<script src="resources/js/js.js"></script>
<script>
	${message}
</script>
<link href="resources/css/loginc.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div id="form">
		<h1>Schedule</h1>
		<!--<div class="row">
			<div class="userType">일반 유저 로그인</div>
			<div class="userType">
				<input type="checkbox" name="changeType" id="box_1" onClick="changeUserType()" value="G"> <label for="box_1"></label>
				<input type="hidden" name="userType" value="G">
			</div>
		</div> -->

		<div id="content">
<!-- onKeyUp="korCheck(this, event)" -->
			<div class="box">
				<input type="text" name="userId" placeholder="아이디" onKeyUp="korCheck(this, event)"> <input
					type="password" name="userPwd" placeholder="비밀번호">
			</div>
			<div class="button">
				<button onClick="readyAccess('1')">로그인</button>
			</div>

			<div id="sublist">
				   <a href="JoinForm"> 회원 가입 </a>
			</div>
		</div>
	</div>
</body>
</html>