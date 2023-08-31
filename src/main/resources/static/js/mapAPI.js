function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {

            var addr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }


            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            document.getElementById('post_num').value = data.zonecode;
            document.getElementById("address").value = addr;
            document.getElementById("rental_spot").value = addr;
            document.getElementById("address_detail").focus();
        }
    }).open();
}