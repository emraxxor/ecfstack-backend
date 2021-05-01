package hu.emraxxor.fstack.demo.core.web.scroll;

import java.util.List;

/**
 * 
 * @author Attila Barna
 *
 */
public interface ScrollSourceInterface<DATA_TYPE> {

	/**
	 * Specifies the query
	 */
	void query();
	
	/**
	 * Current token
	 */
	String token();
	
	
	/**
	 * Number of documents
	 */
	int count();
	
	/**
	 * Query initialization
	 */
	void initializeQuery();
	
	/**
	 *
	 */
	int size();
	
	/**
	 * Total result number of the query
	 */
	long total();
	
	/**
	 * The result of the query
	 */
	List<DATA_TYPE> content();
	
	/**
	 * Sets extra parameters for the source
	 */
	void params(List<?> params);
	
	
	void postInit();
	
	
	void beforeDestroy();
}
