package access;

import java.util.List;
import java.util.Map;

public interface ObjectAccess<T> {
    void add(T t);
    void update(T t, int id);
    void delete(int id);
    T retrieve(int id);
    List<T> retrieveAll();
    boolean exists(int id);
    Map<Integer,Object> setMapAdd(T t);
    Map<Integer,Object> setMapUpdate(T t, int id);
    Map<Integer,Object> setMap(int id);


}
