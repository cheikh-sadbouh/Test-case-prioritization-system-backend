package my.upm.emma.testing;


import java.io.*;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


@Controller
public class UploadController {

    JSONObject object ;
    JSONArray keys ;
    @Autowired
    StorageService storageService;
    @Resource
    DaoInter doaObj;
    String [] Options = {"","","",""};
    List<String> files = new ArrayList<String>();
   List<String> returnobject = new ArrayList<>();
   HashMap<String,ArrayList<String>>  data = new HashMap<>();

   String [] testingOptions;
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
            returnobject.add(file.getOriginalFilename());

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            System.out.println(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message));
            System.out.println(e.getStackTrace());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @PostMapping(value = "/getoption")
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @ResponseBody
    public ResponseEntity<String> getOptions(@RequestBody  Object payload){

        String message = "ok";
        try {
            ObjectMapper oMapper = new ObjectMapper();

            try {
                String json = oMapper.writeValueAsString(payload);

                System.out.println(json);   // compact-print

                object = new JSONObject(json.trim());
                 keys = object.names ();

                for (int i = 0; i < keys.length (); ++i) {

                    String key = keys.getString (i); // Here's your key
                    String value = object.getString (key); // Here's your value

                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }








            new Thread(new Runnable() {
                @Override
                public void run() {
                    // code goes here.
                  runTest();
                    readXml();
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
                    System.out.println("node attr name :"+node.getAttributes().getNamedItem("sourcefilename").getNodeValue());

                    for (int s =0 ; s < returnobject.size() ; s++) {
                        System.out.println("class anme :"+returnobject.get(s).toString());
                        if (node.getAttributes().getNamedItem("sourcefilename").getNodeValue().equals(returnobject.get(s).toString())) {
                            Element eElement = (Element) node;
                            System.out.println("class found ");
                            NodeList ChildNodes = eElement.getChildNodes();
                            ArrayList<String> opt = new ArrayList<>();
                            String currentFilename ="";
                            for (int ii = 0; ii < ChildNodes.getLength(); ii++) {
                                Node nodes = ChildNodes.item(ii);
                                if (nodes.getNodeType() == nodes.ELEMENT_NODE && Objects.equals("counter", nodes.getNodeName())) {
                                    System.out.println("inside counter   ");

                                     keys = object.names ();
                                    for (int c = 0; c < keys.length(); ++c) {
                                        System.out.println("insde keys  ");
                                        String key = keys.getString(c); // Here's your key
                                        String value = object.getString(key); // Here's your value
                                        System.out.println("key"+key);
                                        System.out.println("value"+value);

                                        if (nodes.getAttributes().getNamedItem("type").getNodeValue().equals(key.toUpperCase()) && value.equals("true")) {
                                          String fileName = node.getAttributes().getNamedItem("sourcefilename").getNodeValue();
                                            System.out.println("file name " +fileName);


                                           String  testingOption = nodes.getAttributes().getNamedItem("type").getNodeValue();
                                            System.out.println("testing option " + testingOption);
                                            String  coverd = nodes.getAttributes().getNamedItem("covered").getNodeValue();
                                            System.out.println("coverd for "+ testingOption +"  option is :"+coverd);
                                           // opt.add(fileName);
                                            currentFilename = fileName;
                                            opt.add(coverd);
                                        //  responsEntity re=  Config.getbean();
                                          //re.setFileName(fileName);
                                          //re.setTestingOption(testingOption);
                                         //   doaObj.save(re);


                                        }

                                    }


                                }
                            }
                            System.out.println("data size"+data.size());
                            data.put(currentFilename,opt);
                            //data.put(node.getAttributes().getNamedItem("sourcefilename").getNodeValue(),nodes.getAttributes().getNamedItem("type").getNodeValue());
                            System.out.println("data size afert "+data.size());
                        }


                    }




                }
            }
System.out.println("data :");
System.out.println(data);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
