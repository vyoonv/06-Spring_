/* 요소 얻어와 변수에 저장 */
const totalCount = document.querySelector("#totalCount"); 
const completeCount = document.querySelector("#completeCount"); 
const reloadBtn = document.querySelector("#reloadBtn"); 
// 할 일 추가 관련 요소
const todoTitle = document.querySelector("#todoTitle"); 
const todoContent = document.querySelector("#todoContent"); 
const addBtn = document.querySelector("#addBtn"); 
// 할 일 목록 조회 관련 요소 
const tbody = document.querySelector("#tbody"); 
// 할 일 상세 조회 관련 요소 
const popupLayer = document.querySelector("#popupLayer"); 
const popupTodoNo = document.querySelector("#popupTodoNo"); 
const popupTodoTitle = document.querySelector("#popupTodoTitle"); 
const popupComplete = document.querySelector("#popupComplete")
const popupRegDate = document.querySelector("#popupRegDate"); 
const popupTodoContent = document.querySelector("#popupTodoContent"); 
const popupClose = document.querySelector("#popupClose"); 
// 상세 조회 버튼 
const deleteBtn = document.querySelector("#deleteBtn"); 
const updateView = document.querySelector("#updateView"); 
const changeComplete =document.querySelector("#changeComplete"); 
// 수정 레이어 버튼 
const updateLayer = document.querySelector("#updateLayer"); 
const updateTitle = document.querySelector("#updateTitle"); 
const updateContent = document.querySelector("#updateContent"); 
const updateBtn = document.querySelector("#updateBtn"); 
const updateCancel = document.querySelector("#updateCancel");



function getTotalCount() {

    //비동기로 서버에서 전체 투두 개수 조회하는 fetch API 코드 작성
    fetch("/ajax/totalCount")
    .then(response => {
        return response.text(); 
        // response.text() : 응답 데이터를 문자열/숫자 형태로 변환한 결과를 가지는 Promise객체 반환
    })
    .then(result => {
        totalCount.innerText = result; 
    });

};

getTotalCount(); 

function getCompleteCount() {

    fetch("/ajax/completeCount")
    .then( response => {
        return response.text(); 
    })
    .then( result => {
        completeCount.innerText = result;
    });

};

getCompleteCount(); 

// 새로고침 버튼 
reloadBtn.addEventListener("click", () => {
    getTotalCount(); 
    getCompleteCount(); 
}); 

// 할 일 추가 
addBtn.addEventListener("click", ()=> {
    // 비동기로 할 일 요청하는 fetch 코드 작성 
    // 파라미터를 저장한 js객체 
    const param = {
        "todoTitle" : todoTitle.value, 
        "todoContent" : todoContent.value
    }; 
    
    fetch("/ajax/add", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(param) // param 객체를 JSON(string) 형태로 변환 
    })
    .then( resp => resp.text() )
    .then( temp => {

        if(temp > 0) {
            todoTitle.value =""; 
            todoContent.value=""; 
            getTotalCount(); 
        } else {
            alert("추가 실패..");
        }

    });

}); 


// 비동기로 할 일 목록 조회
const selectTodoList = () => {
    
    fetch("/ajax/selectList")
    .then(resp => resp.text())
    .then(result => {

        const todoList = JSON.parse(result); 
        tbody.innerHTML =""; 

        // #tbody에 tr/td 요소를 생성해서 내용 추가 
        for(let todo of todoList) {
            const tr = document.createElement("tr"); 
            const arr = ['todoNo', 'todoTitle', 'complete', 'regDate']; 

            for(let key of arr) {
                const td = document.createElement("td"); 
                //제목인 경우 
                if( key === 'todoTitle') {
                    const a = document.createElement("a"); 
                    // a 태그 생성해서 상세 조회 가능하게 생성 
                    a.innerText = todo[key]; // 제목을 a 태그 내용으로 대입 
                    a.href = "/ajax/detail?todoNo="+todo.todoNo;
                    td.append(a); 
                    tr.append(td); 

                    //a태그 클릭시 기본 이벤트(페이지 이동) 막기 동기 요청이라 막아야함 
                    a.addEventListener("click", (e)=>{
                        e.preventDefault(); 

                        // 할 일 상세 비동기 요청 
                        // 클릭된 a태그의 href 속성 값 불러오기 
                        selectTodo(e.target.href); 
                    });

                    continue; 
                }

                td.innerText = todo[key]; 
                // todoTitle아닌 나머지 애들 대입 
                tr.append(td); 
                //todo 늘어날때 마다 행 늘리기 
            }

            // tbody의 자식으로 tr (한 행 )추가 
            tbody.append(tr); 
            
        }

    }); 
}; 

