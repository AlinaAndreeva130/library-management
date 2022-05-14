package ru.andreeva.library.service.dao;

import lombok.Getter;
import lombok.Setter;
import ru.andreeva.library.service.util.Operation;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "issuance_of_book")
@Getter
@Setter
public class IssuanceOfBook {
    @EmbeddedId
    private IssuanceOfBookId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = 100)
    private Operation operation;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}