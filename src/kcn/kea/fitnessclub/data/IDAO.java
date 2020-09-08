package kcn.kea.fitnessclub.data;
// kcn
import java.util.HashMap;
import java.util.List;

public interface IDAO<T,U> {

	/**
	 * Creates a new entity in data-source based on the supplied thing.
	 * Returns id of thing if successful; some agreed signal, see implementation.
	 */
	U create(T thing);

	 /**
	 * Returns a T-type object, read from data-source
	  * - queried with supplied id (type U).
	 * Returns null if there is no 'thing' by id in db.
	 */
	T read(U id);

	/**
	 * Method queries data-source and returns complete map of type T objects,
	 * keyed by entity id (of type U)
	*/
	HashMap<U,T> readAll();

	/**
	 * Method executes update to DB based on supplied thing type-T:
	 * Returns true if update was written to DB, false if nothing was written.
	 */
	boolean update(T thing, U id);

	/**
	 * Method removes object from data-source, where data-source entity-id
	 * equals the supplied id, and returns a thing of type U
	 */
	boolean delete(U id);
}