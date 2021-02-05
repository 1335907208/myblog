'use strict';

var Index = function Index() {};
Index.prototype.init = function () {
    this.initSlider();
    this.initProjects();
    this.initMap();
    this.initHeaderSearch();
};
// 1. 首屏Slider
Index.prototype.initSlider = function () {
    // $('.el-button').click(function () {
    //   window.location.href = "project.html";
    // });
    // $('#subenu_member').click(function(){
    //     window.location.href = "userCenter.html";
    // })
    var sliderBoxSwiper = new Swiper('#homeSliderBox .swiper-container', {
        // autoplay:false,
        speed: 300,
        autoplay: {
            delay: 8000
        },
        navigation: {
            nextEl: '#homeSliderBox .swiper-button-next',
            prevEl: '#homeSliderBox .swiper-button-prev'
        },
        pagination: {
            el: '#homeSliderBox .swiper-pagination',
            clickable: true,
            renderBullet: function renderBullet(index, className) {
                // return '<div class="page-btn '+className+'"></div>';
                return '<div class="page-btn ' + className + '"><span class="tip">' + (index + 1) + '</span></div>';
            }
        }
    });
};
// 2. 项目projects
Index.prototype.initProjects = function () {
    // $('.column8').click(function () {
    //   window.location.href = "project.html";
    // });

    // $('.rightTop-right').click(function () {
    //   window.location.href = "project.html";
    // });
    var projectsBoxSwiper = new Swiper('#projectsBox .swiper-container', {
        watchSlidesProgress: true,
        // slidesPerView: 2,
        slidesPerView: 'auto',
        centeredSlides: true,
        loop: true,
        loopedSlides: 3,
        speed: 300,
        autoplay: {
            delay: 5000
        },
        navigation: {
            nextEl: '#projectsBox .swiper-button-next',
            prevEl: '#projectsBox .swiper-button-prev'
        },
        pagination: {
            el: '#projectsBox .swiper-pagination',
            clickable: true,
            renderBullet: function renderBullet(index, className) {
                return '<div class="page-btn ' + className + '"></div>';
                // return '<div class="page-btn '+className+'"><span class="tip">' + (index + 1) + '</span></div>';
            }
        }
    });
};








//Shwointerest关闭
$('.Application_file_close').click(function(){
    $('.Application_file').hide();
})





// 4. 顶部位置搜索
Index.prototype.initHeaderSearch = function () {

    //select2 初始化
    $('.header__select-localtion').select2({
        placeholder: 'Localtion',
        minimumResultsForSearch: -1,
        allowClear: true
    });
    $('.header__select-localtion').select2("val", '1');
    $('.header__select-status').select2({
        placeholder: 'Status',
        minimumResultsForSearch: -1,
        allowClear: true
    });
    $('.header__select-type').select2({
        placeholder: 'Type',
        minimumResultsForSearch: -1,
        allowClear: true
    });
    $('.header__select-bedrooms').select2({
        placeholder: 'Bedrooms',
        minimumResultsForSearch: -1,
        allowClear: true
    });
    $('.header__select-price').select2({
        placeholder: 'Price',
        minimumResultsForSearch: -1,
        allowClear: true
    });

    $(".header__select-localtion").on("select2:select", function (e) {
        $('#indexMap .select-localtion').select2('val', e.target.value);
    });

    $(".header__select-status").on("select2:select", function (e) {
        $('#indexMap .select-status').select2('val', e.target.value);
        console.log(e.target.value);
    });

    $(".header__select-type").on("select2:select", function (e) {
        $('#indexMap .select-type').select2('val', e.target.value);
        console.log(e.target.value);
    });

    $(".header__select-bedrooms").on("select2:select", function (e) {
        $('#indexMap .select-bedrooms').select2('val', e.target.value);
        console.log(e.target.value);
    });

    $(".header__select-price").on("select2:select", function (e) {
        $('#indexMap .select-price').select2('val', e.target.value);
        console.log(e.target.value);
    });

    $('#header .header__screen-button').click(function (e) {
        $('body')[0].style.overflow = 'auto';
        $('#header #search-full-screen')[0].style.display = 'none';
        $("body,html").animate({
            scrollTop: $('#mapBox').offset().top + 190
        }, 800);
        $("#indexMap .search-button").click();
    });

    $('#header .btnSearch').click(function (e) {
        $('body')[0].style.overflow = 'hidden';
        $('#header #search-full-screen')[0].style.display = 'block';
    });

    $('.header__screen-close').click(function () {
        $('body').css({
            overflow: 'auto'
        });
        $('#search-full-screen').hide();
    });
};

$(function () {
    var index = new Index();
    index.init();
});






