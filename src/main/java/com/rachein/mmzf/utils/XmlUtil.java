package com.rachein.mmzf.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtil {

    public static Map<String, Object> parseXml(String xml) {
        if (StringUtils.isBlank(xml)) return null;
        try {
            return parseXml(xml.getBytes(StandardCharsets.UTF_8), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static Map<String, String> parseXmlToStrMap(String xml) {
        if (StringUtils.isBlank(xml)) return null;
        Map<String, String> result = new HashMap<>();
        try {
            Map<String, Object> map = parseXml(xml);
            for (String key : map.keySet()) {
                Object value = map.get(key);
                result.put(key, String.valueOf(value));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseXml(byte[] xmlBytes, String charset) {
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        Map<String, Object> map = new HashMap<>();
        try {
            Document doc = reader.read(source);
            Iterator<Element> iter = doc.getRootElement().elementIterator();
            while (iter.hasNext()) {
                Element e = iter.next();
                if (!e.elementIterator().hasNext()) {
                    map.put(e.getName(), e.getTextTrim());
                    continue;
                }
                Iterator<Element> iterator = e.elementIterator();
                Map<String, String> param = new HashMap<>();
                while (iterator.hasNext()) {
                    Element el = iterator.next();
                    param.put(el.getName(), el.getTextTrim());
                }
                map.put(e.getName(), param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> getMap(InputStream inputStream) {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element element = document.getRootElement();
        List<Element> elementList = element.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }
}
