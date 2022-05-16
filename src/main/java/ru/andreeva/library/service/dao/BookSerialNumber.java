package ru.andreeva.library.service.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreeva.library.service.util.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_serial_number")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSerialNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private Status status;

    @Column(name = "status_date", nullable = false)
    private LocalDate statusDate;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}