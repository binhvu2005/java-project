DROP DATABASE recruitment_management;
CREATE DATABASE IF NOT EXISTS recruitment_management;
USE recruitment_management;
CREATE TABLE candidate (
                           id int primary key auto_increment,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           phone VARCHAR(20),
                           gender ENUM('MALE', 'FEMALE', 'OTHER'),
                           dob DATE,
                           experience INT DEFAULT 0,
                           description TEXT
);

-- Bảng account với khóa ngoại trỏ đến candidate
CREATE TABLE account (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         candidateId int,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL unique ,
                         role ENUM('CANDIDATE', 'ADMIN') NOT NULL,
                         status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
                         FOREIGN KEY (candidateId) REFERENCES candidate(id)
);




-- Bảng công nghệ
CREATE TABLE technology (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
);

-- Bảng trung gian giữa ứng viên và công nghệ
CREATE TABLE candidate_technology (
                                      candidateId INT,
                                      technologyId INT,
                                      PRIMARY KEY (candidateId, technologyId),
                                      FOREIGN KEY (candidateId) REFERENCES candidate(id) ON DELETE CASCADE,
                                      FOREIGN KEY (technologyId) REFERENCES technology(id) ON DELETE CASCADE
);

-- Bảng vị trí tuyển dụng
CREATE TABLE recruitment_position (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(100) NOT NULL UNIQUE,
                                      description TEXT,
                                      minSalary DECIMAL(15,2) check ( minSalary > 0),
                                      maxSalary DECIMAL(15,2) check ( maxSalary > 0),
                                      minExperience INT DEFAULT 0 check ( minExperience >= 0),
                                      status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
                                      createdDate DATE DEFAULT (CURRENT_DATE),
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
                             candidateId INT NOT NULL,
                             recruitmentPositionId INT NOT NULL,
                             cvUrl TEXT not null ,
                             progress ENUM('APPLIED', 'INTERVIEWING', 'OFFER', 'REJECTED', 'WITHDRAWN') DEFAULT 'APPLIED' not null ,
                             interviewRequestDate DATETIME,
                             interviewRequestResult ENUM('ACCEPTED', 'REJECTED', 'PENDING') DEFAULT 'PENDING' not null ,
                             interviewLink TEXT,
                             interviewTime DATETIME,
                             interviewResult ENUM('PASS', 'FAIL', 'PENDING') DEFAULT 'PENDING',
                             interviewResultNote TEXT,
                             destroyAt DATETIME,
                             destroyReason TEXT,
                             createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                             updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (candidateId) REFERENCES candidate(id) ,
                             FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position(id)
);
-- khoi tạo admin nếu chưa có
DELIMITER //

CREATE PROCEDURE sp_admin_init (
)
BEGIN
    DECLARE admin_count INT;
    SELECT COUNT(*) INTO admin_count FROM account WHERE role = 'ADMIN';
    IF admin_count = 0 THEN
        INSERT INTO account( email, password, role) Values
            ('admin@gmail.com',SHA2('admin', 256), 'ADMIN');
    END IF;
END //
DELIMITER ;
DELIMITER //

CREATE PROCEDURE sp_get_role (
    IN in_id int
)
BEGIN
    DECLARE user_role ENUM('CANDIDATE', 'ADMIN');
    -- Kiểm tra email tồn tại
    SELECT role INTO user_role
    FROM account
    WHERE id = in_id;
    -- Nếu không tìm thấy
    IF user_role IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'id không tồn tại';
    ELSE
        -- Trả về vai trò
        SELECT user_role AS role;
    END IF;
END //

DELIMITER ;
-- đang nhập
DELIMITER //

CREATE PROCEDURE sp_login (
    IN in_email VARCHAR(100),
    IN in_password VARCHAR(255)
)
BEGIN
    SELECT   *	FROM account a
                           LEFT JOIN candidate c ON a.candidateId = c.id
    WHERE a.email = in_email
      AND a.password = SHA2(in_password, 256);
