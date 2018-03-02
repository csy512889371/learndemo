package com.ctoedu.demo.facade.user.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.CreatedDate;

import com.ctoedu.common.entity.UUIDEntity;
import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.common.entity.enums.BooleanEnum;
import com.ctoedu.demo.facade.Principal;

/**
 *
 * Date:2016年11月8日 下午2:10:29
 * Version:1.0
 */
@Entity
@Table(name="UMS_USER")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UmsUser extends UUIDEntity<String> implements Principal{

	private static final long serialVersionUID = 1L;
	private String username;
	private String nickname;
	private String password;
	private String salt;
	@CreatedDate
	@Column(name = "CREATE_DATE")
	private Date createDate;
	/**
	 * 是否可用
	 */
	@Column(name="IS_AVAILABLE")
	private Short isAvailable = AvailableEnum.TRUE.getValue();
	/**
	 * 是否删除
	 */
	@Column(name = "DELETED")
    private Short deleted = BooleanEnum.FALSE.getValue();
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private UmsPerson person;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Short getDeleted() {
		return deleted;
	}
	public void setDeleted(Short deleted) {
		this.deleted = deleted;
	}
	public Short getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}
	public UmsPerson getPerson() {
		return person;
	}
	public void setPerson(UmsPerson person) {
		this.person = person;
	}
	/**
     * 生成新的盐
     */
    public void generateSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(10));
    }
}
