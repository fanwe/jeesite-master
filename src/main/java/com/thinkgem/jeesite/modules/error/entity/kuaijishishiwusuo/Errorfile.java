/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.error.entity.kuaijishishiwusuo;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公司聘用的会计师事务所(未匹配到的)Entity
 * @author zhb
 * @version 2018-12-11
 */
public class Errorfile extends DataEntity<Errorfile> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// file_name
	
	public Errorfile() {
		super();
	}

	public Errorfile(String id){
		super(id);
	}

	@Length(min=0, max=255, message="file_name长度必须介于 0 和 255 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}