END //

DELIMITER ;

-- procedure đăng ký
DELIMITER //

CREATE PROCEDURE sp_candidate_register (
    IN in_name VARCHAR(100),
    IN in_email VARCHAR(100),
    IN in_phone VARCHAR(20),
    IN in_gender ENUM('Male', 'Female', 'Other'),
    IN in_dob DATE,
    IN in_experience INT,
    IN in_description TEXT,
    IN in_password VARCHAR(255)
)
BEGIN
    DECLARE candidate_id INT;
    DECLARE email_count INT;
    -- Kiểm tra email trùng
    SELECT COUNT(*) INTO email_count
    FROM (
             SELECT email FROM candidate
             UNION
             SELECT email FROM account
         ) AS combined_emails
    WHERE email = in_email;

    IF email_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Email already registered';
    ELSE
        START TRANSACTION;

        -- Thêm candidate và lấy ID vừa tạo
        INSERT INTO candidate (name, email, phone, gender, dob,experience, description)
        VALUES (in_name, in_email, in_phone, in_gender, in_dob,in_experience, in_description);

        SET candidate_id = LAST_INSERT_ID();

        -- Tạo tài khoản
        INSERT INTO account (candidateId, email, password, role)
        VALUES (candidate_id, in_email, SHA2(in_password, 256), 'CANDIDATE');

        COMMIT;
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
    DECLARE tech_status ENUM('active', 'inactive');

    -- Lấy trạng thái hiện tại nếu tồn tại
    SELECT status INTO tech_status
    FROM technology
    WHERE name = in_name
    LIMIT 1;

    -- Nếu không có => thêm mới
    IF tech_status IS NULL THEN
        INSERT INTO technology (name, status)
        VALUES (in_name, 'active');

        -- Nếu đang inactive => bật lại
    ELSEIF tech_status = 'inactive' THEN
        UPDATE technology
        SET status = 'active'
        WHERE name = in_name;

        -- Nếu đang active => báo lỗi
    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Công nghệ này đã tồn tại và đang ACTIVE.';
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

-- ứng viên
-- đổi mật khẩu

DELIMITER //

CREATE PROCEDURE sp_change_candidate_password (
    IN in_account_id INT,
    IN in_old_password VARCHAR(255),
    IN in_new_password VARCHAR(255)
)
BEGIN
    DECLARE existing_count INT;

    -- Kiểm tra mật khẩu cũ có đúng không
    SELECT COUNT(*) INTO existing_count
    FROM account
    WHERE id = in_account_id
      AND password = SHA2(in_old_password, 256)
      AND role = 'CANDIDATE';

    IF existing_count = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Mật khẩu cũ không đúng hoặc tài khoản không tồn tại';
    ELSE
        -- Cập nhật mật khẩu mới
        UPDATE account
        SET password = SHA2(in_new_password, 256)
        WHERE id = in_account_id
          AND role = 'CANDIDATE';
    END IF;
END //

DELIMITER ;
-- Cập nhật tên ứng viên
DELIMITER //
CREATE PROCEDURE sp_update_candidate_name (
    IN in_candidate_id INT,
    IN in_name VARCHAR(100)
)
BEGIN
    UPDATE candidate
    SET name = in_name
    WHERE id = in_candidate_id;
END //
DELIMITER ;

-- Cập nhật số điện thoại
DELIMITER //
CREATE PROCEDURE sp_update_candidate_phone (
    IN in_candidate_id INT,
    IN in_phone VARCHAR(20)
)
BEGIN
    UPDATE candidate
    SET phone = in_phone
    WHERE id = in_candidate_id;
END //
DELIMITER ;

-- Cập nhật giới tính
DELIMITER //
CREATE PROCEDURE sp_update_candidate_gender (
    IN in_candidate_id INT,
    IN in_gender ENUM('Male', 'Female', 'Other')
)
BEGIN
    UPDATE candidate
    SET gender = in_gender
    WHERE id = in_candidate_id;
