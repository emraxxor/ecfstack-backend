package com.github.emraxxor.fstack.demo.core.web.scroll;

import java.util.Arrays;
import com.github.emraxxor.fstack.demo.core.exceptions.UnsupportedTypeException;

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
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends DefaultScrollResponse, V extends AbstractScrollSource> T generate(T o, V source, Object... params) throws UnsupportedTypeException {
    	validate(o);

		o.source(source);
    	
    	if ( params != null )
    		o.params(Arrays.asList(params));
    	
    	o.postInit();
        o.queryInitialization();
        o.executeQuery();
        o.onQueryComplete();
        o.beforeDestroy();
        return o;
    }

}
