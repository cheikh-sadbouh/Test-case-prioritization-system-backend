package my.upm.emma.testing;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class responsEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    public String fileName;
    public String testingOption;

    public responsEntity(){}
    public responsEntity(String fileName , String testingOption){
        this.fileName = fileName;
        this.testingOption =  testingOption ;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTestingOption(String testingOption) {
        this.testingOption = testingOption;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTestingOption() {
        return testingOption;
    }
}



