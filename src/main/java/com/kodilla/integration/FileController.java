package com.kodilla.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileWritingMessageHandler outputFileHandler;

    @Autowired
    public FileController(FileWritingMessageHandler outputFileHandler) {
        this.outputFileHandler = outputFileHandler;
    }

    @PostMapping("/{fileName}")
    public void createFile(@PathVariable String fileName, @RequestBody String fileContent) throws IOException {
        Files.write(Paths.get("data/input/" + fileName), fileContent.getBytes());
    }

    @GetMapping("/{fileName}")
    public String readFile(@PathVariable String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("data/input/" + fileName)));
    }

    @GetMapping("/output")
    public List<String> getOutputFiles() throws IOException {
        List<String> fileNames = new ArrayList<>();
        try (var files = Files.list(Paths.get("data/output"))) {
            files.forEach(file -> fileNames.add(file.getFileName().toString()));
        }
        return fileNames;
    }

}
