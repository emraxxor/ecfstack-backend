package hu.emraxxor.fstack.demo.core.web.scroll;

import java.util.List;

/**
 * 
 * @author Attila Barna
 *
 * @param <WEB_APP_CONTEXT>
 * @param <DATA_TYPE>
 */
public interface ScrollOperations<WEB_APP_CONTEXT, DATA_TYPE> {
	
	
	/**
	 * Post initialization
	 */
	void postInit();
	
	/**
	 * Before destroy
	 */
	void beforeDestroy();
	
	/**
	 * Initialize the query of the source
	 */
	void queryInitialization();
	
	/**
	 * Execute the query
	 */
	void executeQuery();
	
	/**
	 * Called after the query has been executed
	 */
	void onQueryComplete();
	
	/**
	 * Sets the source
	 */
	void source(AbstractScrollSource<WEB_APP_CONTEXT, DATA_TYPE> source);
	
	/**
	 * Sets the parameters
	 */
	void params(List<?> params);
}
