DROP USER 'rgms'@'localhost';

CREATE USER 'rgms'@'localhost' IDENTIFIED BY 'seng2050';
GRANT ALL PRIVILEGES ON *.* TO 'rgms'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;