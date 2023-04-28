
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
#가보자고( 해시태그 테이블)
CREATE TABLE `trip_hashtags` (
                                 `th_id`	 int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '가보자고해시태그 아이디',
                                 `t_id`	int unsigned NOT NULL COMMENT '맞춤추천 아이디',
                                 `tag_id`	int unsigned NOT NULL COMMENT '태그 아이디',
                                 FOREIGN KEY (t_id) REFERENCES trips (t_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                 FOREIGN KEY (tag_id) REFERENCES hashtags (tag_id) ON DELETE CASCADE ON UPDATE CASCADE
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
#상품판매(주문상세 테이블)
#카드정보 sale_date 는 보안상 데이터 베이스에 저장하는건 위험해서 따로 인증절차 방법으로
#카드정보말고 결제를 무엇으로 했는지만 알수있게 info 추가.
CREATE TABLE `sell_details` (
                                `oder_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '주문번호 아이디',
                                `u_id`	varchar(255)NOT NULL COMMENT '유저 아이디',
                                `m_id`	int unsigned NOT NULL COMMENT '마일리지 아이디',
                                `s_id`  int unsigned  COMMENT '판매글 아이디',
                                `price`	varchar(255)	COMMENT '결제 금액',
                                `sale_date`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '결제일',
                                `info` varchar(255) COMMENT '결제 정보',
                                `use_date`	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사용일자',
                                `use_check`	BOOLEAN DEFAULT FALSE COMMENT '사용여부',
                                FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (m_id) REFERENCES mileages (m_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                FOREIGN KEY (s_id) REFERENCES sells(s_id)ON DELETE SET NULL ON UPDATE CASCADE
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
#제약조건 추가 1개만북마크 가능하게

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



#환불 페이지
CREATE TABLE `sell_refunds` (
                                `refund_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '환불 아이디',
                                `oder_id`	int unsigned NOT NULL COMMENT '주문번호 아이디',
                                `u_id`	varchar(255) NOT NULL COMMENT '유저 아이디',
                                `refund_check` BOOLEAN COMMENT '환불여부',
                                `refund_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '환불일자',
                                FOREIGN KEY (oder_id) REFERENCES sell_details (oder_id)ON UPDATE CASCADE ON DELETE CASCADE ,
                                FOREIGN KEY (u_id) REFERENCES users (u_id)  ON UPDATE CASCADE ON DELETE CASCADE
);
#장바구니
CREATE TABLE `sell_carts` (
                              `cart_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '장바구니 아이디',
                              `u_id`	varchar(255) NOT NULL COMMENT '작성자 아이디',
                              `s_id`	int unsigned NOT NULL COMMENT '옵션 아이디',
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
                             option_name VARCHAR(255) NOT NULL COMMENT '구매옵션 이름',
                             price INT NOT NULL COMMENT '구매옵션 가격',
                             post_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '구매 일시',
                             FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (s_id) REFERENCES sells (s_id) ON DELETE SET NULL ON UPDATE CASCADE
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
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(3, 'user02', '놀러가자', '언제놀러가지', '2023-05-01', '2023-05-03', '/public/img/plan/p1Sample.jpg', 'PUBLIC', false);
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(4, 'user04', '제주도 한 달 살기', '나의 워라벨을 위함', '2023-02-01', '2023-03-03', '/public/img/plan/p1Sample.jpg', 'PUBLIC', false);
INSERT INTO plans(p_id, u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES(5, 'user05', '경주에서 수학여행 즐기기', '어른이 되어 도전하는 수학여행', '2023-06-01', '2023-06-03', '/public/img/plan/p1Sample.jpg', 'PUBLIC', false);

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

#플래너 그림판 로딩 테스트용 더미(User01로 접속하면 나옴)

INSERT INTO plan_contents (con_id, p_id, t_id, s_id, day_n, title, info, time, img_path) VALUES (1, 1, null, null, 1, '한라산', '그림판 로딩 테스트 → 그림판 데이터가 있는 경우', '06:00 ~ 12:00', null);
INSERT INTO plan_contents (con_id, p_id, t_id, s_id, day_n,  title, info, time, img_path) VALUES (2, 1, null, null, 1, '툇마루 카페', '스케쥴은 추가 했지만 그림판을 아직 안 만든 경우', '14:00 ~ 19:00', null);
INSERT INTO plan_contents (con_id, p_id, t_id, s_id, day_n,  title, info, time, img_path) VALUES (3, 1, null, null, 1, '몽돌해변', '그림판까지 만들었지만, 아무것도 안 그린 경우', '19:00 ~ 23:00', null);
INSERT INTO plan_contents (con_id, p_id, t_id, s_id, day_n,  title, info, time, img_path) VALUES (4, 1, null, null, 2, '한라산 2회차', 'n일째 일정을 구분해서 출력해야함(아직)', '00:00 ~ 07:00', null);

INSERT INTO plan_content_paths (path_id, con_id, can_path)
VALUES (1, 1, '[
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[552,69],"path":[[551,69],[550,69],[545,69],[539,69],[532,71],[524,74],[519,76],[511,80],[502,85],[494,90],[488,93],[487,95],[487,96],[488,96],[497,101],[506,104],[514,108],[519,112],[522,114],[524,115],[525,116],[526,116],[530,118],[533,119],[534,120]],"range":[[487,69],[551,120]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[512,96],"path":[[513,96],[518,91],[518,89],[523,87],[527,85],[531,84],[538,83],[547,82],[556,81],[563,81],[568,81],[571,82],[573,85],[575,87],[578,91],[579,95],[580,100],[580,106],[578,110],[575,112],[571,115],[567,115],[564,115],[563,115],[561,111],[560,106],[559,101],[559,97],[562,95],[566,92],[572,89],[579,87],[587,86],[594,85],[599,84],[604,84],[607,84],[609,84],[611,85],[615,90],[615,91]],"range":[[513,81],[615,115]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[648,90],"path":[[647,90],[641,90],[635,93],[631,97],[629,100],[629,103],[629,107],[630,110],[633,113],[635,114],[639,114],[642,114],[645,114],[647,114],[649,112],[651,108],[652,103],[653,97],[653,93]],"range":[[629,90],[653,114]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[669,79],"path":[[669,80],[669,87],[669,97],[671,107],[674,115],[674,120],[675,120],[676,118]],"range":[[669,80],[676,120]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[682,87],"path":[[682,88],[684,93],[686,99],[689,105],[690,109],[690,110]],"range":[[682,88],[690,110]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[690,84],"path":[[690,83],[691,82],[693,81],[695,80],[697,80],[702,82],[706,83],[707,84],[708,84],[708,85],[708,86],[707,91],[705,97],[705,105],[705,110],[705,112],[704,112]],"range":[[690,80],[708,112]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[685,118],"path":[[686,118],[687,118],[692,118],[696,118],[699,117],[703,116],[708,114],[711,113]],"range":[[686,113],[711,118]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[717,83],"path":[[717,87],[717,98],[718,110],[720,118],[721,123],[722,123],[724,125],[725,125]],"range":[[717,87],[725,125]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[739,87],"path":[[740,87],[743,88],[751,88],[757,89],[759,90],[759,91],[759,93],[758,98],[755,101],[752,105],[748,109],[745,111],[744,111],[743,111],[742,113]],"range":[[740,87],[759,113]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[749,108],"path":[[750,108],[752,108],[755,109],[758,110],[761,112],[763,114],[766,116],[767,117],[768,118],[769,118],[770,118]],"range":[[750,108],[770,118]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[775,90],"path":[[775,91],[775,96],[775,104],[775,110],[775,115],[775,119],[775,123],[775,126],[775,128],[776,128]],"range":[[775,91],[776,128]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[796,85],"path":[[797,85],[801,85],[806,85],[809,85],[810,85]],"range":[[797,85],[810,85]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[791,88],"path":[[790,89],[788,92],[788,95],[788,100],[788,104],[788,106],[789,109],[791,109],[796,109],[802,109],[807,108],[810,107],[811,107],[812,107],[813,107]],"range":[[788,89],[813,109]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":0.5,"scale":1,"moveTo":[809,109],"path":[[809,110],[808,113],[807,118],[807,122],[806,126],[804,129],[802,131],[799,134],[796,136],[793,136],[792,136],[792,135],[792,134],[792,132],[792,131],[794,131],[796,130],[799,130],[803,129],[807,129],[812,129],[815,128],[818,128],[820,128],[821,128],[823,128],[824,128]],"range":[[792,110],[824,136]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[654,171],"path":[[653,171],[649,175],[645,181],[645,187],[645,193],[646,198],[650,201],[656,204],[663,204],[670,204],[674,204],[675,203],[676,199],[677,195],[677,191],[677,185],[674,180],[670,174],[665,170],[661,168],[659,168],[657,168]],"range":[[645,168],[677,204]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[657,205],"path":[[657,207],[655,212],[654,216],[654,218],[654,220],[653,221],[652,221]],"range":[[652,207],[657,221]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[630,226],"path":[[631,226],[632,226],[633,226],[639,226],[646,226],[652,226],[657,226],[659,226],[663,226],[668,225],[670,225]],"range":[[631,225],[670,226]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[645,240],"path":[[646,240],[647,240],[649,240],[654,240],[657,241],[660,242],[660,244],[660,245],[660,246],[659,249],[657,251],[657,252],[656,253],[654,254],[652,255]],"range":[[646,240],[660,255]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[638,257],"path":[[639,257],[640,257],[645,257],[652,255],[655,254],[656,252],[657,252],[656,252],[655,252],[653,253],[648,257],[642,261],[639,266],[638,270],[638,271],[639,271],[640,271],[644,271],[652,268],[660,265],[661,265]],"range":[[638,252],[661,271]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[692,197],"path":[[693,197],[696,197],[703,200],[707,204],[707,206],[707,209],[707,212],[707,214],[707,217],[706,219],[704,219]],"range":[[693,197],[707,219]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[682,218],"path":[[684,218],[690,218],[697,218],[705,218],[708,218],[707,218]],"range":[[684,218],[708,218]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[683,232],"path":[[682,234],[680,237],[680,239],[681,239],[682,241],[687,243],[695,245],[700,245],[704,243],[707,238],[707,237]],"range":[[680,234],[707,245]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[711,213],"path":[[711,215],[711,220],[711,226],[711,231],[710,237],[710,241],[709,244],[709,245],[708,245]],"range":[[708,215],[711,245]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[707,229],"path":[[707,228],[708,228],[710,226],[713,224],[716,222],[720,222],[723,222]],"range":[[707,222],[723,228]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[733,197],"path":[[734,197],[739,197],[753,202],[760,205],[763,209],[764,216],[763,223],[761,228],[759,232],[758,234],[757,234]],"range":[[734,197],[764,234]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[769,202],"path":[[769,203],[769,209],[769,213],[770,218],[771,224],[771,226],[771,229],[772,230]],"range":[[769,203],[772,230]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[774,215],"path":[[775,215],[777,214],[784,214],[790,214]],"range":[[775,214],[790,215]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[754,247],"path":[[754,248],[754,250],[754,251],[754,253],[756,253],[757,254],[758,254]],"range":[[754,248],[758,254]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[757,240],"path":[[759,239],[767,239],[776,239],[781,240],[782,240],[783,243],[783,245],[783,248],[783,251]],"range":[[759,239],[783,251]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[763,264],"path":[[765,264],[765,263],[768,261],[773,259],[778,257],[781,255],[784,253]],"range":[[765,253],[784,264]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[806,182],"path":[[806,184],[806,193],[806,200],[806,207],[807,211],[807,213],[808,213],[810,213]],"range":[[806,184],[810,213]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[809,190],"path":[[811,188],[820,188],[828,188],[832,188],[833,188],[834,188],[832,195],[826,201],[820,208],[816,213],[814,215],[813,215],[813,216],[812,216]],"range":[[811,188],[834,216]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#800080","fillStyle":"#800080","lineWidth":0.5,"scale":1,"moveTo":[803,234],"path":[[799,238],[793,245],[791,252],[791,256],[792,261],[795,264],[800,265],[805,265],[809,265],[809,262],[809,259],[809,255],[809,250],[807,246],[805,242],[804,241]],"range":[[791,238],[809,265]],"pageSize":0.9591666666666666},
{"type":"img","strokeStyle":"#808080","fillStyle":"#800080","lineWidth":2,"scale":1,"moveTo":[102,70],"lineTo":[475,410],"src":"data:image/jpeg;base64,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","range":[],"pageSize":0.9591666666666666},
{"type":"stamp","strokeStyle":"#808080","fillStyle":"#800080","lineWidth":2,"scale":1,"moveTo":[87,34],"lineTo":[159,104],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},
{"type":"stamp","strokeStyle":"#808080","fillStyle":"#800080","lineWidth":2,"scale":1,"moveTo":[56,90],"lineTo":[138,155],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},
{"type":"stamp","strokeStyle":"#808080","fillStyle":"#800080","lineWidth":2,"scale":1,"moveTo":[143,46],"lineTo":[197,90],"src":"/public/img/plan/star.png","range":[],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#0000ff","fillStyle":"#0000ff","lineWidth":2,"scale":1,"moveTo":[52,449],"path":[[53,449],[56,447],[59,445],[64,442],[68,441],[72,439],[78,439],[86,439],[96,440],[104,442],[110,443],[114,444],[119,445],[123,446],[129,448],[135,450],[140,451],[146,451],[152,451],[160,450],[170,449],[180,448],[188,446],[195,444],[202,442],[208,441],[213,441],[217,441],[221,442],[223,443],[226,445],[230,447],[235,449],[241,449],[248,449],[257,449],[266,449],[274,446],[283,442],[292,440],[300,438],[310,438],[320,439],[328,440],[335,441],[341,441],[347,442],[356,444],[364,445],[373,446],[380,446],[386,445],[393,443],[400,441],[410,440],[422,439],[433,437],[443,436],[451,436],[457,436],[463,436],[472,437],[480,439],[490,440],[499,441],[504,442],[506,442],[509,442],[515,441],[531,439],[548,436],[564,434],[572,432],[574,432],[576,432],[579,432],[583,432],[587,432],[592,433],[597,433],[607,434],[618,434],[629,435],[635,435],[641,435],[645,434],[650,433],[656,432],[663,431],[670,431],[676,431],[682,432],[688,434],[696,436],[706,440],[714,443],[719,443],[722,443],[726,443],[734,441],[745,439],[757,436],[767,435],[774,435],[778,435],[782,435],[789,435],[799,435],[810,436],[817,438],[823,438],[828,439],[833,439],[842,439],[852,437],[862,436],[870,435],[876,435],[883,435],[889,435],[897,435],[911,435],[925,435],[936,435],[941,435],[943,435],[943,434],[944,434],[945,434],[947,433],[949,432],[952,430],[954,429],[955,428],[955,427]],"range":[[53,427],[955,451]],"pageSize":0.9591666666666666},
{"type":"pen","strokeStyle":"#008000","fillStyle":"#008000","lineWidth":2,"scale":1,"moveTo":[48,467],"path":[[52,467],[61,466],[72,463],[82,460],[90,458],[97,456],[107,455],[118,455],[130,454],[136,454],[140,455],[143,456],[147,457],[151,460],[157,463],[164,464],[168,464],[175,464],[184,464],[195,462],[208,458],[222,456],[231,454],[239,452],[245,451],[253,451],[259,452],[265,452],[272,454],[279,456],[285,458],[291,459],[296,460],[304,460],[317,460],[332,458],[343,455],[353,453],[359,451],[367,451],[378,449],[390,449],[401,448],[408,447],[413,447],[416,447],[421,447],[428,448],[443,446],[457,444],[469,442],[477,440],[481,440],[486,440],[491,440],[497,440],[502,442],[508,443],[514,444],[521,444],[532,444],[546,441],[562,438],[571,437],[575,436],[576,436],[577,436],[580,437],[585,440],[592,444],[595,447],[599,449],[603,450],[610,451],[619,451],[628,451],[634,450],[640,448],[645,446],[651,445],[656,445],[663,446],[669,447],[674,449],[679,451],[685,454],[693,456],[704,457],[715,458],[724,458],[731,457],[738,456],[745,455],[750,454],[754,454],[758,455],[763,457],[769,460],[775,461],[782,461],[788,461],[793,460],[801,457],[809,454],[820,451],[834,450],[841,450],[846,450],[849,450],[854,450],[859,450],[864,449],[869,447],[876,445],[886,442],[902,438],[912,437],[915,437],[916,437],[918,438],[922,440],[927,442],[933,442],[937,442],[941,442],[943,442],[944,442],[945,443],[947,444],[948,444],[950,445],[953,445],[955,445],[958,445],[960,445],[962,445],[964,445],[966,445],[968,445],[969,445],[969,444]],"range":[[52,436],[969,467]],"pageSize":0.9591666666666666}]');

INSERT INTO plan_content_paths (path_id, con_id, can_path)
VALUES (2, 3, '[{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[76,41],"path":[[76,40],[76,39],[77,39],[77,38],[78,38],[78,37],[79,37],[80,37],[80,36],[81,36],[82,36],[83,36],[84,36],[84,35],[85,35],[86,35],[86,34],[87,34],[88,34],[89,34],[90,34],[91,34],[92,34],[92,33],[93,33],[94,33],[95,33],[96,33],[97,33],[98,33],[99,33],[100,33],[101,34],[101,35],[102,35],[102,36],[103,36],[103,37],[104,37],[104,38],[104,39],[104,40],[105,40],[105,41],[105,42],[105,43],[105,44],[106,44],[106,45],[106,46],[106,47],[106,48],[107,48],[107,49],[107,50],[107,51],[107,52],[107,53],[107,54],[107,55],[107,56],[107,57],[107,58],[107,59],[107,60],[107,61],[107,62],[106,62],[106,63],[106,64],[105,64],[105,65],[104,65],[103,65],[103,66],[103,67],[102,67],[102,68],[101,68],[101,69],[100,69],[99,70],[98,70],[98,71],[98,72],[97,72],[96,73],[95,73],[94,74],[93,75],[93,76],[92,76],[91,77],[91,78],[90,78],[90,79],[89,80],[89,81],[88,81],[88,82],[87,82],[87,83],[87,84],[86,85],[86,86],[85,86],[85,87],[85,88],[85,89],[85,90],[85,91],[85,92],[85,93],[85,94],[86,94],[86,95],[86,96],[87,96],[87,97],[88,97],[89,97],[89,98],[90,98],[91,99],[92,99],[93,99]],"range":[[76,33],[107,99]],"pageSize":0.6158333333333333},{"type":"pen","strokeStyle":"#000000","fillStyle":"#000000","lineWidth":0.5,"scale":1,"moveTo":[98,123],"path":[[98,124],[97,124],[97,125],[97,126],[96,126],[96,127],[95,127],[95,128],[95,129],[95,130],[95,131],[94,131],[94,132],[94,133],[94,134],[94,135],[94,136],[94,137],[94,138],[95,138],[96,138],[96,139],[97,139],[98,139],[99,139],[100,139],[101,139],[102,139],[102,138],[103,138],[104,137],[105,137],[105,136],[106,136],[106,135],[106,134],[107,134],[107,133],[108,133],[108,132],[109,131],[109,130],[109,129],[110,128],[110,127],[110,126],[110,125],[110,124],[110,123],[110,122],[109,122],[108,122],[107,122],[106,122],[105,122],[104,122],[103,122],[102,122],[101,122],[100,122],[100,123],[99,123],[98,123],[97,123],[97,124],[96,124],[95,124]],"range":[[94,122],[110,139]],"pageSize":0.6158333333333333}]');
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
    ('user01', '제주에서 즐기는 봄꽃 여행', '제주', '서귀포', '010-1234-5648', 'https://www.visitjeju.net/kr/','제주의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '힐링'),
    ('user02', '서울에서 즐기는 봄꽃 여행', '서울', '여의도', '010-1111-2262', 'https://www.visitjeju.net/kr/','서울의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, '체험'),
    ('user03', '대전에서 즐기는 봄꽃 여행', '대전', '동구', '010-1234-5689', 'https://www.visitjeju.net/kr/','대전의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, '박물관'),
    ('user04', '강원도에서 즐기는 봄꽃 여행', '강원', '속초', '010-5555-5355', 'https://www.visitjeju.net/kr/','강원도의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, '힐링'),
    ('user05', '인천에서 즐기는 봄꽃 여행', '인천', '강화도', '010-8888-8688', 'https://www.visitjeju.net/kr/','인천의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, '반려동물'),
    ('user06', '부산에서 즐기는 봄꽃 여행', '부산', '목포', '010-2222-3233', 'https://www.visitjeju.net/kr/','부산의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, '레저'),
    ('user07', '대전에서 즐기는 봄꽃 여행', '대전', '동구', '010-7777-7767', 'https://www.visitjeju.net/kr/','대전의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, '레저'),
    ('user08', '강원도에서 즐기는 봄꽃 여행', '강원', '속초', '010-1234-5577', 'https://www.visitjeju.net/kr/','강원도의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, '체험'),
    ('user09', '인천에서 즐기는 봄꽃 여행', '인천', '강화도', '010-9999-1999', 'https://www.visitjeju.net/kr/','인천의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, '반려동물'),
    ('user10', '부산에서 즐기는 봄꽃 여행', '부산', '목포', '010-7777-7676', 'https://www.visitjeju.net/kr/','부산의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, '힐링');


#가보자고 이미지 더미
INSERT INTO trip_imgs (t_id,img_path,img_main)
VALUES     (1,'/public/img/trip/default.jpeg',0),
           (1,'/public/img/trip/default.jpeg',1),
           (2,'/public/img/trip/default.jpeg',0),
           (2,'/public/img/trip/default.jpeg',1),
           (3,'/public/img/trip/default.jpeg',0),
           (3,'/public/img/trip/default.jpeg',1),
           (4,'/public/img/trip/default.jpeg',0),
           (4,'/public/img/trip/default.jpeg',1),
           (5,'/public/img/trip/default.jpeg',0),
           (5,'/public/img/trip/default.jpeg',1),
           (6,'/public/img/trip/default.jpeg',0),
           (6,'/public/img/trip/default.jpeg',1),
           (7,'/public/img/trip/default.jpeg',0),
           (7,'/public/img/trip/default.jpeg',1),
           (8,'/public/img/trip/default.jpeg',0),
           (8,'/public/img/trip/default.jpeg',1),
           (9,'/public/img/trip/default.jpeg',0),
           (9,'/public/img/trip/default.jpeg',1),
           (10,'/public/img/trip/default.jpeg',0),
           (10,'/public/img/trip/default.jpeg',1)




       ;


#가보자고 리뷰 데이터
INSERT INTO trip_reviews (t_id, u_id, content, visit, grade)
VALUES
    (1, 'user01', '이곳이 정말 멋진 곳이에요!', 1, 5),
    (1, 'user02', '여기는 가보자고 추천해준 곳 중에서 제일 좋았어요.', 1, 4),
    (1, 'user03', '다음에도 꼭 다시 방문하고 싶은 곳이에요!', 1, 4),
    (1, 'user04', '이곳은 정말 특별한 경험이었어요.', 1, 5),
    (1, 'user05', '여기는 앞으로도 자주 찾게 될 것 같아요.', 1, 3);

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
    (1,'/public/img/comm/1681969679943_2023.jpeg',0),
    (2,'/public/img/comm/1681969832415_8296.jpeg',0),
    (3,'/public/img/comm/1681976587025_8955.jpeg',0),
    (4,'/public/img/comm/1682237197655_3190.png',0);

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
INSERT INTO comm_bookmarks (c_id, u_id)
VALUES
        (1, 'user01'),
        (2, 'user01'),
        (3, 'user01'),
        (4, 'user01'),
        (1, 'user02'),
        (2, 'user02'),
        (3, 'user02'),
        (4, 'user02'),
        (4, 'user03'),
        (4, 'user04');

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


