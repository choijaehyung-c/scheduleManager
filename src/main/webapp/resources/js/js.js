/*
location.href
location.protocol
location.port
location.pathname #
location.search #
*/


function readyAccess(inout){
	
	if(inout==1){
		const userId = document.getElementsByName("userId")[0];
		const userPwd = document.getElementsByName("userPwd")[0];
		
		if(userId.value ==""){
			userId.focus();
			return;
		}
		if(userPwd.value ==""){
			userPwd.focus();
			return;
		}
	}
	getAjax("https://api.ipify.org?format=json","sendAccessInfo",inout);
}

function sendAccessInfo(Ip,inout){
	let form;
	const method = makeInput("hidden","method",inout);
	const publicIp = makeInput("hidden","publicIp",Ip.ip);
	const privateIp = makeInput("hidden","privateIp",location.host);
	if(inout == "1"){
	const userId = document.getElementsByName("userId")[0];
	const userPwd = document.getElementsByName("userPwd")[0];
	form = makeForm("Access","post");
	form.appendChild(userId);
	form.appendChild(userPwd);
	}else{
	form = makeForm("Logout","post");
	}
	const browser = makeInput("hidden","browser",navigator.userAgent.replace(/ /g,""));
	form.appendChild(browser);
	form.appendChild(method);
	form.appendChild(publicIp);
	form.appendChild(privateIp);
	document.body.appendChild(form);
	form.submit();
}

function getAjax(jobCode,fn,clientData=""){
	let ajax = new XMLHttpRequest();
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			if(clientData != ""){
			window[fn](JSON.parse(ajax.responseText),clientData);
			}else{
			window[fn](JSON.parse(ajax.responseText));
			}
		}
	}
	ajax.open("GET",jobCode);
	ajax.send();
}
function postAjax(jobCode,clientData,fn,type){
	let ajax = new XMLHttpRequest();
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status == 200){
			let data = ajax.responseText;
			if(type == "json"){
				try{
				window[fn](JSON.parse(data));}
				catch(error){
					window[fn](data);
				}
			}else{
				window[fn](data);
			}
		}
	}
	let formData = new FormData();
	ajax.open("POST",jobCode);
	if(type=="json"){
		ajax.setRequestHeader("Content-type", "application/json; charset=utf-8");	
	}else if(type=="file"){
		formData.append('file1',clientData.files[0],clientData.files[0].name);
		alert("filefile");
	}else{
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");//폼데이
	}
	if(type=="file"){
		ajax.send(formData);
	}else{
	ajax.send(clientData);
	}
}

function teamManage(){
	let divMain = document.getElementById("main");
	if(divMain.innerHTML.includes("getList")){
		
		let getList = document.getElementById("getList");
		let getList2 = document.getElementById("getList2");
		getList.parentNode.removeChild(getList);
		getList2.parentNode.removeChild(getList2);
		
		return;
	}
	divMain.innerHTML +="<input type=\"button\" id=\"getList\" class=\"submit\" value=\"팀 목록\" onClick=\"postAjax('schedule/teamList','','getTeamList','json')\"/>";
	divMain.innerHTML +="<input type=\"button\" id=\"getList2\" class=\"submit\" value=\"팀 추가\" onClick=\"addTeam()\"/>";
	
	
}

function getTeamList(data){
	let list = document.getElementById("memList");
	
	if(list.innerHTML.includes("submit3")){
		list.innerHTML = "";
		return;
	}
	
	let makeHtml ="";
	for(i=0 ; i <data.length ; i++){
		makeHtml += "<input type=\"button\" class=\"submit3\" value=\""+data[i].teName+"\" onclick=\"getTeamMemberList('"+data[i].teCode+"','"+data[i].teName+"')\"/>";
	}
	makeHtml += "<div id=\"memList2\"></div>";
	list.innerHTML = makeHtml;
}

function getTeamMemberList(data1,data2){
	let list = document.getElementById("memList");
	if(list.innerHTML.includes("hiddenTeCode")){
		let getTc = document.getElementById("hiddenTeCode");
		let getTn = document.getElementById("hiddenTeCode2");
		getTc.parentNode.removeChild(getTc);
		getTn.parentNode.removeChild(getTn);
	}
	list.innerHTML+= "<input type=\"hidden\" id=\"hiddenTeCode\" value=\""+data1+"\" />";
	list.innerHTML+= "<input type=\"hidden\" id=\"hiddenTeCode2\" value=\""+data2+"\" />";
	
	let jsonData= {teCode:data1} ;
	let clientData = JSON.stringify(jsonData);
	console.log(clientData);
	postAjax('schedule/teamMemberList',clientData,'showTeamMemberList','json');
}

