package cn.ctoedu.jpa.repository;

import cn.ctoedu.jpa.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 2018/3/2.
 *
 * @author ctoedu
 * @since 1.0
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
