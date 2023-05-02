
DROP DATABASE gabojagoPlan;
CREATE DATABASE gabojagoPlan CHARACTER SET utf8;
use gabojagoPlan;

DROP USER IF EXISTS 'gabojagoDba'@'localhost';
DROP USER IF EXISTS 'gabojagoServerDev'@'localhost';

CREATE USER 'gabojagoDba'@'localhost' IDENTIFIED BY 'mysql123';
GRANT ALL PRIVILEGES ON gabojagoPlan.* TO 'gabojagoDba'@'localhost';

CREATE USER 'gabojagoServerDev'@'localhost' IDENTIFIED BY 'mysql123';
GRANT SELECT, INSERT, UPDATE, DELETE ON gabojagoPlan.* TO 'gabojagoServerDev'@'localhost';

#유저 테이블
#MBTI NULL OR NOT NULL??필수 선택??
CREATE TABLE `users` (
                         `u_id`	varchar(255) UNIQUE PRIMARY KEY COMMENT '사용자 아이디',
                         `pw`	varchar(255) NOT NULL COMMENT'비밀번호',
                         `name`	varchar(255) NOT NULL COMMENT'이름',
                         `nk_name`	varchar(255) UNIQUE NOT NULL COMMENT'닉네임',
                         `email`	varchar(255) UNIQUE NOT NULL COMMENT'이메일',
                         `birth`	date NOT NULL COMMENT'생년월일',
                         `phone`	varchar(20) UNIQUE NOT NULL COMMENT'핸드폰',
                         `address`	varchar(255) COMMENT '주소',
                         `detail_address`	varchar(255) COMMENT '상세주소',
                         `pr_content`	text  COMMENT '자기소개 ',
                         `permission`	enum('USER','PARTNER','ADMIN') NOT NULL DEFAULT 'USER' COMMENT '권한',
                         `mbti`	enum('ISTJ','ISTP','ISFJ','ISFP','INTJ','INTP','INFJ','INFP','ESTJ','ESTP','ESFJ','ESFP','ENTJ','ENTP','ENFJ','ENFP')NOT NULL COMMENT 'MBTI',
                         `img_path`	varchar(255) COMMENT '프로필 이미지',
                         `post_time`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
                         `store_name`	varchar(255) COMMENT '상호명',
                         `business_id`	varchar(255) COMMENT '사업자 번호'
);
ALTER TABLE users ADD COLUMN status          ENUM('SIGNUP','EMAIL_CHECK','BLOCK','LEAVE','REPORT') NOT NULL DEFAULT 'SIGNUP' COMMENT '계정상태';
ALTER TABLE users ADD COLUMN     email_check_code VARCHAR(8) COMMENT '이메일 확인 코드';