selectTodoList(); 


//비동기로 할 일 상세 조회 
const selectTodo = (url) => {
    // 매개변수 url = "/ajax/detail?todoNo=10" 형태의 문자열 
    
    fetch(url)
    .then(resp => resp.json())
    .then(todo => {
       // const todo = JSON.parse(result); 

       console.log(todo); 

        popupTodoNo.innerText = todo.todoNo; 
        popupTodoTitle.innerText = todo.todoTitle; 
        popupTodoContent.innerText = todo.todoContent;
        popupComplete.innerText = todo.complete; 
        popupRegDate.innerText = todo.regDate; 
        
        popupLayer.classList.remove("popup-hidden"); 

        updateLayer.classList.add("popup-hidden"); 

    }); 
    
}; 


// 상세조회 창 닫기 
popupClose.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden"); 
}); 


// 삭제하기 
deleteBtn.addEventListener("click", ()=>{

    if( !confirm("정말 삭제하시겠습니까?") ) {return; }

    // 삭제할 할 일 번호 얻어오기 
    const todoNo = popupTodoNo.innerText; 

    fetch("/ajax/delete", {
        method : "DELETE", 
        headers : {"Content-Type" : "application/json"},
        body : todoNo

    })
    .then(resp => resp.text())
    .then( result => {

        if(result >0) {
            alert("삭제 되었습니다"); 
            popupLayer.classList.add("popup-hidden"); 
            getCompleteCount(); 
            getTotalCount(); 
            selectTodoList(); 

        } else {
            alert("삭제 실패.."); 
        }


    }); 

}); 


// 완료 여부 변경 버튼 
changeComplete.addEventListener("click", ()=>{

    const todoNo = popupTodoNo.innerText; 
    const complete = popupComplete.innerText === 'Y' ? 'N' : 'Y';
    
    const obj = {"todoNo" : todoNo, "complete" : complete}; 

    fetch("/ajax/changeComplete", {
        method : "PUT", 
        headers : {"Content-Type" : "application/json"}, 
        body : JSON.stringify(obj)
    })
    .then( resp => resp.text())
    .then( result => {

        if(result >0) {

            popupComplete.innerText = complete; 

            const count = Number(completeCount.innerText);
            // completeCount.innerText : text 형태니까 number 타입으로 형변환 
            if(complete === 'Y') completeCount.innerText = count +1; 
            else                 completeCount.innerText = count -1;  

            selectTodoList(); 

        } else {
            alert("완료 여부 변경 실패!"); 
        }

    }); 

}); 


// 상세 조회에서 수정버튼 클릭시 
updateView.addEventListener("click", ()=> {
    popupLayer.classList.add("popup-hidden"); 

    updateLayer.classList.remove("popup-hidden"); 

    updateTitle.value = popupTodoTitle.innerText; 
    updateContent.value = popupTodoContent.innerHTML.replaceAll("<br>", "\n"); 

    updateBtn.setAttribute("data-todo-no", popupTodoNo.innerText); 

}); 

// 수정 레이어에서 취소 버튼 클릭시 
updateCancel.addEventListener("click", ()=>{
    updateLayer.classList.add("popup-hidden"); 
    popupLayer.classList.remove("popup-hidden"); 
}); 

// 수정 레이어에서 수정 버튼 클릭시 
updateBtn.addEventListener("click", e => {
    // 서버로 전달해야 하는 값 
    const obj = {
        "todoNo" : e.target.dataset.todoNo, 
        "todoTitle" : updateTitle.value,
        "todoContent" : updateContent.value
    }; 

    fetch("/ajax/update", {
        method : "PUT", 
        headers : {"Content-Type" : "application/json"}, 
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result>0) {
            alert("수정 성공!"); 

            updateLayer.classList.add("popup-hidden"); 

            selectTodoList(); 

            popupTodoTitle.innerText = updateTitle.value; 
            popupTodoContent.innerHTML = updateContent.value.replaceAll("\n", "<br>"); 

            popupLayer.classList.remove("popup-hidden"); 

            updateTitle.value = ""; 
            updateContent.value=""; 
            updateBtn.removeAttribute("data-todo-no"); 
            
        } else {
            alert("수정실패.."); 
        }
    }); 
}); 