package ru.andreeva.library.service.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "issuance_of_book_log")
@Getter
@Setter
public class IssuanceOfBookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "reader_id", nullable = false)
    private Long readerId;

    @Column(name = "book_serial_number_id", nullable = false)
    private Integer bookSerialNumberId;

    @Column(name = "operation", nullable = false, length = 100)
    private String operation;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}