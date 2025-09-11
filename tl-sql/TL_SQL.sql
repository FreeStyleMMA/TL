CREATE DATABASE IF NOT EXISTS TL
  DEFAULT CHARACTER SET utf8mb4    -- UTF-8 + 이모지 지원 (메일 	`아이디 고려함)
  DEFAULT COLLATE utf8mb4_0900_ai_ci; -- 대소문자/악센트 무시
USE TL;

-- member 테이블
CREATE TABLE IF NOT EXISTS member (
  memberId   VARCHAR(30)  NOT NULL PRIMARY KEY,
  memberPw   VARCHAR(100) NOT NULL,
  memberName VARCHAR(20)  NOT NULL,
  created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- performance 테이블
CREATE TABLE IF NOT EXISTS performance (
  per_id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  per_title     VARCHAR(200) NOT NULL,
  per_startD    DATE         NOT NULL,
  per_endD      DATE         NOT NULL,
  per_place     VARCHAR(100) NOT NULL,
  per_runT      VARCHAR(50),
  per_sche      TEXT,
  per_price     VARCHAR(100),
  per_genre     VARCHAR(50),
  per_poster    VARCHAR(1000),
  per_ticket    VARCHAR(1000),
  per_address   VARCHAR(255),      
  per_latitude  DECIMAL(11,8),     
  per_longitude DECIMAL(11,8),     
  per_region    VARCHAR(50),       
  per_rank      INT,              
  created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 테스트 계정 
INSERT INTO member (memberId, memberPw, memberName)
VALUES 
('jun123',   '@1234RandomPassWord', '홍길동'),
('alice01',  '@TestPass1',          '김철수'),
('bob02',    '@TestPass2',          '이영희'),
('charlie03','@TestPass3',          '박민수'),
('diana04',  '@TestPass4',          '최지현');

-- 더미 공연
INSERT INTO performance 
(per_title, per_startD, per_endD, per_place, per_runT, per_sche, per_price, per_genre, per_poster, per_ticket,
 per_address, per_latitude, per_longitude, per_region, per_rank)
VALUES
('뮤지컬 레미제라블', '2025-09-10', '2025-11-30', '블루스퀘어 신한카드홀', '170분',
 '화~금 19:30, 주말 14:00/19:00', 'VIP 160,000원 / R석 130,000원 / S석 90,000원', '뮤지컬',
 'poster_lesmis.jpg', 'https://tickets.interpark.com/lesmis',
 '서울특별시 용산구 한남동 727-56', 37.54102000, 127.00282000, '서울특별시', 1),

('콘서트 임영웅 전국투어', '2025-10-03', '2025-10-05', '고양 킨텍스 제1전시장', '150분',
 '10/3~5 18:00', 'VIP 165,000원 / 일반 143,000원', '콘서트',
 'poster_limyoungwoong.jpg', 'https://ticket.yes24.com/limtour',
 '경기도 고양시 일산서구 킨텍스로 217-60', 37.66877000, 126.74560000, '경기도', 2),

('뮤지컬 위키드', '2025-09-01', '2025-10-15', '예술의전당 오페라극장', '160분',
 '화~금 19:30, 토 14:00/19:00, 일 15:00', 'VIP 150,000원 / R석 120,000원 / S석 80,000원', '뮤지컬',
 'poster_wicked.jpg', 'https://ticket.interpark.com/wicked',
 '서울특별시 서초구 남부순환로 2406', 37.47835000, 127.01123000, '서울특별시', 3),

('콘서트 IU 2025', '2025-12-20', '2025-12-21', '서울 잠실종합운동장 올림픽주경기장', '180분',
 '12/20~21 18:00', 'VIP 187,000원 / 일반 143,000원', '콘서트',
 'poster_iu2025.jpg', 'https://ticket.melon.com/iu2025',
 '서울특별시 송파구 올림픽로 25', 37.51500000, 127.07250000, '서울특별시', 4),

('연극 옥탑방 고양이', '2025-09-01', '2025-12-31', '대학로 틴틴홀', '100분',
 '매일 14:00 / 16:30 / 19:00', '전석 40,000원', '공연',
 'poster_rooftopcat.jpg', 'https://tickets.interpark.com/rooftopcat',
 '서울특별시 종로구 대학로 12길 21', 37.58265000, 127.00220000, '서울특별시', 5);
 
 -- post 테이블
CREATE TABLE IF NOT EXISTS `post` (
  `no`        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 글 번호
  category    VARCHAR(20)   NOT NULL,                      -- '후기','잡답' 등 (ENUM 대신 자유롭게)
  title       VARCHAR(200)  NOT NULL,
  content     TEXT          NOT NULL,
  memberId    VARCHAR(30)   NOT NULL,                      -- 작성자 (member.memberId)
  `date`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  media       VARCHAR(1000) NULL,                          -- 이미지/동영상 경로(문자열)
  CONSTRAINT fk_post_member
    FOREIGN KEY (memberId) REFERENCES member(memberId)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  INDEX ix_post_category_date (category, `date`),
  INDEX ix_post_member (memberId)
);

-- reply 테이블
CREATE TABLE IF NOT EXISTS reply (
  `no`        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,  -- 댓글 번호
  originNo    BIGINT UNSIGNED NOT NULL,                    -- 원글 번호 (post.no)
  memberId    VARCHAR(30)   NOT NULL,                      -- 작성자
  content     TEXT          NOT NULL,
  `date`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_reply_post
    FOREIGN KEY (originNo) REFERENCES `post`(`no`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_reply_member
    FOREIGN KEY (memberId) REFERENCES member(memberId)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  INDEX ix_reply_origin_date (originNo, `date`)
);

INSERT INTO post (category, title, content, memberId, media)
VALUES
('후기', '레미제라블 관람 후기', '뮤지컬 레미제라블 진짜 감동이었습니다!', 'jun123', 'poster_lesmis.jpg'),
('잡답', '요즘 듣는 음악', 'IU 콘서트 준비하면서 아이유 노래만 듣고 있어요.', 'alice01', NULL),
('후기', '옥탑방 고양이 재밌네요', '대학로에서 보고 왔는데 소극장이라 몰입감 최고.', 'bob02', NULL),
('잡답', '공연 예매 팁', '티켓팅은 인터파크 앱 알림 맞춰놓는 게 제일 좋아요.', 'charlie03', NULL),
('후기', '임영웅 콘서트 다녀왔습니다', '현장에서 감동 그 자체였습니다.', 'diana04', 'poster_limyoungwoong.jpg');

INSERT INTO post (category, title, content, memberId, media)
VALUES
('잡답', '댓글 없는 테스트 글', '이 글에는 일부러 댓글을 달지 않았습니다.', 'alice01', NULL);

INSERT INTO reply (originNo, memberId, content)
VALUES
(1, 'alice01', '저도 레미제라블 봤는데 눈물났어요!'),
(1, 'bob02', '노래가 진짜 압도적이었죠.'),
(2, 'charlie03', '저도 아이유 곡만 돌려듣는 중입니다 ㅎㅎ'),
(2, 'diana04', '이번에 새 앨범도 나온다던데 기대되네요.'),
(3, 'jun123', '저도 소극장 공연은 분위기가 좋아서 추천합니다.'),
(3, 'alice01', '연기자분들 연기력이 진짜 👍'),
(4, 'bob02', '맞아요, 알림 필수! 자리 선점하려면 빠릿해야 합니다.'),
(4, 'diana04', '요즘은 카드사 제휴 예매도 빨라서 좋아요.'),
(5, 'jun123', '임영웅 라이브는 확실히 다르죠.'),
(5, 'charlie03', '저도 다음엔 꼭 가보고 싶네요!');

-- 확인용 쿼리
SHOW TABLES;
DESC member;
DESC performance;
SELECT * FROM member;
SELECT * FROM performance;
SELECT * FROM post;
SELECT * FROM reply;

-- 전체 공연 개수
SELECT COUNT(*) AS total_performances FROM performance;

-- 장르별 개수
SELECT per_genre, COUNT(*) AS cnt
FROM performance
GROUP BY per_genre;

-- 뮤지컬 개수만
SELECT COUNT(*) AS musical_cnt
FROM performance
WHERE per_genre = '뮤지컬';

-- 콘서트 개수만
SELECT COUNT(*) AS concert_cnt
FROM performance
WHERE per_genre = '콘서트';

-- 공연(연극) 개수만
SELECT COUNT(*) AS play_cnt
FROM performance
WHERE per_genre = '공연';

-- ==============================
-- 게시글(post) 확인용
-- ==============================

-- 전체 게시글 개수
SELECT COUNT(*) AS total_posts FROM post;

-- 카테고리별 게시글 개수
SELECT category, COUNT(*) AS cnt
FROM post
GROUP BY category;

-- 특정 카테고리(예: 후기) 게시글 개수
SELECT COUNT(*) AS review_posts
FROM post
WHERE category = '후기';

-- 작성자별 게시글 개수
SELECT memberId, COUNT(*) AS cnt
FROM post
GROUP BY memberId
ORDER BY cnt DESC;

-- 날짜별 게시글 개수
SELECT DATE(`date`) AS d, COUNT(*) AS cnt
FROM post
GROUP BY DATE(`date`)
ORDER BY d DESC;

-- ==============================
-- 댓글(reply) 확인용
-- ==============================

-- 전체 댓글 개수
SELECT COUNT(*) AS total_replies FROM reply;

-- 게시글별 댓글 개수
SELECT originNo AS post_no, COUNT(*) AS reply_cnt
FROM reply
GROUP BY originNo
ORDER BY reply_cnt DESC;

-- 특정 카테고리(예: 후기) 게시글에 달린 댓글 수
SELECT p.category, COUNT(r.no) AS reply_cnt
FROM reply r
JOIN post p ON r.originNo = p.no
WHERE p.category = '후기';

-- 작성자별 댓글 개수
SELECT memberId, COUNT(*) AS cnt
FROM reply
GROUP BY memberId
ORDER BY cnt DESC;

-- ==============================
-- 게시글 + 댓글 통합 확인
-- ==============================

-- 게시글별 댓글 수 포함
SELECT p.no AS post_no, p.title, p.category,
       COUNT(r.no) AS reply_cnt
FROM post p
LEFT JOIN reply r ON r.originNo = p.no
GROUP BY p.no, p.title, p.category
ORDER BY p.no DESC;

-- 댓글 없는 게시글만
SELECT p.no, p.title, p.category
FROM post p
LEFT JOIN reply r ON r.originNo = p.no
WHERE r.no IS NULL;