END //
DELIMITER ;

-- Cập nhật ngày sinh
DELIMITER //
CREATE PROCEDURE sp_update_candidate_dob (
    IN in_candidate_id INT,
    IN in_dob DATE
)
BEGIN
    UPDATE candidate
    SET dob = in_dob
    WHERE id = in_candidate_id;
END //
DELIMITER ;
-- Cập nhật kinh nghiệm
DELIMITER //
CREATE PROCEDURE sp_update_candidate_experience (
    IN in_candidate_id INT,
    IN in_experience INT
)
BEGIN
    UPDATE candidate
    SET experience = in_experience
    WHERE id = in_candidate_id;
END //
DELIMITER ;
-- Cập nhật mô tả
DELIMITER //
CREATE PROCEDURE sp_update_candidate_description (
    IN in_candidate_id INT,
    IN in_description TEXT
)
BEGIN
    UPDATE candidate
    SET description = in_description
    WHERE id = in_candidate_id;
END //
DELIMITER ;
-- thêm công nghệ cho ứng viên
DELIMITER //
CREATE PROCEDURE sp_add_candidate_technology (
    IN in_candidate_id INT,
    IN in_technology_id INT
)
BEGIN
    DECLARE tech_status VARCHAR(10);
    DECLARE tech_count INT;

    -- Kiểm tra xem công nghệ có tồn tại và đang active không
    SELECT status INTO tech_status
    FROM technology
    WHERE id = in_technology_id;

    IF tech_status IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Công nghệ không tồn tại';
    ELSEIF tech_status != 'active' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Công nghệ đang không hoạt động';
    ELSE
        -- Kiểm tra xem công nghệ này đã được thêm cho ứng viên chưa
        SELECT COUNT(*) INTO tech_count
        FROM candidate_technology
        WHERE candidateId = in_candidate_id AND technologyId = in_technology_id;

        IF tech_count = 0 THEN
            INSERT INTO candidate_technology (candidateId, technologyId)
            VALUES (in_candidate_id, in_technology_id);
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Công nghệ đã tồn tại cho ứng viên này';
        END IF;
    END IF;
END //
DELIMITER ;
-- Lấy danh sách công nghệ của ứng viên
DELIMITER //
CREATE PROCEDURE sp_get_candidate_technologies (
    IN in_candidate_id INT
)
BEGIN
    SELECT t.id, t.name
    FROM candidate_technology ct
             JOIN technology t ON ct.technologyId = t.id
    WHERE ct.candidateId = in_candidate_id
      AND t.status = 'active';
END //
DELIMITER ;
-- Xóa công nghệ của ứng viên
DELIMITER //
CREATE PROCEDURE sp_remove_candidate_technology (
    IN in_candidate_id INT,
    IN in_technology_id INT
)
BEGIN
    DELETE FROM candidate_technology
    WHERE candidateId = in_candidate_id
      AND technologyId = in_technology_id;
END //
DELIMITER ;
-- lấy thông tin ứng viên theo id

DELIMITER //
CREATE PROCEDURE sp_get_candidate_by_id (
    IN in_account_id INT
)
BEGIN
    SELECT *
    FROM candidate
    WHERE candidate.id = in_account_id;
END //
DELIMITER ;
-- lấy id ứng viên theo id tài khoản
drop procedure if exists sp_get_candidate_id_by_account_id;
DELIMITER //

CREATE PROCEDURE sp_get_candidate_id_by_account_id (
    IN in_account_id INT
)
BEGIN
    SELECT candidateId
    FROM account
    WHERE id = in_account_id;
END //

DELIMITER ;
-- quản lý uứng viên
-- lấy số trang danh sách ứng viên
DELIMITER //
CREATE PROCEDURE sp_get_candidate_page (
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(id) / in_limit) AS totalPage
    FROM candidate;
