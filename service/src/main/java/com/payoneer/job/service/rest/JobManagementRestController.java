package com.payoneer.job.service.rest;

import com.payoneer.job.service.service.JobFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@Slf4j
public class JobManagementRestController {

    private final JobFacadeService jobFacadeService;


    @RequestMapping(path = "/execute/{name}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object executeJob(@PathVariable String name, @RequestParam Map<String, String> params) {
        log.debug("Start job execution");
        return jobFacadeService.executeJob(name, params);
    }


    @RequestMapping(path = "/schedule/{name}/{dateTime}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object scheduleJob(@PathVariable String name, @PathVariable LocalDateTime dateTime,
                              @RequestParam Map<String, String> params) {
        log.debug("Start job scheduling");
        return jobFacadeService.scheduleJob(name, params, dateTime);
    }


    @RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findJobInfo(@PathVariable Long id) {
        log.debug("Start job info search");
        return jobFacadeService.getJobInfo(id);
    }


    @RequestMapping(path = "/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAll() {
        log.debug("Start query job info");
        return jobFacadeService.getAllJobInfo();
    }

}
