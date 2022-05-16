package ru.andreeva.library.service.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "reader_id", nullable = false)
    private Long readerId;

    @Column(name = "book_serial_number_id", nullable = false)
    private Long bookSerialNumberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IssuanceOfBookId entity = (IssuanceOfBookId) o;
        return Objects.equals(this.bookSerialNumberId, entity.bookSerialNumberId) &&
                Objects.equals(this.readerId, entity.readerId) &&
                Objects.equals(this.bookId, entity.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookSerialNumberId, readerId, bookId);
    }

}