END //
DELIMITER ;
-- lấy danh sách ứng viên theo trang

DELIMITER //
CREATE PROCEDURE sp_get_candidate (
    IN in_page INT,
    IN in_limit INT
)

BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    SELECT c.id,
           c.name,
           c.email,
           c.phone,
           c.gender,
           c.dob,
           c.experience,
           c.description,
           a.status
    FROM candidate c
             JOIN account a ON c.id = a.candidateId
    WHERE a.role = 'CANDIDATE'
      AND a.status = 'active'
    LIMIT in_limit OFFSET offset;
END //

DELIMITER ;
-- khóa mở tài khoản unứng viên theo id của ungwsg viên
DELIMITER //
CREATE PROCEDURE sp_unlock_candidate (
    IN in_candidate_id INT,
    IN in_status ENUM('active', 'inactive')
)
BEGIN
    UPDATE account
    SET status = in_status
    WHERE candidateId = in_candidate_id;
END //
DELIMITER ;
-- Tìm kiếm ứng viên theo tên có phân trang
DELIMITER //
CREATE PROCEDURE sp_search_candidate (
    IN in_name VARCHAR(100),
    IN in_page INT,
    IN in_limit INT
)

BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    -- Return paginated results
    SELECT c.id,
           c.name,
           c.email,
           c.phone,
           c.gender,
           c.dob,
           c.experience,
           c.description
    FROM candidate c
    WHERE c.name LIKE CONCAT('%', in_name, '%')
    LIMIT in_limit OFFSET offset;
END //

DELIMITER ;

-- Procedure to get total pages for search results
DELIMITER //

CREATE PROCEDURE sp_search_candidate_page(
    IN in_name VARCHAR(100),
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(*) / in_limit) AS totalPage
    FROM candidate c
    WHERE c.name LIKE CONCAT('%', in_name, '%');
END //

DELIMITER ;
--  Lọc ứng viên theo kinh nghiệm có phân trang
DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_experience(
    IN in_min_experience INT,
    IN in_max_experience INT,
    IN in_page INT,
    IN in_limit INT
)
BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    SELECT c.id,
           c.name,
           c.email,
           c.phone,
           c.gender,
           c.dob,
           c.experience,
           c.description
    FROM candidate c
    WHERE c.experience BETWEEN in_min_experience AND in_max_experience
    LIMIT in_limit OFFSET offset;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_experience_page(
    IN in_min_experience INT,
    IN in_max_experience INT,
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(*) / in_limit) AS totalPage
    FROM candidate c
    WHERE c.experience BETWEEN in_min_experience AND in_max_experience;
END //

DELIMITER ;
-- Lọc ứng viên theo giới tính có phân trang

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_gender(
    IN in_gender ENUM ('Male', 'Female', 'Other'),
    IN in_page INT,
    IN in_limit INT
)
BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    SELECT c.id,
           c.name,
           c.email,
           c.phone,
           c.gender,
           c.dob,
           c.experience,
           c.description
    FROM candidate c
    WHERE c.gender = in_gender
    LIMIT in_limit OFFSET offset;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_gender_page(
    IN in_gender ENUM ('Male', 'Female', 'Other'),
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(*) / in_limit) AS totalPage
    FROM candidate c
    WHERE c.gender = in_gender;
END //

DELIMITER ;
-- Lọc ứng viên theo công nghệ có phân trang

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_technology(
    IN in_technology_id INT,
    IN in_page INT,
    IN in_limit INT
)
BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    SELECT DISTINCT c.id,
                    c.name,
                    c.email,
                    c.phone,
                    c.gender,
                    c.dob,
                    c.experience,
                    c.description
    FROM candidate c
             JOIN candidate_technology ct ON c.id = ct.candidateId
             JOIN technology t ON ct.technologyId = t.id
    WHERE t.id = in_technology_id
      AND t.status = 'active'
    LIMIT in_limit OFFSET offset;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_technology_page(
    IN in_technology_id INT,
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(DISTINCT c.id) / in_limit) AS totalPage
    FROM candidate c
             JOIN candidate_technology ct ON c.id = ct.candidateId
             JOIN technology t ON ct.technologyId = t.id
    WHERE t.id = in_technology_id
      AND t.status = 'active';
