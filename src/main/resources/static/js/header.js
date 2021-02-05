

function handleFinance() {
    window.location.href="/finance";
};
function handleHome() {
    window.location.href="/index";
};

$('.project__imgbox-vr').on('click',function (){
    $('#vrBox #vr-full-screen iframe').attr('src', 'https://my.matterport.com/show/?m=KHLdRDNjwd7');
    $('body').css({
        overflow: 'hidden'
    });
    $('#vrBox #vr-full-screen').show();
})

$('.vr__screen-close').click(function () {
    $('body').css({
        overflow: 'auto'
    });
    $('#vrBox #vr-full-screen').hide();
});

$('#header .subcontent_vr .media').on('click',function (){
    $('#vrBox #vr-full-screen iframe').attr('src', 'https://my.matterport.com/show/?m=KHLdRDNjwd7');
    $('body').css({
        overflow: 'hidden'
    });
    $('#vrBox #vr-full-screen').show();
})
var videoMedia = null;
function mediaLoad(id) {
    var options = {
        autoplay: true
    };
    videoMedia = videojs(id, options);
    videoMedia.ready(function () {});
}
$('#header .subcontent_live .media').on('click',function (){
    $('body').css({
        overflow: 'hidden'
    });
    $('.video-container').show();
    var videoSrc = $(this).attr('data-src');
    var videoTitle = $(this).attr('data-title');
    var videoDesc = $(this).attr('data-desc');
    // let videoSrc = 'http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4'
    var videoStr = '<video id="videoMedia" class="video-js vjs-big-play-centered" controls width="800" height="600"  >\n                <source src="' + videoSrc + '"></source>\n                </video>';
    $('.vedio-media').append(videoStr);
    $('.video-title').text(videoTitle);
    $('.video-desc').text(videoDesc);
    mediaLoad("videoMedia");
})

$('#videoBox #close').click(function () {
    $('.video-container').hide();
    videoMedia.dispose();
    $('body').css({
        overflow: 'auto'
    });
});

function handleUrlNone() {
    common.alert('Coming Soon')
};
function subenumember(status) {
    console.log(status);
    if(status==0){
        alert("您还未登录，请前往登录！");
        return;
    }
    if(status==1){
        window.location.href="/userCenter";
    }
};
var getUserid = $('#header #userId').val()
function handleHeaderApplition(project) {
    var URL = "";
    if(project.linkType==1){
        URL = project.siteUrl
    }else{
        if(getUserid == null || !getUserid){
            URL = '/?login=application&projectId='+project.id
        }else{
            switch (project.openStatus) {
                case 1:
                    URL = '/userCenter/openingQuotation?id='+project.id
                    break;
                case 2:
                    URL = '/userCenter/daily?id='+project.id
                    break;
                case 4:
                    URL = '/userCenter/application?projectId='+project.id
                    break;
            }
        }

    }
    window.location.href= URL;
}

function handleHeaderVr(project) {
    var URL = "";
    if(project.linkType==1){
        URL = project.siteUrl
    }else{
        URL = "/project?id="+project.id+"&type=vrBox"
    }
    window.location.href= URL;
}
function handleHeaderLive(project) {
    var URL = "";
    if(project.linkType==1){
        URL = project.siteUrl
    }else{
        URL = "/project?id="+project.id+"&type=eventsBox"
    }
    window.location.href= URL;
}

$('#button').click(function () {
    var forgotpassword=$('#resetPassword #ForgotPassword').val()
    var repeatpassword=$('#resetPassword #RepeatPassword').val();
    var code = $('#resetPassword .id_code').val()
    var username = $('#resetPassword .id_username').val()
    var identity = $('#resetPassword .id_identity').val()
    console.log(password,code,username,repeatpassword,identity,111)
    if (forgotpassword != repeatpassword){
        common.alert("no")
    }else {
        $.ajax({
            url: "/client/resetPassword",    //请求的url地址
            dataType:"json",   //返回格式为json
            type: "GET",   //请求方式
            data: {
                username :username,
                code : code,
                newPassword : repeatpassword,
                identity : identity
            },
            success:function(res){//请求成功时处理
                if(res.success){
                    $('#resetPassword .graybox').hide();
                    $('#buyerRegForm').hide();
                    alert(111111111)
                    common.success("email has been sent to your email address, please go to confirm, if not received, you can resend");
                    window.location.href="/userCenter/profile";
                }else{
                    common.alert("Password inconsistency");
                }
            }
        });
    }
})


