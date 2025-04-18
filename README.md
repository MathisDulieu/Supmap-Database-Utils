# Database-Utils

## Overview
Database-Utils is a core utility library for the SUPMAP application that provides common database
operations, connection management, and data access tools used across all microservices. This module
ensures consistent database interactions and abstracts away the complexity of database operations.

## Features
- Connection Pool Management: Efficient database connection handling
- Transaction Management: Utilities for handling database transactions
- Query Builders: Fluent APIs for creating type-safe database queries
- Migration Support: Database schema versioning and migration tools
- Common Repository Patterns: Reusable data access patterns
- Caching Strategies: Performance optimization through intelligent caching

## Key Components

### Connection Management
Provides connection pooling and database connectivity with support for different database vendors.

### Transaction Utilities
Offers transaction management capabilities with programmatic and declarative transaction control.

### Query Builders
Type-safe query construction tools that help prevent SQL injection and syntax errors.

### Migration Tools
Database schema versioning and migration utilities for maintaining database schema integrity.

### Repository Patterns
Common implementation of repository patterns for consistent data access across services.

## Usage
Import the database-utils library into your service:

<dependency>
    <groupId>com.novus</groupId>
    <artifactId>database-utils</artifactId>
    <version>${project.version}</version>
</dependency>

Then use the utilities in your service implementations:

import com.novus.database_utils.connection.ConnectionPool;
import com.novus.database_utils.query.QueryBuilder;

@Service
public class AlertRepositoryImpl implements AlertRepository {

    private final ConnectionPool connectionPool;
    private final QueryBuilder queryBuilder;

    @Autowired
    public AlertRepositoryImpl(ConnectionPool connectionPool, QueryBuilder queryBuilder) {
        this.connectionPool = connectionPool;
        this.queryBuilder = queryBuilder;
    }

    public Alert findAlertById(String id) {
        // Use database utilities to implement repository methods
        String query = queryBuilder.select("*").from("alerts").where("id = ?").build();

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            // Execute query and map results
        }
    }
}

## Benefits of Using Database-Utils

1. Consistency: Ensures all services interact with databases in a consistent manner
2. Security: Built-in protection against SQL injection and other database security issues
3. Performance: Optimized connection pooling and query execution
4. Maintainability: Centralized database access logic reduces code duplication
5. Testability: Easier to mock database access for unit testing

## Development Guidelines

When working with database utilities:

1. Always use parameterized queries to prevent SQL injection
2. Properly manage database connections by closing them in finally blocks or try-with-resources
3. Use transactions appropriately for operations that require atomicity
4. Leverage the provided caching mechanisms for frequently accessed data
5. Follow the repository pattern for consistent data access
6. Utilize query builders rather than constructing raw SQL strings

## Configuration

Database connection properties can be configured in your application.properties file:

db.url=jdbc:postgresql://localhost:5432/supmap
db.username=supmap_user
db.password=secure_password
db.connection-pool.max-size=20
db.connection-pool.idle-timeout=300000

## Building

To build the database-utils module:

mvn clean install

This will compile the code, run tests, and install the artifact to your local Maven repository for use in other modules.