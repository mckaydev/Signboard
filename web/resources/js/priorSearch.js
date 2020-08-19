function createFunction() {
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
    bmkForm.appendChild(bmkHistory);

    delForm.appendChild(delInput);
    delForm.appendChild(delHistory);

    infoBox.appendChild(bmkForm);
    infoBox.appendChild(delForm);
    infoBox.appendChild(infoImgArea);
    infoBox.appendChild(infoTextArea);
    // infoBox.appendChild(document.createTextNode(jsonFile.length));

    infoImgArea.appendChild(infoImg);
    infoTextArea.appendChild(infoText);

    let storeName = document.createElement("p");
    let storeMenu = document.createElement("p");
    let storePhone = document.createElement("p");
    let aLineReview = document.createElement("p");
    infoText.appendChild(storeName);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(storeMenu);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(storePhone);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(aLineReview);

    let test = document.getElementById("container").firstChild;

    for(let i = 0; i < jsonFile.length; i++) {
        infoBox.id = "infoBox" + i;
        infoImg.src = "/img/" + jsonFile[i]['imageFileName'];
        bmkInput.value = jsonFile[i]['imageFileName'];
        delInput.value = jsonFile[i]['imageFileName'];
        bmkIs.value = jsonFile[i]['isBookmarked'];

        storeName.textContent = jsonFile[i]['storeName'];
        storeMenu.textContent = jsonFile[i]['storeMenu'];
        storePhone.textContent = jsonFile[i]['storePhone'];
        aLineReview.textContent = jsonFile[i]['aLineReview'];
        if (jsonFile[i]['isBookmarked'] === 0) {
            bmkHistory.innerText = "북마크 추가";
        } else {
            bmkHistory.innerText = "북마크 삭제";
        }

        document.getElementById("container").insertBefore(infoBox.cloneNode(true), test);
    }
}
