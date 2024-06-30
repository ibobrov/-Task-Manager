package ru.ibobrov.backend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    public Map<Integer, TaskDto> map = new HashMap<>(Map.of(1, new TaskDto(1, "task1"),
            2, new TaskDto(2, "task2")));


    @GetMapping("/api/public/task")
    public List<TaskDto> getAllTask() {
        return map.values().stream().toList();
    }

    @PostMapping("/api/public/task")
    public TaskDto addTask(@RequestBody TaskDto taskDto) {
        return map.put(map.size(), taskDto);
    }

    @PutMapping("/api/public/task/{id}/")
    public TaskDto updateTask(@RequestBody TaskDto taskDto, @PathVariable Integer id) {
        return map.put(id, taskDto);
    }

    @DeleteMapping("/api/public/task/{id}/")
    public void deleteTask(@PathVariable Integer id) {
        map.remove(id);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskDto {
        private Integer key;
        private String val;
    }
}
