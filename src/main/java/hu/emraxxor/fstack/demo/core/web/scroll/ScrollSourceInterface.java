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
	public void query();	
	
	/**
	 * Current token
	 * @return
	 */
	public String token();
	
	
	/**
	 * Number of documents
	 * @return
	 */
	public int count();
	
	/**
	 * Query initialization
	 */
	public void initializeQuery();
	
	/**
	 * 
	 * @return
	 */
	public int size();
	
	/**
	 * Total result number of the query
	 * @return
	 */
	public long total();
	
	/**
	 * The result of the query
	 * @return
	 */
	public List<DATA_TYPE> content();
	
	/**
	 * Sets extra parameters for the source
	 * @param params
	 */
	public void params(List<?> params);
	
	
	public void postInit();
	
	
	public void beforeDestroy();
}
