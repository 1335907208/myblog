'use strict';

var Project = function Project() {};
Project.prototype.init = function () {
    this.initSlider();
    this.initGallery();
    this.initVideo();
    this.initSitePlan();
    this.initFloorPlan();
    this.initVR();
    this.init360View();
    this.initEvent();
    this.initNav();
};
// 1. 首屏Slider
Project.prototype.initSlider = function () {
    var sliderBoxSwiper = new Swiper('#sliderBox .swiper-container', {
        autoplay: false,
        navigation: {
            nextEl: '#sliderBox .swiper-button-next',
            prevEl: '#sliderBox .swiper-button-prev'
        },
        pagination: {
            el: '#sliderBox .swiper-pagination',
            clickable: true,
            renderBullet: function renderBullet(index, className) {
                // return '<div class="page-btn '+className+'"></div>';
                return '<div class="page-btn ' + className + '"><span class="tip">' + (index + 1) + '</span></div>';
            }
        }
    });
};

// 2. Gallery
Project.prototype.initGallery = function () {

    // Gallery Model
    lightGallery($('#certify .swiper-wrapper')[0], {
        // href: temp,
        thumbnail: true,
        zoom: true,
        scale: 3,
        selector: '.swiper-slide:not(.swiper-slide-duplicate)',
    });
    lightGallery($('#locationBox .location__pic')[0], {
        // href: temp,
        thumbnail: true,
        zoom: true,
        scale: 3
    });
    // Gallery Swiper
    var galleryBoxSwiper = new Swiper('#galleryBox .swiper-container', {
        watchSlidesProgress: true,
        // slidesPerView: 2,
        slidesPerView: 'auto',
        centeredSlides: true,
        loop: true,
        loopedSlides: 3,
        autoplay: true,
        navigation: {
            nextEl: '#galleryBox .swiper-button-next',
            prevEl: '#galleryBox .swiper-button-prev'
        },
        pagination: {
            el: '#galleryBox .swiper-pagination',
            clickable: true,
            renderBullet: function renderBullet(index, className) {
                return '<div class="page-btn ' + className + '"></div>';
                // return '<div class="page-btn '+className+'"><span class="tip">' + (index + 1) + '</span></div>';
            }
        },
        on: {
            progress: function progress(_progress) {
                for (var i = 0; i < this.slides.length; i++) {
                    var slide = this.slides.eq(i);
                    var slideProgress = this.slides[i].progress;
                    var modify = 1;
                    if (Math.abs(slideProgress) > 1) {
                        modify = (Math.abs(slideProgress) - 1) * 0.3 + 1;
                    }
                    var translate = slideProgress * modify * 260 + 'px';
                    var scale = 1 - Math.abs(slideProgress) / 5;
                    var zIndex = 999 - Math.abs(Math.round(10 * slideProgress));
                    slide.transform('translateX(' + translate + ') scale(' + scale + ')');
                    slide.css('zIndex', zIndex);
                    slide.css('opacity', 1);
                    if (Math.abs(slideProgress) > 3) {
                        slide.css('opacity', 0);
                    }
                }
            },
            setTransition: function setTransition(transition) {
                for (var i = 0; i < this.slides.length; i++) {
                    var slide = this.slides.eq(i);
                    slide.transition(transition);
                }
            }
        }

    });

};

Project.prototype.createGalleryData = function () {
    var temp = [];
    for (var i = 1; i < 9; i++) {
        temp.push({
            "src": '/img/gallery/banner' + i + '.jpg',
            'thumb': '/img/gallery/banner' + i + '.jpg'
            // 'subHtml': '<h4>Fading Light</h4><p>Classic view from Rigwood Jetty on Coniston Water an old archive shot similar to an old post but a little later on.</p>'
        });
    }
    lightGallery($('#certify .swiper-wrapper')[0], {
        // href: temp,
        thumbnail: true,
        zoom: true,
        scale: 3
    });
    lightGallery($('#locationBox .location__pic')[0], {
        // href: temp,
        thumbnail: true,
        zoom: true,
        scale: 3
    });
};
// Project.prototype.createSiteplanData = function () {
//     var temp = [],
//         infoList = ['1st Floor', '7th Floor', '27th Floor'];
//     for (var i = 0; i < 3; i++) {
//         temp.push({
//             "src": '/media/siteplan/' + i + '.jpg',
//             'thumb': '/media/siteplan/' + i + '.jpg',
//             'subHtml': infoList[i]
//         });
//     }
//     lightGallery($('#siteplanBox .siteplan__pic')[0], {
//         href: temp,
//         thumbnail: true,
//         zoom: true,
//         scale: 3
//     });
// };

