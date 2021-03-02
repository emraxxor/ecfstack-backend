package hu.emraxxor.fstack.demo.data.type;

/**
 * 
 * @author attila
 *
 */
public class AlbumTypeConverter implements FormElementConverter<AlbumType> {

	@Override
	public AlbumType convert(String e) {
		return AlbumType.valueOf(e.toString());
	}

	@Override
	public String convert(Object e) {
		if ( e instanceof AlbumType ) {
			return ((AlbumType) e).name();
		}
		return e.toString();
	}
	
}
