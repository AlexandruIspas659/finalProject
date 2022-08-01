package ro.sda.javaro35.finalProject.dto.job;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ro.sda.javaro35.finalProject.entities.job.Categories;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class JobDto {

    Long id;
    String title;
    String description;
    Categories category;
    String salary;
    String image;

}
