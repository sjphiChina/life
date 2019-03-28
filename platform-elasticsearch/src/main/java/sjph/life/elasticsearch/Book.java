package sjph.life.elasticsearch;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "lifetest", type = "books")
public class Book {
    @Id
    private String id;
	String name;
	String message;
	String type ;

	public Book() {
    }
	
	/**
     * @param id
     * @param name
     * @param message
     * @param type
     */
    public Book(String id, String name, String message, String type) {
        super();
        this.id = id;
        this.name = name;
        this.message = message;
        this.type = type;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", message=" + message + ", type=" + type
                + "]";
    }
	
}
