package ro.sda.javaro35.finalProject.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sda.javaro35.finalProject.dto.job.JobDto;
import ro.sda.javaro35.finalProject.dto.job.JobMapper;
import ro.sda.javaro35.finalProject.entities.job.Categories;
import ro.sda.javaro35.finalProject.entities.job.Job;
import ro.sda.javaro35.finalProject.repository.JobRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SpringJobService {

    private static final Logger log = LoggerFactory.getLogger(SpringJobService.class);

    private JobRepository jobRepository;

    private JobMapper jobMapper;

    public void save(JobDto jobDto) {
        log.info("saving job {}", jobDto.getTitle());
        Job jobEntity = jobMapper.toEntity(jobDto);
        jobRepository.save(jobEntity);
    }

    public List<Job> findAll() {
        log.info("finding all jobs");
        return jobRepository.findAll();
    }

    public JobDto findById(Long id) {
        log.info("finding by id");
        Job jobEntity = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("job not found"));
        return jobMapper.toDto(jobEntity);
    }

    public List<Job> findAllByCategory(String category) {
        log.info("finding by category");
        Categories categ = Categories.valueOf(category);
        return jobRepository.findAllByCategory(categ);
    }

    public void update(Long jobId, JobDto jobDto) {
        log.info("update job {}", jobDto);
        jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        save(jobDto);
    }

    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        jobRepository.deleteById(id);
    }
}
