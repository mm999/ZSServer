package com.huishu.ManageServer.entity.dbThird;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

/**
 * @author hhy
 * @date 2018年3月16日
 * @Parem
 * @return 
 * 
 */
@Entity
@Table(name = "t_word_related")
public class KeyWordRelatedEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
		@Id
		@GeneratedValue
		@Column(name = "id", nullable = false)
		private Long id;
		
		//关联关系
		@Column(name="t_word_related")
		private String related;
		
		//关联id
		@Column(name="t_related_word_id")
		private Long relateId;
		
		//词id
		@Column(name="t_word_id")
		private Long wordId;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getWordId() {
			return wordId;
		}

		public void setWordId(Long wordId) {
			this.wordId = wordId;
		}

		public String getRelated() {
			return related;
		}

		public void setRelated(String related) {
			this.related = related;
		}

		public Long getRelateId() {
			return relateId;
		}

		public void setRelateId(Long relateId) {
			this.relateId = relateId;
		}

		@Override
		public String toString() {
			return JSONObject.toJSONString(this);
		}
}
