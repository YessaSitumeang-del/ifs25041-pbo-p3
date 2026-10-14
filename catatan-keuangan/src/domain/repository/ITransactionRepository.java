package domain.repository;

import domain.entity.Transaction;

/** Port repository untuk transaksi keuangan (ID bertipe Integer). */
public interface ITransactionRepository extends IRepository<Transaction, Integer> {
}
