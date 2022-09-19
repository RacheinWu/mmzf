$(function () {
    $("#toUsers ").click(function () {
        let b = $("#toUsers input");

        // b.attr("checked",true);
        if (b.prop('checked') === true) {
            $(".menue-container").css("display", "block");
            b.attr("checked",false);
        } else {
            $(".menue-container").css("display", "none");
            b.attr("checked",true);
        }
    })
})