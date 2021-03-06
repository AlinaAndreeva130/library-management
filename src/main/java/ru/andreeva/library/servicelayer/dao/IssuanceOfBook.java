package ru.andreeva.library.servicelayer.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreeva.library.servicelayer.util.Operation;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuanceOfBook {
    @EmbeddedId
    private IssuanceOfBookId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = 100)
    private Operation operation;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}