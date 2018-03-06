package cn.ctoedu.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created on 2018/2/5.
 *
 *
 * @since 1.0
 */
@Entity
@Table(name = "book_detail")
@Data
public class BookDetail implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    public BookDetail() {
    }

    public BookDetail(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
