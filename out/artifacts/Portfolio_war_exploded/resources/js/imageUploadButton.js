$(document).ready(function() {
    let fileTarget = $('.fileBox .upload-hidden');
    fileTarget.on('change', function() {
        let filename
        // 값이 변경 되면
        if(window.FileReader) {
            // modern browser
            filename = $(this)[0].files[0].name;
        } else {
            // old IE
            filename = $(this).val().split('/').pop().split('\\').pop();
            // 파일 명만 추출
        }
        // 추출한 파일명 삽입
        $(this).siblings('.upload-name').val(filename);
    });
});
