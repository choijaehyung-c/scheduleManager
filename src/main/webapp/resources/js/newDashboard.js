// Date 객체 생성
let date = new Date();

const renderCalendar = () => {

	const viewYear = date.getFullYear();
	const viewMonth = date.getMonth();
	
	// year-month 채우기
	document.querySelector('.year-month').textContent = `${viewYear}년 ${viewMonth + 1}월`;
	
	// 지난 달 마지막 Date, 이번 달 마지막 Date
	const prevLast = new Date(viewYear, viewMonth, 0);
	const thisLast = new Date(viewYear, viewMonth + 1, 0);
	
	const PLDate = prevLast.getDate();
	const PLDay = prevLast.getDay();
	
	const TLDate = thisLast.getDate();
	const TLDay = thisLast.getDay();
	
	// Dates 기본 배열들
	const prevDates = [];
	const thisDates = [...Array(TLDate + 1).keys()].slice(1);
	const nextDates = [];
	
	 // prevDates 계산
	if (PLDay !== 6) {
	  for (let i = 0; i < PLDay + 1; i++) {
	    prevDates.unshift(PLDate - i);
	  }
	}
	
	// nextDates 계산
	for (let i = 1; i < 7 - TLDay; i++) {
	  nextDates.push(i);
	}
	
	 // Dates 합치기
	const dates = prevDates.concat(thisDates, nextDates);
	
	const firstDateIndex = dates.indexOf(1);
	  const lastDateIndex = dates.lastIndexOf(TLDate);
	console.log(date);
	 // Dates 정리
	dates.forEach((date, i) => {
		
	  const condition = i >= firstDateIndex && i < lastDateIndex + 1 ? 'this':'other';
	  dates[i] = "<div class ='date' onclick=\"choiceDay(this,\'"+(firstDateIndex+date-1)+"\',\'"+condition+"\')\"><span class =\'"+condition+"\'>"+date+"</span></div>";
	    //dates[i] = `<div class="date" onClick="test(this,${firstDateIndex+date-1},${condition})"><span class="${condition}"><input type="hidden" value="${condition}"/>${date}</span></div>`;	
		})
	
	document.querySelector('.dates').innerHTML = dates.join('');
	
	 const today = new Date();
	let index=-1;
    if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
    for (let date of document.querySelectorAll('.this')) {
		index++;
      if (+date.innerText === today.getDate()) {
        date.classList.add('today');
		choiceDay(date,index,'this');
		break;
      }
    }
  } 

};
renderCalendar();

function prevMonth(){
  date.setMonth(date.getMonth() - 1);
  renderCalendar();
}

function nextMonth(){
  date.setMonth(date.getMonth() + 1);
  renderCalendar();
}

function goToday(){
  date = new Date();
  renderCalendar();
}


function choiceDay(obj,index,condition){
	if(condition=='other'){
		return;
	}
	onOffDate(index);
	let sideInfo = document.getElementById("sideInfo"); 
	sideInfo.innerHTML = "<div id ='chDate'>"+replaceDate(obj.innerText)+"</div>";
}
function onOffDate(index){
	let date = document.getElementsByClassName("date");

	for(i=0; i<date.length; i++){
	date[i].style.backgroundColor="#FFFFFF";
	}
	date[index].style.backgroundColor="#ffe6d6";
}

function replaceDate(obj){
   let yearmonth = document.getElementsByClassName("year-month")[0];
   let month = yearmonth.innerText.split("년")[1];
   month = month.replace('월','');
   month = month.replace(' ','');
   if(month.length == 1){
      month = "-0"+month;
   }else{
      month = "-"+month;
   }
   
   let day = obj;
   if(day.length == 1){
      day = "-0"+ day;
   }else{
      day = "-"+day;
   }
   return yearmonth.innerText.split("년")[0]+month+day;
}

function modalClose() {
   let modal = document.getElementById("modal");
   modal.style.display = "none";
}
function modalOpen() {
   let modal = document.getElementById("modal");
   modal.style.display = "flex";
}
////////////////////////////////////////////
