package com.zaiym.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class jaxb {

    public static void main(String[] args) {
        //results();
        try {
            String file = "E:\\workspace\\eep\\zip\\20180808\\out\\out4.xml";
            JAXBContext context = JAXBContext.newInstance(DetectResults.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DetectResults results = (DetectResults)unmarshaller.unmarshal(new File(file));
            System.out.println(results);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void results(){
        DetectResults results = new DetectResults();
        DetectResults.ZipFile zipFile = new DetectResults.ZipFile();
        zipFile.setCheckTime("2018-08-07 15:40:20");
        zipFile.setCheckResult("通过");
        zipFile.setFoundation("归档环节");
        zipFile.setPath("E:\\workspace\\eep\\zip\\20180807\\9527.eep");
        Check check = new Check();
        check.setExplain("真实性检测");
        check.setCheckResult("通过");
        zipFile.getChecks().add(check);
        Item item = new Item();
        item.setCode("YJ-ZS-1-1");
        item.setExplain("固化信息有效性检测");
        item.setCheckResult("通过");
        item.setMemo("[001-2018-长期-0001中文件元数据.xml文件里名为\"计算机文件名\"的节点值为空][001-2018-长期-0002中文件元数据.xml文件里名为\"计算机文件名\"的节点值为空][001-2018-长期-0002中文件元数据.xml文件里名为\"计算机文件名\"的节点值为空]");
        check.getItems().add(item);
        item = new Item();
        item.setCode("YJ-ZS-2-3");
        item.setExplain("设定值域的元数据项值域符合度检测");
        item.setCheckResult("通过");
        check.getItems().add(item);

        check = new Check();
        check.setExplain("安全性检测");
        check.setCheckResult("通过");
        results.getFiles().add(zipFile);
        zipFile.getChecks().add(check);

        zipFile = new DetectResults.ZipFile();
        zipFile.setCheckTime("2018-08-07 15:40:21");
        zipFile.setCheckResult("通过");
        zipFile.setFoundation("归档环节");
        zipFile.setPath("E:\\workspace\\eep\\zip\\20180807\\9528.eep");
        check = new Check();
        check.setExplain("完整性检测");
        check.setCheckResult("未通过");
        zipFile.getChecks().add(check);
        results.getFiles().add(zipFile);
        JaxbUtil.toXml(results, new File("E:\\workspace\\eep\\zip\\20180808\\out\\out4.xml"));
    }
}