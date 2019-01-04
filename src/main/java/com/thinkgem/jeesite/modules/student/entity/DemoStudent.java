/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学生表Entity
 * @author zhb
 * @version 2018-11-28
 */
public class DemoStudent extends DataEntity<DemoStudent> {
	
	private static final long serialVersionUID = 1L;
	private String studentName;		// 学生姓名
	private String studentAge;		// 学生年龄
	private String studentSex;		// 学生性别
	private String classId;		// 班级
	private String className;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public DemoStudent() {
		super();
	}

	public DemoStudent(String id){
		super(id);
	}

	@Length(min=0, max=32, message="学生姓名长度必须介于 0 和 32 之间")
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	@Length(min=0, max=4, message="学生年龄长度必须介于 0 和 4 之间")
	public String getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(String studentAge) {
		this.studentAge = studentAge;
	}
	
	@Length(min=0, max=255, message="学生性别长度必须介于 0 和 255 之间")
	public String getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(String studentSex) {
		this.studentSex = studentSex;
	}
	
	@Length(min=0, max=255, message="班级长度必须介于 0 和 255 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
}