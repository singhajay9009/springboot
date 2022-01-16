package com.sample.fileOperations.service;

import com.sample.fileOperations.exceptions.FileOperationException;
import com.sample.fileOperations.properties.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileOperationService implements FileOperations{

    private final Path path;
    private FileStorageProperties fileStorageProperties;

    @Autowired
    public FileOperationService(FileStorageProperties fileStorageProperties) {
        this.path = Paths.get(fileStorageProperties.getUploadDir());
    }


    @Override
    public void init() {
        try{
            Files.createDirectories(path);
        }catch(IOException e){
            throw new FileOperationException("Could not initialize directory", e);
        }
    }

    @Override
    public void store(MultipartFile multipartFile) {
        try{
            if(multipartFile.isEmpty()){
                throw new FileOperationException("Failed to store empty file");
            }
            Path destinationFile = this.path.resolve(
                    Paths.get(multipartFile.getOriginalFilename()))
                            .normalize()
                            .toAbsolutePath();
            if(destinationFile.getParent().equals(this.path.toAbsolutePath())){
                throw new FileOperationException("Cannot store file outside current dir");
            }
            try(InputStream inputStream = multipartFile.getInputStream()){


                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

        }catch(IOException e){
            throw new FileOperationException("Failed to store file", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.path, 1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        }catch(IOException e){
            throw new FileOperationException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String fileName) {
        return path.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try{
            Path path = load(fileName);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new FileOperationException("Could not read file: " + fileName);
            }
        }catch(MalformedURLException e){
            throw new FileOperationException("could not read file: " + fileName, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Override
    public void delete(String fileName){
        FileSystemUtils.deleteRecursively(path.resolve(fileName).toFile());
    }
}
