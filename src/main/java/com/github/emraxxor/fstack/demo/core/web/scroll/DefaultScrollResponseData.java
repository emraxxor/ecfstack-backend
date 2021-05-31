package com.github.emraxxor.fstack.demo.core.web.scroll;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author Attila Barna
 *
 * @param <DATA_TYPE>
 */
@Data
public class DefaultScrollResponseData<DATA_TYPE> {

	protected List<DATA_TYPE> data;
	
	protected String token;

	protected long total;
	
	protected int count;
	
	public DefaultScrollResponseData() {
		this.data = new ArrayList<>();
	}
	
	
}
