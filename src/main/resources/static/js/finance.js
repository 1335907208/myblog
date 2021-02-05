$('#Finance .firstnav li').click(function () {
    $('#Finance .firstnav li').removeClass('liactive');
    $('.finance_container').removeClass('finance_container_show');
    $(this).eq(0).addClass('liactive');
    $('#finance_title')[0].innerText = $(this)[0].innerText;
    $('div[name='+$(this).eq(0).attr("data-link")+']').addClass('finance_container_show');
})

$('.select-flowerCount-type').select2({
    placeholder: 'Type',
    minimumResultsForSearch: -1,
    allowClear: true
});
function countFlowerCount() {
    const additional_data = [
        [0,0.12,0.15],
        [0.05,0.15,0.15],
        [0.2,0.2,0.2]
    ]
    const type = $('#Finance .select-flowerCount-type').val();
    const price = $('#Finance .property-price').val()
    if(!type || !price){return}
    const formatPrice = parseFloat(price)
    let flowerSum = 0
     if(price > 1000000){
        flowerSum = (formatPrice*0.03)-5400+(formatPrice-1000000)*0.01
    }else if(price > 360000){
        flowerSum = (formatPrice-360000) * 0.03 + 5400
    }else if(price > 180000){
        flowerSum = (formatPrice-180000) * 0.02 + 1800
    }else{
        flowerSum = formatPrice*0.01
    }
    $('#Finance .buyer-sum').each(function (index) {
        $(this)[0].setAttribute('data-price',flowerSum)
        $(this)[0].innerText = '$'+ fmoney(flowerSum,2)
    })
    const pickAdditional_data = additional_data[parseInt(type)-1]
    $('#Finance .additional-sum').each(function (index) {
        const sum = parseFloat(price) * (pickAdditional_data[index])
        $(this)[0].setAttribute('data-price',sum)
        $(this)[0].innerText = '$'+ fmoney(sum,2)
    })
    $('#Finance .totalBuyer-sum').each(function (index) {
        const additionalDom = $('#Finance .additional-sum')[index].getAttribute('data-price') || 0
        const buyerDom = $('#Finance .buyer-sum')[index].getAttribute('data-price') || 0
        const sum = parseFloat(additionalDom) + parseFloat(buyerDom)
        $(this)[0].innerText = '$'+ fmoney(sum,2)
    })
}
function countRentalYield() {
    const price1 = $('#Finance .rentalYield-price').val()
    const price2 = $('#Finance .rentalYield-rental').val()
    if(!price1 || !price2){return}
    const sum = ((parseFloat(price2) * 12 )/parseFloat(price1) * 100).toFixed(2)
    $('.rental-yield-sum')[0].innerText = sum + '%'

}
function countProgressPayment() {
    const price = $('#Finance .progressPayment-price').val()
    if(!price){return}
    $('.progress-payment-sum').each(function () {
        const ratio = $(this).attr('data-ratio')
        const sum = parseFloat(ratio) * parseFloat(price)
        $(this)[0].innerText = '$'+ fmoney(sum,2)
    })
}
function fmoney(s,n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    const l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    let t = "";
    for (let i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}
