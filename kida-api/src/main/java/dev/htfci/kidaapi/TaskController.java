package dev.htfci.kidaapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;


@RestController
@RequestMapping("/api/v1/files/task")
public class TaskController {
    private final FileStorageService fileStorageService;
    private final AiService aiService;


    public TaskController(FileStorageService fileStorageService, AiService aiService) {
        this.fileStorageService = fileStorageService;
        this.aiService = aiService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/summarize/{filename}")
    public ResponseEntity<String> summarize(@PathVariable("filename") String filename) {
        if (filename == null || filename.isBlank()) {
            return ResponseEntity.badRequest().body("File is null");
        }
        String content = fileStorageService.readContent(filename);
        TaskDto taskDto = new TaskDto(content);
        HttpResponse<String> response = aiService.send(taskDto);
        return ResponseEntity.ok().body(response.body());
    }
}
