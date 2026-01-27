package dev.hazoe.springbootrest.service;


import dev.hazoe.springbootrest.model.JobPost;
import dev.hazoe.springbootrest.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JobService {
    private JobRepo repo;

    @Autowired
    public JobService(JobRepo repo) {
        this.repo = repo;
    }

    public void addJob(JobPost jobPost) {
        repo.save(jobPost);
    }

    public List<JobPost> getJobs() {
        return repo.findAll();
    }

    public JobPost getJob(int postId) {
        return repo.findById(postId).orElse(new JobPost());
    }

    public void updateJob(JobPost jobPost) {
        repo.save(jobPost);
    }

    public void deleteJob(int postId) {
        repo.deleteById(postId);
    }

    public String loadData() {
        List<JobPost> jobs = new ArrayList<>(Arrays.asList(

                new JobPost(null, "Java Developer", "Must have good experience in core Java and advanced Java", 2,
                        List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")),


                new JobPost(null, "Frontend Developer", "Experience in building responsive web applications using React", 3,
                        List.of("HTML", "CSS", "JavaScript", "React")),


                new JobPost(null, "Data Scientist", "Strong background in machine learning and data analysis", 4,
                        List.of("Python", "Machine Learning", "Data Analysis")),


                new JobPost(null, "Network Engineer", "Design and implement computer networks for efficient data communication", 5,
                        List.of("Networking", "Cisco", "Routing", "Switching")),


                new JobPost(null, "Mobile App Developer", "Experience in mobile app development for iOS and Android", 3,
                        List.of("iOS Development", "Android Development", "Mobile App")),
                new JobPost(null, "Software Engineer", "Exciting opportunity for a skilled software engineer.", 3,
                        List.of("Java", "Spring", "SQL")),
                new JobPost(null, "Data Scientist", "Join our data science team and work on cutting-edge projects.", 5,
                        List.of("Python", "Machine Learning", "TensorFlow")),
                new JobPost(null, "Frontend Developer", "Create amazing user interfaces with our talented frontend team.", 2,
                        List.of("JavaScript", "React", "CSS")),
                new JobPost(null, "Network Engineer", "Design and maintain our robust network infrastructure.", 4,
                        List.of("Cisco", "Routing", "Firewalls")),
                new JobPost(null, "UX Designer", "Shape the user experience with your creative design skills.", 3,
                        List.of("UI/UX Design", "Adobe XD", "Prototyping"))

        ));

        repo.saveAll(jobs);
        return "success";
    }

    public List<JobPost> searchJob(String keyword) {
        return repo.findByPostProfileContainingOrPostDescContaining(keyword, keyword);
    }
}
