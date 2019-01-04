/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classes.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级信息Entity
 * @author zhb
 * @version 2018-11-28
 */
public class DemoClasses extends DataEntity<DemoClasses> {
	
	private static final long serialVersionUID = 1L;
	private String className;		// 班级名称
	
	public DemoClasses() {
		super();
	}

	public DemoClasses(String id){
		super(id);
	}

	@Length(min=0, max=255, message="班级名称长度必须介于 0 和 255 之间")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}