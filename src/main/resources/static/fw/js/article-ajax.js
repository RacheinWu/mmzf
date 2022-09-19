$(function () {
    $("#save-to-draft").click(function () {
        alert("上传草稿")
        saveToDraft();
    })
})


function uploadImgCover() {

}

function saveToDraft() {
    //获取封面参数：
    //获取content的富文本内容
    //校验：如果封面参数 || content == null or "", 返回失败
    let content = $("#editor-container").html();
    // let content = "";
    alert(content)
    let article = {
        author : "吴远健",
        content : content,
        content_source_url : "",
        digest : "这个是摘要",
        need_open_comment : 0,
        only_fans_can_comment : 1,
        show_cover_pic : 1,
        thumb_media_id : "kYfwkiVvC5IFc_sNjR30nD8S_fTiwErngcRgXEwIMGa6qBwV0cPM3i9WRgTdD-__",
        title: "这个标题"
    }
    let articles = [article];
    let requestBody = {
        articles : articles
    }
    $.ajax({
        async: true,
        type:"POST",
        url: "http://localhost/api/draft/add",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify(requestBody),
        dataType:"json",
        success: function (data) {
            if (data.code === 200) {
                alert("保存成功！");
            }
        }
    })
}