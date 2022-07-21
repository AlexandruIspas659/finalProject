package ro.sda.javaro35.finalProject.dto.job;

import org.springframework.stereotype.Component;
import ro.sda.javaro35.finalProject.entities.job.Job;

@Component
public class JobMapper {

    public JobDto toDto(Job job) {
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCategory(job.getCategory());
        dto.setSalary(job.getSalary());
        return dto;
    }

    public Job toEntity(JobDto jobDto) {
        Job job = new Job();
        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setCategory(jobDto.getCategory());
        job.setSalary(jobDto.getSalary());
        return job;
    }

}
