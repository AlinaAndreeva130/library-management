package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.service.dao.User;

public interface UsersRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
