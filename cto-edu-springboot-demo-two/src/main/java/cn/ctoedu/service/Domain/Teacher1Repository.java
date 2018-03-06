package cn.ctoedu.service.Domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
 * @CacheConfig主要用于配置该类中会用到的一些缓存配置，我们查询的数据会存到cacheNames="teacher1s"这个缓存对象中
 */
@CacheConfig(cacheNames="teachers")
public interface Teacher1Repository  extends JpaRepository<Teacher1,Integer>,JpaSpecificationExecutor<Teacher1>{
  
  //配置这个函数的返回值加入到缓存中，查询的时候先从缓存中获取，如果不存在再去数据库中查询。
 //除了@Cacheable，还有@CachePut和@CacheEvict。
  //@CachePut它每次都会调用函数，所以主要用于数据库新增和修改上。
  //@CacheEvict 配置函数上，通常用在删除方法上，用来从缓存中移除相应数据。
  @Cacheable(key="#p0")
  Teacher1 findByName(String name);
  
  /*
   * 在spring boot中通过@EnableCaching注解自动化配置合适的缓存管理器。
   * Generic、JCache、EhCache。。。reids   
   * spring.chache.type强制指定缓存管理器。
   * 可以直接在resources下配置ehcache.xml，spring boot会自己侦测到。但是为了后期的演示，我们将其放到config文件夹中，并且改名为ehcache-config.xml,然后配置
   * 指定这个xml    spring.cache.ehchache.config=classpath:cofnig/ehcache-config.xml
   */
  @CachePut(key="#p0.name")
  Teacher1 save(Teacher1 teacher1);
}
