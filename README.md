# JDBC / JPA / Hibernate

[![Java](https://img.shields.io/badge/Java-21-orange.svg?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36.svg?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8+-4479A1.svg?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A learning project exploring database access in Java — starting with plain
**JDBC** and growing into **JPA** and **Hibernate** examples.

## Current contents

### JDBC

| Example | Class | What it shows |
|---------|-------|---------------|
| Plain JDBC | `org.example.JDBC.JDBCExample` | Connecting to MySQL, running a query, and printing any result set generically |
| PreparedStatement | `org.example.JDBC.PreparedStatement.PreparedStatementExample` | Parameterized query using a `?` bind variable |
| Batch insert | `org.example.JDBC.Batch_operation.BatchOperation` | Inserting multiple rows in one round-trip with `addBatch()` / `executeBatch()` |
| Transaction | `org.example.JDBC.Transaction.Transaction` | Running two statements as one unit — commit on success, rollback on failure |

### JPA / Hibernate

| Example | Class | What it shows |
|---------|-------|---------------|
| Basics | `org.example.JPA.JPAExample` | Mapping a `User` entity with Hibernate 6, persisting and querying it via JPQL |
| CRUD | `org.example.JPA.CRUD_operations.UserService` | Full create / read / update / delete cycle with `EntityManager` |
| Entity annotations | `org.example.JPA.Important_Annotation_For_Entity.User` | `@Column`, `@Temporal`, `@Enumerated`, `@Lob`, `@Transient` on one entity |
| Pagination (JPQL) | `org.example.JPA.Pagination.Using_JPQL.Pagination` | Paging with `setFirstResult` (offset) + `setMaxResults` (limit) |
| Pagination (Criteria) | `org.example.JPA.Pagination.Using_criteria_API.CriteriaExample` | Same paging built type-safely with the Criteria API |
| Lazy fetching | `org.example.JPA.LAZY_AND_EAGR_FETCHING.LAZY.FetchingDemo` | `FetchType.LAZY` → `LazyInitializationException` after the `EntityManager` closes |
| Eager fetching | `org.example.JPA.LAZY_AND_EAGR_FETCHING.EAGER.FetchingDemo` | `FetchType.EAGER` → the collection is loaded upfront and readable after close |
| Relationship mappings | `org.example.JPA.JPA_mapping.*` | `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany` entity pairs |

### Hibernate (native API)

These use Hibernate's own `SessionFactory` / `Session` API bootstrapped from
[`hibernate.cfg.xml`](src/main/resources/hibernate.cfg.xml) (separate `hibernateExample`
schema, auto-created on first run). Credentials are still injected from
`DB_USERNAME` / `DB_PASSWORD`, never hardcoded.

| Example | Class | What it shows |
|---------|-------|---------------|
| Session CRUD | `org.example.Hibernate.HibernateExample.App` | Create / read / update / delete with `SessionFactory`, `persist`/`get`/`remove` |
| get() vs load() | `org.example.Hibernate.Get_and_Load.HibernateExample` | `get()` (immediate SELECT) vs `load()`/`getReference()` (lazy proxy) |
| Inheritance — single table | `org.example.Hibernate.Inheritance_Mapping.single_table_inheritance.SingleTableDemo` | `SINGLE_TABLE` — one table + discriminator column |
| Inheritance — joined | `org.example.Hibernate.Inheritance_Mapping.table_per_class_hierarchy.JoinedDemo` | `JOINED` — base table + a joined table per subclass |
| Inheritance — table per class | `org.example.Hibernate.Inheritance_Mapping.table_per_subClass.TablePerClassDemo` | `TABLE_PER_CLASS` — a standalone table per concrete subclass |

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
  ('Aryan Kanani', 'aryan@example.com'),
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
email: aryan@example.com
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
├── pom.xml                 # Maven build + mysql-connector-j & hibernate-core deps
├── DATABASE.md             # Connection details & DB admin reference
├── src/main/
│   ├── java/org/example/
│   │   ├── App.java
│   │   ├── JDBC/
│   │   │   ├── JDBCExample.java
│   │   │   ├── PreparedStatement/PreparedStatementExample.java
│   │   │   ├── Batch_operation/BatchOperation.java
│   │   │   └── Transaction/Transaction.java
│   │   ├── JPA/
│   │   │   ├── User.java                      # @Entity mapped to the users table
│   │   │   ├── JPAExample.java                # persist + JPQL query
│   │   │   ├── CRUD_operations/               # create / read / update / delete
│   │   │   ├── Important_Annotation_For_Entity/  # column & mapping annotations
│   │   │   ├── Pagination/                    # JPQL + Criteria API paging
│   │   │   ├── LAZY_AND_EAGR_FETCHING/        # LAZY vs EAGER fetch demos
│   │   │   └── JPA_mapping/                   # one-to-one/many, many-to-one/many
│   │   └── Hibernate/                         # native SessionFactory API
│   │       ├── HibernateExample/              # Session CRUD
│   │       ├── Get_and_Load/                  # get() vs load()
│   │       └── Inheritance_Mapping/           # single-table / joined / table-per-class
│   └── resources/
│       ├── META-INF/persistence.xml          # JPA (EntityManager) config
│       └── hibernate.cfg.xml                  # Hibernate (SessionFactory) config
└── src/test/java/org/example/AppTest.java
```

> **Note:** the JPA and Hibernate examples use `hibernate.hbm2ddl.auto=update`,
> which issues DDL (CREATE/ALTER TABLE). Run them as a user with schema privileges
> (e.g. `root`), not the CRUD-only `appuser`. The Hibernate examples also create
> their own `hibernateExample` schema automatically on first run.

Each example has a `main` method — run any of them with the `-Dexec.mainClass`
flag shown in the [Run](#run) section (swap in the class from the table above).

## Roadmap

- [x] Plain JDBC example
- [x] PreparedStatement example
- [x] Batch insert example
- [x] Transaction example
- [x] JPA / Hibernate basics
- [x] JPA CRUD operations
- [x] Entity annotations
- [x] Pagination (JPQL + Criteria API)
- [x] Lazy vs eager fetching
- [x] Relationship mappings
- [x] Native Hibernate Session CRUD
- [x] Hibernate get() vs load()
- [x] Hibernate inheritance mapping (single-table / joined / table-per-class)
- [ ] Named queries
- [ ] Spring Data JPA example
