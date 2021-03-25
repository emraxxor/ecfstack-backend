package hu.emraxxor.fstack.demo.mapping;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "picture")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
	
	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String name;
	
	@Field(type = FieldType.Text)
	private String description;
	
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime creationTime;
	
	@Field(type = FieldType.Keyword)
	private String path;
	
	@Field(type = FieldType.Keyword)
	private String fileName;
	
	@Field(type = FieldType.Long)
	private Long albumId;
	
	@Field
	private List<Long> authors;
	
}
