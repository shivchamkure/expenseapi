
package in.cody.expensetrackerapi.controller;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.cody.expensetrackerapi.entity.Expense;
import in.cody.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;


@RestController
public class ExpenseController {

	private final ExpenseService expenseService;

	public ExpenseController(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
	@GetMapping("/expense")
	public List<Expense> getAllExpenses(Pageable page) {
		return expenseService.getAllExpenses(page).toList();
		 
	}
	
	@GetMapping("/expense/{id}")
	public Expense getExpenseById(@PathVariable Long id) {
		return expenseService.getExpense(id);
	}
	
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	@RequestMapping(value="/expense", method= RequestMethod.DELETE)
	public void deleteExpenseById(@RequestParam("id") Long id) {
		 expenseService.deleteExpense(id);
	}
	
	@ResponseStatus(value=HttpStatus.CREATED)
	@PostMapping("/expense")
	public Expense saveExpense(@Valid @RequestBody Expense expense) {
		System.out.println("validation passed");
		return  expenseService.saveExpense(expense);
	}
	
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(value="/expense", method= RequestMethod.PUT)
	public Expense updateExpense(@RequestBody Expense expense, @RequestParam Long id) {
		return expenseService.updateExpenseDetails(id, expense);
	}
	
	@GetMapping("/expense/category")
	public List<Expense> readByCategory(@RequestParam("category") String category, Pageable page){
		return expenseService.readByCategory(category, page);
		
	}
	
	@GetMapping("/expense/name")
	public List<Expense> readByName(@RequestParam("key") String keyword, Pageable page){
		return expenseService.readByName(keyword, page);
		
	}
	
	@GetMapping("/expense/date")
	public List<Expense> readByDate(@RequestParam(required = false) Date startDate, 
			@RequestParam(required = false) Date endDate, Pageable page){
		return expenseService.readByDate(startDate, endDate, page);
		
	}
	
	

}
