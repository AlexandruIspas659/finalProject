package ro.sda.javaro35.finalProject.dto.job;

import org.springframework.stereotype.Component;
import ro.sda.javaro35.finalProject.entities.job.Job;

@Component
public class JobMapper {

    public JobResponse toDto(Job job) {
        JobResponse dto = new JobResponse();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCategory(job.getCategory());
        dto.setSalary(job.getSalary());
        return dto;
    }

    /*public Job toEntity(JobRequest jobRequest) {
        Job job = new Job();
        job.setTitle(jobRequest.getTitle());
    }*/
}
