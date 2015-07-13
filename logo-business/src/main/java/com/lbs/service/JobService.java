package com.lbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbs.model.Job;
import com.lbs.model.JobViewModel;
import com.lbs.repository.JobRepository;

@Service
public class JobService {

	@Autowired
	private JobRepository jobRepository;
	
	public List<Job> findAllJobs(){
		return (List<Job>) jobRepository.findAll();
	}
	
	public void saveJob(Job job){
		jobRepository.save(job);
	}
	
	public List<JobViewModel> customQueryResults(){
		return jobRepository.findAllCustomModel();
	}
}
