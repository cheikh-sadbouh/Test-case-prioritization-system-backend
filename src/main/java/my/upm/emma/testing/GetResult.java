package my.upm.emma.testing;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@CrossOrigin
public class GetResult {
  static  String status="inprocess";
    @GetMapping("/getTestResult")
    @CrossOrigin
    public ResponseEntity<?> gettestingresult(){
        HashMap<String, String[]> m = new HashMap<String, String[]>();
        m.put("status",new String[]{status});
        return  ResponseEntity.status(HttpStatus.OK).body(m);
    }
}
