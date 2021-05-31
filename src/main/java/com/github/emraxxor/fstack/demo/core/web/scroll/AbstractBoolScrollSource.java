package com.github.emraxxor.fstack.demo.core.web.scroll;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.github.emraxxor.fstack.demo.core.data.es.ESDataElement;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Attila Barna
 *
 * @param <T>
 */
public abstract class AbstractBoolScrollSource<T> extends AbstractScrollSource<WebApplicationContext, T> {

	protected ElasticsearchRestTemplate template;

	protected RestHighLevelClient client;

	protected SearchResponse response;
	
	protected BoolQueryBuilder query;
	
	protected String index;
	
	public AbstractBoolScrollSource(WebApplicationContext context, String token, String index) {
		super(context, token);
		this.index = index;
		this.template = this.context.getBean(ElasticsearchRestTemplate.class);
		this.client = this.context.getBean(RestHighLevelClient.class);
		this.size = 50;
	}
	
	@Override
	public void query()  {
		try {
			if (token == null || token.equalsIgnoreCase("") || token.equalsIgnoreCase("null"))  {
				SearchRequest searchRequest = new SearchRequest(index); 
				SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
				sourceBuilder.query(query);
				sourceBuilder.size(this.size);
				searchRequest.scroll(new TimeValue(30000L));
				searchRequest.source(sourceBuilder);			
				response = client.search(searchRequest, RequestOptions.DEFAULT);
			} else {
				SearchScrollRequest scrollRequest = new SearchScrollRequest(token);
				scrollRequest.scroll(new TimeValue(30000L));
				response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
			}
			token = response.getScrollId();
			total = response.getHits().getTotalHits().value;
			count = response.getHits().getHits().length;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int count() {
		return this.count;
	}
	
	
	public T convert(SearchHit o) {
		T e = new Gson().fromJson(o.getSourceAsString(), new TypeToken<T>(){}.getType());
		if ( e instanceof ESDataElement<?>)
			((ESDataElement<?>)e).setDocumentId(o.getId());
		return e;
	}
	
	@Override
	public List<T> content() {
		List<T> result = new ArrayList<>();
		if (response.getHits().getHits().length > 0) {
				Arrays.stream(response.getHits().getHits())
					 .map(this::convert)
					 .forEach(result::add);
			
		} 
		return result;
	}

	@Override
	public abstract void initializeQuery();


}
