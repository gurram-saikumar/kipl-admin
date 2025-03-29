package com.kipl.models;




import com.kipl.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="ROLE_MASTER")
public class RoleMaster extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	public RoleMaster() {
		
	}

	public RoleMaster(Long id) {
		this.id = id;
	}

	public RoleMaster(String id) {
		this.id = Long.parseLong(id);
	}
	  	@Column(name = "NAME")
	    private String name;
	  	
	  	@Column(name = "DESCRIPTION")
	    private String description;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	  	
	 


}
