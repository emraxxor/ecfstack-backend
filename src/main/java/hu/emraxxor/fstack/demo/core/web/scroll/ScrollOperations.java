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
	public void postInit();
	
	/**
	 * Before destroy
	 */
	public void beforeDestroy();
	
	/**
	 * Initialize the query of the source
	 */
	public void queryInitialization();
	
	/**
	 * Execute the query
	 */
	public void executeQuery();
	
	/**
	 * Called after the query has been executed
	 */
	public void onQueryComplete();
	
	/**
	 * Sets the source
	 * @param source
	 */
	public void source(AbstractScrollSource<WEB_APP_CONTEXT, DATA_TYPE> source);
	
	/**
	 * Sets the parameters
	 * @param params
	 */
	public void params(List<?> params);
}
