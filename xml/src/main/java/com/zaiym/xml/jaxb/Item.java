package com.zaiym.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * 检测项详细描述
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    /**
     * 编码
     */
    @XmlAttribute(name = "Code")
    private String code;

    /**
     * 检测项名称
     */
    @XmlAttribute(name = "Explain")
    private String explain;

    /**
     * 检测结果
     */
    @XmlAttribute(name = "CheckResult")
    private String checkResult;

    /**
     * 详细描述
     */
    @XmlValue
    private String memo;

    public Item() {
    }

    /**
     * 返回 编码
     *
     * @return 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 返回 检测项名称
     *
     * @return 检测项名称
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 设置 检测项名称
     *
     * @param explain 检测项名称
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
     * 返回 详细描述
     *
     * @return 详细描述
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置 详细描述
     *
     * @param memo 详细描述
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}