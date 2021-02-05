'use strict';
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){
            if(pair[1]){
                return pair[1];
            }else{
                return true;
            }
        }
    }
    return false;
}

var urlType = getQueryVariable('login')
var projectIdBYUrl = getQueryVariable('projectId')
var isProjectPage = getQueryVariable('id')
var buyer = true
if(urlType){
    $('.login').show();
}
$('#loginBtn').click(function () {
    $('.login').show();
    if(isProjectPage){
        $("#sideMenu").animate({
            width: 0
        });
    }
})
// $('#logoutBtn').click(function () {
//
//     window.location.href = "/userCenter/logout"
// })
$('.closeLogin').click(function () {
    $('.login').hide();
})
$('.register').click(function () {
    $('.login').hide();
    $('.zhucez').show();
    $('.zhuceo').hide();
    $('.zhucex').hide();
})
$('.guanbi').click(function () {
    var test = null;
    $('.zhucez').hide();
    $('.zhuceo').hide();
    $('.intermediary').hide();
    $('.zhucez input,.zhuceo input,.intermediary input').val(null);
})
$('.neiron .neiron_user').click(function () {
    $('#buyerReg').show();
    $('.zhucez').hide();
    $(this).parent().addClass("zhucxzys");
    $(this).parent().siblings().removeClass("zhucxzys");
    buyer = true
})
$('.neiron .neiron_inmediar').click(function () {
    $('#agentReg').show();
    $('.zhucez').hide();
    $(this).parent().addClass("zhucxzys");
    $(this).parent().siblings().removeClass("zhucxzys");
    buyer = false
})


$('.registry input').click(function () {
    $(this).addClass("zhucxzys");
    $(this).parent().parent().siblings().children().children().removeClass("zhucxzys");
    $(this).parent().parent().addClass("zhucxzysd");
    $(this).parent().parent().siblings().removeClass("zhucxzysd");
})
$('.Forgot_pwd').click(function () {
    $('#userlogin_div').hide();
    $('#agentlogin_div').hide();
    $('#Forgot_div').show();
    $('#loginButton_user').hide();
    $('#loginButton_agert').hide();
    $('#loginButton_forgot').show();
})
$('#userlogin').click(function(){
    $('#userlogin_div').show();
    $('#agentlogin_div').hide();
    $('#Forgot_div').hide();
    $(this).addClass("Registerswitchtype");
    $('#agentlogin').removeClass("Registerswitchtype");
    $('#loginButton_forgot').hide();
    $('#loginButton_user').show();
    $('#loginButton_agert').hide();
    $('.Forgot_pwd').click(function () {
        $('#userlogin_div').hide();
        $('#agentlogin_div').hide();
        $('#Forgot_div').show();
        $('#loginButton_user').hide();
        $('#loginButton_agert').hide();
        $('#loginButton_forgot').show();
    })
})
$('#agentlogin').click(function(){
    $('#userlogin_div').hide();
    $('#agentlogin_div').show();
    $('#Forgot_div').hide();
    $(this).addClass("Registerswitchtype");
    $('#userlogin').removeClass("Registerswitchtype");
    $('#loginButton_user').hide();
    $('#loginButton_forgot').hide();
    $('#loginButton_agert').show();
    $('.Forgot_pwd').click(function () {
        $('#userlogin_div').hide();
        $('#agentlogin_div').hide();
        $('#Forgot_div').show();
        $('#loginButton_user').hide();
        $('#loginButton_agert').hide();
        $('#loginButton_forgot').show();
    })
})

$('#userlogin').each(function() {
    $(this).addClass("Registerswitchtype");
})
$('#registryAgentGender').click(function(){
    $(this).parent().parent().parent().addClass("zhucxzysd");
    $('.registry input').parent().parent().removeClass("zhucxzysd");
})

