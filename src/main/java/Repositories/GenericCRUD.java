package Repositories;

import java.sql.SQLException;
import java.util.List;

import Models.User;

public interface GenericCRUD<T, ID> {
    <S extends T> S save(S entity) throws SQLException;
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T entity) throws SQLException;
    void deleteById(ID id) throws SQLException;
}
