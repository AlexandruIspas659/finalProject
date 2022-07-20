package ro.sda.javaro35.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sda.javaro35.finalProject.entities.job.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
