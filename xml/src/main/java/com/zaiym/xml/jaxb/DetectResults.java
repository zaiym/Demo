package com.zaiym.xml.jaxb;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 检测结果
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DER")
public class DetectResults {

    /**
     * eep文件列表
     */
    @XmlElement(name = "ZIPFILE")
    private List<ZipFile> files = new ArrayList<ZipFile>();

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ZipFile{

        /**
         * 检测时间
         */
        @XmlAttribute(name = "CheckTime")
        private String checkTime;

        /**
         * 检测结果
         */
        @XmlAttribute(name = "CheckResult")
        private String checkResult;

        /**
         * 归档阶段
         */
        @XmlAttribute(name = "Foundation")
        private String foundation;

        /**
         * 四性检测列表
         */
        @XmlElement(name = "CHECK")
        private List<Check> checks = new ArrayList<Check>();

        /**
         * eep包路径
         */
        @XmlAttribute(name = "Path")
        private String path;

		/** 
		 * 返回 检测时间
		 * @return 检测时间 
		 */
		public String getCheckTime() {
			return checkTime;
		}

		/** 
		 * 设置 检测时间
		 * @param checkTime 检测时间 
		 */
		public void setCheckTime(String checkTime) {
			this.checkTime = checkTime;
		}

		/** 
		 * 返回 检测结果
		 * @return 检测结果 
		 */
		public String getCheckResult() {
			return checkResult;
		}

		/** 
		 * 设置 检测结果
		 * @param checkResult 检测结果 
		 */
		public void setCheckResult(String checkResult) {
			this.checkResult = checkResult;
		}

		/** 
		 * 返回 归档阶段
		 * @return 归档阶段 
		 */
		public String getFoundation() {
			return foundation;
		}

		/** 
		 * 设置 归档阶段
		 * @param foundation 归档阶段 
		 */
		public void setFoundation(String foundation) {
			this.foundation = foundation;
		}

		/** 
		 * 返回 eep包路径
		 * @return eep包路径 
		 */
		public String getPath() {
			return path;
		}

		/** 
		 * 设置 eep包路径
		 * @param path eep包路径 
		 */
		public void setPath(String path) {
			this.path = path;
		}

		/** 
		 * 返回 四性检测列表
		 * @return 四性检测列表 
		 */
		public List<Check> getChecks() {
			return checks;
		}

		/** 
		 * 设置 四性检测列表
		 * @param checks 四性检测列表 
		 */
		public void setChecks(List<Check> checks) {
			this.checks = checks;
		}

        /**
         * 检测是否通过
         * @return
         */
		public boolean isPassed(){
		    return "通过".equals(this.checkResult);
        }
    }

	/** 
	 * 返回 eep文件列表
	 * @return eep文件列表 
	 */
	public List<ZipFile> getFiles() {
		return files;
	}

	/** 
	 * 设置 eep文件列表
	 * @param files eep文件列表 
	 */
	public void setFiles(List<ZipFile> files) {
		this.files = files;
	}
    
}