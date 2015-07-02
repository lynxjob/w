package pams.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/*
 * 客户信息
 * 
 * */
@Entity
public class ClientMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	//客户全称
	private String fullName;
	//客户简称
	private String shortName;
	//客户地址
	private String address;
	//邮政编码
	private String postcode;
	//电话
	private String telephone;
	//联系人
	private String linkmanName;
	//联系人电话
	private String linkmanPhone;
	//E-mail
	private String e_mail;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getLinkmanPhone() {
		return linkmanPhone;
	}
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String eMail) {
		e_mail = eMail;
	}
	public String toString(){
		return "["+id+",'"+fullName+"'],";
	}
	
	
	

}
