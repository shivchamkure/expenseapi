package in.cody.expensetrackerapi.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;


@Setter @Getter
@AllArgsConstructor
@Entity
@Table(name="tbl_expenses")
public class Expense {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="expense_name")
	@NotNull(message="Expense name must not be 'null'")
	@Size(min=3, message="Expense name must be least 3 character")
	private String name;
	
	@Column
	private String description;
	
	@Column(name="expense_amount")
	@NotNull(message="Expense amount must not be 'null'")
	@Min(value = 100)
	private BigDecimal amount;
	
	@Column
	private String category;
	
	@Column
	private java.sql.Date date;
	
	@Column(name="created_at", nullable=false, updatable=false)
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private Timestamp updatedAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;



	public Expense() {
	}
	@Override
	public String toString() {
		return "Expense [id=" + id + ", name=" + name + ", description=" + description + ", amount=" + amount
				+ ", category=" + category + ", date=" + date + "]";
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	public BigDecimal getAmount() {
		return amount;
		}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Expense expense = (Expense) o;
		return Objects.equals(id, expense.id) && Objects.equals(name, expense.name) && Objects.equals(description, expense.description) && Objects.equals(amount, expense.amount) && Objects.equals(category, expense.category) && Objects.equals(date, expense.date) && Objects.equals(createdAt, expense.createdAt) && Objects.equals(updatedAt, expense.updatedAt) && Objects.equals(user, expense.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, amount, category, date, createdAt, updatedAt, user);
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public Timestamp getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