END //

DELIMITER ;
-- Lọc ứng viên theo tuổi có phân trang

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_age(
    IN in_min_age INT,
    IN in_max_age INT,
    IN in_page INT,
    IN in_limit INT
)
BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    SELECT c.id,
           c.name,
           c.email,
           c.phone,
           c.gender,
           c.dob,
           c.experience,
           c.description
    FROM candidate c
    WHERE TIMESTAMPDIFF(YEAR, c.dob, CURDATE()) BETWEEN in_min_age AND in_max_age
    LIMIT in_limit OFFSET offset;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_filter_candidate_by_age_page(
    IN in_min_age INT,
    IN in_max_age INT,
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(*) / in_limit) AS totalPage
    FROM candidate c
    WHERE TIMESTAMPDIFF(YEAR, c.dob, CURDATE()) BETWEEN in_min_age AND in_max_age;
END //

DELIMITER ;
-- reset mật khẩu ứng viên
DELIMITER //
CREATE PROCEDURE sp_reset_candidate_password (
    IN in_candidateId INT,
    IN in_new_password VARCHAR(255)
)
BEGIN
    UPDATE account
    SET password = SHA2(in_new_password, 256)
    WHERE candidateId = in_candidateId;
END //
DELIMITER ;
-- quản lý vị trí tuyển dụng
-- lấy số trang vị trí tuyển dụng
DELIMITER //
CREATE PROCEDURE sp_get_recruitment_position_page (
    IN in_limit INT
)
BEGIN
    SELECT CEIL(COUNT(id) / in_limit) AS totalPage
    FROM recruitment_position
    WHERE status = 'ACTIVE';
END //
DELIMITER ;
-- lấy danh sách vị trí tuyển dụng theo trang
DELIMITER //
CREATE PROCEDURE sp_get_recruitment_position (
    IN in_page INT,
    IN in_limit INT
)
BEGIN
    DECLARE offset INT;
    SET offset = (in_page - 1) * in_limit;

    SELECT id, name, description, minSalary, maxSalary, minExperience, createdDate, expiredDate
    FROM recruitment_position
    WHERE status = 'ACTIVE'
    LIMIT in_limit OFFSET offset;
END //
DELIMITER ;

-- thêm vị trí tuyển dụng nếu tên đã tồn tại thì không thêm được hoặc có thể đổi status thành active nếu tên đã bị xóa
DELIMITER //
CREATE PROCEDURE sp_add_recruitment_position (
    IN in_name VARCHAR(100),
    IN in_description TEXT,
    IN in_minSalary DECIMAL(15,2),
    IN in_maxSalary DECIMAL(15,2),
    IN in_minExperience INT,
    IN in_expiredDate DATE
)
BEGIN
    DECLARE position_count INT;

    SELECT COUNT(*) INTO position_count
    FROM recruitment_position
    WHERE name = in_name;

    IF position_count = 0 THEN
        INSERT INTO recruitment_position (name, description, minSalary, maxSalary, minExperience, expiredDate)
        VALUES (in_name, in_description, in_minSalary, in_maxSalary, in_minExperience, in_expiredDate);
    ELSE
        UPDATE recruitment_position
        SET status = 'ACTIVE'
        WHERE name = in_name;
    END IF;
