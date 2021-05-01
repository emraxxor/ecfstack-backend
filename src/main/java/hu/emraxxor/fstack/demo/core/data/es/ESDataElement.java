package hu.emraxxor.fstack.demo.core.data.es;

public interface ESDataElement<T> {

	String getDocumentId();
	
	T setDocumentId(String documentId);
}
