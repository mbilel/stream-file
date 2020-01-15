package com.example.fileserver.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@RequestMapping("/test")
public class Client {

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping(value = "/invoke")
    public void get(){
        HttpHeaders headers = new HttpHeaders();//1 Line

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));//2 Line
        HttpEntity statusEntity=new HttpEntity(headers);//5 Line
        String uri_status="http://localhost:8080/get-file?fileName=eclipseaa.zip";//6 Line

        //ResponseEntity<byte[]> resp_status = restTemplate.getForEntity(uri_status,  byte[].class, "1");//7 Line

        File file = restTemplate.execute(uri_status, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = new File("/home/bilel/Downloads/input/eclipsedownloaded.zip");
            ret.createNewFile();
            //File ret = File.createTempFile("eclipse111", ".zip", new File("/home/bilel/Downloads/input/"));
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });



        System.out.println("Hello "+file.getName());
    }
}
