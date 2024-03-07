package in.cody.expensetrackerapi.service;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.cody.expensetrackerapi.entity.Expense;


public interface ExpenseService {
	
	Page<Expense> getAllExpenses(Pageable page);
	
	Expense getExpense(Long id);
	
	void deleteExpense(Long id);
	
	Expense saveExpense(Expense expense);
	
	Expense updateExpenseDetails(Long id, Expense expense);
	
	List<Expense> readByCategory(String category, Pageable page);
	
	List<Expense> readByName(String keyword, Pageable page);
	
	List<Expense> readByDate(Date startDate, Date endDate, Pageable page);
	
}
