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
                             `img_main`	boolean	COMMENT '메인이미지'
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
                         `img_main`	boolean	COMMENT '대표이미지',
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
                                 `title`	varchar(255) NOT NULL COMMENT '개별스케줄 제목',
                                 `info`	varchar(255)  COMMENT '부가정보',
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
                                  FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                                  FOREIGN KEY (c_id) REFERENCES communitys (c_id) ON DELETE CASCADE ON UPDATE CASCADE,
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
CREATE TABLE `sell-imgs` (
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
                                  `sbook_id`	int unsigned AUTO_INCREMENT PRIMARY KEY COMMENT '판매북마크 아이디',
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
                              `o_id`	int unsigned NOT NULL COMMENT '옵션 아이디',
                              FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE CASCADE ON UPDATE CASCADE,
                              FOREIGN KEY (o_id) REFERENCES sell_options (o_id) ON DELETE CASCADE ON UPDATE CASCADE
);


#마이페이지( 팔로우 테이블)
CREATE TABLE `follows` (
                           `f_id`	varchar(255)  PRIMARY KEY COMMENT '팔로우 아이디',
                           `to_users`	varchar(255)	NOT NULL COMMENT '받는유저 아이디',
                           `from_users`	varchar(255)NOT NULL COMMENT '보내는유저',
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

#유저더미
INSERT INTO users (u_id, pw, name, nk_name, email, birth, phone, address, detail_address, pr_content, permission, mbti, img_path, store_name, business_id)
    VALUES
       ('USER01', '1234', '김철수', '바보철수', 'user01@example.com', '1990-01-01', '010-1234-5678', '서울특별시 강남구', '삼성동 123-45', '안녕하세요. 저는 철수입니다.', 'USER', 'ISTJ', '/images/user01.jpg', NULL, NULL),
       ('USER02', '1234', '김영수', '영수', 'kimyoungsoo@gmail.com', '1995-02-03', '010-1111-2222', '서울특별시 강남구', '신사동 123-1', '안녕하세요. 저는 웹 개발자입니다.', 'USER', 'ISTJ', NULL, NULL, NULL),
       ('USER03', '1234', '이은지', '은지', 'leeeunji@gmail.com', '1998-06-17', '010-1234-5679', '서울특별시 관악구', '신림동 543-2', '안녕하세요. 저는 디자이너입니다.', 'USER', 'INFP', NULL, NULL, NULL),
       ('USER04', '1234', '박민수', '민수', 'parkminsou@gmail.com', '1987-09-23', '010-5555-5555', '경기도 부천시', '상동 23-4', '안녕하세요. 저는 영화 감독입니다.', 'USER', 'INTJ', NULL, NULL, NULL),
       ('USER05', '1234', '장현아', '현아', 'janghyuna@gmail.com', '2000-01-01', '010-8888-8888', '서울특별시 송파구', '잠실동 456-7', '안녕하세요. 저는 대학생입니다.', 'USER', 'ENFP', NULL, NULL, NULL),
       ('USER06', '1234', '오현우', '현우', 'ohyunwoo@gmail.com', '1992-07-11', '010-2222-3333', '서울특별시 종로구', '관수동 23-1', '안녕하세요. 저는 작가입니다.', 'USER', 'ISFP', NULL, NULL, NULL),
       ('USER07', '1234', '서지수', '지수', 'seojisoo@gmail.com', '1985-12-30', '010-7777-7777', '서울특별시 서대문구', '이화동 17-5', '안녕하세요. 저는 편집자입니다.', 'USER', 'INTP', NULL, NULL, NULL),
       ('USER08', '1234', '강민지', '민지', 'kangminji@gmail.com', '1994-03-24', '010-1234-5677', '서울특별시 마포구', '망원동 456-7', '안녕하세요. 저는 영화 배우입니다.', 'USER', 'ESFP', NULL, NULL, NULL),
       ('USER09', '1234','박성준', '성준', 'parksungjun@gmail.com', '1991-11-11', '010-9999-9999', '경기도 의정부시', '송산동 123-4', '안녕하세요. 저는 의사입니다.', 'USER', 'ENTJ', NULL, NULL, NULL),
       ('USER10', '1234', '임수현', '수현', 'limsoohyun@gmail.com', '1996-08-08', '010-7777-7776', '서울특별시 강동구', '천호동 456-7', '안녕하세요. 저는 공무원입니다.', 'PARTNER', 'ISTP', NULL, NULL, NULL);


INSERT INTO plans(u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES('USER02', '놀러가자', '언제놀러가지', '2023-05-01', '2023-05-03', '/images/beach.jpg', 'PUBLIC', true);
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
    ('USER01', 1, 'Lets hike together!', 'Looking for hiking buddies in the Seoul area. Planning to go to Bukhansan National Park next weekend.', '서울', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);


#플래너더미
INSERT INTO plans(u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES('USER01', 'Weekend Trip to the Beach', '이번 주말 해변에 놀러갈 여행 친구 구함', '2023-04-01', '2023-04-03', '/images/beach.jpg', 'PUBLIC', true);
INSERT INTO plans(u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES('USER02', '놀러가자', '언제놀러가지', '2023-05-01', '2023-05-03', '/images/beach.jpg', 'PUBLIC', true);
INSERT INTO plans(u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES('USER03', '강원도삼척여행', '강원도다녀오기', '2022-03-11', '2022-03-15', '/images/kang.jpg', 'PUBLIC', true);
INSERT INTO plans(u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES('USER04', '제주도한달살기', '나의 워라벨을 위함', '2023-02-01', '2023-03-03', '/images/jeju.jpg', 'PUBLIC', true);
INSERT INTO plans(u_id, title, info, plan_from, plan_to, img_path, plan_status, review) VALUES('USER05', '경주에서수학여행즐기기', '어른이되어 도전하는 수학여행', '2023-06-01', '2023-06-03', '/images/gjtrip.jpg', 'PUBLIC', true);

#커뮤니티더미
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
    ('USER01', 1, 'Lets hike together!', 'Looking for hiking buddies in the Seoul area. Planning to go to Bukhansan National Park next weekend.', '서울', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
    ('USER02', 2, 'Coffee lovers unite!2', 'Looking for people who enjoy trying out new coffee shops in the Gyeonggi area. Lets share our favorite spots and maybe even organize a coffee tasting event.1', '서울', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
    ('USER03', 3, 'Coffee lovers unite!3', 'Looking for people who enjoy trying out new coffee shops in the Gyeonggi area. Lets share our favorite spots and maybe even organize a coffee tasting event.2', '제주', 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
    ('USER04', 4, 'Coffee lovers unite!4', 'Looking for people who enjoy trying out new coffee shops in the Gyeonggi area. Lets share our favorite spots and maybe even organize a coffee tasting event.3', '경기', 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0);
INSERT INTO communitys (u_id, p_id, title, content, area, istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj, esfp, entj, entp, enfj, enfp) VALUES
    ('USER05', 5, 'Coffee lovers unite!5', 'Looking for people who enjoy trying out new coffee shops in the Gyeonggi area.', '강원', 0, 0, 1, 1,1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);


#판매글 데이터
INSERT INTO sells (u_id, area, title, content, category, qnt, img_main)
VALUES
    ('user01', '서울', '판매글 제목1', '판매글 내용1', '워터', 5, true),
    ('user02', '인천', '판매글 제목2', '판매글 내용2', '테마', 3, false),
    ('user03', '대전', '판매글 제목3', '판매글 내용3', '키즈', 1, true),
    ('user04', '광주', '판매글 제목4', '판매글 내용4', '레저', 8, true),
    ('user05', '대구', '판매글 제목5', '판매글 내용5', '박물관', 2, false),
    ('user06', '울산', '판매글 제목6', '판매글 내용6', '워터', 4, true),
    ('user07', '부산', '판매글 제목7', '판매글 내용7', '테마', 3, false),
    ('user08', '세종', '판매글 제목8', '판매글 내용8', '키즈', 2, true),
    ('user09', '경기', '판매글 제목9', '판매글 내용9', '레저', 5, false),
    ('user10', '강원', '판매글 제목10', '판매글 내용10', '박물관', 1, true),
    ('user01', '충북', '판매글 제목11', '판매글 내용11', '워터', 3, false),
    ('user02', '충남', '판매글 제목12', '판매글 내용12', '테마', 6, true),
    ('user03', '전북', '판매글 제목13', '판매글 내용13', '키즈', 4, true),
    ('user04', '전남', '판매글 제목14', '판매글 내용14', '레저', 7, false),
    ('user05', '경북', '판매글 제목15', '판매글 내용15', '박물관', 2, true),
    ('user06', '경남', '판매글 제목16', '판매글 내용16', '워터', 5, false),
    ('user07', '제주', '판매글 제목17', '판매글 내용17', '테마', 1, true),
    ('user07', '제주', '판매글 제목17', '판매글 내용17', '키즈', 1, true),
    ('user07', '제주', '판매글 제목171', '판매글 내용17', '레저', 1, true),
    ('user07', '제주', '판매글 제목171', '판매글 내용17', '테마', 1, true);

INSERT INTO `sell_options` (`s_id`, `name`, `price`, `stock`)

VALUES
    (1, '성인', '10000', 20),
    (1, '청소년', '8000', 30),
    (1, '소인', '5000', 10);


#가보자고 더미
INSERT INTO gabojagoPlan.trips (u_id, title, area, address, phone, url_address, content,
                                istj, istp, isfj, isfp, intj, intp, infj, infp, estj, estp, esfj,
                                esfp, entj, entp, enfj, enfp, category)
VALUES
    ('user01', '제주에서 즐기는 봄꽃 여행', '제주', '서귀포', '01011112222', 'https://www.visitjeju.net/kr/','제주의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '힐링'),
    ('user02', '서울에서 즐기는 봄꽃 여행', '서울', '여의도', '01011123222', 'https://www.visitjeju.net/kr/','서울의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, '힐링'),
    ('user03', '대전에서 즐기는 봄꽃 여행', '대전', '동구', '01013145222', 'https://www.visitjeju.net/kr/','대전의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, '힐링'),
    ('user04', '강원도에서 즐기는 봄꽃 여행', '강원', '속초', '01022115522', 'https://www.visitjeju.net/kr/','강원도의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, '힐링'),
    ('user05', '인천에서 즐기는 봄꽃 여행', '인천', '강화도', '01014312222', 'https://www.visitjeju.net/kr/','인천의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, '힐링'),
    ('user06', '부산에서 즐기는 봄꽃 여행', '부산', '목포', '01055115322', 'https://www.visitjeju.net/kr/','부산의 아름다운 벚꽃을 감상하며 봄을 느껴보세요.', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, '힐링');


