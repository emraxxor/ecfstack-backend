package com.github.emraxxor.fstack.demo.data.type;

import com.github.emraxxor.fstack.demo.entities.User;
import org.modelmapper.PropertyMap;

/**
 * 
 * @author attila
 *
 */
public class SimpleUserPropertyMap extends PropertyMap<SimpleUser, User> {

	 @Override
     protected void configure() {
         skip(destination.getUserId());
         skip(destination.getUserPassword());
         skip(destination.getIsActive());
         skip(destination.getLastSeen());
         skip(destination.getCreatedOn());
         skip(destination.getIsActive());
         skip(destination.getUserName());
         skip(destination.getRole());
     }
}
