package com.mitdy.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6719777290870805277L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AbstractEntity)) {
			return false;
		}
		AbstractEntity bean = (AbstractEntity) o;
		if (id == null && bean.id != null) {
			return false;
		}
		if (id != null && bean.id == null) {
			return false;
		}
		return (bean.getClass().equals(super.getClass())) && (bean.id.equals(this.id));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (id != null ? id.intValue() : 0);
		return result;
	}

}
