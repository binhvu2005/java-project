DROP DATABASE recruitment_management;
CREATE DATABASE IF NOT EXISTS recruitment_management;
USE recruitment_management;
-- Bảng ứng viên
CREATE TABLE candidate (
                           id varchar(5) primary key check ( id REGEXP '^C[0-9]{4}$'),
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           phone VARCHAR(20),
                           gender ENUM('Male', 'Female', 'Other'),
                           dob DATE,
                           description TEXT,
                           password VARCHAR(255) NOT NULL,
                           status ENUM('active', 'inactive') DEFAULT 'active',
                           createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Bảng quản trị viên (admin)
CREATE TABLE admin (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       adminName VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Bảng công nghệ
CREATE TABLE technology (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

-- Bảng trung gian giữa ứng viên và công nghệ
CREATE TABLE candidate_technology (
                                      candidateId VARCHAR(5),
                                      technologyId INT,
                                      PRIMARY KEY (candidateId, technologyId),
                                      FOREIGN KEY (candidateId) REFERENCES candidate(id) ON DELETE CASCADE,
                                      FOREIGN KEY (technologyId) REFERENCES technology(id) ON DELETE CASCADE
);

-- Bảng vị trí tuyển dụng
CREATE TABLE recruitment_position (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL,
                                      description TEXT,
                                      minSalary DECIMAL(15,2),
                                      maxSalary DECIMAL(15,2),
                                      minExperience INT,
                                      createdDate DATE,
                                      expiredDate DATE
);

-- Bảng trung gian giữa vị trí tuyển dụng và công nghệ yêu cầu
CREATE TABLE recruitment_position_technology (
                                                 positionId INT,
                                                 technologyId INT,
                                                 PRIMARY KEY (positionId, technologyId),
                                                 FOREIGN KEY (positionId) REFERENCES recruitment_position(id) ON DELETE CASCADE,
                                                 FOREIGN KEY (technologyId) REFERENCES technology(id) ON DELETE CASCADE
);

-- Bảng đơn ứng tuyển
CREATE TABLE application (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             candidateId VARCHAR(5) NOT NULL,
                             recruitmentPositionId INT NOT NULL,
                             cvUrl TEXT,
                             progress ENUM('applied', 'interviewing', 'offer', 'rejected', 'withdrawn') DEFAULT 'applied',
                             interviewRequestDate DATE,
                             interviewRequestResult ENUM('accepted', 'rejected', 'pending'),
                             interviewLink TEXT,
                             interviewTime DATETIME,
                             interviewResult ENUM('pass', 'fail', 'pending'),
                             interviewResultNote TEXT,
                             destroyAt DATETIME,
                             destroyReason TEXT,
                             createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                             updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (candidateId) REFERENCES candidate(id) ON DELETE CASCADE,
                             FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position(id) ON DELETE CASCADE
);

-- xóa trạng thái đăng nhập (đăng xuất)
DELIMITER //
CREATE PROCEDURE sp_logout (
    IN in_token VARCHAR(255)
)
BEGIN
    DELETE FROM login WHERE token = in_token;
END //
DELIMITER ;
-- đăng nhập admin
DELIMITER //

CREATE PROCEDURE sp_admin_login (
    IN in_adminName VARCHAR(100),
    IN in_password VARCHAR(255)
)
BEGIN
    SELECT id, adminName
    FROM admin
    WHERE adminName = in_adminName
      AND password = SHA2(in_password, 256);
END //

DELIMITER ;
-- khoi tạo admin nếu chưa có
DELIMITER //

CREATE PROCEDURE sp_admin_init (
)
BEGIN
    DECLARE admin_count INT;

    SELECT COUNT(*) INTO admin_count FROM admin;

    IF admin_count = 0 THEN
        INSERT INTO admin(adminName, password)
        VALUES ('admin', SHA2('admin', 256));
    END IF;
END //

DELIMITER ;
