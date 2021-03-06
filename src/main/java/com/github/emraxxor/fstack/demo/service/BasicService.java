package com.github.emraxxor.fstack.demo.service;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author attila
 *
 * @param <T>
 * @param <R>
 */
public interface BasicService<T,R extends CrudRepository<T, ?>> {

	R repository();
	
}
