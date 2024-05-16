// 프로필 이미지 추가 / 변경 / 삭제 
const profile = document.querySelector("#profile"); 

let statusCheck = -1; 
// 프로필 이미지 상태 변수 -1: 초기 // 0: 삭제 // 1: 새이미지 선택 

let backupInput; 
// 변경된 상태를 백업해서 저장할 변수 

if(profile != null) {
    const profileImg = document.querySelector("#profileImg"); 
    // 프로필 이미지가 보여지는 요소 

    let imageInput = document.querySelector("#imageInput"); 
    // 실제 업로드할 프로필 이미지 선택하는 요소 

    const deleteImage = document.querySelector("#deleteImage"); 
    // 프로필 이미지 제거하고 기본 이미지로 변경하는 요소 

    /* input type="file"의 값이 변했을 때 동작할 함수(이벤트 핸들러) */
    const changeImageFn = e => {
        

        const maxSize = 1024 * 1024 * 5; 
        // 업로드 가능한 파일 최대 크기 지정하여 필터링 
        const file = e.target.files[0];
        
        // 업로드된 파일이 없다면(취소한 경우)
        if(file == undefined) {
            // 백업의 백업본 
            const temp = backupInput.cloneNode(true);  // 백업용 input태그  
            // cloneNode() : 자식요소 복사해서 붙여넣기 
            imageInput.after(backupInput); 
            // input 요소 다음에 백업 요소 추가 
            imageInput.remove(); 
            // 화면에 존재하는 기존 input 제거 
            imageInput = backupInput; 
            // 화면에 추가된 백업본에는 이벤트 리스너가 존재하지 않기 때문에 추가 

            imageInput.addEventListener("change", changeImageFn); 
            // "change" : 변동사항이 생기면 이벤트 발생 

            backupInput = temp; 
            // temp 변동사항을 backup에 대입
            return; 
        }

        // 선택된 파일이 최대 크기를 초과한 경우 
        if(file.size > maxsize) {

            alert("5MB 이하의 이미지 파일을 선택해 주세요."); 

            if(statusCheck == -1) {
                // 변경된적 없는 초기 상태에서 5mb 초과하는 이미지를 선택한 경우 
                imageInput.value = ''; 
            } else {
                // 기존에 변경하려고 선택한 이미지가 있는데 
                // 다음에 선택한 이미지가 최대 크기를 초과한 경우 
                // 비우지 말고 그 전에 선택한 이미지가 계속 보이게 
                
                // 백업의 백업본 
                const temp = backupInput.cloneNode(true); 
                imageInput.after(backupInput); 
                // after() : 선택한 요소 뒤에 새 요소를 추가하거나, 다른 곳에 있는 요소를 이동

                imageInput.remove(); 
                // 화면에 존재하는 기존 input제거 
                imageInput = backupInput; 
                // imageInput 변수에 백업을 대입해서 대신 하도록 함 
                imageInput.addEventListener("change", changeImageFn); 
                // 화면에 추가된 백업본에는 이벤트 리스너가 존재하지 않기 때문에 추가 
                backupInput = temp; 
                // 한 번 화면에 추가된 요소(backupInput)은 재사용 불가능하므로 
                // 백업본이 temp를 backupInput으로 변경 
            }

            return; 
        }

        // 선택된 이미지 미리보기 
        // js에서 파일을 읽을 때 사용하는 객체, 파일을 읽고 클라이언트 컴퓨터에 저장할 수 있음
        // FileReader 객체는 웹 어플리케이션에서 비동기적으로 파일의 내용을 읽을 수 있게 해줌
        const reader = new FileReader(); 
         
        // 선택한 파일을 읽어와 result 변수에 저장
        reader.readAsDataURL(file); // readAsDataURL업로드 파일 url을 읽어오기 
        
        reader.addEventListener("load", e => {

            const url = e.target.result; // reader.result 

            profileImg.setAttribute("src", url); // 프로필 이미지에 src 속성으로 url값 세팅 

            statusCheck = 1; // 새 이미지 선택 상태를 기록 
            
            backupInput = imageInput.cloneNode(true); // 파일이 선택된 input을 복제해서 백업

        }); 
    }; 

    // 2) imageInput에 change 이벤트로 changeImageFn 등록 
    // change 이벤트 : 새로운 값이 기존 값과 다를 경우 발생 
    imageInput.addEventListener("change", changeImageFn); 

    // 4) x 버튼 클릭시 기본 이미지로 변경 
    deleteImage.addEventListener("click", ()=> {

        profileImg.src = "/images/user.png"; // 프로필 이미지를 기본 이미지로 변경 
        imageInput.value = ''; // input에 저장된 값을 ''빈칸으로 변경 
        backupInput = undefined; // 백업본도 삭제 
        statusCheck = 0; // 삭제 상태임을 기록 

    }); 

    // 5) profile form 제출시 
    profile.addEventListener("submit", e => {
        let flag = true; 

        if(loginMemberProfileImage == null && statusCheck == 1) flag = false; 
        // 기존 프로필 이미지 없고 새 이미지 선택된 경우 
        if(loginMemberProfileImage != null && statusCheck == 0) flag = false; 
        // 기존 프로필 이미지 있었는데 삭제한 경우 
        if(loginMemberProfileImage != null && statusCheck == 1) flag = false; 
        // 기존 프로필 이미지 있었고 새 이미지 선택된 경우 

        // 나머지 경우 == 기존 상태에서 변경사항 없는 경우  
        if(flag) {
            e.preventDefault(); 
            alert("이미지 변경 후 클릭하세요");
        }

    }); 

}