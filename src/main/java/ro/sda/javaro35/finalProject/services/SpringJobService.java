package ro.sda.javaro35.finalProject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sda.javaro35.finalProject.dto.job.JobDto;
import ro.sda.javaro35.finalProject.entities.job.Categories;
import ro.sda.javaro35.finalProject.entities.job.Job;
import ro.sda.javaro35.finalProject.repository.JobRepository;

import java.util.List;

@Service
public class SpringJobService {

    private static final Logger log = LoggerFactory.getLogger(SpringJobService.class);

    private JobRepository jobRepository;

    @Autowired
    public SpringJobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void save(Job job) {
        log.info("saving job {}", job.getTitle());
        jobRepository.save(job);
    }

    public List<Job> findAll() {
        log.info("finding all jobs");
        return jobRepository.findAll();
    }

    public Job findById(Long id) {
        log.info("finding by id");
        return jobRepository.findById(id).orElseThrow(() -> new RuntimeException("job not found"));
    }

    public List<Job> findAllByCategory(String category) {
        log.info("finding by category");
        Categories categ = Categories.valueOf(category);
        return jobRepository.findAllByCategory(categ);
    }

    public void update(Long jobId, JobDto jobDto) {
        log.info("update job {}", jobDto);
        jobRepository.findById(jobId)
                .map(existingJob -> updateEntity(jobDto, existingJob))
                .map(updatedJob -> jobRepository.save(updatedJob))
                .orElseThrow(() -> new RuntimeException("job not found"));
    }

    public Job updateEntity(JobDto jobDto, Job existingJob) {
        existingJob.setTitle(jobDto.getTitle());
        existingJob.setDescription(jobDto.getDescription());
        existingJob.setCategory(jobDto.getCategory());
        existingJob.setSalary(jobDto.getSalary());
        return existingJob;
    }

    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        jobRepository.deleteById(id);
    }
}
