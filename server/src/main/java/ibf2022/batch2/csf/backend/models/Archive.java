package ibf2022.batch2.csf.backend.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

public class Archive {

	
	private String bundleId;
    private Date date;
    private String title;
	private String name;
	private String comments;
	private List<String> urls;

	public String getBundleId() {  
        return bundleId;
    }

    public void setBundleId(String bundleId) {  
        this.bundleId = bundleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Document toDocument() {
		Document document = new Document();
		document.put("bundleId", UUID.randomUUID().toString().substring(0, 8));
        document.put("date", this.date);
        document.put("title", this.title);
        document.put("name", this.name);
        document.put("comments", this.comments);
        document.put("urls", this.urls);

		return document;
	}

	public static Archive create(Document doc) {
		Archive post = new Archive();
		post.setBundleId(doc.getString("bundleId"));
        post.setDate(doc.getDate("date"));
        post.setTitle(doc.getString("title"));
        post.setName(doc.getString("name"));
        post.setComments(doc.getString("comments"));
        post.setUrls(doc.getList("urls", String.class));
        
		return post;
	}

}