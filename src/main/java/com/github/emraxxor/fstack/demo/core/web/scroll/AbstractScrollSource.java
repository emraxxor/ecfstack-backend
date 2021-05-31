package com.github.emraxxor.fstack.demo.core.web.scroll;

import java.util.List;

/**
 * 
 * @author Attila Barna
 *
 * @param <WEB_APP_CONTEXT>
 * @param <DATA_TYPE>
 */
public abstract class AbstractScrollSource<WEB_APP_CONTEXT, DATA_TYPE> implements ScrollSourceInterface<DATA_TYPE> {
	
	protected WEB_APP_CONTEXT context;
	
	protected List<DATA_TYPE> content;
	
	protected String token;
	
	protected int count;
	
	protected int size;
	
	protected  long total;
	
	protected List<?> params;

	
	public AbstractScrollSource(WEB_APP_CONTEXT context, String token) {
			this.context = context;
			this.token = token;
			this.size = 50;
	}
	
	@Override
	public String token() {
		return this.token;
	}
	
	@Override
	public long total() {
		return this.total;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public List<DATA_TYPE> content() {
		return this.content;
	}
	
	@Override
	public void params(List<?> params) {
		this.params = params;
	}

	public <T1,T2> void onProcess(T1 data, T2 element) {
		
	}

}
