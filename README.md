### [Create database](#create-database)

Connect to your local mysql database: `mysql -uroot`

Execute the following:

```sql
CREATE DATABASE accounts;
grant all on accounts.* to 'root'@'localhost';
```

### [Testing](#testing)

```
curl --user user:secret http://localhost:8080/accounts
```