// 3. video
Project.prototype.initVideo = function () {
    var videoMedia = null;
    function mediaLoad(id) {
        var options = {
            autoplay: true
        };
        videoMedia = videojs(id, options);
        videoMedia.ready(function () {});
    }
    // function mediaLoads() {
    //     var options = {
    //         autoplay: true
    //     };
    //     videoMedia = videojs(options);
    //     videoMedia.ready(function () {});
    // }
    $('.video-player__pic,.video-player__play').bind('click', function () {
        $('.video-container').show();
        var videoSrc = $('.video-player').attr('data-src');
        var type = $('.video-player').attr('data-urlType');
        var videoTitle = $('.video-player').attr('data-title');
        var videoDesc = $('.video-player').attr('data-desc');
        // let videoSrc = 'http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4'
        console.log(type,videoSrc,'++++++')
        if(type == 1){
            var videoStr = '<video id="videoMedia" class="video-js vjs-big-play-centered" controls width="800" height="600"  >\n                <source src="' + videoSrc + '"></source>\n                </video>';
            $('.vedio-media').append(videoStr);
            mediaLoad("videoMedia");
        }else if(type == 2){
            var videoStrs = '<iframe id="videoMedia"  style="width: 800px; height: 600px;" allowtransparency="true" src="' + videoSrc + '" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>'
            // th:src="${projectEvent.liveUrlWorld}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            $('.vedio-media').append(videoStrs);
            mediaLoad("videoMedia");
        }
        $('.video-title').text(videoTitle);
        $('.video-desc').text(videoDesc);

    });
    $('#close').click(function () {
        $('.video-container').hide();
        videoMedia.dispose();
        $('.vedio-media').innerHTML='';
        // $('.vedio-iframe').innerHTML='';
        $('.vedio-media').empty();
        // $('.vedio-iframe').empty();
        // $('.video-player__pic').off()
    });
    $('.video-item').click(function () {
        $('.video-container').show();
        // let videoSrc = 'http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4'
        var videoSrc = $(this).attr('data-src');
        var type = $(this).attr('data-urlType');
        var videoTitle = $(this).attr('data-title');
        var videoDesc = $(this).attr('data-desc');
        console.log(type,videoSrc,'++++++')
        if(type == 1){
            var videoStr = '<video id="videoMedia" class="video-js vjs-big-play-centered" controls width="800" height="600"  >\n                <source src="' + videoSrc + '"></source>\n                </video>';
            $('.vedio-media').append(videoStr);
            mediaLoad("videoMedia");
        }else if(type == 2){
            var videoStrs = '<iframe id="videoMedia" style="width: 800px; height: 600px;" allowtransparency="true" src="' + videoSrc + '" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>'
            // th:src="${projectEvent.liveUrlWorld}" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            $('.vedio-media').append(videoStrs);
            mediaLoad("videoMedia");
        }
        // var videoStr = '<video id="videoMedia" class="video-js vjs-big-play-centered" controls width="800" height="600"  >\n                <source src="' + videoSrc + '"></source>\n                </video>';
        // $('.vedio-iframe').append(videoStrs);
        $('.video-title').text(videoTitle);
        $('.video-desc').text(videoDesc);
    });
    //events
    // var eventVideo = null;
    // function eventVideoLoad(id) {
    //     eventVideo = videojs(id);
    //     eventVideo.ready(function () {});
    // }
    // eventVideoLoad('eventVideo');
};

// 4. Site Plan
Project.prototype.initSitePlan = function () {
    var $sitePlanDom = $('.siteplan__btn-item');
    $sitePlanDom.click(function () {
        // var src = $(this).data('src');
        // $('.siteplan__pic img').attr('src', src);
        var index = $(this).data('index');
        var $sitePlanImgDom = $('.siteplan__pic a[data-index=' + index + ']');
        $sitePlanDom.removeClass('siteplan__btn-item_active');
        $sitePlanImgDom.addClass('active').siblings().removeClass('active')
        $(this).addClass('siteplan__btn-item_active');
    });
    // $('.siteplan__btn a:nth-of-type(1)').each(function(){
    //     $(this).addClass('siteplan__btn-item_active');
    // });
    var temp = []
    $sitePlanDom.each(function (index, item) {
        temp.push({
            "src": $(item).data('src'),
            'thumb': $(item).data('src'),
            'subHtml': $(item).text()
        });
    })
    lightGallery($('#siteplanBox .siteplan__pic')[0], {
        href: temp,
        thumbnail: true,
        zoom: true,
        scale: 3
    });
};

