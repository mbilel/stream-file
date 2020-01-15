package com.example.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@SpringBootApplication
public class FileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
	}
	@RequestMapping(value = "/get-file", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE}, method = RequestMethod.GET)
	public void getFile(@RequestParam(value="fileName", required=false) String fileName, HttpServletRequest request,
						HttpServletResponse response) throws IOException {


		byte[] reportBytes = null;
		File result = new File("/home/bilel/Downloads/" + fileName);

		if (result.exists()) {
			InputStream inputStream = new FileInputStream("/home/bilel/Downloads/" + fileName);
			String type = result.toURL().openConnection().guessContentTypeFromName(fileName);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setHeader("Content-Type", type);

			reportBytes = new byte[100];//New change
			OutputStream os = response.getOutputStream();//New change
			int read = 0;
			while ((read = inputStream.read(reportBytes)) != -1) {
				os.write(reportBytes, 0, read);
			}
			os.flush();
			os.close();

		}
	}
}