#해시태그 테이블
CREATE TABLE `hashtags` (
                            `tag_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '태그 아이디',
                            `tag_name`	varchar(255) UNICODE NOT NULL COMMENT '태그이름'
);

CREATE TABLE hashtags_new
(
    tag VARCHAR(255) PRIMARY KEY COMMENT '태그 내용'
);


#공지사항 테이블
CREATE TABLE `notices` (
                           `n_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '공지사항 아이디',
                           `u_id`	varchar(255)	NOT NULL COMMENT '작성자 아이디',
                           `title`	varchar(255) NOT NULL COMMENT '제목',
                           `content`	TEXT COMMENT '내용',
                           `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                           `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                           `view_count`	INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                           `img_path`	varchar(255) COMMENT '프로필 이미지',
                           FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#공지사항 조회수 테이블
#제약조건 추가 조회수1번만 가능하게
CREATE TABLE `notice_view_counts` (
                                      `nvc_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '조회수 아이디',
                                      `n_id`	int unsigned NOT NULL COMMENT '게시글 아이디',
                                      `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                                      `view_count`	INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                                      FOREIGN KEY (n_id) REFERENCES notices (n_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                      CONSTRAINT notice_viewcnt UNIQUE (u_id, n_id)
);

#마일리지 테이블
CREATE TABLE `mileages` (
                            `m_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '마일리지 아이디',
                            `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                            `mileage`	int	default 0 COMMENT '마일리지',
                            `content`	varchar(255)	COMMENT '적립내용',
                            `post_time`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '적립 일자',
                            FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#맞춤 추천 테이블
CREATE TABLE `trips` (
                         `t_id`	int unsigned  AUTO_INCREMENT PRIMARY KEY COMMENT'맞춤추천 아이디',
                         `u_id`	varchar(255)  COMMENT '작성자 아이디',
                         `title`	varchar(255) NOT NULL COMMENT '제목',
                         `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                         `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                         `view_count`	INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                         `area`	ENUM('서울', '인천', '대전', '광주', '대구', '울산', '부산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주')NOT NULL COMMENT '지역',
                         `address`	varchar(255) COMMENT '주소',
                         `phone`	varchar(20) UNIQUE NOT NULL COMMENT'핸드폰',
                         `url_address`	varchar(255) COMMENT '추천 홈페이지 URL',
                         `content`	TEXT  COMMENT '내용',
                         `istj`	boolean	COMMENT 'MBTI',
                         `istp`	boolean	COMMENT 'MBTI',
                         `isfj`	boolean	COMMENT 'MBTI',
                         `isfp`	boolean COMMENT 'MBTI',
                         `intj`	boolean	COMMENT 'MBTI',
                         `intp`	boolean	COMMENT 'MBTI',
                         `infj`	boolean	COMMENT 'MBTI',
                         `infp`	boolean	COMMENT 'MBTI',
                         `estj`	boolean	COMMENT 'MBTI',
                         `estp`	boolean	COMMENT 'MBTI',
                         `esfj`	boolean	COMMENT 'MBTI',
                         `esfp`	boolean	COMMENT 'MBTI',
                         `entj`	boolean	COMMENT 'MBTI',
                         `entp`	boolean	COMMENT 'MBTI',
                         `enfj`	boolean	COMMENT 'MBTI',
                         `enfp`	boolean	COMMENT 'MBTI',
                         `category`	enum('힐링','체험','반려동물','레저','박물관')	NOT NULL COMMENT '카테고리',
                         FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE SET NULL ON UPDATE CASCADE
);

#가보자고(리뷰 페이지)
CREATE TABLE `trip_reviews` (
                                `tr_id` 	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고리뷰 아이디',
                                `t_id`	int unsigned NOT NULL COMMENT '맞춤추천 아이디',
                                `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                `content`	TEXT  COMMENT '내용 ',
                                `visit`	boolean default true COMMENT '방문여부',
                                `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                                `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                                `grade`	int unsigned	COMMENT '평점',
                                FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#가보자고(해시태그 리뷰 테이블)
CREATE TABLE `trip_review_hashtags` (
                                        `trh_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '해시태그 아이디',
                                        `tag_id`	int unsigned NOT NULL COMMENT '태그 아이디',
                                        `tr_id`	int unsigned NOT NULL COMMENT '리뷰 아이디',
                                        FOREIGN KEY (tag_id) REFERENCES hashtags (tag_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (tr_id) REFERENCES trip_reviews (tr_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#가보자고(이미지보드 테이블)
CREATE TABLE `trip_imgs` (
                             `ti_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '이미지 아이디',
                             `t_id`	int unsigned NOT NULL COMMENT '맞춤추천 아이디',
                             `img_path`	varchar(255) NOT NULL COMMENT'이미지 경로',
                             `img_main`	boolean	COMMENT '메인이미지',
                             FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE


);
#가보자고(북마크 테이블)
#제약조건 추가 1개만북마크 가능하게
CREATE TABLE `trip_bookmarks` (
                                  `tb_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고북마크 아이디',
                                  `t_id`	int unsigned NOT NULL COMMENT '맞춤추천 아이디',
                                  `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                  FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  CONSTRAINT t_bookmark UNIQUE (u_id, t_id)
);
#가보자고(리뷰에 덧글 페이지)
CREATE TABLE `trip_review_comments` (
                                        `trc_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고리뷰댓글 아이디',
                                        `tr_id` int unsigned NOT NULL COMMENT '가보자고리뷰 아이디',
                                        `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                        `content` TEXT  COMMENT '내용 ',
                                        `status`	enum('PUBLIC','PRIVATE') DEFAULT 'PUBLIC' COMMENT '상태',
                                        `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                                        `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                                        `parent_trc_id`	INT UNSIGNED COMMENT '부모 댓글 아이디(대댓글)',
                                        FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (tr_id) REFERENCES trip_reviews (tr_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                        FOREIGN KEY (parent_trc_id) REFERENCES trip_review_comments (trc_id) ON DELETE CASCADE ON UPDATE CASCADE
);
ALTER TABLE trip_review_comments ADD COLUMN img_path VARCHAR(255) COMMENT '이미지 경로';

#가보자고 (좋아요 페이지)
#제약조건 추가 1개만 좋아요 가능하게
CREATE TABLE `trip_likes` (
                              `tl_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고좋아요 아이디',
                              `t_id`	int unsigned NOT NULL COMMENT '맞추추천 아이디',
                              `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                              FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT t_likes UNIQUE (u_id, t_id)
);
#가보자고( 리뷰_좋아요 테이블)
#제약조건 추가 1개만 좋아요 가능하게
CREATE TABLE `trip_review_likes` (
                                     `trl_id`	 int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고리뷰좋아요 아이디',
                                     `tr_id`	int unsigned NOT NULL COMMENT '가보자고리뷰 아이디',
                                     `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                     FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (tr_id) REFERENCES trip_reviews (tr_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT tr_likes UNIQUE (u_id, tr_id)
);

#가보자고 해시태그
CREATE TABLE trip_hashtags
(
    th_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '보드 해시태그 pk',
    t_id  INT UNSIGNED COMMENT '게시글 아이디',
    tag   VARCHAR(255) NOT NULL COMMENT '태그 내용',
    UNIQUE (t_id, tag) COMMENT '게시글에 똑같은 태그가 등록되지 않도록',
    FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag) REFERENCES hashtags_new (tag) ON DELETE CASCADE ON UPDATE CASCADE
);

#가보자고( 리뷰 신고 테이블)
CREATE TABLE `trip_review_reports` (
                                       `trr_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고신고글 아이디',
                                       `tr_id`	int unsigned NOT NULL COMMENT '가보자고리뷰 아이디',
                                       `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                                       `content`	text NOT NULL COMMENT '신고내용',
                                       `post_time`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '신고 날짜',
                                       FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                       FOREIGN KEY (tr_id) REFERENCES trip_reviews (tr_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#가보자고 조회수 테이블
#제약조건 추가 조회수1번만 가능하게
CREATE TABLE `trip_viewcounts` (
                                   `tv_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고조회수 아이디',
                                   `t_id`	int unsigned NOT NULL COMMENT '맞춤추천 아이디',
                                   `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                                   `view_count`	INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                                   FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT trip_viewcnt UNIQUE (u_id, t_id)

);



#같이가자(플래너)
#`info` `from` `to` 어떤 형식???
CREATE TABLE `plans` (
                         `p_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이가자플랜 아이디',
                         `u_id`	varchar(255)  COMMENT '작성자 아이디',
                         `title`	varchar(255) NOT NULL COMMENT '제목',
                         `info`	varchar(255)  COMMENT '설명',
                         `plan_from`    date COMMENT '일정시작',
                         `plan_to`  date COMMENT '일정 끝',
                         `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                         `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                         `img_path`	varchar(255) COMMENT '대표 이미지',
                         `plan_status`	enum('PUBLIC','PRIVATE') DEFAULT 'PUBLIC' COMMENT '상태',
                         `review` boolean COMMENT '리뷰작성 여부',
                         FOREIGN KEY (u_id) REFERENCES users (u_id) ON UPDATE CASCADE ON DELETE SET NULL
);

#여행지티켓 테이블
#img_main 칼럼 변경
CREATE TABLE `sells` (
                         `s_id`		int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판패글 아이디',
                         `u_id`	varchar(255) NOT NULL COMMENT '작성자 아이디',
                         `area`	ENUM('서울', '인천', '대전', '광주', '대구', '울산', '부산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주')	NOT NULL,
                         `title`	varchar(255) NOT NULL COMMENT '제목',
                         `content`	TEXT  COMMENT '내용',
                         `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                         `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                         `view_count`	INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                         `category`	enum('워터','테마','키즈','레저','박물관')	NOT NULL COMMENT '카테고리',
                         `qnt`	int unsigned COMMENT '수량',
                         `img_main`	varchar(255)  NOT NULL	COMMENT '대표이미지',
                         FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#상품판매 (상품옵션 테이블)
CREATE TABLE `sell_options` (
                                `o_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판매옵션 아이디',
                                `s_id` int unsigned NOT NULL COMMENT '판매글 아이디',
                                `name` VARCHAR(255) NOT NULL COMMENT '옵션 이름',
                                `price` varchar(255) NOT NULL COMMENT '옵션 가격',
                                `stock` int unsigned COMMENT '재고수량',
                                FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#같이가자(그림판)
#con_info (어떤형식 text? 간단한 글??)
CREATE TABLE `plan_contents` (
                                 `con_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이가자컨텐츠 아이디',
                                 `p_id`	int unsigned NOT NULL COMMENT '같이가자플랜 아이디',
                                 `t_id`	int unsigned  COMMENT '맞춤추천 아이디',
                                 `s_id` int unsigned  COMMENT '판매글 아이디',
                                 `day_n` tinyint(4) unsigned COMMENT '몇일째의 스케쥴인지',
                                 `title` varchar(255) NOT NULL COMMENT '개별스케줄 제목',
                                 `info`	varchar(255)  COMMENT '경유지, 메모 정보',
                                 `time`	varchar(255)  COMMENT '시작 - 끝',
                                 `img_path`	varchar(255) COMMENT'이미지 경로',
                                 FOREIGN KEY (p_id) REFERENCES plans (p_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#같이가자(체크리스트)
#content = 항목?? 내용?? title??
CREATE TABLE `plan_check_lists` (
                                    `cl_id`	 int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이가자체크리스트 아이디',
                                    `p_id`	int unsigned NOT NULL COMMENT '같이가자플랜 아이디',
                                    `content`	varchar(255) COMMENT '항목',
                                    `check_status`	enum('CHECKED','UNCHECKED')	COMMENT '체크여부',
                                    FOREIGN KEY (p_id) REFERENCES plans (p_id) ON DELETE CASCADE ON UPDATE CASCADE

);
#가보자고(리뷰_덧글 좋아요페이지)
#제약조건 추가 1개만 좋아요 가능하게
#유저아이디 2개여서 뺏어요 확인바람
CREATE TABLE `trip_review_comment_likes` (
                                             `trcl_id`	 int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고리뷰덧글좋아요 아이디',
                                             `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                             `trc_id`	int unsigned NOT NULL COMMENT '가보자고리뷰댓글 아이디',
                                             FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                             FOREIGN KEY (trc_id) REFERENCES trip_review_comments (trc_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT trr_likes UNIQUE (u_id, trc_id)
);

#가보자고(리뷰_이미지보드 테이블)
CREATE TABLE `trip_review_imgs` (
                                    `tri_id`	 int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고이미지 아이디',
                                    `tr_id`	int unsigned NOT NULL COMMENT '가보자고리뷰 아이디',
                                    `img_path`	varchar(255) COMMENT'이미지 경로',
                                    FOREIGN KEY (tr_id) REFERENCES trip_reviews (tr_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#같이가자(멤버목록)
#멤버유저아이디 fk조건 안가져도 되는건지??
CREATE TABLE `plan_members` (
                                `ml_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이가자멤버목록 아이디',
                                `p_id`	int unsigned NOT NULL COMMENT '같이가자플랜 아이디',
                                `mu_id`	varchar(255) COMMENT '멤버유저 아이디',
                                FOREIGN KEY (p_id) REFERENCES plans (p_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (mu_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#같이가자(그림판경로데이터 테이블)
CREATE TABLE `plan_content_paths` (
                                      `path_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이가자그림판경로 아이디',
                                      `con_id`	int unsigned  COMMENT '컨텐츠 아이디',
                                      `can_path`	MEDIUMTEXT	COMMENT '캔버스 데이터',
                                      FOREIGN KEY (con_id) REFERENCES plan_contents (con_id) ON DELETE SET NULL ON UPDATE CASCADE

);



#같이놀자( 커뮤니티)
#STATUS 없어서 공개 비공개 설정 해놓음.
CREATE TABLE `communitys` (
                              `c_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자커뮤니티글 아이디',
                              `u_id`	varchar(255)  COMMENT '작성자 아이디',
                              `p_id`	int unsigned  COMMENT '같이가자플랜 아이디',
                              `title`	varchar(255) NOT NULL COMMENT '제목',
                              `content`	TEXT  COMMENT '내용',
                              `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                              `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                              `view_count`	INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                              `status`	enum('PUBLIC','PRIVATE') DEFAULT 'PUBLIC' COMMENT '상태',
                              `area`	ENUM('서울', '인천', '대전', '광주', '대구', '울산', '부산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주')NOT NULL,
                              `istj`	boolean	COMMENT 'MBTI',
                              `istp`	boolean	COMMENT 'MBTI',
                              `isfj`	boolean	COMMENT 'MBTI',
                              `isfp`	boolean COMMENT 'MBTI',
                              `intj`	boolean	COMMENT 'MBTI',
                              `intp`	boolean	COMMENT 'MBTI',
                              `infj`	boolean	COMMENT 'MBTI',
                              `infp`	boolean	COMMENT 'MBTI',
                              `estj`	boolean	COMMENT 'MBTI',
                              `estp`	boolean	COMMENT 'MBTI',
                              `esfj`	boolean	COMMENT 'MBTI',
                              `esfp`	boolean	COMMENT 'MBTI',
                              `entj`	boolean	COMMENT 'MBTI',
                              `entp`	boolean	COMMENT 'MBTI',
                              `enfj`	boolean	COMMENT 'MBTI',
                              `enfp`	boolean	COMMENT 'MBTI',
                              FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE SET NULL ON UPDATE CASCADE,
                              FOREIGN KEY (p_id) REFERENCES plans (p_id) ON DELETE SET NULL ON UPDATE CASCADE
);


#같이놀자( 덧글)
CREATE TABLE `comm_comment` (
                                `cc_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자댓글 아이디',
                                `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                                `u_id`	varchar(255)	NOT NULL COMMENT '유저 아이디',
                                `content`	TEXT  COMMENT '내용',
                                `status`	enum('PUBLIC','PRIVATE') DEFAULT 'PUBLIC' COMMENT '상태',
                                `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                                `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                                `parent_cr_id`	INT UNSIGNED COMMENT '부모 댓글 아이디(대댓글)',
                                FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (parent_cr_id) REFERENCES comm_comment (cc_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#같이놀자 ( 이미지테이블)
CREATE TABLE `comm_imgs` (
                             `ci_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자이미지 아이디',
                             `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                             `img_path`	varchar(255) COMMENT '이미지 경로',
                             `img_main`	boolean	COMMENT '대표 이미지',
                             FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#같이놀자 (신고 테이블)
#게시글에 신고 1번 가능 제약조건
CREATE TABLE `comm_reports` (
                                `cr_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자신고글 아이디',
                                `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                                `u_id`	varchar(255)	NOT NULL COMMENT '유저 아이디',
                                `content`	TEXT  COMMENT '신고 내용',
                                `post_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
                                FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT comm_reports UNIQUE (u_id, c_id)
);
#같이놀자 ( 좋아요 테이블)
#제약조건 추가 1개만 좋아요 가능하게
CREATE TABLE `comm_likes` (
                              `cl_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자 좋아요 아이디',
                              `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                              `u_id`	varchar(255)	NOT NULL COMMENT '유저 아이디',
                              FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT c_likes UNIQUE (u_id, c_id)
);
#같이놀자(북마크 테이블)
#제약조건 추가 1개만북마크 가능하게 + 북마크에 p_id 추가
CREATE TABLE `comm_bookmarks` (
                                  `cbook_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자북마크 아이디',
                                  `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                                  `u_id`	varchar(255)	NOT NULL COMMENT '유저 아이디',
                                  `p_id`	int unsigned NOT NULL COMMENT '플랜 아이디',
                                  FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (p_id) REFERENCES plans (p_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  CONSTRAINT c_bookmark UNIQUE (u_id, c_id)
);
#같이놀자(해시태그 테이블)
CREATE TABLE `comm_hashtags` (
                                 `ch_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자해시태그 아이디',
                                 `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                                 `tag_id` int unsigned NOT NULL COMMENT '태그 아이디',
                                 FOREIGN KEY (tag_id) REFERENCES hashtags (tag_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#같이놀자(조회수 테이블)
#제약조건 추가 조회수1번만 가능하게
CREATE TABLE `comm_viewcounts` (
                                   `cv_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '같이놀자조회수 아이디',
                                   `c_id`	int unsigned NOT NULL COMMENT '같이놀자커뮤니티글 아이디',
                                   `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                                   `view_count` INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                                   FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT comm_viewcnt UNIQUE (u_id, c_id)
);


#상품판매 (이미지 테이블)
CREATE TABLE `sell_imgs` (
                             `simg_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판매이미지 아이디',
                             `s_id`	int unsigned NOT NULL COMMENT '판매글 아이디',
                             `img_path`	varchar(255) COMMENT'이미지 경로',
                             FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE

);

#상품판매(조회수)
#제약조건 추가 조회수1번만 가능하게
CREATE TABLE `sell_view_counts` (
                                    `svc_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판매조회수 아이디',
                                    `s_id`		int unsigned NOT NULL COMMENT '판매글 아이디',
                                    `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                                    `view_count`  INT UNSIGNED DEFAULT 0 COMMENT '조회수',
                                    FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CONSTRAINT sell_viewcnt UNIQUE (u_id, s_id)
);

#상품판매 ( 북마크)
#제약조건 추가 1개만북마크 가능하게
CREATE TABLE `sell_bookmarks` (
                                  `sb_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판매북마크 아이디',
                                  `s_id`	int unsigned NOT NULL COMMENT '판매글 아이디',
                                  `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                  FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  CONSTRAINT s_bookmark UNIQUE (u_id, s_id)
);



# #환불 페이지
# CREATE TABLE `sell_refunds` (
#                                 `refund_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '환불 아이디',
#                                 `oder_id`	int unsigned NOT NULL COMMENT '주문번호 아이디',
#                                 `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
#                                 `refund_check` BOOLEAN COMMENT '환불여부',
#                                 `refund_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '환불일자',
#                                 FOREIGN KEY (oder_id) REFERENCES sell_details (oder_id)ON UPDATE CASCADE ON DELETE CASCADE ,
#                                 FOREIGN KEY (u_id) REFERENCES users (u_id)  ON UPDATE CASCADE ON DELETE CASCADE
# );
#장바구니
CREATE TABLE `sell_carts` (
                              `cart_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '장바구니 아이디',
                              `u_id`	varchar(255) NOT NULL COMMENT '작성자 아이디',
                              `s_id`	int unsigned NOT NULL COMMENT '옵션 아이디',
                              UNIQUE (u_id, s_id),
                              FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#마이페이지( 팔로우 테이블)

CREATE TABLE `follows` (
                           `f_id`	int unsigned AUTO_INCREMENT  PRIMARY KEY COMMENT '팔로우인덱스',
                           `to_users`	varchar(255)	NOT NULL COMMENT '받는유저 아이디',
                           `from_users`	varchar(255)NOT NULL COMMENT '보내는유저',
                           UNIQUE (from_users, to_users),
                           FOREIGN KEY (to_users) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (from_users) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE

);

#마이페이지( 문의하기 테이블)
CREATE TABLE `qnas` (
                        `q_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '문의글 아이디',
                        `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                        `title`	varchar(255) NOT NULL COMMENT '제목',
                        `content`	text	COMMENT '내용',
                        `file_path`	varchar(255)	COMMENT '파일첨부',
                        `post_time`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '문의 날짜',
                        FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#문의하기(덧글 테이블)
CREATE TABLE `qna_replys` (
                              `qr_id` int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판매옵션 아이디',
                              `q_id`	int unsigned NOT NULL COMMENT '문의글 아이디',
                              `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                              `content`	text	COMMENT '댓글 내용',
                              `post_time`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
                              `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
                              `parent_qna_id`	INT UNSIGNED COMMENT '부모 댓글 아이디(대댓글)',
                              FOREIGN KEY (q_id) REFERENCES qnas (q_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (parent_qna_id) REFERENCES qnas (q_id) ON DELETE CASCADE ON UPDATE CASCADE
);


#쪽지 테이블
CREATE TABLE `notes` (
                         `l_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '쪽지 아이디',
                         `to_users`	varchar(255)  COMMENT '받는 아이디',
                         `from_users`	varchar(255) NOT NULL COMMENT '보내는 유저 아이디',
                         `post_time`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '발신일자',
                         `title`	  varchar(255)NOT NULL COMMENT '제목',
                         `content`	varchar(255) COMMENT '짧은내용',
                         `status`	boolean COMMENT '읽은 상태',
                         `note_keb`	boolean COMMENT '보관 상태',
                         FOREIGN KEY (to_users) REFERENCES users (u_id) ON DELETE SET NULL ON UPDATE CASCADE

);
#주문정보

CREATE TABLE sell_orders (
                             so_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '주문번호',
                             u_id VARCHAR(255) NOT NULL COMMENT '유저정보',
                             s_id INT UNSIGNED COMMENT '판매글번호',
                             total_price INT NOT NULL COMMENT '총구매 가격',
                             `info` varchar(255) COMMENT '결제 정보',
                             post_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '구매 일시',
                             FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE sell_order_details (
                                    sod_id INT unsigned NOT NULL AUTO_INCREMENT,
                                    u_id VARCHAR(255),
                                    s_id INT unsigned,
                                    o_id int unsigned,
                                    so_id INT unsigned,
                                    cnt int comment '수량',
                                    post_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '구매일시',
#                                     used_check boolean default false comment '사용여부',
                                    PRIMARY KEY (sod_id),
                                    FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (o_id) REFERENCES sell_options (o_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (so_id) REFERENCES sell_orders (so_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE sell_tickets (
                              st_id INT AUTO_INCREMENT PRIMARY KEY COMMENT'티켓아이디',
                              sod_id INT UNSIGNED NOT NULL COMMENT'구매옵션아이디',
                              ticket_num VARCHAR(20) NOT NULL COMMENT'티켓번호',
                              use_check BOOLEAN DEFAULT FALSE COMMENT'사용여부',
                              used_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '사용시간',
                              FOREIGN KEY (sod_id) REFERENCES sell_order_details(sod_id) ON DELETE CASCADE ON UPDATE CASCADE
);



#유저더미
INSERT INTO users (u_id, pw, name, nk_name, email, birth, phone, address, detail_address, pr_content, permission, mbti, img_path, store_name, business_id)
VALUES
    ('user01', '1234', '김철수', '바보철수', 'user01@example.com', '1990-01-01', '010-1234-5678', '서울특별시 강남구', '삼성동 123-45', '안녕하세요. 저는 철수입니다.', 'USER', 'ISTJ', '/public/img/user/profile.jpg', NULL, NULL),
    ('user02', '1234', '김영수', '영수', 'kimyoungsoo@gmail.com', '1995-02-03', '010-1111-2222', '서울특별시 강남구', '신사동 123-1', '안녕하세요. 저는 웹 개발자입니다.', 'USER', 'ISTJ', '/public/img/user/profile.jpg', NULL, NULL),
    ('user03', '1234', '이은지', '은지', 'leeeunji@gmail.com', '1998-06-17', '010-1234-5679', '서울특별시 관악구', '신림동 543-2', '안녕하세요. 저는 디자이너입니다.', 'USER', 'INFP', '/public/img/user/profile.jpg', NULL, NULL),
    ('user04', '1234', '박민수', '민수', 'parkminsou@gmail.com', '1987-09-23', '010-5555-5555', '경기도 부천시', '상동 23-4', '안녕하세요. 저는 영화 감독입니다.', 'USER', 'INTJ', '/public/img/user/profile.jpg', NULL, NULL),
    ('user05', '1234', '장현아', '현아', 'janghyuna@gmail.com', '2000-01-01', '010-8888-8888', '서울특별시 송파구', '잠실동 456-7', '안녕하세요. 저는 대학생입니다.', 'USER', 'ENFP', '/public/img/user/profile.jpg', NULL, NULL),
    ('user06', '1234', '오현우', '현우', 'ohyunwoo@gmail.com', '1992-07-11', '010-2222-3333', '서울특별시 종로구', '관수동 23-1', '안녕하세요. 저는 작가입니다.', 'USER', 'ISFP', '/public/img/user/profile.jpg', NULL, NULL),
    ('user07', '1234', '서지수', '지수', 'seojisoo@gmail.com', '1985-12-30', '010-7777-7777', '서울특별시 서대문구', '이화동 17-5', '안녕하세요. 저는 편집자입니다.', 'USER', 'INTP','/public/img/user/profile.jpg', NULL, NULL),
    ('user08', '1234', '강민지', '민지', 'kangminji@gmail.com', '1994-03-24', '010-1234-5677', '서울특별시 마포구', '망원동 456-7', '안녕하세요. 저는 영화 배우입니다.', 'USER', 'ESFP', '/public/img/user/profile.jpg', NULL, NULL),
    ('user09', '1234','박성준', '성준', 'parksungjun@gmail.com', '1991-11-11', '010-9999-9999', '경기도 의정부시', '송산동 123-4', '안녕하세요. 저는 의사입니다.', 'USER', 'ENTJ', '/public/img/user/profile.jpg', NULL, NULL),
    ('user10', '1234', '임수현', '수현', 'limsoohyun@gmail.com', '1996-08-08', '010-7777-7776', '서울특별시 강동구', '천호동 456-7', '안녕하세요. 저는 공무원입니다.', 'PARTNER', 'ISTP', '/public/img/user/profile.jpg', NULL, NULL);


#플래너더미
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(1, 'user01', '취업 기원 제주도 여행', '취업 좀 하자 제발', '2023-04-01', '2023-04-03', '/public/img/plan/p1Sample.jpg', 'PUBLIC', false);
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(2, 'user02', '강원도 삼척 여행', '강원도 다녀오기', '2022-03-11', '2022-03-15', '/public/img/plan/p2Sample.jpg', 'PUBLIC', true);
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(3, 'user02', '놀러 가자', '일단 ㄱㄱ', '2023-05-01', '2023-05-03', '/public/img/plan/p3Sample.jpg', 'PUBLIC', false);
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(4, 'user04', '제주도 한 달 살기', '제주도에서 살고 싶다...', '2023-02-01', '2023-03-03', '/public/img/plan/p4Sample.png', 'PUBLIC', false);
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(5, 'user05', '경주에서 수학여행 즐기기', '경주 얼마만이냐', '2023-06-01', '2023-06-03', '/public/img/plan/p5Sample.jpg', 'PUBLIC', false);

#플랜 같이 짜는 멤버
INSERT INTO plan_members (ml_id, p_id, mu_id) VALUE (1, 1, 'USER02');
INSERT INTO plan_members (ml_id, p_id, mu_id) VALUE (2, 1, 'USER03');
INSERT INTO plan_members (ml_id, p_id, mu_id) VALUE (3, 2, 'USER04');
INSERT INTO plan_members (ml_id, p_id, mu_id) VALUE (4, 2, 'USER05');
INSERT INTO plan_members (ml_id, p_id, mu_id) VALUE (5, 4, 'USER01');

#플랜 체크리스트
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (1, 1, '숙소 예약 하기', 'CHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (2, 1, '비행기 표 구입', 'CHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (3, 1, '재밌게 놀다 오기', 'UNCHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (4, 2, '기념품 사오기', 'CHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (5, 2, 'KTX 예약 08:20 ~', 'UNCHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (6, 2, '재밌게 놀다 오기', 'UNCHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (7, 3, '숙소 예약 하기', 'CHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (8, 3, '비행기 표 구입', 'CHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (9, 3, '여권 발급 받기', 'UNCHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (10, 4, '기념품 사오기', 'CHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (11, 4, 'KTX 예약 08:20 ~', 'UNCHECKED');
INSERT INTO plan_check_lists (cl_id, p_id, content, check_status) VALUE (12, 4, '다이소 다녀오기', 'UNCHECKED');

#플래너 그림판 로딩 테스트용 더미(User01로 접속하면 나옴)

INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (1, 1, null, null, 1, '한라산국립공원', '관음사탐방로 코스, 숙소에서 30분, 우의 꼭 챙겨 가야됨', '06:00 ~ 12:00', '/public/img/plan/1682684497189_8156.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (2, 1, null, null, 1, '선녀 물회', '내려올 때 테이블링 미리 하기!', '14:00 ~ 15:00', '/public/img/plan/1682684497228_534.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (3, 1, null, null, 1, '함덕 해수욕장', '제주도 시외버스터미널 → 동회선 시외버스 이용(30분)', '19:00 ~ 23:00', '/public/img/plan/1682684497242_4631.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (4, 1, null, null, 2, '서귀포 어시장', '☆☆☆방어 필수☆☆☆', '06:00 ~ 08:00', '/public/img/plan/1682684497260_3031.png');

INSERT INTO gabojagoplan.plan_content_paths (path_id, con_id, can_path) VALUES (1, 1, '[{"type":"img","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[35,23],"lineTo":[550,452],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[328,123],"path":[[328,122],[330,121],[333,119],[336,118],[338,117],[340,117],[343,116],[344,116],[345,116],[346,116],[347,118],[349,120],[351,121],[352,122],[354,123],[355,125],[356,126],[357,126],[358,126],[361,126],[364,126],[369,126],[376,124],[385,120],[393,118],[399,115],[401,115],[402,115],[404,115],[405,115],[407,117],[410,119],[411,122],[414,125],[416,128],[418,130],[419,131],[420,132],[421,132],[423,132],[426,131],[431,130],[439,126],[449,123],[455,121],[459,120],[461,120],[463,120],[464,120],[467,122],[470,123],[472,124],[474,126],[475,126],[477,126],[479,126],[484,125],[490,121],[497,118],[502,116],[504,115],[505,115],[507,115],[508,115],[509,115],[512,115],[514,115],[517,115],[519,115],[520,115],[521,115],[522,115],[523,115],[524,115],[525,115]],"range":[[328,115],[525,132]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[591,239],"path":[[590,239],[587,239],[583,238],[579,237],[574,234],[567,231],[562,227],[557,222],[553,218],[549,213],[546,209],[542,204],[541,201],[540,200]],"range":[[540,200],[590,239]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[591,239],"path":[[590,239],[587,239],[583,238],[579,237],[574,234],[567,231],[562,227],[557,222],[553,218],[549,213],[546,209],[542,204],[541,201],[540,200]],"range":[[540,200],[590,239]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[546,194],"path":[[545,194],[543,197],[541,199],[538,202],[537,203],[535,204],[534,205],[534,206],[533,206]],"range":[[533,194],[545,206]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[546,194],"path":[[545,194],[543,197],[541,199],[538,202],[537,203],[535,204],[534,205],[534,206],[533,206]],"range":[[533,194],[545,206]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[537,195],"path":[[538,195],[540,196],[543,199],[548,202],[553,204],[557,205],[560,206],[563,206],[564,206],[566,207],[567,207]],"range":[[538,195],[567,207]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[537,195],"path":[[538,195],[540,196],[543,199],[548,202],[553,204],[557,205],[560,206],[563,206],[564,206],[566,207],[567,207]],"range":[[538,195],[567,207]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[587,253],"path":[[588,253],[596,253],[599,253],[601,253],[602,253],[604,257],[606,259],[606,262],[607,264],[607,266],[604,269],[601,270],[597,271],[592,273],[589,274],[588,274],[589,274],[591,274],[598,274],[606,274],[613,274],[619,274],[623,274],[626,274],[626,273],[626,271]],"range":[[588,253],[626,274]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[587,253],"path":[[588,253],[596,253],[599,253],[601,253],[602,253],[604,257],[606,259],[606,262],[607,264],[607,266],[604,269],[601,270],[597,271],[592,273],[589,274],[588,274],[589,274],[591,274],[598,274],[606,274],[613,274],[619,274],[623,274],[626,274],[626,273],[626,271]],"range":[[588,253],[626,274]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[627,257],"path":[[627,258],[627,260],[627,264],[627,268],[625,271],[624,275],[623,277],[623,278],[622,278]],"range":[[622,258],[627,278]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[627,257],"path":[[627,258],[627,260],[627,264],[627,268],[625,271],[624,275],[623,277],[623,278],[622,278]],"range":[[622,258],[627,278]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[624,265],"path":[[628,268],[631,270],[635,272],[637,274],[638,275],[639,275]],"range":[[628,268],[639,275]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[624,265],"path":[[628,268],[631,270],[635,272],[637,274],[638,275],[639,275]],"range":[[628,268],[639,275]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[639,259],"path":[[641,264],[643,270],[645,277],[647,281],[648,283],[649,283],[651,280]],"range":[[641,264],[651,283]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[639,259],"path":[[641,264],[643,270],[645,277],[647,281],[648,283],[649,283],[651,280]],"range":[[641,264],[651,283]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[656,250],"path":[[657,250],[658,250],[659,250],[667,253],[675,255],[680,257],[682,258],[682,259],[682,261],[682,264],[682,268],[682,270],[680,271],[679,273],[678,273]],"range":[[657,250],[682,273]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[656,250],"path":[[657,250],[658,250],[659,250],[667,253],[675,255],[680,257],[682,258],[682,259],[682,261],[682,264],[682,268],[682,270],[680,271],[679,273],[678,273]],"range":[[657,250],[682,273]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[694,244],"path":[[694,249],[696,256],[697,262],[697,268],[698,272],[698,274],[698,275]],"range":[[694,249],[698,275]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[694,244],"path":[[694,249],[696,256],[697,262],[697,268],[698,272],[698,274],[698,275]],"range":[[694,249],[698,275]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[699,267],"path":[[700,267],[701,267],[702,266],[706,266],[707,266]],"range":[[700,266],[707,267]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[699,267],"path":[[700,267],[701,267],[702,266],[706,266],[707,266]],"range":[[700,266],[707,267]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[685,283],"path":[[685,285],[685,287],[685,289],[686,290],[689,291],[693,292],[700,292],[707,291],[714,288],[719,286],[722,284],[723,282]],"range":[[685,282],[723,292]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[685,283],"path":[[685,285],[685,287],[685,289],[686,290],[689,291],[693,292],[700,292],[707,291],[714,288],[719,286],[722,284],[723,282]],"range":[[685,282],[723,292]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[691,235],"path":[[683,240],[672,247],[660,257],[646,267],[632,278],[623,286],[616,290],[611,293],[606,296],[603,297]],"range":[[603,240],[683,297]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[691,235],"path":[[683,240],[672,247],[660,257],[646,267],[632,278],[623,286],[616,290],[611,293],[606,296],[603,297]],"range":[[603,240],[683,297]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[629,236],"path":[[634,236],[647,241],[662,249],[678,258],[689,266],[697,274],[701,278],[702,281],[704,283],[704,284],[705,284]],"range":[[634,236],[705,284]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":0.5,"scale":1,"moveTo":[629,236],"path":[[634,236],[647,241],[662,249],[678,258],[689,266],[697,274],[701,278],[702,281],[704,283],[704,284],[705,284]],"range":[[634,236],[705,284]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[464,39],"path":[[463,40],[463,41],[463,42],[466,43],[468,45],[470,45],[471,45],[473,45],[474,45],[475,45],[476,44],[476,43],[477,42],[477,40],[477,39],[476,38],[475,37],[473,37],[471,37],[468,38],[466,40],[465,42],[464,45],[464,46],[466,47],[468,47],[472,47],[474,46],[476,44],[477,42],[477,41],[477,40],[477,39],[477,38],[477,37],[475,37],[473,37],[470,37],[468,38],[466,41],[465,42],[465,44],[465,45],[466,46],[468,47],[470,47],[472,47],[474,46],[475,43],[476,42],[476,39],[476,37],[476,36],[475,36],[473,36],[472,38],[470,41],[469,42],[469,43],[470,43],[470,42],[471,41],[471,40],[471,39],[472,39]],"range":[[463,36],[477,47]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[464,39],"path":[[463,40],[463,41],[463,42],[466,43],[468,45],[470,45],[471,45],[473,45],[474,45],[475,45],[476,44],[476,43],[477,42],[477,40],[477,39],[476,38],[475,37],[473,37],[471,37],[468,38],[466,40],[465,42],[464,45],[464,46],[466,47],[468,47],[472,47],[474,46],[476,44],[477,42],[477,41],[477,40],[477,39],[477,38],[477,37],[475,37],[473,37],[470,37],[468,38],[466,41],[465,42],[465,44],[465,45],[466,46],[468,47],[470,47],[472,47],[474,46],[475,43],[476,42],[476,39],[476,37],[476,36],[475,36],[473,36],[472,38],[470,41],[469,42],[469,43],[470,43],[470,42],[471,41],[471,40],[471,39],[472,39]],"range":[[463,36],[477,47]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[464,39],"path":[[463,40],[463,41],[463,42],[466,43],[468,45],[470,45],[471,45],[473,45],[474,45],[475,45],[476,44],[476,43],[477,42],[477,40],[477,39],[476,38],[475,37],[473,37],[471,37],[468,38],[466,40],[465,42],[464,45],[464,46],[466,47],[468,47],[472,47],[474,46],[476,44],[477,42],[477,41],[477,40],[477,39],[477,38],[477,37],[475,37],[473,37],[470,37],[468,38],[466,41],[465,42],[465,44],[465,45],[466,46],[468,47],[470,47],[472,47],[474,46],[475,43],[476,42],[476,39],[476,37],[476,36],[475,36],[473,36],[472,38],[470,41],[469,42],[469,43],[470,43],[470,42],[471,41],[471,40],[471,39],[472,39]],"range":[[463,36],[477,47]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[469,38],"path":[[468,38],[467,38],[467,39],[467,40],[467,41],[467,42],[468,42],[469,40],[471,38],[471,37],[471,36],[471,37],[471,38],[471,39],[471,40]],"range":[[467,36],[471,42]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[469,38],"path":[[468,38],[467,38],[467,39],[467,40],[467,41],[467,42],[468,42],[469,40],[471,38],[471,37],[471,36],[471,37],[471,38],[471,39],[471,40]],"range":[[467,36],[471,42]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[469,38],"path":[[468,38],[467,38],[467,39],[467,40],[467,41],[467,42],[468,42],[469,40],[471,38],[471,37],[471,36],[471,37],[471,38],[471,39],[471,40]],"range":[[467,36],[471,42]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[489,36],"path":[[491,35],[496,34],[503,32],[512,30],[523,28],[530,27],[531,26]],"range":[[491,26],[531,35]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[489,36],"path":[[491,35],[496,34],[503,32],[512,30],[523,28],[530,27],[531,26]],"range":[[491,26],[531,35]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[489,36],"path":[[491,35],[496,34],[503,32],[512,30],[523,28],[530,27],[531,26]],"range":[[491,26],[531,35]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[549,14],"path":[[548,14],[543,19],[543,20],[541,21],[539,22],[539,23]],"range":[[539,14],[548,23]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[549,14],"path":[[548,14],[543,19],[543,20],[541,21],[539,22],[539,23]],"range":[[539,14],[548,23]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[549,14],"path":[[548,14],[543,19],[543,20],[541,21],[539,22],[539,23]],"range":[[539,14],[548,23]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[546,21],"path":[[547,21],[548,21],[549,21],[550,21],[552,22],[553,22],[554,23],[555,23]],"range":[[547,21],[555,23]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[546,21],"path":[[547,21],[548,21],[549,21],[550,21],[552,22],[553,22],[554,23],[555,23]],"range":[[547,21],[555,23]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[546,21],"path":[[547,21],[548,21],[549,21],[550,21],[552,22],[553,22],[554,23],[555,23]],"range":[[547,21],[555,23]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[534,33],"path":[[535,33],[536,33],[540,33],[547,33],[553,32],[556,31],[559,31],[560,30],[561,30]],"range":[[535,30],[561,33]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[534,33],"path":[[535,33],[536,33],[540,33],[547,33],[553,32],[556,31],[559,31],[560,30],[561,30]],"range":[[535,30],[561,33]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[534,33],"path":[[535,33],[536,33],[540,33],[547,33],[553,32],[556,31],[559,31],[560,30],[561,30]],"range":[[535,30],[561,33]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[551,35],"path":[[550,35],[549,38],[548,41],[548,43],[548,45],[547,46]],"range":[[547,35],[550,46]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[551,35],"path":[[550,35],[549,38],[548,41],[548,43],[548,45],[547,46]],"range":[[547,35],[550,46]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[551,35],"path":[[550,35],[549,38],[548,41],[548,43],[548,45],[547,46]],"range":[[547,35],[550,46]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[541,46],"path":[[542,46],[543,46],[548,46],[554,46],[556,46],[558,46],[558,49],[558,51],[558,54],[556,56],[556,58]],"range":[[542,46],[558,58]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[541,46],"path":[[542,46],[543,46],[548,46],[554,46],[556,46],[558,46],[558,49],[558,51],[558,54],[556,56],[556,58]],"range":[[542,46],[558,58]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[541,46],"path":[[542,46],[543,46],[548,46],[554,46],[556,46],[558,46],[558,49],[558,51],[558,54],[556,56],[556,58]],"range":[[542,46],[558,58]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[579,19],"path":[[579,20],[578,21],[576,26],[574,30],[572,35],[570,38],[569,41],[569,42],[568,42]],"range":[[568,20],[579,42]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[579,19],"path":[[579,20],[578,21],[576,26],[574,30],[572,35],[570,38],[569,41],[569,42],[568,42]],"range":[[568,20],[579,42]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[579,19],"path":[[579,20],[578,21],[576,26],[574,30],[572,35],[570,38],[569,41],[569,42],[568,42]],"range":[[568,20],[579,42]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[571,33],"path":[[572,33],[579,33],[583,34],[585,35],[586,35],[586,36],[587,37]],"range":[[572,33],[587,37]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[571,33],"path":[[572,33],[579,33],[583,34],[585,35],[586,35],[586,36],[587,37]],"range":[[572,33],[587,37]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[571,33],"path":[[572,33],[579,33],[583,34],[585,35],[586,35],[586,36],[587,37]],"range":[[572,33],[587,37]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[578,45],"path":[[576,46],[575,47],[574,49],[573,50],[572,51],[570,53],[568,54]],"range":[[568,46],[576,54]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[578,45],"path":[[576,46],[575,47],[574,49],[573,50],[572,51],[570,53],[568,54]],"range":[[568,46],[576,54]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[578,45],"path":[[576,46],[575,47],[574,49],[573,50],[572,51],[570,53],[568,54]],"range":[[568,46],[576,54]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[564,54],"path":[[565,54],[572,53],[576,53],[581,52],[584,51],[587,51],[589,51],[591,51],[593,51],[595,51],[597,51],[598,51]],"range":[[565,51],[598,54]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[564,54],"path":[[565,54],[572,53],[576,53],[581,52],[584,51],[587,51],[589,51],[591,51],[593,51],[595,51],[597,51],[598,51]],"range":[[565,51],[598,54]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ff0000","fillStyle":"#ff0000","lineWidth":0.5,"scale":1,"moveTo":[564,54],"path":[[565,54],[572,53],[576,53],[581,52],[584,51],[587,51],[589,51],[591,51],[593,51],[595,51],[597,51],[598,51]],"range":[[565,51],[598,54]],"pageSize":0.9591666666666666},{"type":"stamp","strokeStyle":"#808080","fillStyle":"#ff0000","lineWidth":2,"scale":1,"moveTo":[288,46],"lineTo":[331,88],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},{"type":"stamp","strokeStyle":"#808080","fillStyle":"#ff0000","lineWidth":2,"scale":1,"moveTo":[281,68],"lineTo":[313,98],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},{"type":"stamp","strokeStyle":"#808080","fillStyle":"#ff0000","lineWidth":2,"scale":1,"moveTo":[271,98],"lineTo":[304,117],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},{"type":"stamp","strokeStyle":"#808080","fillStyle":"#ff0000","lineWidth":2,"scale":1,"moveTo":[286,86],"lineTo":[321,109],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[340,58],"path":[[341,58],[342,58],[343,56],[344,53],[345,51],[347,48],[350,47],[352,45],[353,45],[355,44],[355,43],[356,43]],"range":[[341,43],[356,58]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[360,32],"path":[[361,32],[367,32],[372,32],[375,32],[375,33],[374,36],[371,38],[369,40],[367,41],[366,42],[367,42],[369,43],[372,43],[373,43],[373,44],[373,49],[373,52],[371,56],[369,59],[368,60],[368,59]],"range":[[361,32],[375,60]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[389,37],"path":[[389,38],[388,38],[386,41],[385,44],[383,47],[383,50],[384,50],[386,50],[389,50],[390,50],[391,49],[392,48],[393,47],[393,45],[393,44],[394,43],[394,41],[394,40],[394,39]],"range":[[383,38],[394,50]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[406,23],"path":[[406,24],[406,25],[406,29],[406,32],[406,36],[408,39],[408,42],[409,43],[410,44],[412,44],[413,46],[415,46]],"range":[[406,24],[415,46]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[424,24],"path":[[424,25],[424,27],[424,31],[424,35],[423,38],[423,41],[423,43],[423,44]],"range":[[423,25],[424,44]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[415,39],"path":[[415,38],[418,38],[420,38],[422,38],[423,38]],"range":[[415,38],[423,38]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[413,45],"path":[[414,45],[416,45],[417,45],[418,45]],"range":[[414,45],[418,45]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[403,53],"path":[[404,53],[406,53],[410,52],[414,51],[417,50],[420,49],[422,47],[423,47],[424,47],[425,46]],"range":[[404,46],[425,53]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[417,48],"path":[[417,49],[417,50],[417,51],[416,51],[416,52],[416,53],[416,54],[415,54],[415,56],[415,57],[415,58]],"range":[[415,49],[417,58]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[406,58],"path":[[406,59],[406,60],[406,61],[406,62],[407,62],[412,62],[413,62],[416,61],[418,61],[421,61],[423,60],[425,60],[426,59],[428,59],[429,59],[430,58],[431,58],[432,57],[433,57]],"range":[[406,57],[433,62]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[435,46],"path":[[436,46],[437,46],[438,46],[439,46],[440,47],[441,47],[442,47],[443,47],[444,47],[445,47],[446,46],[447,45],[448,45]],"range":[[436,45],[448,47]],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#808080","fillStyle":"#ffa500","lineWidth":2,"scale":1,"moveTo":[695,48],"lineTo":[954,222],"src":"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/4gHYSUNDX1BST0ZJTEUAAQEAAAHIAAAAAAQwAABtbnRyUkdCIFhZWiAH4AABAAEAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAAAADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlkZXNjAAAA8AAAACRyWFlaAAABFAAAABRnWFlaAAABKAAAABRiWFlaAAABPAAAABR3dHB0AAABUAAAABRyVFJDAAABZAAAAChnVFJDAAABZAAAAChiVFJDAAABZAAAAChjcHJ0AAABjAAAADxtbHVjAAAAAAAAAAEAAAAMZW5VUwAAAAgAAAAcAHMAUgBHAEJYWVogAAAAAAAAb6IAADj1AAADkFhZWiAAAAAAAABimQAAt4UAABjaWFlaIAAAAAAAACSgAAAPhAAAts9YWVogAAAAAAAA9tYAAQAAAADTLXBhcmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABtbHVjAAAAAAAAAAEAAAAMZW5VUwAAACAAAAAcAEcAbwBvAGcAbABlACAASQBuAGMALgAgADIAMAAxADb/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkzODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P/2wBDARESEhgVGC8aGi9jQjhCY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P/wAARCAC1AQ4DASIAAhEBAxEB/8QAGgAAAgMBAQAAAAAAAAAAAAAAAwQAAQIFBv/EADoQAAICAQMBBgQEBQQBBQEAAAECAxEABBIhMQUTIkFRYXGBkaEUMrHhI0JSwfBictHxkgYVJDNTgv/EABkBAAMBAQEAAAAAAAAAAAAAAAABAgMEBf/EACERAAICAgMAAwEBAAAAAAAAAAABAhEDEiExQQQTURQi/9oADAMBAAIRAxEAPwDrbcm3NZeelZwamNuXtzWXhYamNuXtzWXWFhqY25e3NZMLDUztybc3kxWGpjbl7c1kwsepW3L25eXisNTO3L25rJisepnblVm8rCwozWXWXkvCwoqslZd5LwsNSqyVl3kvCw1KrLrJeS8LCiVlVl3kvCwoqslZLyXhYUSsrLvKvCx0TJlXkvCwo8y/aUiS0287T/UfnmV10qT73mlCnmixwEKzPIDEfCBdkcDNvp5HLO6cG/ykAft++ebvXp00OQ9qahI95bct/wAw5r1xuPWzNGHs1f8AMBz9M5sWmUR7kbk+RHT9scIrTxLTWh/MDx0+fGJ5ZdWNRX4bOtnJBLVfUDyzB1c9gd6QSPXnBohd7U7Vvp7Vk0iIzSK0znZZJK9K98m232FGjq9ShAZiSBzTdcMmslCi3cgc31vEpncKd7GmUkNXF1dXlaeYd3cjqvhuupbG3JdMKR0BrdQWFN4R1sDnCxdpMG2zRNQ/nHTOK08wIlZBsbp6XjiFpYVZxW5et0TlLLkj6LWL8OsusQjxWD6dc3+Ji/8A0X4ef0zgS94sTbGYEcFCefLkYnLOH2mqccMSeubR+RL0hwiesTUxOwVZFJPQeuE3jnkcdfbOTpNSXgG6IAjgliPS/wBM0dV/EMYFr1JGH9TXaH9S8Z1d3vkvOcZI5iFYstdLb9cyyNv3RTMNooi+K+GOPyr7Q/p/GdO8q8Uh1ZUMuoUhlYgEUbF8dPOsLDqtPM5RJFLel9c6VOL9MXGSDXkvNbMrZl0SVeS813eTuziAzeS833Ryd0cQwd5LOE7rJ3WAA7yXhO7yd1gAO8l4Tu8nd4ACvJeE7vJ3Y9cABXkvC7B65NgwA88pYBd1NQpqzZo8IaPtibopSi26LjobPHth0A7taPJHyzyGvTps2kQQDjwjr7DDgo4bddgVxgCfD4ec0GBrrx6jIY0ywACCKyFhEG8IuQFb6dch21xyMqTkrt4NXZwTadgL6iBzFErP4UJNlwAtnkc5mSHukSRQWS9wUmr+VYaZDLJukY8/0+HnNIWVQOGKDgt5ZpsAqy95G6RozleQGHKjC9nzSyRvu2gpt2hz8vj5+mMy91KX7yyWXpV4OHTwJDTKCwokrx0HT4Y9lXI65DsQ8MgYyIAN25UJ+nGcuPRySNfFhiCGP3zqy7pF/iMBt/LQBAHT5Yi+rcy7KrbahnIF+/T7VgnXQOgm6WDT90Za5pVXq59LPNdPLDsy6gIAdyAgtIzFBfp0+GKQwtLMtb22GzZ+NdMectCrIYQ59GIoD3GVt4CAStEJVcgqApP5WO7jgi8JpnRlYFQ9DlhX9vP/AJwZWOQVtj3/AMxI688Wfb44UIVQsT3nNjivhfTE5JdDRgGUneBI0ZoK27eBXsR1983pYoZdnhLvzuk4+465Bp9p3mRVI6bWquf85yJJGZRJEOKNkAg16HnHv+jNR/iYZKhneQAnduN7a8q/fOgdZGhjVyfH0YdCc5MxDSIYZKQuCw3Efp5Y+rMUJDbwOB06/vmsMzXTIeOLHlZWFqbrg5rOWrd3IrF9rlqJHAI/w/rjem1AnTyDDrR4+WdcMilx6c84OPI1xksYPnKs5oRYWxlWMHZyrwoLC2MljBbsrdhQWGsZVjBbsrdhQWGsZLwN5LwoLC2Mm7BWcqzgFnmUTYu+SOOMEbvymyb98kMjAMEJ2k8WbPzvnnATSB+78XpwrG/p8cuKU7gsiF7Ynaw5P+cZ5dHUdKFyDcpW78j04whAtyADtPJHTEY9XtkMLoVN8DaB8sZ06lubc3x4uD8MyarsaD30BPBzNg8AXzlohDkevW2yMNo4GQOjLttyLZG4gAVzllQ55HyyhEt+L6Y016FGiTZJ4oZSixZN5sKAOSQLs8XhIwhV6BHkGPHzrEOhMExuWRbJrqT5dPbIIxIxd0G0igoFUOOPthZFHeiMN78DNXTBV4UeeVsKg05WPTqsQ2WBRHn8/p9MCssquWslvfLfb3IDPbDkDJCtg0CR64bD7ZkXIx3MaFmvTN7mqkFJ6VhI4Qboj0zLAgFRYxOQUwKxo5JkYqRVGueMyYwCCvHS/f3w4Ujg1l0vDHn4YnIKA93GfEeGJ554ynWmWl486wsilxVcngADqcK2gvw7h3Y6i6OKwpi7gTxEFj4RZy9MH09yk2hB8RPF4Hce82gbfIAG6wsOt7kuNoeM9eKvNI5HF8BwzpfiAF3XmTqFB8Wc6NwSw0yGiS3dtQHyyjKQw7yF0Y88rnYvkt9GLxpHSGrQA8kj4ZpdRG4FN8s5neROeHVSeKPF5tkYdOuWs7JeNHTDqehByi1dazmpI6ggEj1sYVJgOvWvrmscsX2RKDQ8GXz/AFyWnmfocVSQt0UkedZl5o16mjfTzGa9mdjnHl+uQe/2Oc86qOuAb+H75Y1qA9CPrhQWh8Uf5ssr7jFI9WpHr88INSCOh+HlhyPg8rH3sMwUbN1EADgMM0RLO5Xa0Y68khfqM6w06NX8OiOKvyyvwke5SFraKAUUM8b7EdtCuiR/E/dkEtdG6b+4x6JwyAAt8xX9s1HSsSyij/TxhKBQ88+QIyJS2GgYClvExHlwLycdKNeuQjpx1yFmC8D55IyVQoEZYUcWKPXnMKSSAQflhdq3dE4w7ICoBB/N9sikqDRq/TMlbJvIeCDZ+eAGAFUg0bHmRxl7SbXk17YZIxIwG6iOhq8pYXEpRlAINWemOxUB2Br3Xx6euWoC0F49T64eXTvETQNHBMDdAfIYWFBYwzLfOZD29A3z0HOaisIwJHIrnBbeRsoYmNvgO6GN/GCTV1mGXcwPQHyGEihLgMxJ58zmlASdQKUEWCx6YU6sdWb0+nLVISQFPTC6kkQs6irF0wrAvqwg2Qlqs2x6nMvqS+naMkl2vk9BiGc4xncoJah06ZccCluKVBzzhzz5DLjiaSVUHTzN47JougQI4lsV5dW/bNbJAoDxr3X+t6AxmLSiGPwnnm5Dwb9sWkQy+F2ZlB4vEmOgHd6V/wCHs2EcBg36fvg5YNRo1LRSbozzu8v+8N3G08Djz8sIC4Ur/KfImxmiySRLjZzV1W1w3dgMTyV4+2MJPG93QPoeM3JpYZbNlGHUKcXPZshvYVN9NxIzVZYsimNKSW8FgHy6jLMas35ePpia6TXQsAB4R/qBGMp+IVrljUKOpDZaya9MTjfaIYAejH5jBmI+TA/DDDUKR4nB91GBdkosoc/L980XyWvSPqRkxyKNwHzBzQkn6NG/tQAOVpxMTSwbr6lx5fPGV0YkTx0BdgAdMUvlMqOBdlqrXdE374QKv9NfPCPR4AU+95XRQQBXTrnmm1AXULZ3BR75iM7jagH1N3jG5XPAse4ygqheEJ+dY7ArYDRI+2WY7XjCKb6ge1nMbzuIJH1wsDGyz5HJVe5vDlT0OUUAJJ4v0wsAO0EdPrlhbFX9c2qEtllVQ+N1T/cwGMZcERRt98Dr7Y1NErtu62Ob4AzGlKlCRKhA54OakmAUKzgD4VhyPwWaQooReQPXnMja4/LRzTz6dessbe4POAOv06n+Zv8AaOn1rGk2TYdFtdm3n1rLji2PZWz61iUvanI7iPj/AF9ftg27SmYcBV+A/wCcpQkLZHTWTut1iz5cVgRH3zFmYKb5sYmO1NUCTvXny2jNR9puOJY1b3U1lOMmGyGBEu6lBsnj1wkmlVFBBtj5f51wTdpw0KRrHsB98BL2wEFxwKGHQ7rrF9cmG0RhYZGk27ecdXRleFYWR19M4h7blJ5iT5EjNDtxlDKkJUHqd14/qn+ApxH5xqgpUvZA6BuB784IPuXmQj4C8Ui7XiCkSpLZrpVYxB2joXmDFzHXkyYnjkvA2T9HoNG7oGLuo9GAvBzr+GUGVSVJoG7wzdo6cpSToSf9QFZTfxVWI3ydxbd5e2S0XwLiVQppQvqa5zUaTSAFVIH+vjMTyRpPtiiXgVuPTLTVt4RyQTya4GSI3LpZzQBjAvnNpGsCmQgFwvNCsDG834gAzblPqL/6yauY7dgbafOxf0GMfAm80jyb9tgi+DRxqGJm5IpT/VisUZEgB3FR/TxnSWtighl/3Hk4CRZalFeVZhyOvAHucVk7Qji1YhppAQDx7mvniknd8GVrrgUf+QcdWNyGlnIB3OSvpdZn8SaocAngHAgEj/BhUifYCq9MXBIbvyaFBRV9cjuByxF4NUkkWwt16ms1XltHHXEMJHKhYeLnDM1UKUH44oYxuJVQB65TaqBPzN/484qvoB1d3PF88HNkebfXOeO1NOK/N/45Uva0QU90pLeRby+Qyljk/BWh52ijW3cIPc5yddrkmPdxrag8NZH2xOWR5nLuSxPmczXGbxxJcsiUy1Yg2HIPtkPBsnIsbEeEgDM0yn1zZRM9mbFE5oBiCR0+OZ7xuhGVvx6kuSJZyBq8zlBvXKJytSdjRfMs5AzBkrIHU9euWoCbL3EnKazm7X1GQkVl6ojZgqOSjllucm8ZSihWyqyq5y9wyWMeqDZkIBGQFlra7D4HJYyHE8aY1Nl/iNQrWJmP+7nGIe09VFu/+tt3W1q8VsZLGZSwR/C1lZ04u2QLL6chiKtWxnTPpdS4aJm7wn8rCjnD4yiSD4ePcZhL4/4bLLfZ6s93pkHUkngXeDd3lG2tqnqRfOL9kI88FzT7lHRa5HzzqAxqhMbRqvmxNnOOSp0brkRTs494SYlWM8uz8k/575ZghTwq0rDyCgcYxLtNnlz/AKjX26YtJOsTBSwU9dq9cXIcIXESHkH4cc5vaI+XJzbovQN1xXVRyqgaJyR7dcErdWNqg0uphganG0+nTF27Vj//ACZl+NZzJAzWWJJ68i8zXGdKwxXZnsxzV9oPOoCp3aegN4oCKNmshrizlNQPABzSMUuEQ2aBNCrPwyxZ68ZSg7RZ59shJJyjO7ND1/TL3Uen1zF88sMux64xNm+845UDM7ycqgR7/HMFSP8AvKSJ2bCZV4FrUDnM7zlKIqD7gMyWwW68u8qgNEA5nbkvLvLQE5y92VlYCNbsl3mLzWUhUSslHJeVeMVE5yZMtUZ/yqT74PgKM5YBZgo6ngYZNKXat4BHJ4PGESARr+ch74ZPLpx9/TMZZoJcM0jjb7MJo52qlHPkWAxqHs9oph+ICgXypu8tNXICadlNctwDX0zD6guAIm/3C73ZyvPNmqhFHej1GkggAifatEeY++K6vtPwhdKqAHkte3/s5yZO9lZQzWg45J6egySMnefwlbpQIJPOc+vPJrszffTyqxnkIU3t5J/TplGWTulpqjHFnoD6ZttJONiTaWgVqmG6ufn647ptSsCtHNCGjB8Ozgg+d5TpcAXvcnofpm9kjAigRlHUMF/MijoK88oW5KxkuD1AHJznKsoaTSzI3eF0cceHpi//ALajMalAHlfJxpYjN4HDiMXuo1+2ZqOMgwRrx/OeTmilJei7E27PCcGRfqBlNoJaBSNmB6EC86f4jfJzEJCOtrVfLGYtTK7VHCrAeVEVlfbL0WqZ52TSuDyrCvI8YJ43ApV+memlOoLtsKBiPyst/e85raCeV/GyAE8lfLNIZU+yXA5BiYD1GUoIPA5xjU6eeKUqVerpSBw2LEyDqTWdC5XBLVBAKHOYYgZnxH1rMuCOuWkZNGWa+uZHOYdqyBxmoBBl2MHvGTeMBBLybsHvGQEt+UE4xUE3ZV4bS6DU6qjHGdp/mIoZ2NF2KkM+6WYOyEMABwOeMlzSLjjbOPBBJOf4a36n0xkaNF/PIQauqz0ZhCxsFXaB1KgDAroI5WB7qSh1APDH3znllm3/AJ4NVhRw/wALH0U2T0LcD7YdtJ3cZZ9OVQ+e08H0vPQmXTwRUkSxqDVKANvufTKWZNZG2nSZDF/WF3hjfQZDcn2yljSPPQQQOWUFQedti7PkLy44Z9g3KFAFj3+GdKHRRyfxI5iIxe5doBFcVjCRxaGK1tVYbtxW/wBMh7PsaicS5dTqKkYksfEVBA+GdnQ6CKJzBPGsjddzKCtfDCaGeNqgh7vdzuJbn3+PXDd/HopLkcJC10zEAA+leWNRoaQ6mj0aqY2j3Aj0qj7V0zl6zs5YlMkYaiaa7+uNT6sd3RWywBEkRsEX1x/dFq9MCedwqwOcppNDo8i2hRmUzu6gsSAq2F/XGGjh0+lvTpcZZdxq7F8nMaiSfxxvuBujzxeA3uqtvd2cLwF4v4Zhu/EKmPSyxohIkCEClr9s5jagq9b2XiyBHd4VdRO6be5ah03nxHNNLIh2zOschFnd6fPCUnIdhhRQFl216A8/fIIwXUhkT3vLGn1Dx7mURqR/O/T6YWLRgFWZ9/qD4VH15zK6CiAu4294bB48PGS2VuQC3lxRwxO9aHdqBzwxzLThVBcAUL4ybbCgcTybmZdgPmTmoiWf+I7WeOGu8pQkq8/wzfIvpkLpECUVrvqetYUwGd6wRDoW/wB1YMnw2KT1WwcXiZSobbZYdG55xfVTHTM1ozeLao5vd5YlFsVnR70BwOKHPIvLaPShAW00W7pW0cZzdDqDqCC6OjJ+b0OOzSxxRFVHjA44BJy9XF8FItV0xoNBFuBvagBvFtToIdQC2m2gnggEUP3zlpDJJZff3nJAYE3x650NB2fqY27xBtUjq56j4XzmsVJcpk9+CLdiytf5QR5br/6xduw9XVhV56eMf856/SrG7OtESr0Mgq/h6+mCKf8AySCCqsb8IsChz986VKfpOiPNQdgySWJJQjj+Wr+uET/0+EXfqdUAi8ttFUPW/wBs9J+CkSacpIYw4rcqgUKJ/v8A50yRCN3MKSiR0Hiur+JxOWS+GNQj+HC0eg7ELhu/n1AqwRG2361zznV0K6OMEwIyrXBZCN31xhNFotkhkWJFQkmRTsIvk8gjOezRpLK2jabUIQNwY8D4Mefljbb7KUUuh0d2yGRiQqjlmKqB88samgkkOmLjnxOKtfa/0+GIt2c0yB3VBbWIiQPTyrz+OVq4N08WjdwZGDMSrkFPMEfbr1rJKGZta66hI5W2KgG81wPl6YvqdTqJ52TTzgRbafcpDA15Uef198Ql0ba1wkWpd4UFb1k5IvzIs+uUp0nZx7lQ8ii6pqVvMm/jjEdOGSDT7YxqDuRTvVQLXmgD5Dy882i/hZXaNRH3n5mJNsfh0vEI9S5gvSPFp4uo2xkkc+XP9s2sMTNvllM7ji2ctQ+B4GIY9JqV2IG2tMrAAKbHS7NcYvP2rEvheNfGL5sKff36fD44CSFY2kmjERViLV1oX6Akcg0cT7p5Eld1jQMWYRlr2+o9B1xgK6uL8VMkUDmBlA2AAEPz1Hv5+vXk4R4e0YZIoX7QlCN/Ursf8+ePLs0+jjMktCS+7qgA3UAmrHPmMVCLKhc6qXUzs/J3GlF+XNDoPrgI7PZQMMVzztqZK4NAADr/AJf/ABm+xO8h7VkjkbxOCWO9jfmKF16+Q64nHG2jdNlhQQWXcTY+OPayBtQkeuiDI0JDd0XG1wp8iP8AOmACvaUBTtB+8VgGN2f7YEq4Rbibkmiprn1vOh2u8epKyg7ivPK8V5f3zmCZCviII685yzesuCdq4CSzrt7sR7ePzsbIvy9Rg9P3u25fH/T5fHqMzI8dcsTXkvGaZ0MYTcVA5AZbydg3EexNZJqIWhd3ZowPED5HOj31MUQ2fP4/HPM9hyFNSyO22Jhb89a6Z6rTzaExljJGR15Ju/InjNMsKlwVFJrkwgI3M8gjUiyOL+WG/EMFHcwAgebGvv8APBSTq1IFBVQDa+EE/wCeuCkmSNQ3eqqg2LBpfp8ucUGl2VVdGjqVSZVlBZ2IO2JaJ5oWa+2Y1PaEqxhkaOnk7vuwKbg9SLv/AKzn63XHUudOJIIogBRVQpIFnj0xzT6rSl2ZZ41kcU2w2xNepqx0zRyS5SIG1kliAm7rao8nA6fLp9s1qNaO7aaVRGVHHnR48vfMS6+NH7pYi+0+MgEjqDZFfH6HBDUtK0kkv/1x9N4JIP2qsyeWX4PgTm18uo1PdwwNu3cyGwK8ufhjOii1faCnYuw8sa9PTn/P7acxiaNZJEZf9C1uP1xnRT/hYrjYIHo+I+I8fCr5+2VGSb5Dlj0kcUGopY+7WggAHXi+T64XvY3C7VtmJVVJ/XE5dXJ3VN3bybh6sD8vIe2Uu3VQRvqpEQAtYUUo58ieR0vn0zdNdIVM6GmgImkEezwtZvmr6i/f5YdX7kqZ9q34fCOMQc7m3RmUowsbQR/nzrF59jGN2alRj19a6/8AXrjsaR1ZZGiiLIVZuviNAYkvae/TGRUty4H8Kru649RiwSfWKUZjFGK2uLPTkEX+uD0mqOl1Emk1pDsTujdEA7weteowTHR12SJgsUu0vMbKnzNfsMVmkEsrRRujjcAIia44vaB1zm63RRS61dTMk4UpYlhAa/YijXFc1l7o49M66aRp4U6iMpvAr84rrXy6YWKh2XTO0rxs6lXQi3KkpYAAAscdcJo4W7+RZDH3agIu0jddef2zlrrBFpm1P4B96DrIQHZR1IFcDnFJe1tP3cNNI00jl3VEsC/h7eXtgAz26FCyourCSRrvVFUC16EEj3zi6TTTbQ7Mp3Hav8QKT0v49RnQ0mm00mvT8XIzvIbRUBAryJI+GdFtFHDqEj06vGsZLkhTsI8wTfP7YrodCUSQwuhMb7buRPfn83p+2N6cPNIjLwvl6/LCrGXO9o0Qo/8ADZAQdvvzySMIYhe1GCpxzfAPmPXAYlqLEdKVk3ixagsea8wa4vFnkCyBgbI6cWMZU952xJAylxACe8PU35ffL1sLxyQxaaKMRA229j0PsPP74CNaGK2lVhECRaAg+A+3Px6e+CKtpZHU6aecuSe9b8o/8Qev+Vj3dpp9MZfE8ha6AN8ceH9cS0z6pi5ZUZFJZN0gDEf58MYEPaCxadgdocD8r2TXx86Hwzp9kaoaqTu4wm3ug4UEEm2brV+g4xM6V9XrAV8Xd1tV1IqwCeoqx/z6XjUJOkSeVFto6vYhDEgm16V1HT/nAQV9JJvJlQCIkKFB4Hl/YceV5x5NOIpSN1C+a4++ekTVFpGSV4zdBAgvy5s379KwbRrLbhP/AOQCRkTja4Dh9nmiu2QKqMxuxZ587GGCSzqF2stcnpePtJGLMnhZTyAentz0xSbV6eWQgIHA8yfXOXa+kVSo8bpGH42MMu4XyL656XQmPcI1iri+vH0yZM6sxMAOs1jQSGBQbut4Nfb0zlajtKQuwVaWyNpN8f4cmTFjiiZN2c+SV5HLObJzUE7wSCSI7WHQ5MmbUI3HqXSXcQG5shuhzvabtFptNHJJEjc7SCPQXYPUdMmTMskU0NMek1fcdnGSNNoILKqmtvAPz65mZ5I9ihyTtDi+goenzyZM54pGiNOuzcCbAND6efrj40cZSM80niUEkgGvj6ZMma4hyIDJTjvWtV3fPj7dfrgIoSFWaRg4UWI6IWyavrZ+ZyZM1JA67Uy6N21asWVqVoieCTZv5YPUtqNUjuJI4jpk7xSiGz7WTkyYANaztCbQLQqQ+pJ9MS0ixLE2qaPc4dSeeCSfh5eWTJiQzsjRmd2iEpRbI4Hoa49PlgV7H0+gkZ3/AIxuxuFV/l5MmAzGpG3UiRFRGL8EIPbr69c0ZG1EsGlf8kh3P70Ca/TJkxiY9tRYndl3lRfOJrqH1tw/kCFfF1PkePpkyYMQyirG5ES7N7EtzyTfU5uTQwWhZd1HcLA4N9emTJjEYh0o3ud1lCQhIsqD1AJx7uqsbmIHl7V0yZMAOeZ+6RQq0DI0Zonir5HpmoiUCk8r/T0APH25yZMQy0hjimcRDY0xBcjp0rp98egjM4jVm5SO7+fH0yZMokQ7TgV37xuSQSffn984+l0KpPMO8bljRXihxxkyZw5ZNSdAkf/Z","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[695,48],"lineTo":[954,222],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[817,216],"lineTo":[1099,443],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[817,216],"lineTo":[1099,443],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[817,216],"lineTo":[1099,443],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[275,187],"path":[[275,188],[274,191],[274,193],[274,195],[273,197],[273,199],[273,200],[273,201],[274,202],[275,202],[277,200],[278,198],[279,196],[279,195],[279,193],[279,192],[278,191],[276,191],[274,191],[272,193],[271,195],[271,198],[271,200],[272,202],[274,204],[277,205],[280,205],[282,205],[283,203],[284,200],[285,198],[285,194],[284,191],[282,190],[280,189],[278,189],[275,191],[274,194],[274,197],[274,198],[274,199],[275,199],[278,199],[281,199],[283,197],[284,194],[285,192],[285,190],[285,189],[284,188],[282,188],[278,189],[275,191],[275,194],[274,194],[274,193],[274,192],[274,190],[275,188]],"range":[[271,188],[285,205]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[275,187],"path":[[275,188],[274,191],[274,193],[274,195],[273,197],[273,199],[273,200],[273,201],[274,202],[275,202],[277,200],[278,198],[279,196],[279,195],[279,193],[279,192],[278,191],[276,191],[274,191],[272,193],[271,195],[271,198],[271,200],[272,202],[274,204],[277,205],[280,205],[282,205],[283,203],[284,200],[285,198],[285,194],[284,191],[282,190],[280,189],[278,189],[275,191],[274,194],[274,197],[274,198],[274,199],[275,199],[278,199],[281,199],[283,197],[284,194],[285,192],[285,190],[285,189],[284,188],[282,188],[278,189],[275,191],[275,194],[274,194],[274,193],[274,192],[274,190],[275,188]],"range":[[271,188],[285,205]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[278,199],"path":[[279,199],[284,201],[294,207],[314,218],[355,238],[396,256],[441,275],[502,297],[559,317],[615,337],[660,354],[696,367],[722,376],[745,382],[756,385],[762,386],[766,388],[769,388],[773,388],[778,388],[783,388],[787,387],[791,386],[796,386],[799,385],[800,384],[801,383],[803,383],[805,381],[807,381],[808,380],[808,379],[809,379],[812,378],[813,378],[814,378],[814,377],[815,377],[816,377],[817,376],[817,375]],"range":[[279,199],[817,388]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[278,199],"path":[[279,199],[284,201],[294,207],[314,218],[355,238],[396,256],[441,275],[502,297],[559,317],[615,337],[660,354],[696,367],[722,376],[745,382],[756,385],[762,386],[766,388],[769,388],[773,388],[778,388],[783,388],[787,387],[791,386],[796,386],[799,385],[800,384],[801,383],[803,383],[805,381],[807,381],[808,380],[808,379],[809,379],[812,378],[813,378],[814,378],[814,377],[815,377],[816,377],[817,376],[817,375]],"range":[[279,199],[817,388]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[768,354],"path":[[769,354],[770,355],[775,357],[779,359],[784,361],[791,365],[797,369],[803,372],[806,374],[807,376],[809,377],[809,378],[809,379],[810,380],[810,381],[811,381],[811,382],[812,382],[813,382],[814,382],[813,382],[806,384],[803,386],[799,389],[795,392],[790,396],[787,398],[784,399],[782,401],[778,401],[774,402],[771,402],[769,402],[767,403],[766,403],[766,404],[766,405],[765,405],[765,406],[764,407],[763,408],[762,410],[762,411],[762,413],[762,414],[762,416]],"range":[[762,354],[814,416]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[768,354],"path":[[769,354],[770,355],[775,357],[779,359],[784,361],[791,365],[797,369],[803,372],[806,374],[807,376],[809,377],[809,378],[809,379],[810,380],[810,381],[811,381],[811,382],[812,382],[813,382],[814,382],[813,382],[806,384],[803,386],[799,389],[795,392],[790,396],[787,398],[784,399],[782,401],[778,401],[774,402],[771,402],[769,402],[767,403],[766,403],[766,404],[766,405],[765,405],[765,406],[764,407],[763,408],[762,410],[762,411],[762,413],[762,414],[762,416]],"range":[[762,354],[814,416]],"pageSize":0.9591666666666666}]');
INSERT INTO plan_content_paths (path_id, con_id, can_path) VALUES (6, 2, null);
INSERT INTO plan_content_paths (path_id, con_id, can_path) VALUES (7, 3, null);
INSERT INTO plan_content_paths (path_id, con_id, can_path) VALUES (8, 4, null);


#커뮤니티더미
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
                                                                                                                                                              ('user01', 1, '서울 여행지 추천해주세요!', '서울에서 반드시 가봐야 할 여행지를 추천해주세요!', '서울', 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user02', 1, '강원도 여행 계획 중인데요!', '강원도에서 꼭 가봐야 할 곳과 추천 숙소가 있으신가요? 공유 부탁드립니다.', '강원', 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0),
                                                                                                                                                              ('user03', 2, '인천에서 맛집 찾기!', '인천에서 유명한 맛집이나 먹을 만한 곳 추천 부탁드립니다.', '인천', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user01', 3, '서울 여행지 추천해주세요!', '서울에서 반드시 가봐야 할 여행지를 추천해주세요!', '서울', 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user02', 4, '강원도 여행 계획 중인데요!', '강원도에서 꼭 가봐야 할 곳과 추천 숙소가 있으신가요? 공유 부탁드립니다.', '강원', 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0),
                                                                                                                                                              ('user03', 1, '인천에서 맛집 찾기!', '인천에서 유명한 맛집이나 먹을 만한 곳 추천 부탁드립니다.', '인천', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user01', 2, '서울 여행지 추천해주세요!', '서울에서 반드시 가봐야 할 여행지를 추천해주세요!', '서울', 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user02', 1, '강원도 여행 계획 중인데요!', '강원도에서 꼭 가봐야 할 곳과 추천 숙소가 있으신가요? 공유 부탁드립니다.', '강원', 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0),
                                                                                                                                                              ('user03', 1, '인천에서 맛집 찾기!', '인천에서 유명한 맛집이나 먹을 만한 곳 추천 부탁드립니다.', '인천', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user01', 1, '서울 자유여행코스 추천', '서울에 3박 4일로 자유여행을 계획중입니다. 추천해주시는 코스나 맛집이 있다면 알려주세요!', '서울', 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user02', 2, '인천 간만에 데이트코스', '최근 바쁜 일상에 지쳐있던 제 남자친구와 함께 인천에 데이트를 갔습니다. 여러분께 추천드릴만한 장소들을 소개해드릴게요!', '인천', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user03', 3, '경기도 캠핑장 추천해주세요', '경기도 지역에서 가족과 함께 캠핑을 계획중입니다. 추천해주실만한 캠핑장이 있다면 알려주세요!', '경기', 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                                                                                                                                              ('user04', 4, '강원도 드라이브 코스 추천', '강원도로 드라이브를 갈 예정입니다. 추천해주실만한 드라이브 코스나 경치 좋은 장소가 있다면 알려주세요!', '강원', 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);

#판매글 데이터
INSERT INTO sells (u_id, area, title, content, category, qnt, img_main)
VALUES
    ('user01', '서울', '판매글 제목1', '판매글 내용1', '워터', 5, '/public/img/sells/1681824976260_1751.jpeg'),
    ('user02', '인천', '판매글 제목2', '판매글 내용2', '테마', 3, '/public/img/sells/1681825094140_3117.png'),
    ('user03', '대전', '판매글 제목3', '판매글 내용3', '키즈', 1, '/public/img/sells/1681826322331_3834.jpeg'),
    ('user04', '광주', '판매글 제목4', '판매글 내용4', '레저', 8, '/public/img/sells/1681827855970_8530.jpeg'),
    ('user05', '대구', '판매글 제목5', '판매글 내용5', '박물관', 2, '/public/img/sells/1681827855974_6437.jpeg'),
    ('user06', '울산', '판매글 제목6', '판매글 내용6', '워터', 4, '/public/img/sells/1681827855978_9700.png'),
    ('user07', '부산', '판매글 제목7', '판매글 내용7', '테마', 3, '/public/img/sells/1681831564266_3265.jpeg'),
    ('user08', '세종', '판매글 제목8', '판매글 내용8', '키즈', 2, '/public/img/sells/1681831564272_5576.jpeg'),
    ('user09', '경기', '판매글 제목9', '판매글 내용9', '레저', 5, '/public/img/sells/1681831564275_4220.jpeg'),
    ('user10', '강원', '판매글 제목10', '판매글 내용10', '박물관', 1, '/public/img/sells/1681864171471_7382.jpeg'),
    ('user01', '충북', '판매글 제목11', '판매글 내용11', '워터', 3, '/public/img/sells/1681865519206_4470.jpeg'),
    ('user02', '충남', '판매글 제목12', '판매글 내용12', '테마', 6, '/public/img/sells/1681868521560_8515.jpeg'),
    ('user03', '전북', '판매글 제목13', '판매글 내용13', '키즈', 4, '/public/img/sells/1681868654202_3174.jpeg'),
    ('user04', '전남', '판매글 제목14', '판매글 내용14', '레저', 7, '/public/img/sells/1682065508712_4507.jpeg'),
    ('user05', '경북', '판매글 제목15', '판매글 내용15', '박물관', 2, '/public/img/sells/1682065508720_8121.jpeg'),
    ('user06', '경남', '판매글 제목16', '판매글 내용16', '워터', 5, '/public/img/sells/1682065508723_666.jpeg'),
    ('user07', '제주', '판매글 제목17', '판매글 내용17', '테마', 1, '/public/img/sells/1682152239247_5163.png'),
    ('user07', '제주', '판매글 제목17', '판매글 내용17', '키즈', 1, '/public/img/sells/1682234090230_1592.png'),
    ('user07', '제주', '판매글 제목171', '판매글 내용17', '레저', 1, '/public/img/sells/1682234803268_3166.png'),
    ('user07', '제주', '판매글 제목171', '판매글 내용17', '테마', 1, '/public/img/sells/1682234803271_4526.png');

#판매글 북마크 더미
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (11, 5, 'USER02');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (10, 4, 'USER02');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (9, 3, 'USER02');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (8, 2, 'USER02');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (7, 1, 'USER02');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (6, 6, 'USER01');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (5, 5, 'USER01');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (4, 4, 'USER01');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (3, 3, 'USER01');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (2, 2, 'USER01');
INSERT INTO sell_bookmarks (sb_id, s_id, u_id) VALUES (1, 1, 'USER01');



INSERT INTO `sell_options` (`s_id`, `name`, `price`, `stock`)

VALUES
    (1, '성인', '10000', 20),
    (1, '청소년', '8000', 30),
    (1, '소인', '5000', 10);

#가보자고 게시글 더미
INSERT INTO trips (u_id, title, area, address, phone, url_address, content,
                   istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj,
                   esfp, entj, entp, enfj, enfp, category)
VALUES
    ('user10', '제주에서 즐기는 봄꽃 여행', '제주', '서귀포', '010-1234-5648', 'https://www.visitjeju.net/kr/','제주의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '힐링'),
    ('user10', '서울에서 즐기는 봄꽃 여행', '서울', '여의도', '010-1111-2262', 'https://www.visitjeju.net/kr/','서울의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, '체험'),
    ('user10', '대전에서 즐기는 봄꽃 여행', '대전', '동구', '010-1234-5689', 'https://www.visitjeju.net/kr/','대전의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, '박물관'),
    ('user10', '강원도에서 즐기는 봄꽃 여행', '강원', '속초', '010-5555-5355', 'https://www.visitjeju.net/kr/','강원도의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, '힐링'),
    ('user10', '인천에서 즐기는 봄꽃 여행', '인천', '강화도', '010-8888-8688', 'https://www.visitjeju.net/kr/','인천의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, '반려동물'),
    ('user10', '부산에서 즐기는 봄꽃 여행', '부산', '목포', '010-2222-3233', 'https://www.visitjeju.net/kr/','부산의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, '레저'),
    ('user10', '대전에서 즐기는 봄꽃 여행', '대전', '동구', '010-7777-7767', 'https://www.visitjeju.net/kr/','대전의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, '레저'),
    ('user10', '강원도에서 즐기는 봄꽃 여행', '강원', '속초', '010-1234-5577', 'https://www.visitjeju.net/kr/','강원도의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, '체험'),
    ('user10', '인천에서 즐기는 봄꽃 여행', '인천', '강화도', '010-9999-1999', 'https://www.visitjeju.net/kr/','인천의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, '반려동물'),
    ('user10', '부산에서 즐기는 봄꽃 여행', '부산', '목포', '010-7777-7676', 'https://www.visitjeju.net/kr/','부산의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, '힐링');


#가보자고 이미지 더미
INSERT INTO trip_imgs (t_id,img_path,img_main)
VALUES     (1,'/public/img/trip/1682991537652_8766.jpeg',0),
           (1,'/public/img/trip/1682991537657_1469.jpeg',1),
           (2,'/public/img/trip/1682991960226_812.jpeg',0),
           (2,'/public/img/trip/1682992699680_4827.jpeg',1),
           (3,'/public/img/trip/1682992156231_8432.jpeg',0),
           (3,'/public/img/trip/1682992464779_3093.jpeg',1),
           (4,'/public/img/trip/1682991883211_9166.jpeg',0),
           (4,'/public/img/trip/1682992753414_6747.jpeg',1),
           (5,'/public/img/trip/1682992604328_4316.jpeg',0),
           (5,'/public/img/trip/1682993272526_8304.jpeg',1),
           (6,'/public/img/trip/1682992925227_3008.jpeg',0),
           (6,'/public/img/trip/1682992925229_7429.jpeg',1),
           (7,'/public/img/trip/1682993008165_4123.jpeg',0),
           (7,'/public/img/trip/1682993008165_4123.jpeg',1),
           (8,'/public/img/trip/1682993049138_5535.jpeg',0),
           (8,'/public/img/trip/1682993049139_8399.jpeg',1),
           (9,'/public/img/trip/1682993319728_4765.jpeg',0),
           (9,'/public/img/trip/1682993319731_6436.jpeg',1),
           (10,'/public/img/trip/1682993396795_6993.jpeg',0),
           (10,'/public/img/trip/1682993396796_8667.jpeg',1),
           (11,'/public/img/trip/1682993532461_6842.jpeg',0),
           (11,'/public/img/trip/1682993532464_8286.jpeg',1)

       ;

#가보자고 북마크 더미
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (1, 1, 'USER01');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (2, 2, 'USER01');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (3, 3, 'USER01');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (4, 4, 'USER01');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (5, 5, 'USER01');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (6, 6, 'USER01');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (7, 1, 'USER02');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (8, 2, 'USER02');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (9, 3, 'USER02');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (10, 4, 'USER02');
INSERT INTO trip_bookmarks (tb_id, t_id, u_id) VALUES (11, 5, 'USER02');


#가보자고 리뷰 데이터
INSERT INTO trip_reviews (t_id, u_id, content, visit, grade)
VALUES
    (1, 'user01', '이곳이 정말 멋진 곳이에요!', 1, 5),
    (1, 'user02', '여기는 가보자고 추천해준 곳 중에서 제일 좋았어요.', 1, 4),
    (1, 'user03', '다음에도 꼭 다시 방문하고 싶은 곳이에요!', 1, 4),
    (1, 'user04', '이곳은 정말 특별한 경험이었어요.', 1, 5),
    (1, 'user05', '여기는 앞으로도 자주 찾게 될 것 같아요.', 1, 3),
    (2, 'user01', '이곳이 정말 멋진 곳이에요!', 1, 3),
    (2, 'user02', '여기는 가보자고 추천해준 곳 중에서 제일 좋았어요.', 1, 2),
    (2, 'user03', '다음에도 꼭 다시 방문하고 싶은 곳이에요!', 1, 4),
    (2, 'user04', '이곳은 정말 특별한 경험이었어요.', 1, 5),
    (2, 'user05', '여기는 앞으로도 자주 찾게 될 것 같아요.', 1, 5);

#팔로우 더미
INSERT INTO follows(to_users, from_users)
VALUES
    ('user01', 'user02'), ('user01', 'user03'), ('user01', 'user04'), ('user01', 'user05'), ('user01', 'user06'),
    ('user02', 'user01'), ('user02', 'user03'), ('user02', 'user05'), ('user02', 'user06'), ('user02', 'user07'),
    ('user03', 'user01'), ('user03', 'user04'), ('user03', 'user05'), ('user03', 'user06'), ('user03', 'user07'),
    ('user04', 'user01'), ('user04', 'user03'), ('user04', 'user05'), ('user04', 'user07'), ('user04', 'user08'),
    ('user05', 'user01'), ('user05', 'user03'), ('user05', 'user04'), ('user05', 'user07'), ('user05', 'user10'),
    ('user06', 'user02'), ('user06', 'user03'), ('user06', 'user04'), ('user06', 'user05'), ('user06', 'user07'),
    ('user07', 'user02'), ('user07', 'user05'), ('user07', 'user06'), ('user07', 'user08'), ('user07', 'user10'),
    ('user08', 'user04'), ('user08', 'user05'), ('user08', 'user06'), ('user08', 'user09'), ('user08', 'user10'),
    ('user09', 'user03'), ('user09', 'user05'), ('user09', 'user06'), ('user09', 'user07'), ('user09', 'user08'),
    ('user10', 'user01'), ('user10', 'user02'), ('user10', 'user05'), ('user10', 'user08'), ('user10', 'user09');

#커뮤니티 이미지 더미
INSERT INTO  comm_imgs (c_id, img_path, img_main)
VALUES
    (1,'/public/img/comm/1.jpg',0),
    (2,'/public/img/comm/2.jpg',0),
    (3,'/public/img/comm/3.jpg',0),
    (4,'/public/img/comm/4.jpg',0),
    (5,'/public/img/comm/1.jpg',0),
    (6,'/public/img/comm/2.jpg',0),
    (7,'/public/img/comm/3.jpg',0),
    (8,'/public/img/comm/4.jpg',0),
    (9,'/public/img/comm/1.jpg',0),
    (10,'/public/img/comm/2.jpg',0),
    (11,'/public/img/comm/3.jpg',0),
    (12,'/public/img/comm/4.jpg',0),
    (13,'/public/img/comm/1.jpg',0);

#가보자고 리뷰 댓글 데이터
INSERT INTO trip_review_comments (tr_id, u_id, content, status, parent_trc_id, gabojagoPlan.trip_review_comments.img_path)
VALUES
    (1, 'user01', '가보자고리 재미있었어요!', 'PUBLIC', NULL, NULL),
    (1, 'user02', '뷰가 너무 아름다웠어요!', 'PUBLIC', NULL, NULL),
    (2, 'user03', '다음에도 꼭 가보고 싶어요!', 'PUBLIC', NULL, NULL),
    (2, 'user04', '추천하는 가보자고리 루트가 있나요?', 'PUBLIC', NULL, NULL),
    (2, 'user05', '가보자고리 최고!', 'PUBLIC', NULL,'/public/img/trip/reviewcmt/1682306538230_44726.jpeg'),
    (2, 'user06', '재밌는 추억이었어요!', 'PUBLIC', NULL, NULL),
    (1, 'user07', '뷰가 너무 아름다워요!', 'PUBLIC', NULL,'/public/img/trip/reviewcmt/1682306538230_44726.jpeg'),
    (2, 'user08', '가보자고리 루트 추천해요!', 'PUBLIC', NULL, NULL),
    (2, 'user09', '다음에도 꼭 가보고 싶어요!', 'PUBLIC', NULL, NULL),
    (2, 'user10', '가보자고리 가는 것을 추천합니다!', 'PUBLIC', NULL, NULL);

#가보자고 리뷰 이미지 더미
INSERT INTO  trip_review_imgs (tri_id, tr_id, img_path)
VALUES
    (1, 1,'/public/img/trip/review/1682515807735_21871.jpeg'),
    (2, 1,'/public/img/trip/review/1682515807737_95047.jpeg'),
    (3, 2,'/public/img/trip/review/1682515830783_32065.jpeg'),
    (4, 3,'/public/img/trip/review/1682515851848_23613.jpeg');

#같이놀자 북마크 더미
INSERT INTO comm_bookmarks (c_id, u_id,p_id)
VALUES
    (1, 'user01',1),
    (2, 'user01',1),
    (3, 'user01',2),
    (4, 'user01',3),
    (1, 'user02',1),
    (2, 'user02',1),
    (3, 'user02',2),
    (4, 'user02',3),
    (4, 'user03',3),
    (4, 'user04',3);

#같이놀자 좋아요 더미
INSERT INTO comm_likes (c_id, u_id)
VALUES
    (1, 'user01'),
    (1, 'user02'),
    (1, 'user03'),
    (1, 'user04'),
    (2, 'user05'),
    (2, 'user06'),
    (3, 'user07'),
    (3, 'user08'),
    (4, 'user09'),
    (4, 'user10');

#해시태그
INSERT INTO hashtags_new(tag)
VALUES ('홍대'),
       ('홍대놀이터'),
       ('홍대맛집'),
       ('홍대입구'),
       ('홍대카페'),
       ('홍대애견'),
       ('food'),
       ('travel'),
       ('music'),
       ('fashion'),
       ('photography'),
       ('한국'),
       ('일본'),
       ('미국'),
       ('유럽'),
       ('인테리어'),
       ('뷰티'),
       ('운동'),
       ('영화'),
       ('꽃'),
       ('동물'),
       ('일상'),
       ('여름'),
       ('가을'),
       ('겨울'),
       ('봄'),
       ('풍경'),
       ('먹방'),
       ('먹심'),
       ('먹보'),
       ('먹짱'),
       ('카페'),
       ('선팔'),
       ('소통'),
       ('셀카'),
       ('스타일'),
       ('축구'),
       ('야구'),
       ('농구'),
       ('배구'),
       ('테니스'),
       ('골프'),
       ('스키'),
       ('수영'),
       ('춤'),
       ('노래'),
       ('기타'),
       ('피아노'),
       ('드라마'),
       ('해외여행'),
       ('국내여행'),
       ('육아'),
       ('공부'),
       ('일'),
       ('금요일'),
       ('토요일'),
       ('일요일'),
       ('월요일'),
       ('화요일'),
       ('수요일'),
       ('에이콘아카데미'),
       ('에이콘'),
       ('acornacademy'),
       ('목요일');

INSERT INTO trip_hashtags (t_id, tag)
VALUES (1, '홍대'),
       (2, '홍대'),
       (3, '홍대'),
       (4, '홍대'),
       (5, '홍대'),
       (7, '홍대'),
       (10, '홍대'),
       (1, '홍대맛집'),
       (1, '한국'),
       (1, 'food'),
       (1, 'travel'),
       (1, '먹심'),
       (2, '홍대놀이터'),
       (2, '홍대맛집'),
       (2, '수요일');
