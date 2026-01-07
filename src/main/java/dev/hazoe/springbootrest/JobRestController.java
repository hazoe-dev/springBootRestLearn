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

    @GetMapping(path = "jobPosts", produces = {"application/xml"})
    public List<JobPost> getJobPosts() {
        return service.getJobs();
    }

    @GetMapping("posts")
    public List<JobPost> getPosts() {
        return service.getJobs();
    }

    @GetMapping("post/{id}")
    public JobPost getPost(@PathVariable("id") int postId) {
        return service.getJob(postId);
    }

    @PostMapping(path = "post", consumes = {"application/xml"})
    public JobPost createPost(@RequestBody JobPost jobPost) {
        service.addJob(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    @PutMapping("post")
    public JobPost updatePost(@RequestBody JobPost jobPost) {
        service.updateJob(jobPost);
        return service.getJob(jobPost.getPostId());
    }

    @DeleteMapping("post/{id}")
    public void deletePost(@PathVariable("id") int postId) {
        service.deleteJob(postId);
    }

    @GetMapping("load")
    public void loadData() {
        service.loadData();
    }

    @GetMapping("post/keyword/{keyword}")
    public List<JobPost> searchPost(@PathVariable("keyword") String keyword) {
        return service.searchJob(keyword);
    }
}
