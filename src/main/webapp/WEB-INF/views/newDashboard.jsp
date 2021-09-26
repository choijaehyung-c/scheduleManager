<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YNHDS NEW DASHBOARD</title>
    <link rel="stylesheet" href="resources/css/calendar.css">

<body>
<div class = "wideZone" >

	<div class = "sideForm">
	<div style="width: 100%; height: 50px;" class="infoBlank">
	
	<input type="button" class="sideAddBtn" value = "add Event+" onclick="modalOpen()" />
	</div>
	<div id="sideInfo" class="infoBlank">
<!-- style="width:100%; height:620px;"  -->
	</div>
	</div>
	<div class = "MngForm">
	    <div class="calendar">
	        <div class="header">
	            <div class="year-month"></div>
	            <div class="nav">
	                <button class="nav-btn go-prev" onclick="prevMonth()">&lt;</button>
	                <button class="nav-btn go-today" onclick="goToday()">Today</button>
	                <button class="nav-btn go-next" onclick="nextMonth()">&gt;</button>
	            </div>
	        </div>
	        <div class="main">
	            <div class="days">
	                <div class="day">일</div>
	                <div class="day">월</div>
	                <div class="day">화</div>
	                <div class="day">수</div>
	                <div class="day">목</div>
	                <div class="day">금</div>
	                <div class="day">토</div>
	            </div>
	            <div class="dates"></div>
	        </div>
	    </div>
	</div>
	
	<div id="modal" class="modal-overlay">
      <div class="modal-window">
      <div class="close-area" onClick="modalClose()">X</div>
      <div class="content">
       		<!--팀 콤보박스, 제목,내용,날짜,사진,버튼 -->
       		<div id ="teamChoice">
       		</div>
       		<div id ="sdTitle">
       			<input type="text" placeholder="event name"/>
       		</div>
       		<div id = "date">
        	</div>
       		<div id = "content">
       		</div>
        	<div id = "picture">
        	</div>
          <select name="tags">
              <option value="event">event</option>
              <option value="important">important</option>
              <option value="birthday">birthday</option>
              <option value="festivity">festivity</option>
           </select>
       		<div id = "sendbtn">
       		</div>
      </div>
      </div>
      </div>
       
   </div>
    <script type="text/javascript" src="resources/js/newDashboard.js"></script>
</body>
</html>
