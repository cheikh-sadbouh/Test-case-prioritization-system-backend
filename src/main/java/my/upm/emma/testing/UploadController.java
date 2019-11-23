package my.upm.emma.testing;

import java.io.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


@Controller
public class UploadController {

    @Autowired
    StorageService storageService;

    List<String> files = new ArrayList<String>();


    @RequestMapping(
            value = ("/post"),
            headers = "content-type=multipart/form-data",
            method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            System.out.println(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message));
            System.out.println(e.getStackTrace());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/getoption", method = RequestMethod.POST, headers = "Accept=application/json")

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<String> getOptions(@RequestBody Map<String, String> payload){
        String message = "ok";
        try {

         System.out.println(payload.get("method"));
         System.out.println(payload.get("line"));
         System.out.println(payload.get("insturction"));
         System.out.println(payload.get("branch"));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // code goes here.
                   runTest();
                    readXml();
                    //GetResult.status="complete";
                }
            }).start();

            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            message = "FAIL to get options";
            System.out.println(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message));
            System.out.println(e.getStackTrace());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

public   void runTest(){
    ProcessBuilder processBuilder = new ProcessBuilder();

    processBuilder.command("bash", "-c", "ls /home/cheikh/IdeaProjects/testing | mvn clean test");


    try {

        Process process = processBuilder.start();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

    } catch (IOException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }


}

public void readXml(){
    try
    {
//creating a constructor of file class and parsing an XML file

        File file = new File(getClass().getClassLoader().getResource("report/jacoco.xml").getFile());
//an instance of factory that gives a document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
        dbf.setValidating(false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        NodeList nodeList = doc.getElementsByTagName("class");
// nodeList is not iterable, so we are using for loop
        for (int itr = 0; itr < nodeList.getLength(); itr++)
        {
            Node node = nodeList.item(itr);
            System.out.println("\nNode Name :" + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE )
            {
                System.out.println("inside getnodetype");
                if(node.getAttributes().getNamedItem("sourcefilename").equals("TestCircle")){
                    System.out.println("sourcefile");
                    Element eElement = (Element) node;
                    if(eElement.getChildNodes().item(9).getNodeName().equals("counter")){

                        System.out.println("counter : "+ eElement.getChildNodes().item(9).getAttributes().getNamedItem("INSTRUCTION"));

                    }
                }


            }
        }
    }
    catch (Exception e)
    {
        e.printStackTrace();

    }
}
}
