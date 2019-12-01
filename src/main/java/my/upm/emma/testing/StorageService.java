package my.upm.emma.testing;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("/home/cheikh/IdeaProjects/testing/src/main/java/user/uploaded/code/");
    private final Path testingLoacation = Paths.get("/home/cheikh/IdeaProjects/testing/src/test/java/user/uploaded/code");

    public StorageService() throws URISyntaxException {
    }

    public void store(MultipartFile file) {
        try {
            if(Objects.requireNonNull(file.getOriginalFilename()).startsWith("Test")){
                Files.copy(file.getInputStream(), this.testingLoacation.resolve(file.getOriginalFilename()));
            }else{
                Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            }

        } catch (Exception e) {
            System.out.println("from store");
            throw new RuntimeException("Failed to store file " +  e);

        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
        FileSystemUtils.deleteRecursively(testingLoacation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
            Files.createDirectory(testingLoacation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}
