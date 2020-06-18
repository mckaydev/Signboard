function setCookie(name, value) {
    document.cookie = name + "=" + value + ";";
}

function getCookie(name) {
    let cookieName = name + "=";
    let slice = document.cookie.split(";");
    for(let i = 0; i<slice.length; i++) {
        let s = slice[i];
        while(s.charAt(0) === '') {
            s = s.substring(1, s.length);
        }
        if(s.indexOf(cookieName) === 0) {
            return s.substring(cookieName.length, s.length);
        }
    }
    return null;
}

export function setScroll() {
    let scrollPosition = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
    setCookie("scrollPosition", scrollPosition);
}

export function getScroll() {
    let scrollPosition = getCookie("scrollPosition");
    if(scrollPosition !== "" && scrollPosition !== 'undefined') {
        window.scroll(0, scrollPosition);
    }
    setCookie("scrollPosition", "");
}