END //
DELIMITER ;
-- xóa vị trí tuyển dụng nếu đã có sự liên kết khoá ngoại thì xử lý bằng cách đổi status nếu không thì xóa luôn
DELIMITER //
CREATE PROCEDURE sp_delete_recruitment_position (
    IN in_id INT
)
BEGIN
    DECLARE position_count INT;

    SELECT COUNT(*) INTO position_count
    FROM recruitment_position_technology
    WHERE positionId = in_id;

    IF position_count > 0 THEN
        UPDATE recruitment_position
        SET status = 'INACTIVE'
        WHERE id = in_id;
    ELSE
        DELETE FROM recruitment_position
        WHERE id = in_id;
    END IF;
END //
DELIMITER ;
-- sửa tên không trùng vị trí tuyển dụng theo id
DELIMITER //
CREATE PROCEDURE sp_update_recruitment_position_name (
    IN in_id INT,
    IN in_name VARCHAR(100)
)
BEGIN
    DECLARE position_count INT;

    SELECT COUNT(*) INTO position_count
    FROM recruitment_position
    WHERE name = in_name;

    IF position_count = 0 THEN
        UPDATE recruitment_position
        SET name = in_name
        WHERE id = in_id;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Tên vị trí tuyển dụng đã tồn tại';
    END IF;
END //
DELIMITER ;
-- sửa mô tả vị trí tuyển dụng theo id
DELIMITER //
CREATE PROCEDURE sp_update_recruitment_position_description (
    IN in_id INT,
    IN in_description TEXT
)
BEGIN
    UPDATE recruitment_position
    SET description = in_description
    WHERE id = in_id;
END //
DELIMITER ;
-- sửa mức lương vị trí tuyển dụng theo id
DELIMITER //
CREATE PROCEDURE sp_update_recruitment_position_salary (
    IN in_id INT,
    IN in_minSalary DECIMAL(15,2),
    IN in_maxSalary DECIMAL(15,2)
)
BEGIN
    UPDATE recruitment_position
    SET minSalary = in_minSalary,
        maxSalary = in_maxSalary
    WHERE id = in_id;
END //
DELIMITER ;
-- sửa kinh nghiệm vị trí tuyển dụng theo id
DELIMITER //
CREATE PROCEDURE sp_update_recruitment_position_experience (
    IN in_id INT,
    IN in_minExperience INT
)
BEGIN
    UPDATE recruitment_position
    SET minExperience = in_minExperience
    WHERE id = in_id;
END //
DELIMITER ;
-- sửa ngày hết hạn vị trí tuyển dụng theo id
DELIMITER //
CREATE PROCEDURE sp_update_recruitment_position_expired_date (
    IN in_id INT,
    IN in_expiredDate DATE
)
BEGIN
    UPDATE recruitment_position
    SET expiredDate = in_expiredDate
    WHERE id = in_id;
END //
DELIMITER ;

-- lấy vị trí tuyển dụng theo id
DELIMITER //
CREATE PROCEDURE sp_get_recruitment_position_by_id (
    IN in_id INT
)
BEGIN
    SELECT id, name, description, minSalary, maxSalary, minExperience, createdDate, expiredDate
    FROM recruitment_position
    WHERE id = in_id
      AND status = 'ACTIVE';
END //
DELIMITER ;
-- lấy danh sách công nghệ theo id vị trí tuyển dụng
DELIMITER //
CREATE PROCEDURE sp_get_technology_by_recruitment_position_id (
    IN in_position_id INT
)
BEGIN
    SELECT t.id, t.name
    FROM recruitment_position_technology rpt
             JOIN technology t ON rpt.technologyId = t.id
    WHERE rpt.positionId = in_position_id;
