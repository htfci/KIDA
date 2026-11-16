package dev.htfci.kidaapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskDto(
        @JsonProperty(value = "content") String content,
        @JsonProperty(value = "task") AiFileProcessTask fileProcessTask,
        @JsonProperty(value = "user_id") String userId) {

    public TaskDto(String content) {
        this(content, null, null);
    }
}
