
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
                        `status` boolean COMMENT '답변 상태',
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
                              `status` boolean COMMENT '답변 상태',
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
                              use_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '사용시간',
                              FOREIGN KEY (sod_id) REFERENCES sell_order_details(sod_id) ON DELETE CASCADE ON UPDATE CASCADE
);

#출석체크 테이블
CREATE TABLE attendance_check (
                              d_id INT AUTO_INCREMENT PRIMARY KEY COMMENT'출석체크인덱스',
                              u_id VARCHAR(255) NOT NULL COMMENT'유저아이디',
                              u_date DATE DEFAULT (CURRENT_DATE) COMMENT'현재날짜',
                              FOREIGN KEY (u_id) REFERENCES users(u_id) ON DELETE CASCADE ON UPDATE CASCADE
);


#유저더미
INSERT INTO users (u_id, pw, name, nk_name, email, birth, phone, address, detail_address, pr_content, permission, mbti, img_path, store_name, business_id)
VALUES
    ('admin', 'admin123', '관리자', '관리자', 'admin@example.com', '1990-01-01', '010-1234-5523', '서울특별시 강남구', '삼성동 123-45', '안녕하세요. 저는 관리자입니다.', 'ADMIN', 'ISTJ', '/public/img/user/profile.jpg', NULL, NULL),
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

INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (1, 1, null, null, 1, '한라산국립공원', '관음사탐방로 코스, 숙소에서 30분, 우의 꼭 챙겨 가야됨', '06:00 ~ 12:00', '/public/img/plan/1682956286675_1180.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (2, 1, null, null, 1, '선녀 물회', '내려올 때 테이블링 미리 하기!', '14:00 ~ 15:00', '/public/img/plan/1682956286715_9198.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (3, 1, null, null, 1, '함덕 해수욕장', '제주도 시외버스터미널 → 동회선 시외버스 이용(30분)', '19:00 ~ 23:00', '/public/img/plan/1682956286732_4611.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (4, 1, null, null, 2, '서귀포 어시장', '☆☆☆방어 필수☆☆☆', '06:00 ~ 08:00', '/public/img/plan/1682684497260_3031.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (5, 1, null, null, 2, '천지연 폭포', '풍경이 멋진 언덕에서 인생샷 남기기', '08:00 ~ 10:00', '/public/img/plan/1682684497260_3031.png');
INSERT INTO gabojagoplan.plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (6, 1, null, null, 2, '오설록 차 박물관', '오설록 티 뮤지엄에서 분위기 있게 차 한잔', '10:00 ~ 14:00', '/public/img/plan/1682684497260_3031.png');


INSERT INTO gabojagoplan.plan_content_paths (path_id, con_id, can_path) VALUES (1, 1, '[{"type":"stamp","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[60.46915725456125,37.53258036490009],"lineTo":[568.2015638575152,467.07211120764555],"src":"/public/img/plan/postIt2.png","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[109.47002606429193,90.70373588184188],"lineTo":[474.370112945265,347.1763683753258],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[763.1624674196352,42.74543874891399],"lineTo":[1144.7437011294528,379.496090356212],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"img","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[763.1624674196352,42.74543874891399],"lineTo":[1144.7437011294528,379.496090356212],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},{"type":"stamp","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[812.1633362293658,170.98175499565596],"lineTo":[868.4622067767159,245.00434404865337],"src":"/public/img/plan/marker.png","range":[],"pageSize":0.9591666666666666},{"type":"stamp","strokeStyle":"#808080","fillStyle":"#000000","lineWidth":2,"scale":1,"moveTo":[941.4422241529105,144.91746307558645],"lineTo":[973.7619461337968,201.2163336229366],"src":"/public/img/plan/marker.png","range":[],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffffff","fillStyle":"#ffffff","lineWidth":0.5,"scale":1,"moveTo":[583.840139009557,267.94092093831455],"path":[[583.840139009557,265.85577758470896],[585.9252823631625,258.5577758470895],[589.0529973935709,252.3023457862728],[595.3084274543875,246.04691572545613],[601.5638575152042,240.83405734144225],[609.9044309296264,236.66377063423113],[621.372719374457,232.49348392702],[629.7132927888792,229.36576889661166],[637.0112945264988,228.32319721980886],[642.2241529105127,227.2806255430061],[647.4370112945265,227.2806255430061],[653.6924413553432,227.2806255430061],[662.0330147697655,228.32319721980886],[672.4587315377933,230.40834057341445],[684.9695916594267,235.62119895742833],[694.3527367506516,239.79148566463945],[699.5655951346655,243.96177237185057],[702.6933101650739,248.1320590790617],[702.6933101650739,249.17463075586448]],"range":[[583.840139009557,227.2806255430061],[702.6933101650739,265.85577758470896]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":0.5,"scale":1,"moveTo":[598.4361424847958,247.08948740225892],"path":[[598.4361424847958,243.96177237185057],[599.4787141615986,239.79148566463945],[600.5212858384015,235.62119895742833],[602.606429192007,232.49348392702],[604.6915725456125,230.40834057341445],[607.8192875760209,228.32319721980886],[611.9895742832321,226.2380538662033],[616.1598609904431,225.19548218940054],[622.4152910512598,222.06776715899218],[630.7558644656821,218.94005212858386],[637.0112945264988,217.89748045178106],[644.3092962641182,217.89748045178106],[650.5647263249349,217.89748045178106],[656.8201563857516,217.89748045178106],[662.0330147697655,217.89748045178106],[665.1607298001738,217.89748045178106],[667.2458731537794,219.98262380538662],[670.3735881841877,223.11033883579498],[673.5013032145961,226.2380538662033],[674.5438748913988,228.32319721980886],[675.5864465682016,230.40834057341445],[676.6290182450044,234.57862728062557],[676.6290182450044,237.7063423110339],[676.6290182450044,241.876629018245],[676.6290182450044,246.04691572545613],[675.5864465682016,250.21720243266725],[672.4587315377933,253.3449174630756],[669.3310165073849,256.47263249348396],[666.2033014769765,259.6003475238923],[662.0330147697655,261.68549087749784],[657.8627280625543,261.68549087749784],[654.735013032146,261.68549087749784],[652.6498696785404,261.68549087749784],[650.5647263249349,260.6429192006951],[648.4795829713294,256.47263249348396],[646.3944396177237,251.25977410947004],[644.3092962641182,246.04691572545613],[644.3092962641182,240.83405734144225],[643.2667245873154,234.57862728062557],[644.3092962641182,227.2806255430061],[647.4370112945265,222.06776715899218],[651.6072980017376,218.94005212858386],[656.8201563857516,215.8123370981755],[663.0755864465682,213.72719374456995],[670.3735881841877,211.64205039096439],[678.71416159861,210.59947871416162],[689.1398783666377,209.55690703735883],[697.48045178106,208.51433536055606],[704.7784535186795,207.47176368375327],[709.9913119026934,207.47176368375327],[714.1615986099044,207.47176368375327],[717.2893136403128,207.47176368375327],[722.5021720243267,208.51433536055606],[727.7150304083406,208.51433536055606],[733.9704604691573,209.55690703735883],[740.225890529974,212.68462206776718],[746.4813205907907,214.76976542137274],[751.6941789748046,216.8549087749783],[757.9496090356213,217.89748045178106],[763.1624674196352,221.02519548218942],[768.375325803649,222.06776715899218],[773.5881841876629,224.15291051259774],[777.7584708948741,225.19548218940054],[780.8861859252825,226.2380538662033],[782.971329278888,227.2806255430061],[786.0990443092963,228.32319721980886],[787.141615986099,228.32319721980886],[789.2267593397047,229.36576889661166],[791.3119026933102,230.40834057341445],[793.3970460469158,230.40834057341445],[795.4821894005213,231.45091225021721],[796.5247610773241,232.49348392702]],"range":[[598.4361424847958,207.47176368375327],[796.5247610773241,261.68549087749784]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":0.5,"scale":1,"moveTo":[598.4361424847958,247.08948740225892],"path":[[598.4361424847958,243.96177237185057],[599.4787141615986,239.79148566463945],[600.5212858384015,235.62119895742833],[602.606429192007,232.49348392702],[604.6915725456125,230.40834057341445],[607.8192875760209,228.32319721980886],[611.9895742832321,226.2380538662033],[616.1598609904431,225.19548218940054],[622.4152910512598,222.06776715899218],[630.7558644656821,218.94005212858386],[637.0112945264988,217.89748045178106],[644.3092962641182,217.89748045178106],[650.5647263249349,217.89748045178106],[656.8201563857516,217.89748045178106],[662.0330147697655,217.89748045178106],[665.1607298001738,217.89748045178106],[667.2458731537794,219.98262380538662],[670.3735881841877,223.11033883579498],[673.5013032145961,226.2380538662033],[674.5438748913988,228.32319721980886],[675.5864465682016,230.40834057341445],[676.6290182450044,234.57862728062557],[676.6290182450044,237.7063423110339],[676.6290182450044,241.876629018245],[676.6290182450044,246.04691572545613],[675.5864465682016,250.21720243266725],[672.4587315377933,253.3449174630756],[669.3310165073849,256.47263249348396],[666.2033014769765,259.6003475238923],[662.0330147697655,261.68549087749784],[657.8627280625543,261.68549087749784],[654.735013032146,261.68549087749784],[652.6498696785404,261.68549087749784],[650.5647263249349,260.6429192006951],[648.4795829713294,256.47263249348396],[646.3944396177237,251.25977410947004],[644.3092962641182,246.04691572545613],[644.3092962641182,240.83405734144225],[643.2667245873154,234.57862728062557],[644.3092962641182,227.2806255430061],[647.4370112945265,222.06776715899218],[651.6072980017376,218.94005212858386],[656.8201563857516,215.8123370981755],[663.0755864465682,213.72719374456995],[670.3735881841877,211.64205039096439],[678.71416159861,210.59947871416162],[689.1398783666377,209.55690703735883],[697.48045178106,208.51433536055606],[704.7784535186795,207.47176368375327],[709.9913119026934,207.47176368375327],[714.1615986099044,207.47176368375327],[717.2893136403128,207.47176368375327],[722.5021720243267,208.51433536055606],[727.7150304083406,208.51433536055606],[733.9704604691573,209.55690703735883],[740.225890529974,212.68462206776718],[746.4813205907907,214.76976542137274],[751.6941789748046,216.8549087749783],[757.9496090356213,217.89748045178106],[763.1624674196352,221.02519548218942],[768.375325803649,222.06776715899218],[773.5881841876629,224.15291051259774],[777.7584708948741,225.19548218940054],[780.8861859252825,226.2380538662033],[782.971329278888,227.2806255430061],[786.0990443092963,228.32319721980886],[787.141615986099,228.32319721980886],[789.2267593397047,229.36576889661166],[791.3119026933102,230.40834057341445],[793.3970460469158,230.40834057341445],[795.4821894005213,231.45091225021721],[796.5247610773241,232.49348392702]],"range":[[598.4361424847958,207.47176368375327],[796.5247610773241,261.68549087749784]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":0.5,"scale":1,"moveTo":[606.7767158992181,199.13119026933103],"path":[[606.7767158992181,200.1737619461338],[605.7341442224154,206.4291920069505],[604.6915725456125,211.64205039096439],[604.6915725456125,215.8123370981755],[604.6915725456125,218.94005212858386],[604.6915725456125,223.11033883579498],[604.6915725456125,226.2380538662033],[604.6915725456125,229.36576889661166],[604.6915725456125,231.45091225021721],[604.6915725456125,234.57862728062557],[604.6915725456125,236.66377063423113],[603.6490008688098,239.79148566463945],[603.6490008688098,241.876629018245],[603.6490008688098,243.96177237185057],[602.606429192007,245.00434404865337],[602.606429192007,246.04691572545613],[601.5638575152042,248.1320590790617],[601.5638575152042,249.17463075586448],[602.606429192007,249.17463075586448],[604.6915725456125,250.21720243266725],[607.8192875760209,250.21720243266725],[609.9044309296264,250.21720243266725],[611.9895742832321,250.21720243266725],[614.0747176368376,250.21720243266725],[617.202432667246,250.21720243266725],[618.2450043440487,250.21720243266725],[619.2875760208515,250.21720243266725],[620.3301476976543,250.21720243266725],[621.372719374457,251.25977410947004],[622.4152910512598,251.25977410947004],[623.4578627280625,252.3023457862728],[625.5430060816682,252.3023457862728]],"range":[[601.5638575152042,200.1737619461338],[625.5430060816682,252.3023457862728]],"pageSize":0.9591666666666666},{"type":"pen","strokeStyle":"#ffa500","fillStyle":"#ffa500","lineWidth":0.5,"scale":1,"moveTo":[606.7767158992181,199.13119026933103],"path":[[606.7767158992181,200.1737619461338],[605.7341442224154,206.4291920069505],[604.6915725456125,211.64205039096439],[604.6915725456125,215.8123370981755],[604.6915725456125,218.94005212858386],[604.6915725456125,223.11033883579498],[604.6915725456125,226.2380538662033],[604.6915725456125,229.36576889661166],[604.6915725456125,231.45091225021721],[604.6915725456125,234.57862728062557],[604.6915725456125,236.66377063423113],[603.6490008688098,239.79148566463945],[603.6490008688098,241.876629018245],[603.6490008688098,243.96177237185057],[602.606429192007,245.00434404865337],[602.606429192007,246.04691572545613],[601.5638575152042,248.1320590790617],[601.5638575152042,249.17463075586448],[602.606429192007,249.17463075586448],[604.6915725456125,250.21720243266725],[607.8192875760209,250.21720243266725],[609.9044309296264,250.21720243266725],[611.9895742832321,250.21720243266725],[614.0747176368376,250.21720243266725],[617.202432667246,250.21720243266725],[618.2450043440487,250.21720243266725],[619.2875760208515,250.21720243266725],[620.3301476976543,250.21720243266725],[621.372719374457,251.25977410947004],[622.4152910512598,251.25977410947004],[623.4578627280625,252.3023457862728],[625.5430060816682,252.3023457862728]],"range":[[601.5638575152042,200.1737619461338],[625.5430060816682,252.3023457862728]],"pageSize":0.9591666666666666}]');

INSERT INTO plan_content_paths (path_id, con_id, can_path) VALUES (6, 2, null);
INSERT INTO plan_content_paths (path_id, con_id, can_path) VALUES (7, 3, null);
INSERT INTO plan_content_paths (path_id, con_id, can_path) VALUES (8, 4, null);
#문의 더미
INSERT INTO qnas (u_id,title,content,file_path)
VALUES
('user04','문의글입니다1','여행사이트에서 제공하는 후기를 신뢰할 수 있을까요?','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다2','여행사이트에서 제공하는 투어를 이용하면서 피해야 할 사항은 무엇인가요?','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다3','여행사이트에서 할인 이벤트를 알려주는 방법은 무엇인가요?','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다4','결제 시 안전한 방법은 무엇인가요?','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다6','여행 상품을 선택할 때 중요한 팁이 있다면 알려주세요!','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다7','계획을 세울 때 가장 필요한 기능은 무엇인가요?','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다8','적립금을 얻는 방법은 무엇인가요?','/public/img/my/16817094242196_2855.jpeg'),
('user04','문의글입니다9','여행사이트에서 제공하는 후기를 신뢰할 수 있을까요?','/public/img/my/16817094242196_2855.jpeg');

INSERT INTO qna_replys (q_id,u_id,content,parent_qna_id)
VALUES
    (1,'user01','답변에 대한 문의입니다.',NULL);

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
    ('user10', '제주', '[제주] 히어로플레이파크 제주 이용권', '더미', '키즈', 2, '/public/img/sells/1682998727973_8328.jpeg'),
    ('user10', '서울', '[가정의달] 마술그리스신화 - 송파 ''올림포스 신''들의 유쾌, 상쾌, 통쾌한 이야기', '<p><img alt="" src="http://img3.tmon.kr/cdn4/deals/2023/01/09/12144041734/summary_fc83d.jpg" style="height:1373px; width:770px" /></p>

<p><img alt="" src="http://img4.tmon.kr/cdn4/deals/2022/07/19/12144041734/summary_542fa.jpg" style="height:3861px; width:770px" /> *</p>
', '테마', 5, '/public/img/sells/1682998207190_8092.jpeg'),
    ('user10', '제주', '[쿠폰할인] 신화월드 테마파크&워터파크', '<p><img alt="" src="https://tourimg.wonderscdn.app/admin/20221230/7/5c8af2f7-572f-4546-a67d-23305ae47f2d.jpg" style="height:1246px; width:1000px" /><img alt="" src="https://tourimg.wonderscdn.app/admin/20230317/3/4a5d7b28-3bc4-411d-aa25-9e716f791fb8.jpg" />&nbsp;&nbsp;*</p>
', '워터', 5, '/public/img/sells/1682912360964_9730.jpeg'),

    ('user10', '경기', '[일산]행유행유 프리미엄 야외수영장', '<p><img alt="" src="https://tourimg.wonderscdn.app/admin/20220614/1/8558a09a-7e34-4de1-9ce2-c8a63de85de2.jpg" style="height:1633px; width:1000px" /></p>

<p>&nbsp;</p>

<h1>이용방법 [티켓 이용 안내]</h1>

<p>1) 가보자고에서 이용날짜 옵션 선택 후 구매</p>

<p>2) 예약일 현장 방문</p>

<p>3) 구매인증 확인후 입장</p>

<p>4) 행휴행유 즐기기!</p>

<p>&nbsp;</p>

<p>* 당일 예약의 경우 오전 9시 이후 가보자고를 통한 예약이 불가합니다.</p>

<p>* 이용을 원하실경우 02-111-2222 으로 연락 부탁드립니다.</p>

<p>* 본 티켓은 좌석예약 티켓으로 입장권은 별도 구매해 주셔야 합니다.</p>

<p>* 1인당 입장권 별도 부과됩니다. (슬라이드 등 이용 시 현장문의) * 좌석 구매 후 당일 현장방문시 티켓 사용처리</p>

<p>* 재판매시 전액 환불 불가 및 이용 불가 하오니 이점 양해 부탁드립니다.</p>

<p>* 5/5~6/18 기간에는 유아풀과 중간풀(0.9m)는 미온수 / 자쿠지는 온수로 운영됩니다.</p>

<p>* 5/5~6/18 기간에는 대수영장 온수 공급이 불가하여 얕은 수심으로 시간대별 보트를 운영합니다.</p>

<p>* 내부사정으로 인해 5/5~5/7 일자는 공사 완료 시 오픈 예정입니다. 환불규정 취소가능여부 : 취소가능 환불규정에 따라 수수료가 발생할 수 있으니 확인해주시기 바랍니다. [환불규정] ▪ 이용 3일전까지 취소 요청시, 100% 환불이 가능합니다.</p>

<p>▪ 현장 중복할인 불가합니다.</p>

<p>▪ 사용한 티켓 환불 불가합니다. ▪ 부분환불 불가합니다. - 당일 취소는 불가합니다. - 일정변경은 이용3일전까지 취소후 재구매 하셔야 합니다. - 취소는 평일기준(월~금) 이용일 3일전 까지 가능하며, 이후 취소는 불가합니다. (티켓 사용처리 후에는 환불불가하며, 휴일의 경우 취소 응대가 불가합니다.) [우천으로 인한 취소 및 날짜변경] - 고양시 기준 호우 강풍 경보 사항을 제외하고는 우천시에도 영업을 하므로, 3일전 환불 규정에 따라 환불 및 날짜변경 불가 합니다.</p>
', '워터', 1, '/public/img/sells/1682907222171_9307.jpeg'),
    ('user10', '경기', '[일산] 원마운트 종일권', '<p><img alt="" src="https://tour.wd.wemakeprice.com/activity/direct/detail/GD230000000080552" /><img alt="" src="https://tourimg.wonderscdn.app/admin/20230331/8/498a457c-3455-486e-a9d9-fde1d86270dc.jpg" style="height:1395px; width:1000px" /></p>

<p><img alt="" src="https://tourimg.wonderscdn.app/admin/20230331/4/141da0c8-48f0-422d-8b27-a613c175167c.jpg" style="height:1339px; width:1000px" /> *</p>', '워터', 1, '/public/img/sells/1682911883176_2180.jpeg'),
    ('user10', '경기', '[특가] 테르메덴 풀앤스파 미들시즌(~6/30)', '<p><img alt="" src="https://tourimg.wonderscdn.app/admin/20230428/3/8f32a283-e840-4e42-b75c-c4dc202ed0f6.jpg" style="height:23375px; width:1000px" /></p>

<p>*</p>
', '레저', 1, '/public/img/sells/1682911459818_3088.jpeg');

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

INSERT INTO sell_imgs (s_id, img_path)
VALUES
    (19,'/public/img/sells/1682911049003_3666.jpeg'),
    (19,'/public/img/sells/1682911049002_495.jpeg'),
    (19,'/public/img/sells/1682911049000_4141.jpeg'),
    (19,'/public/img/sells/1682911048997_8303.jpeg'),
    (15,'/public/img/sells/1682998727966_9047.jpeg'),
    (15,'/public/img/sells/1682998727973_8328.jpeg'),
    (16,'/public/img/sells/1682998207174_6726.jpeg'),
    (16,'/public/img/sells/1682998207190_8092.jpeg'),
    (17,'/public/img/sells/1682912360962_3101.jpeg'),
    (17,'/public/img/sells/1682912360964_9730.jpeg'),
    (18,'/public/img/sells/1682911883176_2180.jpeg'),
    (18,'/public/img/sells/1682911883175_7243.jpeg'),
    (20,'/public/img/sells/1682911459817_7471.jpeg'),
    (20,'/public/img/sells/1682911459818_3088.jpeg');

INSERT INTO `sell_options` (`s_id`, `name`, `price`, `stock`)

VALUES
    (19, '행유행유 성인권', '15000', 999),
    (19, '행유행유 청소년권', '10000', 999),
    (19, '행유행유 유아권(36개월이상)', '5000', 999),
    (15, '주중 기본 소인권', '22800', 999),
    (15, '주말 및 공휴일 기본 소인권', '24000', 999),
    (16, '어린이날 1인 관람권', '19900', 999),
    (17, '신화월드 테마파크 자유이용권', '30000', 999),
    (17, '신화월드 워터파크 종일권', '40000', 999),
    (18, '종일권 1인(대소공통)', '25900', 999),
    (20, '종일권 1인(대소공통)', '26900', 999);

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
           (10,'/public/img/trip/1682993396796_8667.jpeg',1)


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

#공지사항더미
INSERT INTO `notices` (`u_id`, `title`, `content`, `img_path`) VALUES
                                                                                 ('admin', '여름방학 기차여행 이벤트!', '여름방학에 맞춰 기차여행 이벤트를 진행합니다. 대상은 만 12세 이하 어린이와 학생입니다. 이벤트 기간 중에는 30% 할인 혜택도 제공됩니다.', '/images/notice/train_event.jpg'),
                                                                                 ('admin', '해외여행 꿀팁! 호텔 예약할 때 이것만 주의하세요!', '해외여행을 계획하시는 분들에게 호텔 예약할 때 유의해야 할 점을 안내해드립니다. 호텔 예약 시 자세한 정보를 꼭 확인하시기 바랍니다!', '/images/notice/hotel_tips.jpg'),
                                                                                 ('admin', '새로운 루트 추가! 야경 구경하면서 강릉 여행을 떠나세요', '강릉으로 떠나는 여행을 더욱 특별하게 만들어줄 새로운 루트가 추가되었습니다! 야경을 감상하면서 차분한 여행을 즐겨보세요.','/images/notice/gangneung.jpg'),
                                                                                 ('admin', '추석 연휴 즐기기! 가족여행지 추천', '추석 연휴를 맞아 가족들과 함께 떠날만한 여행지를 추천해드립니다. 추석 연휴 기간 동안 다양한 이벤트가 열리니 놓치지 마세요!', '/images/notice/chuseok_trip.jpg'),
                                                                                 ('admin', '오는 추석에 꼭 먹어야 할 음식! 추석 한식 이벤트', '추석 연휴를 맞아 추석 전통 한식을 맛볼 수 있는 이벤트를 진행합니다. 쌀밥, 송편, 전, 고기, 밤 등 다양한 음식을 맛볼 수 있습니다.', '/images/notice/chuseok_food.jpg');

