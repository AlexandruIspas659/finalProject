package ro.sda.javaro35.finalProject.dto.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ro.sda.javaro35.finalProject.entities.job.Job;
import ro.sda.javaro35.finalProject.repository.JobRepository;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE)
public class JobMapper {

    JobRepository jobRepository;

    public JobDto toDto(Job job) {
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCategory(job.getCategory());
        dto.setSalary(job.getSalary());
        if (job.getImage() != null) {
            dto.setImage(job.getImage());
        }
        return dto;
    }

    public Job toEntity(JobDto jobDto) {
        Job job = new Job();
        if (jobDto.getId() != null) {
            job = jobRepository.findById(jobDto.getId()).orElse(job);
        }
        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setCategory(jobDto.getCategory());
        job.setSalary(jobDto.getSalary());
        if (jobDto.getImage() != null) {
            job.setImage(jobDto.getImage());
        }
        return job;
    }

}
