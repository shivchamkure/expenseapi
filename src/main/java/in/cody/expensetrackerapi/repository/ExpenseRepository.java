package in.cody.expensetrackerapi.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cody.expensetrackerapi.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	
	//SELECT * FROM tbl_Expenses WHERE category=?
	Page<Expense> findByCategory(String category, Pageable page);
	
	//SELECT * FROM tbl_expenses WHERE Name LIKE '%keyword%'
	Page<Expense> findByNameContaining(String keyword, Pageable page);
	
	//SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate' AND 'endDate'
	Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

	Page<Expense> findByUserId(Long userId, Pageable page);
	
	
}
