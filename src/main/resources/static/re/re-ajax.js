var openid = "";

$(function () {
    // let redirectUrl = (location.href);
    let code = getUrlParam(location.href, "code");
    if (code != null) {
        // alert(code);
        login(code);
        // alert(openid)
    }
    else {
        let redirectUrl = encodeURIComponent(location.href);
        window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + "wx58b3a19a84005b6f" + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_userinfo";
    }

    //点击按钮上传
    $("#upload").click(function () {
        alert("an")
        updateInfo();
    })
})


function getUrlParam(url, name) {
    let pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    let matcher = pattern.exec(url);
    let items = null;
    if (null != matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch (e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch (e) {
                items = matcher[1];
            }
        }
    }
    return items;
}

function login(code) {
    $.ajax({
        async: true,
        type:"GET",
        url: "http://cn-hk-nf-1.natfrp.cloud:32064/user/access/" + code,
        contentType: "application/json;charset=UTF-8",
        // data:JSON.stringify(requestBody),
        // dataType:"json",
        success: function (data) {
            if (data.code === 200) {
                // alert(data.data)
                openid = data.data;
                // return data.data;
            }
            // else return null;

        }
    });
}

function updateInfo() {

    let t = {
        graduateSchool : $("#graduate_school").val(),
        majority : $("#majority").val(),
        nickname : $("#name").val(),
        tagId : $("#tag").val()
    }
    let c = {
        openId : openid,
        info : t
    }
    $.ajax({
        async: true,
        type:"POST",
        url: "http://cn-hk-nf-1.natfrp.cloud:32064/user/fill",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify(c),
        dataType:"json",
        success: function (data) {
            if (data.code === 200) {
                alert("个人信息修改成功！");


            }

        }
    });
}