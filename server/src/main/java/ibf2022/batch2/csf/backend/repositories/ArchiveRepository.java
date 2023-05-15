package ibf2022.batch2.csf.backend.repositories;



import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.csf.backend.models.Archive;


@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// db.archives.insert({
	// 	    bundleId: "bundleId",
	// 		date: date,
	// 		title: "title",
	//  	name: "name",
	// 		comments: "comments",
	// 		urls: ["url1", "url2", ...]
	//   })
	public ObjectId recordBundle(Archive archive) {
		Document insertedDoc = mongoTemplate.insert(archive.toDocument(), "archives");
		return insertedDoc.getObjectId("_id");
	}

	
	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Optional<Archive> getBundleByBundleId(String bundleId) {
		
		Criteria criteria = Criteria.where("bundleId").is(bundleId);
		Query query = Query.query(criteria);
		Document result = mongoTemplate.findOne(query, Document.class, "post");
		if (null == result)
			return Optional.empty();

		return Optional.of(Archive.create(result));
	}

	

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	// public Object getBundles(/* any number of parameters here */) {
	// 	return null;
	// }
	public List<Archive> getBundles() {
        Query query = new Query();
        query.fields().include("title").include("date");
        return mongoTemplate.find(query, Archive.class);
    }


}






