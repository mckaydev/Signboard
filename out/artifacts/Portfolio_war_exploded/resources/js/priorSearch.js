function createFunction() {
    let infoBox = document.createElement("div");
    infoBox.className = "infoBox";

    let infoImgArea = document.createElement("div");
    infoImgArea.className = "infoImgArea";
    let infoTextArea = document.createElement("div");
    infoTextArea.className = "infoTextArea";

    let infoImg = document.createElement("img");
    infoImg.className = "infoImg";
    let infoText = document.createElement("p");
    infoText.className = "infoText";

    infoBox.appendChild(infoImgArea);
    infoBox.appendChild(infoTextArea);
    // infoBox.appendChild(document.createTextNode(jsonFile.length));

    infoImgArea.appendChild(infoImg);
    infoTextArea.appendChild(infoText);

    let storeName = document.createElement("p");
    let storeMenu = document.createElement("p");
    let storePhone = document.createElement("p");
    infoText.appendChild(storeName);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(storeMenu);
    infoText.appendChild(document.createElement("br"));
    infoText.appendChild(storePhone);

    let test = document.getElementById("container").firstChild;

    for(let i = 0; i < jsonFile.length; i++) {
        infoBox.id = "infoBox" + i;
        infoImg.src = "/img/" + jsonFile[i]['imageFileName'];

        storeName.textContent = jsonFile[i]['storeName'];
        storeMenu.textContent = jsonFile[i]['storeMenu'];
        storePhone.textContent = jsonFile[i]['storePhone'];

        document.getElementById("container").insertBefore(infoBox.cloneNode(true), test);
    }
}