// 5. Floor Plan
Project.prototype.initFloorPlan = function () {
    var typeArr = []
    var filterUnitArr = []
    var check_br = ''
    // 通过BR筛选出对应的户型
    function selectTypeByBr (br) {
        if(!unitType) return []
        var _unitTypeArr = []
        if(!br){
            return []
        }
        unitType.forEach(function(item){
            if(item.bedroom === br) {
                _unitTypeArr.push(item.id)
            }
        })
        return _unitTypeArr
    }
    // 通过户型数组筛选出对应的Block（去重）
    function selectBlockByTypes(typeArr) {
        if(!units) return []
        var _blockArr = []
        if(typeArr.length>0){
            units.forEach(function(item){
                if(typeArr.indexOf(parseInt(item.typeId)) > -1 && _blockArr.indexOf(item.block) === -1) {
                    _blockArr.push(item.block)
                }
            })
        }else{
            units.forEach(function(item){
                if(_blockArr.indexOf(item.block) === -1) {
                    _blockArr.push(item.block)
                }
            })
        }
        return _blockArr
    }
    // 通过楼栋和户型筛选出对应的单位（去重）
    function selectUnitsByBlockAndTypes(block, typeArr) {
        var _unitsArr = []
        var _unitArr = []
        if(typeArr.length<=0){
            units.forEach(function(item){
                if(item.block == block && _unitArr.indexOf(item.unit) == -1) {
                    _unitsArr.push(item)
                    _unitArr.push(item.unit)
                }
            })
            return _unitsArr
        }else{
            units.forEach(function(item){
                if(typeArr.indexOf(parseInt(item.typeId)) > -1 && item.block == block && _unitArr.indexOf(item.unit) == -1) {
                    _unitsArr.push(item)
                    _unitArr.push(item.unit)
                }
            })
            return _unitsArr
        }
    }
    // 通过typeId查询户型
    function selectUnitTypeByTypeId(id) {
        if(!unitType) return {}
        var _unitType = {}
        unitType.forEach(function(item){
            if(item.id == id) {
                _unitType = item
            }
        })
        return _unitType
    }

    function createBlocks(blockArr) {
        var str="";
        blockArr.forEach(function (item){
            str += '<button type="button" class="btn-item" data-block="' + item + '">' + item + '</button>';
        })
        $('#btnLists').html(str);
        $('#floorBlock').html('');
        $('#imgContainer>.download-btn').hide();
        $('#imgContainer').removeClass('active');
        $('#imgContainer>img').attr('src', '');
    }
    function createUnits(unitArr) {
        var str="";
        unitArr.forEach(function (item, index){
            str += '<button type="button" class="btn-item" data-typeid="' + item.typeId + '">' + item.unit + '</button>';
        })
        $('#floorBlock').html(str);
        $('#imgContainer>.download-btn').hide();
        $('#imgContainer').removeClass('active');
        $('#imgContainer>img').attr('src', '');
    }
    $(document).on('click', '#selectBlock>li', function(){
        $(this).addClass('active').siblings().removeClass('active');
        check_br = $(this).data('br')
        var _blockArr = []
        typeArr = selectTypeByBr(check_br)
        _blockArr = selectBlockByTypes(typeArr)
        createBlocks(_blockArr)
        $('.img-container-table').hide()
        $('#imgContainer .table-btn').hide()
    })
    $('#selectBlock>li').eq(0).click()
    $(document).on('click', '#btnLists>button', function(){
        $(this).addClass('active').siblings().removeClass('active');
        var block = $(this).data('block')
        filterUnitArr = selectUnitsByBlockAndTypes(block, typeArr)
        createUnits(filterUnitArr)
        $('#imgContainer').addClass('active');
        $('.img-container-table').show()
        $('#imgContainer .table-btn').hide()
        tableInint(block)
    })
    $('#btnLists>button')[0].click()
    $(document).on('click', '#floorBlock>button', function(){
        $(this).addClass('active').siblings().removeClass('active');
        var typeId = $(this).data('typeid')
        var type = selectUnitTypeByTypeId(typeId)
        $('#imgContainer>img').attr('src', prefixUrl + type.typeUrl);
        $('#imgContainer').addClass('active');
        $('#imgContainer>.download-btn').show();
        $('#imgContainer>.download-btn>a').attr('href', prefixUrl + type.bookUrl);
        $('.img-container-table').hide()
        $('#imgContainer .table-btn').show()
    })
    $(document).on('click', '#imgContainer .table-btn', function(){
        $('.img-container-table').toggle()
        $('#imgContainer>img').toggle()
    })
    $(document).on('click', '#imgContainer .img-container-table td', function(){
        var id = $(this)[0].getAttribute('data-id')
        if(id){
            $('.img-container-table').hide()
            $('#imgContainer>img').show()
            var type = selectUnitTypeByTypeId(id)

            //关联unit
            typeArr = selectTypeByBr(type.bedroom)
            var UnitArrTemp = selectUnitsByBlockAndTypes($('#selectBlock2 .btn-item.active').data('block'), typeArr)
            createUnits(UnitArrTemp)

            $('#imgContainer>img').attr('src', prefixUrl + type.typeUrl);
            $('#imgContainer').addClass('active');
            $('#imgContainer>.download-btn').show();
            $('#imgContainer>.download-btn>a').attr('href', prefixUrl + type.bookUrl);
            $('.img-container-table').hide()
            $('#imgContainer .table-btn').show()
            $('#imgContainer .table-btn').attr('data-show','')

            //bedRoom 关联选中
            $('#selectBlock .list-item').removeClass('active')
            $('#selectBlock .list-item').each(function () {
                if($(this)[0].getAttribute('data-br') == type.bedroom){
                    $(this).addClass('active')
                }
            })
            //unit 关联选中
            $('#floorBlock .btn-item').removeClass('active')
            $('#floorBlock .btn-item').each(function () {
                if($(this)[0].getAttribute('data-typeid') == type.id){
                    $(this).addClass('active')
                }
            })
        }
    })
    function tableInint(block) {
        var filterUnitArr =[]
        units.forEach(item => {
            if(item.block == block){
                filterUnitArr.push(item)
            }
        })
        var tableDom = $('.img-container-table>table')
        tableDom[0].innerHTML = ''
        if(!Array.isArray(filterUnitArr)){return}
        var floorArrTemp = []
        var unitArrTemp = []
        for (var i=0;i<filterUnitArr.length;i++){
            var temp = filterUnitArr[i]
            floorArrTemp.push(temp.floor)
            unitArrTemp.push(temp.unit)
        }
        var floorArr = unique(floorArrTemp).reverse()
        var unitArr = unique(unitArrTemp)
        var rowTemp ='<td> Floor/Unit </td>'
        for (var x=0;x<unitArr.length;x++){
            var unitTemp = unitArr[x]
            rowTemp += '<td>'+unitTemp+'</td>'
        }
        var row = '<thead>'+rowTemp+'</thead>'
        for (var i=0;i<floorArr.length;i++){
            var floorTemp = floorArr[i]
            var td = ''
            for (var x=-1;x<unitArr.length;x++){
                var val =''
                var id =''
                var style= ''
                if(x>-1){
                    var unit = {}
                    filterUnitArr.forEach(o => {
                        if(o.floor == floorTemp && o.unit == unitArr[x]){
                            unit = o
                            return
                        }
                    })
                    var unitTypeId = unit.typeId
                    var unitTypeEL = {}
                    unitType.forEach(o => {
                        if(o.id==unitTypeId){
                            unitTypeEL = o
                            return
                        }
                    })
                    val = unitTypeEL.typeName ? unitTypeEL.typeName : ''
                    id = unitTypeEL.id ? unitTypeEL.id : ''
                    if(check_br && unitTypeEL.bedroom == check_br){
                        style = 'class="active"'
                    }
                }
                var str = (x===-1?floorTemp:val)
                td += '<td data-id="'+id+'" '+style+'>'+str+'</td>'
            }
            row += '<tr>' + td + '</tr>'
        }
        tableDom[0].innerHTML = row
    }
    function unique(arr) {
        if(!Array.isArray(arr)){
            return
        }
        var array = []
        for (var i=0;i<arr.length;i++){
            if(array.indexOf(arr[i]) === -1){
                array.push(arr[i])
            }
        }
        return array
    }

};


