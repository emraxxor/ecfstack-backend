package hu.emraxxor.fstack.demo.core.web.scroll;

import java.util.Arrays;
import hu.emraxxor.fstack.demo.core.exceptions.UnsupportedTypeException;

/**
 * 
 * @author Attila Barna
 *
 */
public class ScrollResponseGenerator {

	
	public static <T> void validate(T o) throws UnsupportedTypeException {
        if (!(o instanceof ScrollOperations) ) {
            throw new UnsupportedTypeException("ScrollResponseGenerator only can be used by object that implements ScrollOperations.");
        }
	}
	
	/**
	 * 
	 * @param o
	 * @param source
	 * @return
	 * @throws UnsupportedTypeException
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends DefaultScrollResponse, V extends AbstractScrollSource> T generate(T o, V source, Object... params) throws UnsupportedTypeException {
    	validate(o); 
    	DefaultScrollResponse response = (DefaultScrollResponse)o; 

    	response.source((AbstractScrollSource) source);
    	
    	if ( params != null )
    		response.params(Arrays.asList(params));
    	
    	response.postInit();
        response.queryInitialization();
        response.executeQuery();
        response.onQueryComplete();
        response.beforeDestroy();
        return (T)response;
    }

}
