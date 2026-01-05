package dev.hazoe.springbootrest;

import dev.hazoe.springbootrest.model.JobPost;
import dev.hazoe.springbootrest.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    @ResponseBody  //specify return JSON data instead of page view;
    // we do not need explicit declaration in case we use @RestController instead of @Controller
    public List<JobPost> getPosts() {
        return service.getJobs();
    }
}
