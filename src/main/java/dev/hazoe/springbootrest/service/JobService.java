package dev.hazoe.springbootrest.service;


import dev.hazoe.springbootrest.model.JobPost;
import dev.hazoe.springbootrest.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private JobRepo repo;

    @Autowired
    public JobService(JobRepo repo) {
        this.repo = repo;
    }

    public void addJob(JobPost jobPost) {
        repo.addJob(jobPost);
    }

    public List<JobPost> getJobs() {
        return repo.getJobs();
    }

    public JobPost getJob(int postId) {
        return repo.getJob(postId);
    }
}
