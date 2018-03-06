package cn.ctoedu.jpa.repository;

import cn.ctoedu.jpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 2018/2/5.
 *
 *
 * @since 1.0
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
