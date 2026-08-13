# Database Connection

Connection details for the JDBC example (`org.example.JDBC.JDBCExample`).

## Server
- **Engine:** MySQL 9.x (Oracle install at `/usr/local/mysql`, managed by launchd)
- **Host / Port:** `localhost:3306`
- **Database:** `mydatabase`
- **Table:** `mytable` — columns: `id` (PK, auto-increment), `name`, `email`, `created_at`

## Application user (use this for the app)
- **Username:** `appuser`
- **Host:** `localhost`
- **Privileges:** `SELECT, INSERT, UPDATE, DELETE` on `mydatabase.*` only (least-privilege; no admin/DDL)
- **Password:** stored outside source control — set via the `DB_PASSWORD` environment variable (not recorded in this file).

> The `root` account is reserved for administrative tasks only. Day-to-day app access goes through `appuser`.

## Configuration (environment variables)
The app reads credentials from env vars, with fallback defaults:

| Variable      | Default                                      |
|---------------|----------------------------------------------|
| `DB_URL`      | `jdbc:mysql://localhost:3306/mydatabase`     |
| `DB_USERNAME` | `root`                                        |
| `DB_PASSWORD` | *(empty)*                                     |

For normal use, set:
```
DB_USERNAME=appuser
DB_PASSWORD=<appuser password>
```

### IntelliJ
Run Configuration for `JDBCExample` → **Environment variables**:
```
DB_USERNAME=appuser;DB_PASSWORD=<appuser password>
```

### Command line
```bash
DB_USERNAME=appuser DB_PASSWORD='<appuser password>' \
  mvn -q compile exec:java -Dexec.mainClass=org.example.JDBC.JDBCExample
```

## Admin snippets (run as root)
Restrict `appuser` to read-only:
```sql
REVOKE INSERT, UPDATE, DELETE ON mydatabase.* FROM 'appuser'@'localhost';
```
Rotate the app user's password:
```sql
ALTER USER 'appuser'@'localhost' IDENTIFIED BY '<new password>';
```
