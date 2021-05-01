package hu.emraxxor.fstack.demo.data.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotoFormElement {

    private String id;

    private String name;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime creationTime;

    private Long albumId;

}
