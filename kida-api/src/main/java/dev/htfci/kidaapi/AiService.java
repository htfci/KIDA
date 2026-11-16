package dev.htfci.kidaapi;

import org.springframework.http.ResponseEntity;

import java.net.http.HttpResponse;


public interface AiService {
    HttpResponse<String> send(TaskDto taskDto);
}
