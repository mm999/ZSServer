package com.huishu.ait.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author yxq
 * @date 2017年8月3日
 * @功能描述：企业分组的实体类
 */
@Entity
@Table(name = "t_company_group")
public class CompanyGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -888515836041527788L;
	@Id
	@GeneratedValue
	@Column(name="group_id", nullable= false)
	private int groupid;
	//分组名称
	@Column(name = "group_name")
	private String groupName;
	//一对多
	@Transient
	private List<Company> companys;
	
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Company> getCompanys() {
		return companys;
	}
	public void setCompanys(List<Company> companys) {
		this.companys = companys;
	}
}