function showTeamMemberList(data){
	let list = document.getElementById("memList2");
	let makeHtml ="<br>Team : "+data[0].teName+"<br><br>";
	for(i=0;i<data.length;i++){
		makeHtml += "name : "+data[i].mmName +"<br>type : "+data[i].typeName +"<br><br>";
		}
	list.innerHTML = makeHtml;
	postAjax('schedule/FriendsList2',data[0].teCode,'FriendsList2','json');
}

function addTeam(){
	let teamName = prompt('팀 이름을 입력하세요.');
	let sendjsonData = [];
	sendjsonData.push({teName:teamName});
	let clientData = JSON.stringify(sendjsonData);
	postAjax('schedule/addTeam',clientData,'getTeamList','json');
}

function Friends(){
	postAjax('schedule/FriendsList','','FriendsList','json');
}
function FriendsList(data){
	
	let list = document.getElementById("memList");
	let info = document.getElementById("info");
	
	if(!info.innerHTML.includes("친구추가")){
	info.innerHTML += "<input type=\"button\" id=\"addF\" class=\"submit2\" value=\"친구추가\" onClick=\"addFriends('')\"/>";
	info.innerHTML += "<input type=\"button\" id=\"confirmF\" class=\"submit2\" value=\"요청목록\" onClick=\"requestFriends()\"/>";
	}else{
		let addF = document.getElementById("addF");
		let confirmF = document.getElementById("confirmF");
		addF.parentNode.removeChild(addF);
		confirmF.parentNode.removeChild(confirmF);
		list.innerHTML = "";
		return;
	}
	
	let makeHtml ="";
	for(i=0;i<data.length;i++){	
		makeHtml += "<br>"+data[i].mmId;
	}	

	
	list.innerHTML = makeHtml;
}
function FriendsList2(data){
	let list = document.getElementById("memList");
	
	if(list.innerHTML.includes("inviteList")){
		let inviteList = document.getElementById("inviteList");
		inviteList.parentNode.removeChild(inviteList);
	}
	
	let makeHtml ="<div id=\"inviteList\"><br>초대 가능 친구 목록<br>";
	for(i=0;i<data.length;i++){	
		makeHtml += "<br><label><input type=\"checkbox\" name=\"tdb\" value=\""+data[i].mmId+"\"/>"+data[i].mmId+"</label>"
	}
	makeHtml += "<br><br><button onClick=\"inviteTeam()\" >친구초대</button></div>";
	list.innerHTML += makeHtml;
	
}

function inviteTeam(){
	let tc = document.getElementById("hiddenTeCode");
	let tn = document.getElementById("hiddenTeCode2");
	let checkbox = document.getElementsByName("tdb");
	let jsondata ="";
	let tdb1=[];
	
	for(i=0;i<checkbox.length;i++){
		if(checkbox[i].checked){
			tdb1.push({"mmId":checkbox[i].value});
		}
	}
	jsondata={teCode:tc.value,teName:tn.value,tdb:tdb1};
	let claData = JSON.stringify(jsondata);
	alert(claData);
	postAjax('schedule/inviteTeam',claData,'asd','json');
}

function asd(data){
	alert('전송완료');
}

function addFriends(data){
	alert(data+"asd");
	let frId = "";
	
	if(data != ""){
		frId=data;
	}else{
		frId = prompt('친구 아이디를 입력하세요.');
	}
	
	alert(frId);
	postAjax('schedule/addFriends',frId,'addFriendsResult','text');
}

function addFriendsResult(data){
	let divMain = document.getElementById("main");
	divMain.innerHTML="<br>"+data;
}

function requestFriends(){
	postAjax('schedule/requestFriends','','requestFriendsList','json');
}

function requestFriendsList(data){
	let list = document.getElementById("memList");
	let makeHtml ="";
	for(i=0;i<data.length;i++){
		makeHtml += "<input type=\"button\" id=\"cfcf\" class=\"submit3\" value=\""+data[i].userId+"\" onClick=\"confirmFriends('"+data[i].userId+"')\"/>"+"<br>";
	}
	list.innerHTML = makeHtml;
}

function confirmFriends(data){
	let yn ="";
	if(confirm("친구 요청을 수락하시겠습니까?")){
		yn="1";
	}else{
		
		if(confirm("친구 요청을 거절하시겠습니까?")){
			yn="2"
		}else{
			return;
		}
		
	}
	let jsonData= {confirm:yn,friend:data} ;
	let clientData = JSON.stringify(jsonData);
	postAjax('schedule/confirmFriends',clientData,'confirmFriendsLResult','json');
}

function confirmFriendsLResult(data){
	window['Friends'];
	alert(data);
}

