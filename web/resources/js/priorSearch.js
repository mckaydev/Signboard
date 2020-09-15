function createFunction(cPage) {
    let infoBox = document.createElement("div");
    infoBox.className = "infoBox";

    let infoImgArea = document.createElement("div");
    infoImgArea.className = "infoImgArea";
    let infoTextArea = document.createElement("div");
    infoTextArea.className = "infoTextArea";


    let bmkForm = document.createElement("form")
    bmkForm.action = "bookmarkHistory";
    bmkForm.method = "post";
    let bmkInput = document.createElement("input");
    bmkInput.style = "display: none";
    bmkInput.type = "text";
    bmkInput.name = "imageFileName";
    let bmkIs = document.createElement("input");
    bmkIs.style = "display: none";
    bmkIs.type = "text";
    bmkIs.name = "isBookmarked";
    let bmkHistory = document.createElement("button")
    bmkHistory.className = "bmkHistory";
    bmkHistory.type = "submit";
    // bmkHistory.innerText = "bookmark";

    let curPage1 = document.createElement("input");
    curPage1.style = "display: none";
    curPage1.name = "curPage";

    let curPage = document.createElement("input");
    curPage.style = "display: none";
    curPage.name = "curPage";
    // curPage.type = "text";

    let delForm = document.createElement("form")
    delForm.action = "deleteHistory";
    delForm.method = "post";
    let delInput = document.createElement("input");
    delInput.style = "display: none";
    delInput.type = "text";
    delInput.name = "imageFileName";
    let delHistory = document.createElement("button")
    delHistory.className = "delHistory";
    delHistory.type = "submit";
    delHistory.innerText = "기록 삭제";


    let infoImg = document.createElement("img");
    infoImg.className = "infoImg";
    let infoText = document.createElement("p");
    infoText.className = "infoText";


    bmkForm.appendChild(bmkInput);
    bmkForm.appendChild(bmkIs);
    bmkForm.appendChild(curPage1);
    bmkForm.appendChild(bmkHistory);

    delForm.appendChild(delInput);
    delForm.appendChild(curPage);
    delForm.appendChild(delHistory);

    infoBox.appendChild(bmkForm);
    infoBox.appendChild(delForm);
    infoBox.appendChild(infoImgArea);
    infoBox.appendChild(infoTextArea);
    // infoBox.appendChild(document.createTextNode(jsonFile.length));

    infoImgArea.appendChild(infoImg);
    infoTextArea.appendChild(infoText);

    let storeName = document.createElement("p");
    let roadAddress = document.createElement("p");
    let rate = document.createElement("p");
    let aLineReview = document.createElement("p");
    aLineReview.id = "aLineReview";
    infoText.appendChild(storeName);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(roadAddress);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(rate);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(aLineReview);

    let test = document.getElementById("container").firstChild;

    console.log('before cPage: ' + cPage);
    console.log('jsonFile.length: ' + jsonFile.length);
    if (cPage * contentPerPage >= jsonFile.length) {
        cPage = Math.floor(jsonFile.length / contentPerPage) - 1;
        currentPage = cPage;
        console.log('cPage--');
    }
    console.log('after cPage: ' + cPage);

    let minContent = cPage * contentPerPage;
    let maxContent = minContent + contentPerPage;
    console.log('maxContent: ' + maxContent);

    for(let i = minContent, j = 0; i < maxContent && i < jsonFile.length; i++, j++) {
        infoBox.id = "infoBox" + j;
        infoImg.src = "/img/" + jsonFile[i]['imageFileName'];
        bmkInput.value = jsonFile[i]['imageFileName'];
        delInput.value = jsonFile[i]['imageFileName'];
        curPage.value = currentPage;
        curPage1.value = currentPage;
        console.log('curPageBox: ' + curPage.value);
        bmkIs.value = jsonFile[i]['isBookmarked'];

        storeName.textContent = "상호명: " + jsonFile[i]['storeName'];
        roadAddress.textContent = "주소: " + jsonFile[i]['roadAddress'];
        rate.textContent = "별점: " + jsonFile[i]['rate'] + "점";
        aLineReview.textContent = "한줄평: " + jsonFile[i]['aLineReview'];
        if (jsonFile[i]['isBookmarked'] === 0) {
            bmkHistory.innerText = "북마크 추가";
        } else {
            bmkHistory.innerText = "북마크 삭제";
        }

        document.getElementById("container").insertBefore(infoBox.cloneNode(true), test);
    }
}

