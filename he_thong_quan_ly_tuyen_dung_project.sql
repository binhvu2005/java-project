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
                            name VARCHAR(100) NOT NULL UNIQUE,
                            status ENUM('active', 'inactive') DEFAULT 'active'
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
                             cvUrl TEXT not null ,
                             progress ENUM('applied', 'interviewing', 'offer', 'rejected', 'withdrawn') DEFAULT 'applied',
                             interviewRequestDate DATETIME,
                             interviewRequestResult ENUM('accepted', 'rejected', 'pending'),
                             interviewLink TEXT,
                             interviewTime DATETIME,
                             interviewResult ENUM('pass', 'fail', 'pending'),
                             interviewResultNote TEXT,
                             destroyAt DATETIME,
                             destroyReason TEXT,
                             createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                             updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (candidateId) REFERENCES candidate(id) ,
                             FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position(id)
);

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
-- quản lí công nghệ
-- lấy số trang môi trang có 5 công nghệ
DELIMITER //
CREATE PROCEDURE sp_get_technology_page (
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(technology.id) / in_limit) AS totalPage
    FROM technology;
END //
DELIMITER ;
-- Lấy danh sách công nghệ tuyển dụng theo trang
DELIMITER //
CREATE PROCEDURE sp_get_technology (
    IN in_page INT,
    IN in_limit INT
)
BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;
    SELECT id, name
    FROM technology
    WHERE status = 'active'
    LIMIT in_limit OFFSET offset;
END //
DELIMITER ;
-- Thêm công nghệ Validate name không trùng nếu trùng voới tên công nghệ đã xóa mền thì đổi status thành active
DELIMITER //
CREATE PROCEDURE sp_add_technology (
    IN in_name VARCHAR(100)
)
BEGIN
    DECLARE technology_count INT;

    SELECT COUNT(*) INTO technology_count
    FROM technology
    WHERE name = in_name;

    IF technology_count = 0 THEN
        INSERT INTO technology (name)
        VALUES (in_name);
    ELSE
        UPDATE technology
        SET status = 'active'
        WHERE name = in_name;
    END IF;
END //
DELIMITER ;
--  Nếu công nghệ tuyển dụng đã có sự liên kết khoá ngoại thì xử lý bằng cách đổi status nếu không thì xóa luôn
DELIMITER //
CREATE PROCEDURE sp_delete_technology (
    IN in_id INT
)
BEGIN
    DECLARE technology_count INT;

    SELECT COUNT(*) INTO technology_count
    FROM candidate_technology
    WHERE technologyId = in_id;

    IF technology_count > 0 THEN
        UPDATE technology
        SET status = 'inactive'
        WHERE id = in_id;
    ELSE
        DELETE FROM technology
        WHERE id = in_id;
    END IF;
END //
DELIMITER ;
-- Sửa công nghệ Validate name không trùng
DELIMITER //
CREATE PROCEDURE sp_update_technology (
    IN in_id INT,
    IN in_name VARCHAR(100)
)
BEGIN
    DECLARE technology_count INT;

    SELECT COUNT(*) INTO technology_count
    FROM technology
    WHERE name = in_name;

    IF technology_count = 0 THEN
        UPDATE technology
        SET name = in_name
        WHERE id = in_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'đã tồn tại công nghệ này';
    END IF;
END //
DELIMITER ;
-- lấy công nghệ theo id
DELIMITER //
CREATE PROCEDURE sp_get_technology_by_id (
    IN in_id INT
)
BEGIN
    SELECT id, name
    FROM technology
    WHERE id = in_id;
END //
DELIMITER ;
-- Đăng nhập ứng viên
DELIMITER //
CREATE PROCEDURE sp_candidate_login (
    IN in_email VARCHAR(100),
    IN in_password VARCHAR(255)
)
BEGIN
    SELECT id
    from candidate
    where candidate.email = in_email
      AND candidate.password = SHA2(in_password,256);
end //
-- đăng kí ứng viên
DELIMITER //
CREATE PROCEDURE sp_candidate_register (
    IN in_name VARCHAR(100),
    IN in_email VARCHAR(100),
    IN in_phone VARCHAR(20),
    IN in_gender ENUM('Male', 'Female', 'Other'),
    IN in_dob DATE,
    IN in_description TEXT,
    IN in_password VARCHAR(255)
)
BEGIN
    INSERT INTO candidate ( name, email, phone, gender, dob, description, password) VALUES
        (in_name,in_email,in_phone,in_gender,in_dob,in_description, SHA2(in_password, 256));

end //
