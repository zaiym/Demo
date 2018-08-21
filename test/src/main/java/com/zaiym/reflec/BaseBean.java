package com.zaiym.reflec;

/**
 * Base Domain Object
 * 
 * @author ZENG YUSHENG
 * @date 2010-11-18
 */
public interface BaseBean {

	/**
	 * 
	 * @return _id String
	 */
	public String get_id();

	/**
	 * 
	 * @param _id
	 *            String
	 */
	public void set_id(String _id);

	/**
	 * DTO实例操作者ID或名称 return DTO _operator String
	 */
	// public String get_operator();

	/**
	 * DTO实例操作者ID或名称
	 * 
	 * @param _operator
	 *            String
	 */
	// public void set_operator(String _operator);

}
