package com.github.emraxxor.fstack.demo.core.data.es;

import lombok.Data;

/**
 * 
 * @author Attila Barna
 *
 */
@Data
public class DocumentField {

	private String fieldName;
	
	private String indexName;
	
	private String documentType;
	
	private Object documentValue;

	public DocumentField(String fieldName, String indexName, String documentType, Object documentValue) {
		super();
		this.fieldName = fieldName;
		this.indexName = indexName;
		this.documentType = documentType;
		this.documentValue = documentValue;
	}

	
}
