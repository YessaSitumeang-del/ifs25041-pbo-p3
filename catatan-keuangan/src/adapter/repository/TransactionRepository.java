package adapter.repository;

import domain.entity.Transaction;
import domain.repository.ITransactionRepository;

/** Implementasi in-memory dari ITransactionRepository. */
public class TransactionRepository extends InMemoryRepository<Transaction, Integer>
        implements ITransactionRepository {
    private int lastId = 0;

    @Override
    protected Integer getId(Transaction entity) {
        return entity.getId();
    }

    @Override
    protected Transaction assignId(Transaction entity, Integer id) {
        return entity.withId(id);
    }

    @Override
    protected Integer generateId() {
        return ++lastId;
    }
}