$('.login .emailSubmit').click(function(){
    $('.login .login_box').show();
    $('.login .submitEmailBox').hide()
})
//重新发送邮件
$('.login .emailConfirm').click(function(){
    var api = ''
    if(buyer){
        api = "/client/buyerReSendMail"
    }else{
        api = "/client/agentReSendMail"
    }
    //用户名
    var Usernamelogin=$("#Usernamelogin").val();
    $.ajax({
        url:api,    //请求的url地址
        dataType:"json",   //返回格式为json
        type:"GET",   //请求方式
        async:true,//请求是否异步，默认为异步，这也是ajax重要特性
        data: {
            username:Usernamelogin
        },
        success:function(res){//请求成功时处理
            $('.login .login_box').show();
            $('.login .submitEmailBox').hide()
            common.alert('Mail sent successfully !');
        },
        fail:function(){//请求失败
            console.log("fail");
        },
        error:function(){//请求出错处理
            console.log("error");
        }
    });
})
//注册成功关闭按钮
$('.register_prompt .close').click(function (){

    if(buyer){
        $('#buyerReg .register_prompt').hide()
        $('#buyerReg .inter_list').show()
    }else{
        $('#agentReg .register_prompt').hide()
        $('#agentReg .inter_list').show()
    }
    $('.reg_model').hide();
    $('.base_form').reset()
})


//用户登录
$('#loginButton_user').click(function(){
    //用户名
    var Usernamelogin=$("#Usernamelogin").val();
    //密码
    var passwordlogin = $("#Passwordloginval").val();
    //console.log(Usernamelogin,passwordlogin);
    if(Usernamelogin == ''){
        common.alert('Please enter your Email to login!');
        return false;
    }else if(passwordlogin != ''){
        $.ajax({
            url:"/client/byerLogin",    //请求的url地址
            dataType:"json",   //返回格式为json
            type:"GET",   //请求方式
            async:true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {
                username:Usernamelogin,
                password:passwordlogin,
            },
            success:function(res){//请求成功时处理
                console.log(res.data);
                switch(res.data.statusCode) {
                    case 0:
                        $.cookie("loginStatus",res.data.statusCode,{"expires":7,path:'/',Domain:"localhost"});
                        $.cookie("userid",res.data.userid,{"expires":7,path:'/',Domain:"localhost"});
                        $.cookie("identity",res.data.identity,{"expires":7,path:'/',Domain:"localhost"});
                        if(!isProjectPage){
                            if(urlType){
                                switch (urlType) {
                                    case 'application':
                                        window.location.href="/userCenter/application?projectId="+projectIdBYUrl;
                                        break;
                                    case 'openingQuotation':
                                        window.location.href="/userCenter/openingQuotation?id="+projectIdBYUrl;
                                        break;
                                    case 'daily':
                                        window.location.href="/userCenter/daily?id="+projectIdBYUrl;
                                        break;
                                    default:
                                        window.location.href="/userCenter/profile";
                                        break;
                                }
                            }else{
                                window.location.href="/userCenter/profile";
                            }
                        }else{
                            window.location.href="/project?id="+isProjectPage;
                        }
                        break;
                    case 1:
                        $('.login .login_box').hide();
                        $('.login .submitEmailBox').show()
                        break;
                    case 2:
                        common.alert(res.msg);
                        break;
                    default:
                        common.alert("System error！");
                }

            },
            fail:function(){//请求失败
                console.log("fail");
            },
            error:function(){//请求出错处理
                console.log("error");
            }
        });
    }else{
        common.alert('Please input a password!');
        return false;
    }
});


