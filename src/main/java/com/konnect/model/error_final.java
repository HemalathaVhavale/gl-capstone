package com.konnect.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity		
@Table(name = "konnect_error_final")
public class error_final{
	 @Id
	public Integer error_number;
	 
		 public String  error_code;
		 //public String error_source;
		 public String error_description;
		 public String error_category;
		 public String solution;
	     public String source_version;
	     public String target_version;
	     public String product_id;
	     public Date created_date;
	    
	}
	
