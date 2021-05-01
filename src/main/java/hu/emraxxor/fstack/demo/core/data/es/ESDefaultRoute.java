package hu.emraxxor.fstack.demo.core.data.es;

/**
 * 
 * @author Attila Barna
 *
 */
public interface ESDefaultRoute<T> {

	String routing();
	
	String getRouting();
	
	T setRouting(String r);
}
