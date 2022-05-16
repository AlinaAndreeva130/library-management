package ru.andreeva.library.servicelayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.andreeva.library.servicelayer.dao.IssuanceOfBookLog;

public interface IssuanceOfBookLogRepository extends JpaRepository<IssuanceOfBookLog, Long>, JpaSpecificationExecutor<IssuanceOfBookLog> {
}