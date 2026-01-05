package dev.hazoe.springbootrest.repo;

import dev.hazoe.springbootrest.model.JobPost;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class JobRepo {
    // ArrayList to store JobPost objects
    private List<JobPost> jobs = new ArrayList<>(Arrays.asList(

            new JobPost(1, "Java Developer", "Must have good experience in core Java and advanced Java", 2,
                    List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")),


            new JobPost(2, "Frontend Developer", "Experience in building responsive web applications using React", 3,
                    List.of("HTML", "CSS", "JavaScript", "React")),


            new JobPost(3, "Data Scientist", "Strong background in machine learning and data analysis", 4,
                    List.of("Python", "Machine Learning", "Data Analysis")),


            new JobPost(4, "Network Engineer", "Design and implement computer networks for efficient data communication", 5,
                    List.of("Networking", "Cisco", "Routing", "Switching")),


            new JobPost(5, "Mobile App Developer", "Experience in mobile app development for iOS and Android", 3,
                    List.of("iOS Development", "Android Development", "Mobile App"))
    ));

    public List<JobPost> getJobs() {
        return jobs;
    }

    public void addJob(JobPost job) {
        jobs.add(job);
    }

    public JobPost getJob(int postId) {
        return jobs.stream()
                .filter(job -> job.getPostId() == postId)
                .findFirst()
                .orElse(null);
    }

    public void updateJob(JobPost jobPost) {
        jobs.stream()
                .filter(job -> job.getPostId() == jobPost.getPostId())
                .forEach(job -> {
                    job.setPostDesc(jobPost.getPostDesc());
                    job.setPostProfile(jobPost.getPostProfile());
                    job.setReqExperience(jobPost.getReqExperience());
                    job.setPostTechStack(jobPost.getPostTechStack());
                });
    }

    public boolean deleteJob(int postId) {
        int index = -1;
        for (JobPost job : jobs) {
            if (job.getPostId() == postId) {
                index = jobs.indexOf(job);
            }
        }
        if (index >= 0) {
            jobs.remove(index);
            return true;
        }
        return false;
    }
}
