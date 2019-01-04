/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shareholdersinformation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shareholdersinformation.entity.ShareholdersInformation;
import com.thinkgem.jeesite.modules.shareholdersinformation.dao.ShareholdersInformationDao;

/**
 * 公司持股人情况Service
 * @author zhb
 * @version 2018-12-10
 */
@Service
@Transactional(readOnly = true)
public class ShareholdersInformationService extends CrudService<ShareholdersInformationDao, ShareholdersInformation> {

	public ShareholdersInformation get(String id) {
		return super.get(id);
	}
	
	public List<ShareholdersInformation> findList(ShareholdersInformation shareholdersInformation) {
		return super.findList(shareholdersInformation);
	}
	
	public Page<ShareholdersInformation> findPage(Page<ShareholdersInformation> page, ShareholdersInformation shareholdersInformation) {
		return super.findPage(page, shareholdersInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(ShareholdersInformation shareholdersInformation) {
		super.save(shareholdersInformation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShareholdersInformation shareholdersInformation) {
		super.delete(shareholdersInformation);
	}
	
}