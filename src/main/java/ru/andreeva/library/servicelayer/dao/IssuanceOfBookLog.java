package ru.andreeva.library.servicelayer.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.andreeva.library.servicelayer.util.Operation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "issuance_of_book_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuanceOfBookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_serial_number_id", nullable = false)
    private BookSerialNumber bookSerialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = 100)
    private Operation operation;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}