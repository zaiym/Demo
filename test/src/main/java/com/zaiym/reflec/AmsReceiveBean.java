package com.zaiym.reflec;


import java.util.Date;

/**
 * EEP包接受登记实体
 */
public class AmsReceiveBean implements BaseBean{

    /**
     * 接收登记单ID
     */
    private Long receive_id;

    /**
     * 接收工作名称
     */
    private String receive_name;

    /**
     * 内容描述
     */
    private String receive_memo;

    /**
     * 接收档案库ID
     */
    private String receive_table_id;

    /**
     * 接收档案库名称
     */
    private String receive_table_name;

    /**
     * 接收人ID
     */
    private long receive_user_id;

    /**
     * 接收人姓名
     */
    private String receive_user_name;

    /**
     * 接收日期
     */
    private Date receive_date;

    /**
     * 移交单位
     */
    private String transfer_dept;

    /**
     * 载体类型与规格
     */
    private String receive_type;

    /**
     * 移交人ID
     */
    private Long transfer_user_id;

    /**
     * 移交人姓名
     */
    private String transfer_user_name;

    /**
     * 移交日期
     */
    private Date transfer_date;

    public AmsReceiveBean(){
    }

    
    /** 
	 * 返回 接收登记单ID
	 * @return 接收登记单ID 
	 */
	public Long getReceive_id() {
		return receive_id;
	}


	/** 
	 * 设置 接收登记单ID
	 * @param receive_id 接收登记单ID 
	 */
	public void setReceive_id(Long receive_id) {
		this.receive_id = receive_id;
	}


	/** 
	 * 返回 接收工作名称
	 * @return 接收工作名称 
	 */
	public String getReceive_name() {
		return receive_name;
	}


	/** 
	 * 设置 接收工作名称
	 * @param receive_name 接收工作名称 
	 */
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}


	/** 
	 * 返回 内容描述
	 * @return 内容描述 
	 */
	public String getReceive_memo() {
		return receive_memo;
	}


	/** 
	 * 设置 内容描述
	 * @param receive_memo 内容描述 
	 */
	public void setReceive_memo(String receive_memo) {
		this.receive_memo = receive_memo;
	}


	/** 
	 * 返回 接收档案库ID
	 * @return 接收档案库ID 
	 */
	public String getReceive_table_id() {
		return receive_table_id;
	}


	/** 
	 * 设置 接收档案库ID
	 * @param receive_table_id 接收档案库ID 
	 */
	public void setReceive_table_id(String receive_table_id) {
		this.receive_table_id = receive_table_id;
	}


	/** 
	 * 返回 接收档案库名称
	 * @return 接收档案库名称 
	 */
	public String getReceive_table_name() {
		return receive_table_name;
	}


	/** 
	 * 设置 接收档案库名称
	 * @param receive_table_name 接收档案库名称 
	 */
	public void setReceive_table_name(String receive_table_name) {
		this.receive_table_name = receive_table_name;
	}


	/** 
	 * 返回 接收人ID
	 * @return 接收人ID 
	 */
	public Long getReceive_user_id() {
		return receive_user_id;
	}


	/** 
	 * 设置 接收人ID
	 * @param receive_user_id 接收人ID 
	 */
	public void setReceive_user_id(Long receive_user_id) {
		this.receive_user_id = receive_user_id;
	}


	/** 
	 * 返回 接收人姓名
	 * @return 接收人姓名 
	 */
	public String getReceive_user_name() {
		return receive_user_name;
	}


	/** 
	 * 设置 接收人姓名
	 * @param receive_user_name 接收人姓名 
	 */
	public void setReceive_user_name(String receive_user_name) {
		this.receive_user_name = receive_user_name;
	}


	/** 
	 * 返回 接收日期
	 * @return 接收日期 
	 */
	public Date getReceive_date() {
		return receive_date;
	}

    /**
     * 返回 接收日期(YYYY-MM-DD)
     * @return 接收日期(YYYY-MM-DD)
     */
	public String getReceive_date_str(){
	    if (this.receive_date == null) {
	        return "";
        }
        return "";
    }

	/** 
	 * 设置 接收日期
	 * @param receive_date 接收日期 
	 */
	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
	}


	/** 
	 * 返回 移交单位
	 * @return 移交单位 
	 */
	public String getTransfer_dept() {
		return transfer_dept;
	}


	/** 
	 * 设置 移交单位
	 * @param transfer_dept 移交单位 
	 */
	public void setTransfer_dept(String transfer_dept) {
		this.transfer_dept = transfer_dept;
	}


	/** 
	 * 返回 载体类型与规格
	 * @return 载体类型与规格 
	 */
	public String getReceive_type() {
		return receive_type;
	}


	/** 
	 * 设置 载体类型与规格
	 * @param receive_type 载体类型与规格 
	 */
	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}


	/** 
	 * 返回 移交人ID
	 * @return 移交人ID 
	 */
	public Long getTransfer_user_id() {
		return transfer_user_id;
	}


	/** 
	 * 设置 移交人ID
	 * @param transfer_user_id 移交人ID 
	 */
	public void setTransfer_user_id(Long transfer_user_id) {
		this.transfer_user_id = transfer_user_id;
	}


	/** 
	 * 返回 移交人姓名
	 * @return 移交人姓名 
	 */
	public String getTransfer_user_name() {
		return transfer_user_name;
	}


	/** 
	 * 设置 移交人姓名
	 * @param transfer_user_name 移交人姓名 
	 */
	public void setTransfer_user_name(String transfer_user_name) {
		this.transfer_user_name = transfer_user_name;
	}


	/** 
	 * 返回 移交日期
	 * @return 移交日期 
	 */
	public Date getTransfer_date() {
		return transfer_date;
	}

    /**
     * 返回 移交日期
     * @return 移交日期（YYYY-MM-DD）
     */
	public String getTransfer_date_str(){
	    if (this.transfer_date == null) {
	        return "";
        }
        return "";
    }

	/** 
	 * 设置 移交日期
	 * @param transfer_date 移交日期 
	 */
	public void setTransfer_date(Date transfer_date) {
		this.transfer_date = transfer_date;
	}


	@Override
    public String get_id() {
        return receive_id.toString();
    }

    @Override
    public void set_id(String _id) {
        this.receive_id = Long.parseLong(_id);
    }
}