END //
DELIMITER ;
-- thêm công nghệ cho vị trí tuyển dụng
DELIMITER //
CREATE PROCEDURE sp_add_technology_to_recruitment_position (
    IN in_position_id INT,
    IN in_technology_id INT
)
BEGIN
    DECLARE tech_status VARCHAR(10);
    DECLARE tech_count INT;

    -- Kiểm tra xem công nghệ có tồn tại và đang active không
    SELECT status INTO tech_status
    FROM technology
    WHERE id = in_technology_id;

    IF tech_status IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Công nghệ không tồn tại';
    ELSEIF tech_status != 'active' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Công nghệ đang không hoạt động';
    ELSE
        -- Kiểm tra xem công nghệ này đã được thêm cho vị trí tuyển dụng chưa
        SELECT COUNT(*) INTO tech_count
        FROM recruitment_position_technology
        WHERE positionId = in_position_id AND technologyId = in_technology_id;

        IF tech_count = 0 THEN
            INSERT INTO recruitment_position_technology (positionId, technologyId)
            VALUES (in_position_id, in_technology_id);
        ELSE
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Công nghệ đã tồn tại cho vị trí tuyển dụng này';
        END IF;
    END IF;
END //
DELIMITER ;
-- xóa công nghệ của vị trí tuyển dụng
DELIMITER //
CREATE PROCEDURE sp_remove_technology_from_recruitment_position (
    IN in_position_id INT,
    IN in_technology_id INT
)
BEGIN
    DELETE FROM recruitment_position_technology
    WHERE positionId = in_position_id
      AND technologyId = in_technology_id;
END //
DELIMITER ;
-- quản lý đơn ứng tuyển của ứng viên
-- tạo đơn ứng tuyển
DELIMITER //
CREATE PROCEDURE sp_create_application (
    IN in_candidateId INT,
    IN in_recruitmentPositionId INT,
    IN in_cvUrl TEXT
)
BEGIN
    DECLARE application_count INT;

    -- Kiểm tra xem ứng viên đã nộp đơn cho vị trí này chưa
    SELECT COUNT(*) INTO application_count
    FROM application
    WHERE candidateId = in_candidateId
      AND recruitmentPositionId = in_recruitmentPositionId;

    IF application_count > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ứng viên đã nộp đơn cho vị trí này';
    ELSE
        INSERT INTO application (candidateId, recruitmentPositionId, cvUrl)
        VALUES (in_candidateId, in_recruitmentPositionId, in_cvUrl);
    END IF;
END //
-- xem đơn đã ứng tuyển của ứng viên
DELIMITER //
CREATE PROCEDURE sp_get_application_by_candidate_id (
    IN in_candidateId INT
)
BEGIN
    SELECT a.id,
           a.cvUrl,
           a.progress,
           rp.name AS recruitmentPositionName
    FROM application a
             JOIN recruitment_position rp ON a.recruitmentPositionId = rp.id
    WHERE a.candidateId = in_candidateId;
END //
DELIMITER ;
-- xem đơn ứng tuyển chi tiêt theo id
DELIMITER //
CREATE PROCEDURE sp_get_application_by_id (
    IN in_applicationId INT
)
BEGIN
    SELECT a.id,
           a.cvUrl,
           a.progress,
           rp.name AS recruitmentPositionName,
           rp.description AS recruitmentPositionDescription,
           rp.minSalary,
           rp.maxSalary,
           rp.minExperience,
           a.interviewRequestDate,
           a.interviewRequestResult,
           a.interviewLink,
           a.interviewTime,
           a.interviewResult,
           a.interviewResultNote,
           a.destroyAt,
           a.destroyReason,
           a.createdAt,
           a.updatedAt

    FROM application a
             JOIN recruitment_position rp ON a.recruitmentPositionId = rp.id
    WHERE a.id = in_applicationId;
END //
DELIMITER ;
-- tham gia phỏng vấn
DELIMITER //

CREATE PROCEDURE sp_confirm_interview (
    IN in_applicationId INT,
    IN in_confirm BOOLEAN
)
BEGIN
    UPDATE application
    SET interviewRequestResult =
            CASE
                WHEN in_confirm THEN 'ACCEPTED'
                ELSE 'REJECTED'
                END,
        updatedAt = NOW()
    WHERE id = in_applicationId;
END //

DELIMITER ;