//中介登录
$('#loginButton_agert').on('click',function(){
    //用户名
    var agentnamelogin=$("#agentnamelogin").val();
    //密码
    var agentPassword = $("#agentPassword").val();

    if(agentnamelogin == ''){
        common.alert('Please enter your Email to login!');
        return false;
    }else if(agentPassword != ''){
        $.ajax({
            url:"client/agentLogin",    //请求的url地址
            dataType:"json",   //返回格式为json
            type:"GET",   //请求方式
            async:true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {
                username:agentnamelogin,
                password:agentPassword,
            },
            success:function(res){//请求成功时处理
                console.log(res);
                switch(res.data.statusCode) {
                    case 0:
                        $.cookie("loginStatus",res.data.statusCode,{"expires":7,path:'/',Domain:"localhost"});
                        $.cookie("userid",res.data.userid,{"expires":7,path:'/',Domain:"localhost"});
                        $.cookie("identity",res.data.identity,{"expires":7,path:'/',Domain:"localhost"});
                        window.location.href="/userCenter/profile";
                        break;
                    case 1:
                        common.alert(res.msg);
                        break;
                    case 2:
                        common.alert(res.msg);
                        break;
                    default:
                        common.alert("System error！");
                }

            },
            fail:function(){//请求失败
                console.log("fail");
            },
            error:function(){//请求出错处理
                console.log("error");
            }
        });
    }else{
        alert('Please input Password!');
        return false;
    }
});



//用户注册
$("#Userregistration").on('click',function(){
    //input的text
    var Password = $(this).parent().siblings().children().children("input[placeholder='PASSWORD']").val();
    var Email = $(this).parent().siblings().children().children("input[placeholder='EMAIL']").val();
    //用户名
    var username=$("#username").val();
    //用户密码
    var userpassword=$("#userpassword").val();
    //用户确认密码
    var usercpassword=$("#usercpassword").val();
    if(Password == ''){
        alert('Please input password!');
        return false;
    }else if(Email == ''){
        alert('Please input emali!');
        return false;
    }else if(userpassword==usercpassword){
        $.ajax({
            url:"client/byerRegister",    //请求的url地址
            dataType:"json",   //返回格式为json
            type:"GET",   //请求方式
            async:true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {
                username:username,
                password:userpassword,
            },
            success:function(res){//请求成功时处理
                console.log(res);

            },
            fail:function(){//请求失败
                console.log("fail");
                alert("Registration failed!");
            },
            error:function(){//请求出错处理
                console.log("error");
            }
        });
        alert("Registration succeeded, please activate!");
        $('.zhuceo').hide();
        $('.zhuceo input').val(null);
    }else{
        alert("The two passwords are not consistent!");
    }
});





//中介注册
$("#Intermediaryregistration").on('click',function(){
    //input的text
    var Password = $(this).parent().siblings().children().children("input[placeholder='PASSWORD']").val();
    var Email = $(this).parent().siblings().children().children("input[placeholder='EMAIL']").val();
    //用户名
    var agentname=$("#agentname").val();
    //用户密码
    var agentpassword=$("#agentpassword").val();
    //用户确认密码
    var agentspassword=$("#agentspassword").val();
    var Names  = $(this).parent().siblings().children().children("input[placeholder='Name']").val();
    var Passpost  = $(this).parent().siblings().children().children("input[placeholder='Last 4 digits of NRIC/Passport No.']").val();
    var Nationality  = $(this).parent().siblings().children().children("input[placeholder='Nationality']").val();
    var Address  = $(this).parent().siblings().children().children("input[placeholder='Address']").val();
    var PostCode  = $(this).parent().siblings().children().children("input[placeholder='Post Code']").val();
    var Mobile  = $(this).parent().siblings().children().children("input[placeholder='Mobile']").val();
    var Agnecy  = $(this).parent().siblings().children().children("input[placeholder='Agnecy']").val();
    var CEANo  = $(this).parent().siblings().children().children("input[placeholder='CEA No.']").val();
    var Gender  = $("#registryAgentGender").val();
    console.log(Gender);
    if(Password == ''){
        alert('Please input a password!');
        return false;
    }else if(Email == ''){
        alert('Please input a emali!');
        return false;
    }else
    if(agentpassword == agentspassword){
        $.ajax({
            url:"client/agentRegister",    //请求的url地址
            dataType:"json",   //返回格式为json
            type:"GET",   //请求方式
            async:true,//请求是否异步，默认为异步，这也是ajax重要特性
            data: {
                username:agentname,
                password:agentpassword,
                name:Names,
                gender:Gender,
                address:Address,
                ceaNo:CEANo,
                mobile:Mobile,
                company:Agnecy,
                emailAddress:Passpost,
                country:Nationality,
                postcode:PostCode
            },
            success:function(res){//请求成功时处理
                console.log(res);
                alert("Registration succeeded, please activate!");
            },
            fail:function(){//请求失败
                console.log("fail");
                alert("Registration failed!");
            },
            error:function(){//请求出错处理
                console.log("error");
            }
        });
        $('.intermediary').hide();
        $('.intermediary input').val(null);
    }else{
        alert("The two passwords are not consistent!");
    }
});


