package hu.emraxxor.fstack.demo.data.type.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import hu.emraxxor.fstack.demo.config.DefaultApplicationConfiguration;
import hu.emraxxor.fstack.demo.core.web.scroll.AbstractBoolScrollSource;
import hu.emraxxor.fstack.demo.data.mapper.PhotoMapper;
import hu.emraxxor.fstack.demo.data.type.PhotoFormElement;
import hu.emraxxor.fstack.demo.data.type.Picture;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Attila Barna
 */
public class PhotoWaterfallSource extends AbstractBoolScrollSource<PhotoFormElement> {

    private final PhotoMapper photoMapper;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public PhotoWaterfallSource(WebApplicationContext context, String token) {
        super(context, token, DefaultApplicationConfiguration.ES_APP_INDICES.PHOTO.val());
        this.photoMapper = context.getBean(PhotoMapper.class);
    }

    @Override
    public void initializeQuery() {
        query = QueryBuilders.boolQuery().must( QueryBuilders.matchAllQuery() );
    }

    @Override
    public PhotoFormElement convert(SearchHit o) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter)).create();
        Picture element = gson.fromJson(o.getSourceAsString(), new TypeToken<Picture>(){}.getType()) ;
        element.setId(o.getId());
        return photoMapper.toPhotoFormElement(element);
    }

    @Override
    public void postInit() {
    }

    @Override
    public void beforeDestroy() {

    }
}
