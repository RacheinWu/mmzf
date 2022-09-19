package com.rachein.mmzf.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 华南理工大学 吴远健
 * @Date 2022/8/11
 * @Description
 */
@RestController
@Api(tags = "活动模块")
@RequestMapping("activities")
public class ActivitiesController {

    @ApiOperation("报名活动")
    @PostMapping("/join")
    public void join() {

    }

    @ApiOperation("添加活动到草稿箱中待审核")
    @PostMapping("/create")
    public void create() {

    }

    @ApiOperation("审核通过该活动")
    @PostMapping("/check")
    public void check() {}

    @ApiOperation("更改活动内容")
    @PostMapping("/update")
    public void update(){}

    @ApiOperation("撤销改活动，顺便撤销联动的图文消息, 并且将参加的人进行撤销，并且对其发送消息")
    @PostMapping("/remove")
    public void remove() {

    }

    @ApiOperation("获取所有活动的列表")
    @GetMapping("/all")
    public void list() {}

    @ApiOperation("获取某活动的详细")
    @GetMapping("/{activity_id}/info")
    public void getInfo(@PathVariable String activity_id) {

    }

    @ApiOperation("获取某活动的报名状态，多少人，有谁")
    @GetMapping("/{activity_id}/state")
    public void getState(@PathVariable String activity_id) {

    }

//    @ApiOperation("其中报名的某人移出去")

}
