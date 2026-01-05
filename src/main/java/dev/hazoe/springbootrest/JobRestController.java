package dev.hazoe.springbootrest;

import dev.hazoe.springbootrest.model.JobPost;
import dev.hazoe.springbootrest.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class JobRestController {

    private JobService service;

    @Autowired
    public JobRestController(JobService service) {
        this.service = service;
    }

    @GetMapping("jobPosts")
    public List<JobPost> getJobPosts() {
        return service.getJobs();
    }

    @GetMapping("posts")
    public List<JobPost> getPosts() {
        return service.getJobs();
    }

    @GetMapping("post/{id}")
    public JobPost getPost(@PathVariable("id")int postId) {
        return service.getJob(postId);
    }
}
