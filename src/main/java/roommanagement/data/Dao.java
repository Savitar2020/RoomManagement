package roommanagement.data;

import roommanagement.model.Event;
import roommanagement.util.Result;

import java.util.List;

/**
 * interface for data access objects
 * <p>
 * M426: RoomManagementDb
 *
 * @author Julia Peric
 * @version 1.0
 * @since 2021-11-25
 */
public interface Dao<T, K> {

    /**
     * gets all datasets in a table
     * @return list of model-objects
     */
    default List<T> getAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * gets a single datasets in a table
     * @param i  primary key
     * @return model-object
     */
    default T getEntity(int i) {
        throw new UnsupportedOperationException();
    }


    /**
     * saves an object to the database entity
     * @param t model-object
     * @return Result-code
     */
    default Result save (T t) {
        throw new UnsupportedOperationException();
    }

    /**
     * deletes an entity in the database
     * @param i primary key
     * @return Result-code
     */
    default Result delete (int i) {
        throw new UnsupportedOperationException();
    }

    /**
     * counts the datasets in a table
     * @return Result-code
     */
    default Integer count() {
        return 0;
    }

}
