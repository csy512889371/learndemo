package cn.ctoedu.jpa.repository;

import cn.ctoedu.jpa.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 2018/2/5.
 *
 * @author ctoedu
 * @since 1.0
 */
public interface PublisherRepository extends JpaRepository<Publisher,Integer> {
}
