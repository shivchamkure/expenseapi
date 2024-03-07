
package in.cody.expensetrackerapi.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.cody.expensetrackerapi.entity.Expense;
import in.cody.expensetrackerapi.exception.ResourceNotFoundException;
import in.cody.expensetrackerapi.repository.ExpenseRepository;


@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepo;

	@Autowired
	private UserService userService;

	@Override
	public Page<Expense> getAllExpenses(Pageable page) {
		return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	}

	@Override
	public Expense getExpense(Long id){
		Optional<Expense> expense = expenseRepo.findById(id);
			if (expense.isPresent()){
				return expense.get();
				}
			throw new ResourceNotFoundException("***** Expense not found by its id "+ id +" ****");
	}

	@Override
	public void  deleteExpense(Long id) {
		Expense existingExpense = getExpense(id);
		expenseRepo.delete(existingExpense);	
	
	}

	@Override
	public Expense saveExpense(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepo.save(expense);
	}

	@Override
	public Expense updateExpenseDetails(Long id, Expense expense){
		Expense existingExpense = getExpense(id);
		existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
		existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
		existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
		existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
		existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
		return expenseRepo.save(existingExpense);
	}

	@Override
	public List<Expense> readByCategory(String category, Pageable page) {
		return expenseRepo.findByCategory(category, page).toList();
	}

	@Override
	public List<Expense> readByName(String keyword, Pageable page) {
		return expenseRepo.findByNameContaining(keyword, page).toList();
	}

	@Override
	public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
		if(startDate == null) {
			startDate = new Date(0);
		}
		if (endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}
		return expenseRepo.findByDateBetween(startDate, endDate, page).toList();	
	}
	
	
	
	
	
	


}






























