CREATE DATABASE IF NOT EXISTS student_feedback;

USE student_feedback;

CREATE TABLE students (
    roll_no INT PRIMARY KEY,
    password VARCHAR(50)
);

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no INT,
    faculty_name VARCHAR(100),
    course VARCHAR(100),
    rating INT,
    comments TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);