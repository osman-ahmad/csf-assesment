package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Controller
@RequestMapping
public class UploadController {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ArchiveRepository archiveRepository;

	public ResponseEntity<String> upload(@RequestPart MultipartFile file){

		List<String> keys;
	
		try{
			keys = imageRepository.upload(file);
		} catch(IOException e) {
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(e.getMessage());
		}
			
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for(String key : keys) {
			arrayBuilder.add(key);
		}
		JsonArray jsonArray = arrayBuilder.build();
	
		JsonObject payload = Json.createObjectBuilder()
			.add("imageKeys", jsonArray)
			.build();
	
		return ResponseEntity.ok(payload.toString());
	}

	// TODO: Task 2, Task 3, Task 4
	

	// TODO: Task 5
	

	// TODO: Task 6

}
