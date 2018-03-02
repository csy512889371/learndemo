package com.ctoedu.common.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.ctoedu.common.model.search.Searchable;
import com.ctoedu.common.repository.callback.SearchCallback;

/**
 *
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
public class BaseRepositoryImpl<M, ID extends Serializable>  extends SimpleJpaRepository<M, ID> implements BaseRepository<M, ID>{
    @SuppressWarnings("unused")
	private final EntityManager entityManager;
    private final JpaEntityInformation<M, ID> entityInformation;
    private final Class<M> entityClass;
    private final String entityName;
    @SuppressWarnings("unused")
	private final String idName;
    /**
     * 查询所有的QL
     */
    private String findAllQL;
    /**
     * 统计QL
     */
    private String countAllQL;
    private SearchCallback searchCallback = SearchCallback.DEFAULT;
    private final RepositoryHelper repositoryHelper;
    public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
    public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";

    public BaseRepositoryImpl(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.entityName = this.entityInformation.getEntityName();
        this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
        this.entityManager = entityManager;
        repositoryHelper = new RepositoryHelper(entityClass);
        findAllQL = String.format(FIND_QUERY_STRING, entityName);
        countAllQL = String.format(COUNT_QUERY_STRING, entityName);
    }

    @Override
    public List<M> findAll() {
        return repositoryHelper.findAll(findAllQL);
    }

    @Override
    public List<M> findAll(final Sort sort) {
        return repositoryHelper.findAll(findAllQL, sort);
    }

    @Override
    public Page<M> findAll(final Pageable pageable) {
        return new PageImpl<M>(
                repositoryHelper.<M>findAll(findAllQL, pageable),
                pageable,
                repositoryHelper.count(countAllQL)
        );
    }

    @Override
    public long count() {
        return repositoryHelper.count(countAllQL);
    }

    @Override
    public void delete(ID id) {
        M m = findOne(id);
        delete(m);
    }

    @Override
    public void delete(M entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof LogicDeleteable) {
            ((LogicDeleteable) entity).markDeleted();
            save(entity);
        } else {
            super.delete(entity);
        }
    }

    @Override
    public Page<M> findAll(final Searchable searchable) {
        List<M> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
        long total = searchable.hasPageable() ? count(searchable) : list.size();
        return new PageImpl<M>(
                list,
                searchable.getPage(),
                total
        );
    }

    @Override
    public long count(final Searchable searchable) {
        return repositoryHelper.count(countAllQL, searchable, searchCallback);
    }

	@Override
	public void delete(ID[] ids) {
		for(ID id:ids){
			this.delete(id);
		}
	}

}