function pageButton() {
    let totalPage = Math.floor((jsonFile.length - 1) / contentPerPage)
    let minPage = Math.floor(currentPage / pagePerPages) * pagePerPages;
    let maxPage = (minPage + (pagePerPages - 1)) > totalPage ? totalPage : minPage + (pagePerPages - 1);

    console.log('minPage: ' + minPage);
    console.log('maxPage: ' + maxPage);
    console.log('totalPage: ' + totalPage);

    let ul = document.createElement("ul");
    ul.id = "pageList";
    ul.className = "pageList";
    document.getElementById("container").appendChild(ul);

    let pageUl = document.getElementById("pageList");
    if (minPage > 0) {
        let page = document.createElement("button")
        page.innerText = "<";
        page.style = "width: 15px; margin-right: 5px;";
        page.onclick = function() { prevPage() };
        pageUl.appendChild(page);
    }

    for (let i = minPage; i <= maxPage; i++) {
        let page = document.createElement("button")
        page.innerText = String(i + 1);
        page.style = "width: 25px";
        if (i === currentPage) {
            page.style = "width: 25px; background-color: white;";
        }
        page.onclick = function() { paging(i) };
        pageUl.appendChild(page);
    }

    if (maxPage < totalPage) {
        let page = document.createElement("button")
        page.innerText = ">";
        page.style = "width: 15px; margin-left: 5px;";
        page.onclick = function() { nextPage() };
        pageUl.appendChild(page);
    }
}

function listPageButton() {
    let totalPage = Math.floor((listSize - 1) / contentPerPage)
    let minPage = Math.floor(currentPage / pagePerPages) * pagePerPages;
    let maxPage = (minPage + (pagePerPages - 1)) > totalPage ? totalPage : minPage + (pagePerPages - 1);

    console.log('pathname: ' + location.pathname);
    // pagingForm.method = "get";

    let ul = document.createElement("ul");
    ul.id = "pageList";
    ul.className = "pageList";

    if (minPage > 0) {
        let page = document.createElement("button")
        page.innerText = "<";
        page.style = "width: 15px; margin-right: 5px;";
        page.onclick = function() { listPrevPage() };
        ul.appendChild(page);
    }

    for (let i = minPage; i <= maxPage; i++) {
        let page = document.createElement("button")
        page.innerText = String(i + 1 + 'p');
        page.style = "width: 25px";
        if (i === currentPage) {
            page.style = "width: 25px; background-color: white;";
        }
        page.onclick = function() { listPaging(i) };

        ul.appendChild(page);

        // let a = document.createElement("a");
        // a.href = location.pathname + '?curPage=' + i;
        // a.innerText = String(i);
        // ul.appendChild(a);
    }

    if (maxPage < totalPage) {
        let page = document.createElement("button")
        page.innerText = ">";
        page.style = "width: 15px; margin-left: 5px;";
        page.onclick = function() { listNextPage() };
        ul.appendChild(page);
    }

    document.getElementById("container").appendChild(ul);
}

function sqlPageButton() {

}

function prevPage() {
    currentPage = (Math.floor(currentPage / pagePerPages) - 1) * pagePerPages;
    document.getElementById("pageList").remove();
    pageButton();
    paging(currentPage);
}

function nextPage() {
    currentPage = (Math.floor(currentPage / pagePerPages) + 1) * pagePerPages;
    document.getElementById("pageList").remove();
    pageButton();
    paging(currentPage);
}

function listPrevPage() {
    currentPage = (Math.floor(currentPage / pagePerPages) - 1) * pagePerPages;
    document.getElementById("pageList").remove();
    listPageButton();
    listPaging(currentPage);
}

function listNextPage() {
    currentPage = (Math.floor(currentPage / pagePerPages) + 1) * pagePerPages;
    document.getElementById("pageList").remove();
    listPageButton();
    listPaging(currentPage);
}

function paging(cPage) {
    currentPage = cPage;
    for (let i = 0; i < contentPerPage; i++) {
        try {
            let infoBox = document.getElementById("infoBox" + i);
            infoBox.remove();
        } catch (typeError) {
            console.log(typeError);
        }
    }
    createFunction(cPage);
    document.getElementById("pageList").remove();
    pageButton();
    console.log('currentPage: ' + currentPage);
}

function listPaging(cPage) {
    currentPage = cPage;
    console.log(currentPage);
    console.log('pathname: ' + location.pathname);
    console.log(location.pathname + '?curPage=' + currentPage);
    location.href = location.pathname + '?curPage=' + currentPage;
}

function sqlPaging() {

}