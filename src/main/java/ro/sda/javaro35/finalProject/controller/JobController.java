package ro.sda.javaro35.finalProject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.sda.javaro35.finalProject.dto.job.JobDto;
import ro.sda.javaro35.finalProject.entities.job.Categories;
import ro.sda.javaro35.finalProject.entities.job.Job;
import ro.sda.javaro35.finalProject.services.SpringJobService;

import java.util.List;

@Controller
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    private final SpringJobService springJobService;

    public JobController(SpringJobService springJobService) {
        this.springJobService = springJobService;
    }

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
    public String add(@ModelAttribute Job job) {
        springJobService.save(job);
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/{id}/edit")
    public String showEditForm(Model model, @PathVariable Long id) {
        model.addAttribute("job", springJobService.findById(id));
        return "job-edit";
    }

    @PostMapping("/jobs/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute JobDto jobDto) {
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
