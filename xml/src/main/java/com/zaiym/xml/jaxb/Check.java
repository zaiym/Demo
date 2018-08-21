package com.zaiym.xml.jaxb;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 四性检测结果
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Check {

    /**
     * 检测项名称：真实性、完整性、可用性、安全性
     */
    @XmlAttribute(name = "Explain")
    private String explain;

    /**
     * 检测结果
     */
    @XmlAttribute(name = "CheckResult")
    private String checkResult;

    /**
     * 详细检测项
     */
    @XmlElement(name = "ITEM")
    private List<Item> items = new ArrayList<Item>();

    public Check() {
    }

    /**
     * 返回 检测项名称：真实性、完整性、可用性、安全性
     *
     * @return 检测项名称：真实性、完整性、可用性、安全性
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 设置 检测项名称：真实性、完整性、可用性、安全性
     *
     * @param explain 检测项名称：真实性、完整性、可用性、安全性
     */
    public void setExplain(String explain) {
        this.explain = explain;
    }

    /**
     * 返回 检测结果
     *
     * @return 检测结果
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * 设置 检测结果
     *
     * @param checkResult 检测结果
     */
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    /**
     * 返回 详细检测项
     *
     * @return 详细检测项
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * 设置 详细检测项
     *
     * @param items 详细检测项
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }
}