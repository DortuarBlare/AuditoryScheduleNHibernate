package interfaces;

import java.util.List;

public interface DAO<Type> {
    Type findById(int id);
    List<Type> findAll();
    void save(Type object);
    void update(Type object);
    void delete(Type object);
}
