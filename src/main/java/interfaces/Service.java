package interfaces;

import java.util.List;

public interface Service<Type> {
    Type findById(int id);
    List<Type> findAll();
    void showAll();
    void save(Type object);
    void update(Type object);
    void delete(Type object);
}
