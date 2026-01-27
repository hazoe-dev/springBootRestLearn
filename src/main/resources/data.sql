INSERT INTO job_post (
    post_profile,
    post_desc,
    req_experience,
    post_tech_stack
) VALUES
      (
          'Backend Developer',
          'Responsible for building REST APIs and microservices',
          3,
          ARRAY['Java', 'Spring Boot', 'PostgreSQL', 'Docker']
      ),
      (
          'Frontend Developer',
          'Build modern web UIs',
          2,
          ARRAY['JavaScript', 'React', 'HTML', 'CSS']
      );
