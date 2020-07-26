package com.konnect.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.konnect.model.error_tbl;

// This will be AUTO IMPLEMENTED by Spring into a Bean called error_tblRepository
// CRUD refers Create, Read, Update, Delete

public interface error_tblRepository extends CrudRepository<error_tbl, Integer> {
	
    @Query(
			value = "SELECT * FROM konnect_error where JIRA_FLAG =0 order by created_date desc", 
				nativeQuery = true)
			Page<error_tbl> findAllErrorsJiraFalse(Pageable p);
	
	@Query(
			value = "SELECT * FROM konnect_error u where u.SOLUTION_FLAG =0 and u.JIRA_FLAG=1 order by created_date desc", 
			nativeQuery = true)
			Page<error_tbl> findAllErrorsSolutionFalseJiraTrue(Pageable p);
	@Query(
			value = "SELECT * FROM konnect_error u where u.SOLUTION_FLAG=1 and u.JIRA_FLAG=1 order by created_date desc", 
			nativeQuery = true)
			Page<error_tbl> findAllErrorsSolutionTrueJiraTrue(Pageable p);

}