# JDBC / JPA / Hibernate

[![Java](https://img.shields.io/badge/Java-21-orange.svg?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36.svg?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8+-4479A1.svg?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A learning project exploring database access in Java — starting with plain
**JDBC** and (planned) growing into **JPA** and **Hibernate** examples.

## Current contents

| Example | Class | What it shows |
|---------|-------|---------------|
| Plain JDBC | `org.example.JDBC.JDBCExample` | Connecting to MySQL, running a query, and printing any result set generically |

The JDBC example demonstrates a few good practices:

- **try-with-resources** so `Connection`, `PreparedStatement`, and `ResultSet` always close.
- **`PreparedStatement`** instead of `Statement` (injection-safe, parameter-ready).
- **Schema-agnostic output** via `ResultSetMetaData` — column names and count are
  discovered at runtime, so it works for any table or query.
- **NULL-safe** printing.
- **No hardcoded credentials** — connection details come from environment variables.

## Requirements

- **JDK 21+**
- **Maven 3.9+**
- A running **MySQL 8+ / 9+** server

## Setup

### 1. Create the database

```sql
CREATE DATABASE IF NOT EXISTS mydatabase;
USE mydatabase;
CREATE TABLE IF NOT EXISTS mytable (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO mytable (name, email) VALUES
  ('Aryan Kanani', 'aryankanani57@gmail.com'),
  ('Robert', 'robert@example.com'),
  ('Alice', NULL);
```

### 2. Configure credentials

The app reads connection settings from environment variables (with fallbacks):

| Variable      | Default                                   |
|---------------|-------------------------------------------|
| `DB_URL`      | `jdbc:mysql://localhost:3306/mydatabase`  |
| `DB_USERNAME` | `root`                                     |
| `DB_PASSWORD` | *(empty)*                                  |

See [`DATABASE.md`](DATABASE.md) for the full connection reference, including the
recommended least-privilege `appuser` account.

## Run

### Command line

```bash
DB_USERNAME=appuser DB_PASSWORD='your_password' \
  mvn -q compile exec:java -Dexec.mainClass=org.example.JDBC.JDBCExample
```

### IntelliJ IDEA

1. Open the project (Maven auto-imports dependencies).
2. Edit the Run Configuration for `JDBCExample` → **Environment variables**:
   `DB_USERNAME=appuser;DB_PASSWORD=your_password`
3. Run.

### Expected output

```
--- Row 1 ---
id: 1
name: Aryan Kanani
email: aryankanani57@gmail.com
created_at: 2026-08-12 22:35:42.0
--- Row 2 ---
id: 2
name: Robert
email: robert@example.com
created_at: 2026-08-12 22:35:42.0
--- Row 3 ---
id: 3
name: Alice
email: NULL
created_at: 2026-08-12 22:35:42.0
```

## Project layout

```
├── pom.xml                 # Maven build + mysql-connector-j dependency
├── DATABASE.md             # Connection details & DB admin reference
├── src/main/java/org/example/
│   ├── App.java
│   └── JDBC/JDBCExample.java
└── src/test/java/org/example/AppTest.java
```

## Roadmap

- [x] Plain JDBC example
- [ ] JPA (Jakarta Persistence) example
- [ ] Hibernate ORM example
