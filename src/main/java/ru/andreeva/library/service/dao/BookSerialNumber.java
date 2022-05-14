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
@Table(name = "book_serial_number")
@Getter
@Setter
public class BookSerialNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "status_date", nullable = false)
    private LocalDate statusDate;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}