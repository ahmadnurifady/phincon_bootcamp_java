package phincon.threadpooltaskexecutor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import phincon.threadpooltaskexecutor.model.GithubUser;
import phincon.threadpooltaskexecutor.service.GitHubLookupService;

@RestController
@Slf4j
public class AsyncController {

    @Autowired
    GitHubLookupService service;

    @GetMapping("/github/user")
    public ResponseEntity<List<GithubUser>> findGithubUser() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<GithubUser> page1 = service.findUser("PivotalSoftware");
        CompletableFuture<GithubUser> page2 = service.findUser("CloudFoundry");
        CompletableFuture<GithubUser> page3 = service.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());
        List<GithubUser> list = new ArrayList();
        list.add(page1.get());
        list.add(page2.get());
        list.add(page3.get());

        return ResponseEntity.ok(list);
    }
}
