package hu.emraxxor.fstack.demo.data.type;

/**
 * 
 * @author attila
 *
 * @param <T>
 */
public interface FormElementConverter<T> {

	public T convert(String e);
	
	public String convert(Object e);


}
