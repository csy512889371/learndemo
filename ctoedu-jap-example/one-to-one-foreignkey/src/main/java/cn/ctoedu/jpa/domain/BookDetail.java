package cn.ctoedu.jpa.domain;

import lombok.Data;

/**
 * Created on 2018/2/5.
 *
 * @author ctoedu
 * @since 1.0
 */
@Entity
@Table(name = "book_detail")
@Data
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @OneToOne(mappedBy = "bookDetail")
    private Book book;

    public BookDetail() {
    }

    public BookDetail(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