// Project.prototype.initFloorPlan = function () {
//     $('#selectBlock>li').click(function () {
//         $(this).addClass('active').siblings().removeClass('active');
//         var str = '';
//         for (var i = 1; i < 11; i++) {
//             str += '<button type="button" class="btn-item">BLOCK ' + i + '</button>';
//         }
//         $('#btnLists').html(str);
//         $('#floorBlock').html('');
//         // let room = $(this).attr('data-room')
//         // $.get('',res=>{
//         //   console.log(res)
//         // })
//     });
//     //处理第二个块
//     $('#btnLists').on('click', 'button', function () {
//         $(this).addClass('active').siblings().removeClass('active');
//         var str = '';
//         // let type = $(this).attr('data-type')
//         // $.get('',res=>{
//         //   console.log(res)
//         // })
//         for (var i = 1; i < 5; i++) {
//             str += '<button type="button" class="btn-item" data-unit="' + i + '">Unit ' + i + '</button>';
//         }
//         var block = $(this).attr('data-block');
//         $('#floorBlock').html(str);
//     });
//     function dealUnit(obj) {
//         var params = [];
//         Object.keys(obj).forEach(function (el) {
//             var val = obj[el];
//             params.push([el, val].join('='));
//         });
//         return params.join('&');
//     }
//     //处理第三个块
//     $('#floorBlock').on('click', 'button', function () {
//         console.log(this);
//         $(this).addClass('active').siblings().removeClass('active');
//         var roof = $(this).attr('');
//         $('#imgContainer').show();
//         var unit = $(this).addClass('active').data('unit');
//         var imgSrc = '/media/floorplan/' + unit + '.png';
//         $('#imgContainer>img').attr('src', imgSrc);
//         $('#imgContainer').css({ 'background': '#fff' });
//         $('#imgContainer>.download-btn')[0].style.display = 'block';
//         // let downUrl = $('#imgContainer>download-btn').attr('src',imgSrc)
//         var downUrl = $('#imgContainer>.download-btn>a').attr('href');
//         downUrl = '?' + dealUnit({ unit: unit });
//         $('#imgContainer>.download-btn>a').attr('href', downUrl);
//     });
// };

