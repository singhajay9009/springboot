package com.sample.s3Ops.s3FileOperations.controlller;

import com.sample.s3Ops.s3FileOperations.model.FileData;
import com.sample.s3Ops.s3FileOperations.service.S3StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;

@RestController
@Slf4j
public class S3FileOpsController {

    private final S3StorageService s3StorageService;

    public S3FileOpsController(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<EntityModel<FileData>> upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        try{
            s3StorageService.uploadFile(multipartFile );
            FileData fileData = constructFileData(multipartFile);
            EntityModel<FileData> entityModel = EntityModel.of(
                    fileData,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(S3FileOpsController.class).downloadFile(multipartFile.getOriginalFilename())).withRel("fileDownloadUrl")
            );
            return ResponseEntity.ok(entityModel);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws Exception {
        try{
            byte[] bytes = s3StorageService.downloadFile(filename);
            ByteArrayResource resource = new ByteArrayResource(bytes);
            return ResponseEntity
                    .ok()
                    .contentLength(bytes.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        }catch(Exception e){
            log.error("Error in downloading file: " + filename + "\n", e);
            throw new Exception(e);
        }
    }

    @DeleteMapping("/deleteFile/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) throws Exception {
        try{
            s3StorageService.deleteFile(fileName);
            return ResponseEntity.ok("file: " + fileName + " deleted...");
        }catch(Exception e){
            log.error("Error in deleting file: " + fileName, e.getMessage());
            throw new Exception(e);
        }
    }


    private FileData constructFileData(MultipartFile file){
        return FileData.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .build();
    }
}
