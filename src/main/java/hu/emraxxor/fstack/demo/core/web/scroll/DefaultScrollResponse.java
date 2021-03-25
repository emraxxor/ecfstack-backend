package hu.emraxxor.fstack.demo.core.web.scroll;

import java.util.List;

/**
 * 
 * Default scroll response for IScroll component
 * @author Attila Barna
 *
 */
public class DefaultScrollResponse<DATA_TYPE, WEB_APP_CONTEXT> extends DefaultScrollResponseData<DATA_TYPE> implements ScrollOperations<WEB_APP_CONTEXT, DATA_TYPE> {
	
	protected AbstractScrollSource<WEB_APP_CONTEXT, DATA_TYPE> source;

	public DefaultScrollResponse() {
		super();
	}
	
	@Override
	public void queryInitialization() {
		source.initializeQuery();
	}
	
	@Override
	public void executeQuery() {
		source.query();
	}
	
	@Override
	public void onQueryComplete() {
		data.addAll(source.content());
		token = source.token();
		total = source.total();
		count = source.count();
	}

	@Override
	public void source(AbstractScrollSource<WEB_APP_CONTEXT, DATA_TYPE> source) {
		this.source = source;
	}

	@Override
	public void params(List<?> params) {
		this.source.params(params);
	}

	@Override
	public void postInit() {
		this.source.postInit();
	}

	@Override
	public void beforeDestroy() {
		this.source.beforeDestroy();
	}
	
}