$('#loginButton_forgot').click(function () {
    var dataArr;
    if(!buyer){
        dataArr = $('#forgot_mail').serializeArray()
    }else{
        dataArr = $('#forgot_mail').serializeArray()
    }
    var data = {}
    var checkError = false
    dataArr.forEach(function(item){
        data[item.name] = item.value
        if(item.value == '')  {
            checkError = true
        }

    })
    data.emailAddress = data.username
    if(checkError) {
        common.alert('Please fill in your details')
        return false
    }

    data.role = buyer ? 'buyer' : 'agent'
        //var api = data.role === 'buyer' ? '/client/mailResetPassword' : '/client/mailResetPassword'
        $.ajax({
            url: "client/mailResetPassword",    //请求的url地址
            dataType:"json",   //返回格式为json
            type: "GET",   //请求方式
            data: data,
            success:function(res){
                //请求成功时处理
                if(res.success){
                    common.success("email has been sent to your email address, please go to confirm, if not received, you can resend")
                    $('#forgot_mail')[0].reset()
                    // $('#buyerRegForm')[0].reset()
                    // common.success("Registration is successful, please go to email to activate！");
                    // $('#submitEmailBox').show()
                    // if(buyer){
                    //     $('#buyerReg .register_prompt').show()
                    //     $('#buyerReg .inter_list').hide()
                    // }else{
                    //     $('#agentReg .register_prompt').show()
                    //     $('#agentReg .inter_list').hide()
                    // }
                }else{
                    common.alert("Please fill in your email address");
                }
            },
            fail:function(){//请求失败
                console.log("fail");
                common.alert("Registration failed！");
            },
            error:function(){//请求出错处理
                console.log("error");
            }
        });

})

$('.reg_Btn').on('click', function (){
    var dataArr;
    if(!buyer){
        dataArr = $('#agentRegForm').serializeArray()
    }else{
        dataArr = $('#buyerRegForm').serializeArray()
    }
    console.log(dataArr)
    var data = {}
    var checkError = false
    console.log(dataArr,222)
    dataArr.forEach(function(item){
        data[item.name] = item.value
        if(item.value == '')  {
            checkError = true
        }

    })
    data.emailAddress = data.username
    if(checkError) {
        common.alert('Please fill in your details')
        return false
    }
    data.role = buyer ? 'buyer' : 'agent'
    if(data.password === data.confirmPassword) {
        var api = data.role === 'buyer' ? '/client/byerRegister' : '/client/agentRegister'
        $.ajax({
            url: api,    //请求的url地址
            dataType:"json",   //返回格式为json
            type: "GET",   //请求方式
            data: data,
            success:function(res){//请求成功时处理
                if(res.success){
                    $('#agentRegForm')[0].reset()
                    $('#buyerRegForm')[0].reset()
                    // common.success("Registration is successful, please go to email to activate！");

                    if(buyer){
                        $('#buyerReg .register_prompt').show()
                        $('#buyerReg .inter_list').hide()
                    }else{
                        $('#agentReg .register_prompt').show()
                        $('#agentReg .inter_list').hide()
                    }
                }else{
                    common.alert("The email has been registered, please re-register!");
                }
            },
            fail:function(){//请求失败
                console.log("fail");
                common.alert("Registration failed！");
            },
            error:function(){//请求出错处理
                console.log("error");
            }
        });
    } else {
        common.alert("The two passwords are not consistent!");
    }
})
