function sendSearchInfo(){
	let search1 = document.getElementsByName("search")[0].value;
	let clientData= {search:search1};
	postAjax('schedule/searchFriend',JSON.stringify(clientData), 'getSearchResult' ,'json');
	}
	
function getSearchResult(jsonD){
	alert(JSON.stringify(jsonD));
	
	let data="<div class='title'>User List</div><br><br><br>";
	for(var i=0; i<jsonD.length;i++){
		data = data + "<span class='memList'>"+ (i+1) +") 아이디 : "+ jsonD[i].userId+" 이름 : "+jsonD[i].userName +"</span><br><br>";
		data+= "<input type='button' class='submit' value='친구 추가' onClick=\"addFriends('"+jsonD[i].userId+"')\"/>"
	}
	
		
	let userlist = document.getElementById("userlist");
	userlist.innerHTML = data;
}

function uploadFile(){
	let file = document.getElementsByName("file1")[0];
	let multitext = document.getElementsByName("multitext")[0];
	let form = document.createElement("form");
	form.setAttribute("action","upload");
	form.setAttribute("method","post");
	form.enctype="multipart/form-data";
	form.appendChild(file);
	form.appendChild(multitext);
	document.body.appendChild(form);
	form.submit();
}

function uploadFileAjax(){
	let file = document.getElementsByName("file1")[0];
	let multitext = document.getElementsByName("multitext")[0];
	let ajax = new XMLHttpRequest();
	
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4 && ajax.status == 200){
			let data = ajax.responseText;
			upupupup(data);
		}
	}
	ajax.open("POST","schedule/fileUpload");
	
	let formData = new FormData();
	console.log(file.files.length);
	
	for(i=0;i<file.files.length;i++){
	formData.append('file1',file.files[i],file.files[i].name);}
	
	formData.append('multitext',multitext.value);
	
	ajax.send(formData);
}

function upupupup(data){
	alert(data);
}

function uploadForm(){
	let upZone = document.getElementById("uploadZone");
	if(!upZone.innerHTML.includes("uploadFile2")){
	upZone.innerHTML +="<button id=\"sendFile\" onclick=\"uploadFile2()\">전송</button>";
	}
	
	if(upZone.innerHTML.includes("upFile")){
		let upFile = document.getElementsByName("upFile");
		if(upFile.length > 4){
			return;
		}
	}
	makeHtml = "<div class=\"fileDiv\"><input type=\"file\" name=\"upFile\" /><div><button onclick=\"deleteNode(this.parentNode)\">삭제</button></div></div>";
	upZone.innerHTML += makeHtml;
}
function deleteNode(node){
	node.parentNode.remove();
	if(!document.getElementById("uploadZone").innerHTML.includes("upFile")){
		document.getElementById("sendFile").remove();
	}
}

function uploadFile2(){
	let upFile = document.getElementsByName("upFile");
	let form = document.createElement("form");
	form.setAttribute("action","upload");
	form.setAttribute("method","post");
	form.enctype="multipart/form-data";
	alert(upFile.length);
	
	for(i=upFile.length-1;i>=0;i--){
	form.appendChild(upFile[i]);
	}
	
	document.body.appendChild(form);
	form.submit();
}

function charCount(word, min, max) {
	return word.length >= min && word.length <= max;
}

function isValidateCheck(word,type) {
	const codeComp = /^[a-z|A-Z]{1}[a-z|A-Z|0-9]{4,11}$/g;
	let result;
	if(type){
	result = codeComp.test(word);}
	else{
	const sEng = /[a-z]/g;
	const bEng = /[A-Z]/g;
	const num = /[0-9]/g;
	const special = /[!@#$%^&*]/g;
	let count = 0;
	if (sEng.test(word)) { count++; }
	if (bEng.test(word)) { count++; }
	if (num.test(word)) { count++; }
	if (special.test(word)) { count++; }
	result = (count>=2)?true:false;
	}
	return result;
}
function korCheck(obj, event) {
	const pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

	if (pattern.test(event.target.value.trim())) {
		obj.value = obj.value.replace(pattern, '').trim();
	}
}

function schedulePage(){
	let form = makeForm("ScheduleForm","get");
	document.body.appendChild(form);
	form.submit();
}

function makeForm(action,method,name = null){//name = null => name값이 없다면 null
	let form = document.createElement("form");
	if(name!=null){form.setAttribute("name",name);}
	form.setAttribute("action",action);
	form.setAttribute("method",method);
	return form;
}
function makeInput(type,name,value){
	let input = document.createElement("input");
	input.setAttribute("type",type);
	input.setAttribute("name",name);
	input.setAttribute("value",value);
	return input;
}
