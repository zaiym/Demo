package com.zaiym.xml.xpath;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dom4jXpath {


    /**
     * 签名标识符的表示方法为：修改R-签名S。其中“R”为EEP修改次数，原始EEP的R值为0,EEP每修改一次，R的值增加1。“S”为签名的顺序号，以阿拉伯数字表示。
     示例：修改0-签名1
     * @param args
     */
    public static void main(String[] args) {
        //文件元数据.xml  无命名空间.xml
        File xml = new File("E:\\workspace\\eep\\文件元数据.xml");
        String xPath = "//namespace:被签名对象/namespace:封装内容/namespace:文件实体块/namespace:文件实体";
        //String xPath = "//namespace:电子签名块/namespace:电子签名";
        //String xPath = "//namespace:锁定签名";
        //String xPath = "//namespace:被签名对象/namespace:封装内容/namespace:机构人员实体块/namespace:机构人员实体";
        //String xPath = "//namespace:被签名对象/namespace:封装内容/namespace:文件实体块/namespace:文件实体/namespace:内容描述/namespace:案卷";
        read(xml, xPath);
        //read2(xml, xPath);
        //System.out.println(getValue(xml,"//namespace:被签名对象/namespace:封装内容/namespace:文件实体块/namespace:文件实体/namespace:内容描述/namespace:题名"));
        m1();
    }

    public static void m1(){
        File file = new File("E:\\workspace\\eep\\20180801\\9528\\0001-2018-10年-0002");
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println(f.getName());
            }
        }
    }

    public static void read(File xml,String xPath){
        try {
            SAXReader reader = new SAXReader();
            reader.setEncoding("UTF-8");
            Map<String,String> namespaceURIs = new HashMap<>();
            namespaceURIs.put("namespace","http://www.saac.gov.cn/standards/ERM/encapsulation");
            reader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
            Document document = reader.read(xml);
            List<Element> elements = document.selectNodes(xPath);
            List<Map<String,Object>> el = new ArrayList<>();
            List<Map<String,Map<String,Object>>> list = new ArrayList<>();
            Map<String,Map<String,Object>> r = new HashMap<>();
            for (Element e : elements) {
                Map<String,String> map = new HashMap<String, String>();
                /**
                Map<String,Object> map = new HashMap<String, Object>();
                read(e,map);
                el.add(map);**/
                //read(e,null, r);

                read(e,map);
                read(e,null,list);
            }
            System.out.println(list.size());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(File xml,String xPath){
        try {
            SAXReader reader = new SAXReader();
            reader.setEncoding("UTF-8");
            Map<String,String> namespaceURIs = new HashMap<>();
            namespaceURIs.put("namespace","http://www.saac.gov.cn/standards/ERM/encapsulation");
            reader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
            Document document = reader.read(xml);
            Node node = document.selectSingleNode(xPath);
            return node == null ? "" : node.getStringValue();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void read2(File xml,String xPath){
        try {
            xPath = "//namespace:被签名对象/namespace:封装内容/namespace:机构人员实体块/namespace:机构人员实体";
            SAXReader reader = new SAXReader();
            reader.setEncoding("UTF-8");
            Map<String,String> namespaceURIs = new HashMap<>();
            namespaceURIs.put("namespace","http://www.saac.gov.cn/standards/ERM/encapsulation");
            reader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
            Document document = reader.read(xml);
            Element e = (Element)document.selectSingleNode(xPath);
            Map<String,String> map = new HashMap<>();
            read(e,map);
            System.out.println(map.size());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void read(Element e,Map<String,String> map){
        List<Element> elements = e.elements();
        if (elements.size() > 1) {
            System.out.println("-------------------");
            for (Element se : elements) {
                if ("M233".equals(se.attributeValue("name"))) {
                    continue;
                }
                read(se, map);
            }
        } else {
            System.out.println(e.attributeValue("name"));
            map.put(e.attributeValue("name"),e.getTextTrim());
        }
    }

    private static void read(Element e,Map<String,Object> map,List<Map<String,Map<String,Object>>> list){
        List<Element> elements = e.elements();
        if (elements.size() > 1) {
            Map<String,Map<String,Object>> node = new HashMap<>();
            String code = e.attributeValue("name");
            Map<String,Object> m = new HashMap<>();
            node.put(code, m);
            System.out.println("-------------->" + code);
            for (Element se : elements) {
                read(se, m, list);
            }
            if (m.size() > 0) {
                list.add(node);
            }
        } else {
            System.out.println(e.attributeValue("name"));
            map.put(e.attributeValue("name"),e.getTextTrim());
        }
    }

    private static void read(Element e,Map<String,Object> map,Map<String,Map<String,Object>> r){
        List<Element> elements = e.elements();
        if (elements.size() > 1) {
            Map<String,Object> m = new HashMap<>();
            String code = e.attributeValue("name");
            System.out.println("-------------->" + code);
            for (Element se : elements) {
                read(se, m, r);
            }
            if (m.size() > 0) {
                r.put(code, m);
            }
        } else {
            System.out.println(e.attributeValue("name"));
            map.put(e.attributeValue("name"),e.getTextTrim());
        }
    }
}