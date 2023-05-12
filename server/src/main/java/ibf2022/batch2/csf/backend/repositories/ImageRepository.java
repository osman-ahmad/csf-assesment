package ibf2022.batch2.csf.backend.repositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {
	
	@Autowired
    private AmazonS3 s3Client;

    @Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName;

	public List<String> upload(MultipartFile file) throws IOException {
		List<String> keys = new ArrayList<>();

        try (ZipInputStream zip = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry = zip.getNextEntry();
	
			while (entry != null) {
                if (!entry.isDirectory()) {

					// User data
					Map<String, String> userData = new HashMap<>();
					userData.put("name", "fred");
					userData.put("uploadTime", (new Date()).toString());
					userData.put("originalFilename", file.getOriginalFilename());

                    // Prepare the metadata
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(entry.getSize());
                    String fileName = entry.getName().toLowerCase();
					if (fileName.endsWith(".png")) {
						metadata.setContentType("image/png");
					} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
						metadata.setContentType("image/jpeg");
					} else if (fileName.endsWith(".gif")) {
						metadata.setContentType("image/gif");
					} else {
						throw new IllegalArgumentException("Unsupported file type: " + fileName);
					}

                    // Use the entry name as part of the key to avoid collisions
                    String key = UUID.randomUUID().toString().substring(0, 8) + "_" + entry.getName();

                    PutObjectRequest putReq = new PutObjectRequest(bucketName, key, zip, metadata);
                    putReq.withCannedAcl(CannedAccessControlList.PublicRead);

                    s3Client.putObject(putReq);

                    keys.add(key);
                }

                zip.closeEntry();
                entry = zip.getNextEntry();
	
            }
        }

        return keys;
    }
}