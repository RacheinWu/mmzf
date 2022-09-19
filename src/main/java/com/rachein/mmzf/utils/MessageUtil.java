package com.rachein.mmzf.utils;

/**
 *
 * 格式xml文件
 */
public class MessageUtil {

    /**
     * 格式化消息
     * @param toUser
     * @param fromUser
     * @param createTime
     * @param msgType
     * @param content
     * @return
     */
    public static String formatMsg(String toUser, String fromUser, int createTime, String msgType, String content) {
        String str = "<xml>\n" +
                "  <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "  <CreateTime>%d</CreateTime>\n" +
                "  <MsgType><![CDATA[%s]]></MsgType>\n" +
                "  <Content><![CDATA[%s]]></Content>\n" +
                "</xml>";
        return String.format(str, toUser, fromUser, createTime, msgType, content);
    }
}
