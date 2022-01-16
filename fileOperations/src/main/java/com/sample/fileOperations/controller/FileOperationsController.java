package com.sample.fileOperations.controller;

import com.sample.fileOperations.exceptions.FileUploadException;
import com.sample.fileOperations.model.FileData;
import com.sample.fileOperations.service.FileOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/fileOps/")
@RequiredArgsConstructor
public class FileOperationsController {

    private final FileOperationService fileOperationService;

    @PostMapping("/upload")
    public ResponseEntity<EntityModel<FileData>> upload(@RequestParam("file") MultipartFile file){
        try {
            fileOperationService.store(file);

            FileData fileData = constructFileData(file);

            EntityModel<FileData> entityModel = EntityModel.of(
                    fileData,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FileOperationsController.class).downloadFile(file.getName())).withRel("fileUri")
            );
            return ResponseEntity.accepted().body(entityModel);
        }catch(Exception e){
            throw new FileUploadException("Exception in uploading file: "+ file.getName(), e);
        }

    }


    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileOperationService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private FileData constructFileData(MultipartFile file){
        return FileData.builder()
                .fileName(file.getName())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .build();
    }
}
