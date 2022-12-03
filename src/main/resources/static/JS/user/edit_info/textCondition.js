/**
* @author 최진실
*/
$(function () {
    let flag = true; // 꼭 전역변수로 선언

    //+비밀번호 일치 비교
    //*********토스트 함수*************
    function fade_in_out(element) {
        if (flag) {
            flag = false;
            if (element.attr("id") == 'u_nickname') {
                $("#toastWrap_nick").removeClass("hide");
                $("#toastWrap_nick").removeClass("fade-out");
                $("#toastWrap_nick").addClass("fade-in");
            } else if (element.attr("id") == 'user_pw') {
                $(".toastText_min1").addClass("hide");
                $("#toastWrap_pw").removeClass("hide");
                $("#toastWrap_pw").removeClass("fade-out");
                $("#toastWrap_pw").addClass("fade-in");
            } else if (element.attr("id") == 'pw_check') {
                $(".toastText_min2").addClass("hide");
                $("#toastWrap_pwcheck").removeClass("hide");
                $("#toastWrap_pwcheck").removeClass("fade-out");
                $("#toastWrap_pwcheck").addClass("fade-in");
            }
             else if (element.attr("id") == 'email') {
                $("#toastWrap_email").removeClass("hide");
                $("#toastWrap_email").removeClass("fade-out");
                $("#toastWrap_email").addClass("fade-in");
            } else if (element.attr("id") == 'tel') {
                $("#toastWrap_tel").removeClass("hide");
                $("#toastWrap_tel").removeClass("fade-out");
                $("#toastWrap_tel").addClass("fade-in");
            }

            setTimeout(function () {
                flag = true; // 추후에 사용할 수 있도록 변수값 변경
                
                if (element.attr("id") == 'u_nickname') {
                    $("#toastWrap_nick").removeClass("fade-in");
                    $("#toastWrap_nick").addClass("fade-out");
                } else if (element.attr("id") == 'user_pw') {
                    $("#toastWrap_pw").removeClass("fade-in");
                    $("#toastWrap_pw").addClass("fade-out");
                } else if (element.attr("id") == 'pw_check') {
                    $("#toastWrap_pwcheck").removeClass("fade-in");
                    $("#toastWrap_pwcheck").addClass("fade-out");
                }else if (element.attr("id") == 'email') {
                    $("#toastWrap_email").removeClass("fade-in");
                    $("#toastWrap_email").addClass("fade-out");
                }else if (element.attr("id") == 'tel') {
                    $("#toastWrap_tel").removeClass("fade-in");
                    $("#toastWrap_tel").addClass("fade-out");
                }
            }, 1000);
        }
    }

    //**********글자 수 세고 제한하는 함수**********

    function textLengthCnt(max_length, element, txt) {
        // 글자수 세기
        if (element.val().length == 0 || element.val() == '') {
            txt.text('0자');
        } else {
            txt.text(element.val().length + '자');
        }
        
        // 글자수 제한
        if (element.val().length > max_length) {
            // 500자 부터는 타이핑 되지 않도록
            element.val(element.val().substring(0, max_length));
            // 500자 넘으면 알림창 뜨도록
            fade_in_out(element);
        }
    }

    //비밀번호
    function textLengthCnt2(min_length, max_length, element, txt) {
        // 글자수 세기
        if (element.val().length == 0 || element.val() == '') {
            txt.text('0자');
        } else {
            txt.text(element.val().length + '자');
        }
        
        // 글자수 제한
        if (element.val().length > max_length) {
            // 500자 부터는 타이핑 되지 않도록
            element.val(element.val().substring(0, max_length));
            // 500자 넘으면 알림창 뜨도록
            fade_in_out(element);
        }
        //8글자 이하면 토스트 출력
        else if (element.val().length!=0 && element.val().length <= min_length) {
            if(element.attr("id") == 'user_pw'){
                $(".toastText_min1").removeClass("hide");
            }else if(element.attr("id") == 'pw_check'){
                $(".toastText_min2").removeClass("hide");
            }
        }else{
            if(element.attr("id") == 'user_pw'){
                $(".toastText_min1").addClass("hide");
            }else if(element.attr("id") == 'pw_check'){
                $(".toastText_min2").addClass("hide");
            }
        }
    }

    //비밀번호 비교
    function texteq(){
        if($('#user_pw').val().length!=0 && $('#pw_check').val().length!=0){
            let result = $('#user_pw').val()===($('#pw_check').val());
            if(result){
                $(".toastText_checkNo").addClass("blind");
                $(".toastText_checkYes").removeClass("blind");
            }else if(!result  || $('#pw_check').val().length == 0){
                $(".toastText_checkYes").addClass("blind");
                $(".toastText_checkNo").removeClass("blind");
            }
            else {
                $(".toastText_checkYes").addClass("blind");
                $(".toastText_checkNo").addClass("blind");
            }
            
        }
    }

    //***********글자 수 제한***************
    //비밀번호 글자수 제한
    $('#user_pw').keyup(function () {
        textLengthCnt2(7, 15, $(this), $('.textCount_pw'));
        if($('#pw_check').val().length>0){
            texteq();
        }else if($('#pw').val().length==0){
            $(".toastText_checkNo").addClass("blind");
        }
    });
    $('#user_pw').keydown(function () {
        textLengthCnt2(7, 15, $(this), $('.textCount_pw'));
    });
    
    //비밀번호 재입력 글자수 제한
    $('#pw_check').keyup(function () {
        textLengthCnt2(7, 15, $(this), $('.textCount_pwcheck'));
        texteq();
        if($('#pw_check').val().length==0){
            $(".toastText_checkNo").addClass("blind");
        }
    });
    $('#pw_check').keydown(function () {
        textLengthCnt2(7, 15, $(this), $('.textCount_pwcheck'));
        // texteq();
    });
    
    //닉네임 글자수 제한
    $('#u_nickname').keyup(function () {
        textLengthCnt(10, $(this), $('.textCount_nick'));
    });
    $('#u_nickname').keydown(function () {
        textLengthCnt(10, $(this), $('.textCount_nick'));
    });
    
    //이메일 글자수 제한
    $('#email').keyup(function () {
        textLengthCnt(40, $(this), $('.textCount_email'));
    });
    $('#email').keydown(function () {
        textLengthCnt(40, $(this), $('.textCount_email'));
    });
    
    //전화번호 글자수 제한
    $('#tel').keyup(function () {
        textLengthCnt(13, $(this), $('.textCount_tel'));
    });
    $('#tel').keydown(function () {
        textLengthCnt(13, $(this), $('.textCount_tel'));
    });

});
