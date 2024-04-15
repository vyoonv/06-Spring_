/* 회원 정보 수정 페이지 */
const updateInfo = document.querySelector("#updateInfo"); 

// #updateInfo 요소가 존재할 때만 수행 
if(updateInfo != null ) {

    // form 제출시 
    updateInfo.addEventListener("submit", e => {
        
        const memberNickname = document.querySelector("#memberNickname"); 
        const memberTel = document.querySelector("#memberTel"); 
        const memberAddress = document.querySelectorAll("[name='memberAddress']"); 
        
        // 닉네임 유효성 검사 
        // 닉네임 미작성시 
        if(memberNickname.value.trim().length === 0) {
            alert("닉네임을 입력해주세요"); 
            e.preventDefault(); // 제출 막기 
            return; 
        }
        
        // 작성했다면 
        // 닉네임 정규식 
        let regExp = /^[가-힣\w\d]{2,10}$/;
        
        // 정규식에 부합하지 않으면 
        if( !regExp.test(memberNickname.value) ) {
            alert("닉네임이 유효하지 않습니다"); 
            e.preventDefault(); 
            return; 
        }

        //****** 닉네임 중복검사 ********
        // 테스트시 닉네임 중복 안되게하기!
        // *****************************

        // 전화번호 유효성 검사 
        // 전화번호 미작성시 
        if(memberTel.value.trim().length === 0) {
            alert("전화번호를 입력해주세요"); 
            e.preventDefault(); 
            return; 
        }

        // 작성했다면 정규식 검사  
        regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

        // 전화번호 정규식에 맞지 않으면
        if( !regExp.test(memberTel.value) ) {
            alert("전화번호가 유효하지 않습니다"); 
            e.preventDefault(); 
            return; 
        }

        // 주소 유효성 검사 
        // 주소는 입력을 안해도 ok , 안하면 전부 안해야 하고 
        // 입력하면 input창 3군데 전부 입력해야 함 
        const addr0 = memberAddress[0].value.trim().length == 0; // true/false값 리턴
        const addr1 = memberAddress[1].value.trim().length == 0; 
        const addr2 = memberAddress[2].value.trim().length == 0; 

        // 모두 true인 경우만 true 저장 
        const result1 = addr0 && addr1 && addr2;  // 아무것도 입력 x
        // 모두 false인 경우만 true 저장 
        const result2 = !(addr0 || addr1 || addr2); // 전부 입력한 경우
        // 모두 입력 또는 모두 미입력이 아니면 
        if( !(result1||result2) ) {
            alert("주소를 모두 작성 또는 미작성해주세요"); 
            e.preventDefault(); 
            return; 
        }

    }); 

}; 


// -----------------------------------------------------------------

/* 비밀번호 수정 */

// 비밀번호 변경 form 태그 
const changePw = document.querySelector("#changePw"); 

if(changePw != null) {
    // 제출 되었을 때 
    changePw.addEventListener("submit", e => {

        const currentPw = document.querySelector("#currentPw"); 
        const newPw = document.querySelector("#newPw"); 
        const newPwConfirm = document.querySelector("#newPwConfirm"); 

        // 값을 모두 입력했는지 
        let str;  // undefined 상태 
        if(currentPw.value.trim().length == 0) str = "현재 비밀번호를 입력해주세요"; 
        else if(newPw.value.trim().length == 0) str = "새 비밀번호를 입력해주세요"; 
        else if(newPwConfirm.value.trim().length == 0) str = "새 비밀번호 확인을 입력해주세요"; 

        if(str != undefined) {
            // str에 값이 대입됨 == if 중 하나 실행됨 
            alert(str); 
            e.preventDefault(); 
            return; 
        }

        // 새 비밀번호 정규식 
        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

        if( !regExp.test(newPw.value) ) {
            alert("새 비밀번호가 유효하지 않습니다"); 
            e.preventDefault(); 
            return; 
        }

        // 정규식 통과 후 새 비밀번호 == 새 비밀번호 확인 값이 같은지 확인 
        if( newPw.value != newPwConfirm.value ) {
            alert("새 비밀번호가 일치하지 않습니다"); 
            e.preventDefault(); 
            return; 
        }



    }); 

}; 


/* 탈퇴 유효성 검사 */

// 탈퇴 form 태그 
const secession = document.querySelector("#secession"); 

if(secession != null) {
    secession.addEventListener("submit", e => {

        const memberPw = document.querySelector("#memberPw"); 
        const agree = document.querySelector("#agree"); 

        // 비밀번호 입력되었는지 확인 
        if(memberPw.value.trim().length == 0) {
            alert("비밀번호를 입력해주세요"); 
            e.preventDefault(); // 제출막기 
            return; 
        }

        // 약관 동의 체크 확인 
        // checkbox 또는 radio -> checked 속성 
        // : 체크시 true, 미체크시 false 반환 
        if(!agree.checked) {
            // 체크 안됐을 때 
            alert("약관에 동의해주세요"); 
            e.preventDefault(); 
            return; 
        }

        // 탈퇴 여부 확인 
        if( !confirm("정말 탈퇴하시겠습니까?") ) {
            alert("취소 되었습니다");
            e.preventDefault();  
            return;
        }


        


    }); 
}; 

















