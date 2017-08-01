package com.hand.activiti.utils;

import com.hand.activiti.components.*;
import com.hand.activiti.dto.UserTask;
import jdk.nashorn.internal.ir.ReturnNode;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HJW on 2017/7/28 0028.
 */
public class MessageUtil {
    // 请求消息类型：文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    // 请求消息类型：图片
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    // 请求消息类型：语音
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    // 请求消息类型：视频
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    // 请求消息类型：小视频
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    // 请求消息类型：地理位置
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    // 请求消息类型：链接
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    // 请求消息类型：事件推送
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    // 事件类型：subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    // 事件类型：unsubscribe(取消订阅)
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    // 事件类型：scan(用户已关注时的扫描带参数二维码)
    public static final String EVENT_TYPE_SCAN = "scan";
    // 事件类型：LOCATION(上报地理位置)
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    // 事件类型：CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";

    // 响应消息类型：文本
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    // 响应消息类型：图片
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    // 响应消息类型：语音
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    // 响应消息类型：视频
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    // 响应消息类型：音乐
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    // 响应消息类型：图文
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return Map
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map map = new HashMap();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 扩展xstream使其支持CDATA
     */
//    private static XStream xstream = new XStream(new XppDriver() {
//        public HierarchicalStreamWriter createWriter(Writer out) {
//            return new PrettyPrintWriter(out) {
//                // 对所有xml节点的转换都增加CDATA标记
//                boolean cdata = true;
//
//                @SuppressWarnings("unchecked")
//                public void startNode(String name, Class clazz) {
//                    super.startNode(name, clazz);
//                }
//
//                protected void writeText(QuickWriter writer, String text) {
//                    if (cdata) {
//                        writer.write("");
//                    } else {
//                        writer.write(text);
//                    }
//                }
//            };
//        }
//    });

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String messageToXml(TextMessage textMessage) {
//        xstream.alias("xml", textMessage.getClass());
        return XmlUtil.toXml(textMessage);
    }

    /**
     * 图片消息对象转换成xml
     *
     * @param imageMessage 图片消息对象
     * @return xml
     */
    public static String messageToXml(ImageMessage imageMessage) {
//        xstream.alias("xml", imageMessage.getClass());
        return XmlUtil.toXml(imageMessage);
    }

    /**
     * 语音消息对象转换成xml
     *
     * @param voiceMessage 语音消息对象
     * @return xml
     */
    public static String messageToXml(VoiceMessage voiceMessage) {
//        xstream.alias("xml", voiceMessage.getClass());
        return XmlUtil.toXml(voiceMessage);
    }

    /**
     * 视频消息对象转换成xml
     *
     * @param videoMessage 视频消息对象
     * @return xml
     */
    public static String messageToXml(VideoMessage videoMessage) {
//        xstream.alias("xml", videoMessage.getClass());
        return XmlUtil.toXml(videoMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String messageToXml(MusicMessage musicMessage) {
//        xstream.alias("xml", musicMessage.getClass());
        return XmlUtil.toXml(musicMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
/*    public static String messageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }*/

    /**
     * 总菜单
     */
    public static String getMainMenu(){
        String content = null;
        StringBuffer buffer = new StringBuffer();
        buffer.append("您好，请回复数字选择服务:").append("\n\n");
        buffer.append("[1] <a href='http://hjwei.ngrok.cc'>我的待办事项</a>").append("\n\n");
        buffer.append("[2] <a href='http://hjwei.ngrok.cc'>我退回的申请</a>").append("\n\n");
        buffer.append("回复数字，不要带括号。或者直接点击链接\n");
        buffer.append("若你未绑定，请回复 '绑定:你的姓名，如：绑定:陈奕迅'");
        content = buffer.toString();
        return content;
    }

    /**
     * 待办事项模板
     * @param userTaskList
     * @return
     */
    public static String getTodoList(List<UserTask> userTaskList){
        StringBuffer content = new StringBuffer();
        content.append("你好！你的待办事项：").append("\n\n");
        if (userTaskList.size() == 0){
            content.append("无");
        }else{
            for (UserTask u: userTaskList) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("["+u.getTaskId()+"] \n\n");
                buffer.append("任务名：<a href='http://www.baidu.com'>"+u.getTaskName()+"</a>").append("\n\n");
                buffer.append("发起人：" +u.getApplicant());
                content.append(buffer);
                content.append("\n----------\n");
            }
        }
        return content.toString();
    }


}
