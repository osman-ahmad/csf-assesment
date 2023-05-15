package ibf2022.batch2.csf.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
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

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

	@GetMapping(path="/bundle/{bundleId}")
	public ResponseEntity<String> retrieveImage(@PathVariable String bundleId) {
    Optional<Archive> r = this.archiveRepository.getBundleByBundleId(bundleId);
    
        Archive a = r.get();
        List<String> urls = new ArrayList<>();
        for (String image : a.getUrls()) {
            try {
                urls.add(image);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String url : urls) {
            arrayBuilder.add(url);
        }
        JsonArray jsonArray = arrayBuilder.build();

        JsonObject payload = Json.createObjectBuilder()
                .add("imageKeys", jsonArray)
                .build();

        return ResponseEntity.ok(payload.toString());
    
	}

	@GetMapping("/bundles")
    public ResponseEntity<List<Archive>> getBundles() {
        List<Archive> bundles = archiveRepository.getBundles();
        if (!bundles.isEmpty()) {
            return ResponseEntity.ok(bundles);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


			

	

}
