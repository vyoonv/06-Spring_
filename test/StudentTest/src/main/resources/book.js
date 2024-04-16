document.getElementById("#selectBookListBtn").addEventListener("click", function() {

    fetch("/book/selectAllList")
    
    .then(response => response.text())
    
    .then(data => {
    
    let bookListSection = document.getElementById("#bookListSection");
    
    bookListSection.innerHTML = "";
    
    if (data.length === 0) {
    
    bookListSection.innerHTML = "<h1>등록된 책이 없습니다</h1>";
    
    } else {
    
    const ul = document.createElement("ul");
    
    data.forEach( function(book) {
    
    var li = document.createElement("li");
    
    li.textContent = book.title;
    
    ul.appendChild(li);
    
    });
    
    bookListSection.appendChild(ul);
    
    }
    
    })
    
    .catch(error => console.error('Error:', error));
    
    });

