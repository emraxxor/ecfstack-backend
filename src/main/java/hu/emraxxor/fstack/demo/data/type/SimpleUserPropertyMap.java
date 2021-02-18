package hu.emraxxor.fstack.demo.data.type;

import org.modelmapper.PropertyMap;

import hu.emraxxor.fstack.demo.entities.User;

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
