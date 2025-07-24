Postgres setup:
schema name: productdb
table name: products

CREATE TABLE products (
    product_id VARCHAR(255) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (product_id)
);

-- Add constraints
ALTER TABLE products ADD CONSTRAINT chk_price_positive CHECK (price > 0);
ALTER TABLE products ADD CONSTRAINT chk_category_valid 
    CHECK (category IN ('Electronics', 'Books', 'Clothing', 'Home'));

inserting demo data for the testing:

INSERT INTO products (product_id, product_name, price, category, available, created_at, updated_at) VALUES
('P001', 'iPhone 15 Pro', 999.99, 'Electronics', true, NOW(), NOW()),
('P002', 'Clean Code Book', 45.00, 'Books', true, NOW(), NOW()),
('P003', 'Nike Air Max', 120.00, 'Clothing', true, NOW(), NOW());
