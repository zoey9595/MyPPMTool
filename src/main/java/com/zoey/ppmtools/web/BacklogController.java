package com.zoey.ppmtools.web;

import com.zoey.ppmtools.domain.ProjectTask;
import com.zoey.ppmtools.services.MapValidationErrorService;
import com.zoey.ppmtools.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result,
                                                     @PathVariable String backlog_id,
                                                     Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal) {
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask,
                                            BindingResult result,
                                            @PathVariable String backlog_id,
                                            @PathVariable String pt_id,
                                            Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) return errorMap;

        ProjectTask projectTask = projectTaskService.updateByProjectSequence(updatedProjectTask, backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal) {
        projectTaskService.deleteProjectTaskByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<>("Project Task '" + pt_id + "' was deleted successfully", HttpStatus.OK);
    }
}
