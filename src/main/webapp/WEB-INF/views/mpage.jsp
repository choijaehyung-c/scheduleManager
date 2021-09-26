<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="${pageContext.request.contextPath}/resources/js/js.js"></script>
<meta charset="UTF-8">
<title>mypage</title>
<link href="resources/css/mpage.css" type="text/css" rel="stylesheet" />
</head>
<body>

	<div class="main">
		<div class="title">${user} </div>
		<div id="stitle">my page</div>
	</div>

	<div class="box">
	<!-- uploadFileAjax uploadFile -->
	<div id="info">
		<img src="/resources/file/${tempAddress}" 
			style="width: 160px; height: auto; margin-bottom:30px;" />
			
		<div >${user} 프로필<input type="file" multiple="multiple" name="file1" /><input type="text" name="multitext"/>
		<button id="upup" onclick="uploadFileAjax()">업로드</button></div>
		
		
		<div >____________</div>		
		<div>이름    : ${userName} </div>
		<div>이메일   : ${userEmail} </div>
	<input type="button" class="submit2" value="친구목록" onClick="Friends()" />
	<input type="button" class="submit2" value="로그아웃" onClick="readyAccess('-1')" />

	</div>
	<div id="main">
		<input type="button" class="submit" value="팀관리" onClick="teamManage()"/> 
		<input type="button" class="submit" value="스케줄관리" onClick="schedulePage()"/>
		<div id="accesszone">
		<input type="text"  name="search" class="box" placeholder="검색어를 입력하세요."/>
		<input type="button" class="submit" value="검색하기" onClick="sendSearchInfo()"/>
		<div id="userlist"></div>
	</div>
	</div>
	<div id="memList">
	</div>
	

	</div>
							
<a href="ScheduleForm">스케줄</a>

</body>

</html>