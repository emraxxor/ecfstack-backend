package com.github.emraxxor.fstack.demo.core.data.es;

public interface ESDataElement<T> {

	String getDocumentId();
	
	T setDocumentId(String documentId);
}
