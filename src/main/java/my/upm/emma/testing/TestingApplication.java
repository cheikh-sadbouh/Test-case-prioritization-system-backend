package my.upm.emma.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = "my.upm.emma.testing")
@EnableJpaRepositories(basePackages = "my.upm.emma.testing")
public class TestingApplication  implements CommandLineRunner {
@Resource
StorageService storageService;
@Resource
DaoInter doaObj;
    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class, args);

    }
    @Override
    public void run(String... arg) throws Exception {
       storageService.deleteAll();
        storageService.init();
        doaObj.deleteAll();
        doaObj.save(new responsEntity("dd","dsdsd"));
    }


    public  void saveit(String filename, String testingOption){
        doaObj.save(new responsEntity(filename,testingOption));
    }

}
