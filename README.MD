#Task 5

Description
Please, complete the following task

1 point.

Make an application that contains business logic for making exchange operations between different currencies.

Create models for dealing with currencies, user accounts and exchange rates. One account can have multiple currency values. Use BigDecimal for performing of exchange calculations.
Data with user accounts should be stored as files (one file per account).
Separate application functionality to DAO, service and utilities.
Create module which will provide high-level operations (manage accounts, currencies, exchange rates).
Create sample accounts and currencies. Define sample exchange rates.
Provide concurrent data access to user accounts. Simulate simultaneous currency exchanges for single account by multiple threads and ensure that all the operations are thread-safe.
Use ExecutorService to manage threads.
Make custom exceptions to let user to know the reason of error. Do not handle runtime exceptions.
Validate inputs such an account existence, sufficiency of currency amount, etc.
Log information about what is happening on different application levels and about conversion results. Use Logger for that.