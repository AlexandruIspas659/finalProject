package ro.sda.javaro35.finalProject.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.sda.javaro35.finalProject.dto.job.JobDto;
import ro.sda.javaro35.finalProject.dto.job.JobMapper;
import ro.sda.javaro35.finalProject.entities.job.Categories;
import ro.sda.javaro35.finalProject.entities.job.Job;
import ro.sda.javaro35.finalProject.services.SpringJobService;
import ro.sda.javaro35.finalProject.utils.ImageUtil;

import java.util.List;

@Controller
@AllArgsConstructor
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    private final SpringJobService springJobService;

    private JobMapper jobMapper;

    @GetMapping("/jobs")
    public String showJobsPage(Model model) {
        List<Job> jobs = springJobService.findAll();
        model.addAttribute("jobsInView", jobs);
        return "jobs-list";
    }

    @GetMapping("/jobs/add")
    public String showAddForm(Model model) {
        Job newJob = new Job();
        model.addAttribute("job", newJob);
        return "job-add";
    }

    @PostMapping("/jobs/add")
    public String add(@RequestParam("file") MultipartFile file, @ModelAttribute JobDto jobDto) {
        if (!file.isEmpty()) {
            jobDto.setImage(ImageUtil.resizeAndCrop(file, 300, 175));
        }
        springJobService.save(jobDto);
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        model.addAttribute("job", springJobService.findById(id));
        return "job-edit";
    }

    @PostMapping("/jobs/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam("file") MultipartFile file, @ModelAttribute JobDto jobDto) {
        if (!file.isEmpty()) {
            jobDto.setImage(ImageUtil.resizeAndCrop(file, 300, 175));
        }
        springJobService.update(id, jobDto);
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/{category}")
    public String findAllByCategory(@PathVariable String category, Model model) {
        List<Job> jobs = springJobService.findAllByCategory(category);
        model.addAttribute("jobsInView", jobs);
        return "jobs-list";
    }

    @GetMapping("/jobs/{id}/delete")
    public String delete(@PathVariable long id) {
        springJobService.delete(id);
        return "redirect:/jobs";
    }


}
