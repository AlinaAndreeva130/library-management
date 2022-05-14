package ru.andreeva.library.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.service.dao.IssuanceOfBookLog;

public interface IssuanceOfBookLogRepository extends JpaRepository<IssuanceOfBookLog, Long>, JpaSpecificationExecutor<IssuanceOfBookLog> {
}