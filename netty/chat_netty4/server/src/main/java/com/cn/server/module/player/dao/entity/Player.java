package com.cn.server.module.player.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 玩家实体对象
 *
 *
 */
@Entity
@Table(name = "player")
public class Player {
	
	/**
	 * 玩家id
	 */
	@Id
	@GeneratedValue
	private long playerId;
	
	/**
	 * 玩家名
	 */
	private String playerName;
	
	/**
	 * 密码
	 */
	private String passward;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 经验
	 */
	private int exp;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
