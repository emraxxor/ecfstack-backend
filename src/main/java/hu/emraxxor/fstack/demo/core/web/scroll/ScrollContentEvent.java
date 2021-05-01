package hu.emraxxor.fstack.demo.core.web.scroll;

/**
 * 
 * @author Attila Barna
 *
 * @param <DATA_ELEMENT>
 */
public interface ScrollContentEvent<DATA_ELEMENT, RESPONSE_ELEMENT> {

	void beforeProcess();
	
	void onProcess(DATA_ELEMENT element);
	
	void afterProcess();
	
	RESPONSE_ELEMENT getResponseElement();
}
