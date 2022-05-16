package ru.andreeva.library.servicelayer.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuanceOfBookId implements Serializable {
    private static final long serialVersionUID = 6509031923347605885L;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_serial_number_id", nullable = false)
    private BookSerialNumber bookSerialNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IssuanceOfBookId entity = (IssuanceOfBookId) o;
        return Objects.equals(this.bookSerialNumber, entity.bookSerialNumber) &&
                Objects.equals(this.reader, entity.reader) &&
                Objects.equals(this.book, entity.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookSerialNumber, reader, book);
    }

}