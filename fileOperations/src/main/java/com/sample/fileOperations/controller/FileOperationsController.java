package com.sample.fileOperations.controller;

import com.sample.fileOperations.exceptions.FileDeleteException;
import com.sample.fileOperations.exceptions.FileUploadException;
import com.sample.fileOperations.model.FileData;
import com.sample.fileOperations.service.FileOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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

@PostMapping("/uploadAll")
    public ResponseEntity<List<EntityModel<FileData>>> uploadAll(@RequestParam("files") MultipartFile[] files){
        try{

            List<EntityModel<FileData>> entities = Arrays.stream(files)
                    .peek(fileOperationService::store)
                    .map((file) -> EntityModel.of(
                            constructFileData(file),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FileOperationsController.class).downloadFile(file.getName())).withRel("fileUri")
                    )).collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(entities);

        }catch(Exception e){
            throw new FileUploadException("Exception in Multiple files upload.", e);
        }
    }


    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileOperationService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        try{
            fileOperationService.delete(fileName);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Deleted file: " + fileName);
        }catch(Exception e){
           throw new FileDeleteException("Exception in deleting file: " + fileName, e);
        }

    }

    private FileData constructFileData(MultipartFile file){
        return FileData.builder()
                .fileName(file.getName())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .build();
    }
}
