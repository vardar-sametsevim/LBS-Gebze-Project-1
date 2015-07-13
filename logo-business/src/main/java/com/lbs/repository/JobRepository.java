package com.lbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lbs.model.Job;
import com.lbs.model.JobViewModel;

public interface JobRepository extends CrudRepository<Job, Long>{
	
	@Query("select new com.lbs.model.JobViewModel(j.name,j.description,j.user.firstname,j.user.team.teamname) from Job j")
	List<JobViewModel> findAllCustomModel();
	
	

}
