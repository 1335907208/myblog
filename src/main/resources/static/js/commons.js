var Common = function () {
    this.toastrOptions =  {
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": false,
        "positionClass": "toast-top-full-width",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
}
Common.prototype.alert = function (msg){
    toastr.options = this.toastrOptions
    if(toastr) toastr["warning"](msg)
}
Common.prototype.success = function (msg){
    toastr.options = this.toastrOptions
    if(toastr) toastr["success"](msg)
}
var common = new Common()
