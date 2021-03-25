package hu.emraxxor.fstack.demo.core.web.scroll;

/**
 * 
 * @author Attila Barna
 *
 * @param <DATA_ELEMENT>
 */
public interface ScrollContentEvent<DATA_ELEMENT, RESPONSE_ELEMENT> {

	
	public void beforeProcess();
	
	public void onProcess(DATA_ELEMENT element);
	
	public void afterProcess();
	
	public RESPONSE_ELEMENT getResponseElement();
	
	
}
