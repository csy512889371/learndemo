package com.ctoedu.service.inter.impl;

import com.ctoedu.service.Domain.Primary.Teacher1;
import com.ctoedu.service.Domain.Primary.Teacher1Repository;
import com.ctoedu.service.inter.TeacherServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceRepoImpl implements TeacherServiceRepo {
    
	@Autowired
	Teacher1Repository teacherRepository;
	
	@Override
	@Transactional(value="transactionManagerPrimary")
	public void createTeacher(Teacher1 teacher) {
		// TODO Auto-generated method stub
		
		try {
			teacherRepository.save(teacher);	
			//***********************************
			  //如下是为了操作测试事务处理
//			Teacher1 teacher1=new Teacher1();
//			teacher1.setName("事务测试");
//			teacher1.setAge(10);
//			teacherRepository.save(teacher1);
//			Teacher1 teacher2=new Teacher1();
//			teacher2.setName("事务测试事务测试事务测试事务测试事务测试事务测试事务测试");
//			teacher2.setAge(10);
//			teacherRepository.save(teacher2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}
		//***********************************
	}

	@Override
	public List<Teacher1> getTeacher() {
		// TODO Auto-generated method stub
		return teacherRepository.findAll();
	}

	@Override
	public List<Teacher1> getPageContent(int page, int size) {
		// TODO Auto-generated method stub
		 Pageable pageable=new PageRequest(page,size,Sort.Direction.ASC,"id");
		return teacherRepository.findAll(pageable).getContent();
	}

	@Override
	public List<Teacher1> getNamePageContent(int page, int size, String name) {
		 Pageable pageable=new PageRequest(page,size,Sort.Direction.ASC,"id");
		 Page<Teacher1> treePage=teacherRepository.findAll(new Specification<Teacher1>(){

			@Override
			public Predicate toPredicate(Root<Teacher1> arg0, CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
				List<Predicate> list=new ArrayList<Predicate>();
				if(name!=null&&name!=""){
					list.add(arg2.equal(arg0.get("name").as(String.class), name));
				}
				Predicate[] p=new Predicate[list.size()];
				return arg2.and(list.toArray(p));
			}
			 
		 },pageable);
		 return treePage.getContent();
	 }

	@Override
	public  List<Teacher1> findByName(String name) {
		// TODO Auto-generated method stub
		return teacherRepository.findByName(name);
	}
}
