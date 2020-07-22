package com.konnect.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.konnect.model.error_final;

// This will be AUTO IMPLEMENTED by Spring into a Bean called error_tblRepository
// CRUD refers Create, Read, Update, Delete

public interface error_finalRepository extends CrudRepository<error_final, Integer> {
/*	@Query("insert into TaskDocumentEntity c (c.idTask, c.description, c.filepath) values (:id,:description,:filepath)")
	public void insertDocumentByTaskId(@Param("id") Long id,@Param("description") String description,@Param("filepath") String filepath);*/
}