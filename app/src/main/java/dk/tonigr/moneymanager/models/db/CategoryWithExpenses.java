package dk.tonigr.moneymanager.models.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithExpenses {
    @Embedded public Category category;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "category_id"
    )
    public List<Expense> expenses;
}
