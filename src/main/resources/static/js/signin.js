// 비밀번호 찾기 이벤트
const siginUpArea = document.querySelector(".siginup-area"); 
const siginUpBtn = document.querySelector(".sigin-up-btn");
const loginArea = document.querySelector(".login-area");
const cancelBtn = document.querySelector(".cancel-btn");

/*
//버튼 이벤트
const accountOkBtn = document.querySelector(".account-ok-btn");
const passwordOkBtn = document.querySelector(".password-click-btn");
*/

siginUpBtn.onclick = () => {
	loginArea.classList.add("visible"); //add가 visible이라는 클래스를 추가
	siginUpArea.classList.remove("visible"); //add가 visible이라는 클래스를 삭제

}

cancelBtn.onclick = () => {
	loginArea.classList.remove("visible");
	siginUpArea.classList.add("visible");
}