// 6. VR
Project.prototype.initVR = function () {
    // VR相关JS事件
    var $vrDom = $('.vr__item');
    $vrDom.on('mouseenter', function () {
        $vrDom.addClass('vr__item_blur');
    });
    $vrDom.on('mouseleave', function () {
        $vrDom.removeClass('vr__item_blur');
    });
    $vrDom.on('click', function () {
        var src = $(this).data('src');
        vrShowAllScreen(src);
    });

    $('.vr__screen-close').click(function () {
        $('body').css({
            overflow: 'auto'
        });
        $('#vr-full-screen').hide();
    });

    function vrShowAllScreen(src) {
        $('#vr-full-screen iframe').attr('src', src);
        $('body').css({
            overflow: 'hidden'
        });
        $('#vr-full-screen').show();
    }
};



// 8. Event
Project.prototype.initEvent = function () {};
// 9. Nav
Project.prototype.initNav = function () {
    //分享
    if(window.innerHeight<814){

    }
    // socialShare('#projectNav .social-share', {
    //     sites: ['facebook', 'twitter', 'google']
    //     // sites:['facebook', 'wechat', 'twitter','google', 'pingtrest'],
    // });
    $('.social-share .icon-wechat').click(function (){
        $('#wechat-full-screen').show()
    })
    $('#wechat-full-screen .wechat__screen-close').click(function (){
        $('#wechat-full-screen').hide()
    })
    //侧边栏
    $("#sideMenuCtr").mouseenter(function () {
        //console.log(1)
        $("#sideMenu").animate({
            width: '528px'
        });
    });
    $("#sideMenu").mouseleave(function () {
        $("#sideMenu").animate({
            width: 0
        });
    });
    // $("#sideMenuCtr").click(function(){
    //     if($("#sideMenu").width()){
    //         $("#sideMenu").animate({
    //             width:0,
    //         });
    //     }else{
    //         $("#sideMenu").animate({
    //             width:'528px',
    //         });
    //
    //     }
    // });
    $("#sliderHead").click(function () {
        $("#sideMenu").animate({
            width: 0
        });
    });
    // 侧边栏滚动监听
    // $(window).scroll(function(){
    //     if($(window).scrollTop() >= $(window).height()){
    //         $("#projectNav").css({
    //             position:'fixed',
    //             top:'0',
    //         });
    //         $("#projectNav").css({
    //
    //         });
    //     } else{
    //         $("#projectNav").css({
    //             position:'absolute',
    //             top:'110px',
    //         });
    //         $("#projectNav").css({
    //
    //         });
    //     }
    // });

    //侧边栏页面内跳转
    $("#projectNav .menuContent li").click(function (e) {
        var id = $(this).attr("data-src");
        if (id) {
            $("body,html").animate({ scrollTop: $('#' + id).offset().top }, 800);
        }
    });
};
$(function () {
    var project = new Project();
    project.init();
});
