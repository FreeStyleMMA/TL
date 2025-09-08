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
  per_id     BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  per_title  VARCHAR(200) NOT NULL,
  per_startD DATE         NOT NULL,
  per_endD   DATE         NOT NULL,
  per_place  VARCHAR(100) NOT NULL,
  per_runT   VARCHAR(50),
  per_sche   TEXT,
  per_price  VARCHAR(100),
  per_genre  VARCHAR(50),
  per_poster VARCHAR(1000),
  per_ticket    VARCHAR(1000),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
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
(per_title, per_startD, per_endD, per_place, per_runT, per_sche, per_price, per_genre, per_poster, per_ticket)
VALUES
('뮤지컬 레미제라블',   '2025-09-10', '2025-11-30', '블루스퀘어 신한카드홀', '170분', '화~금 19:30, 주말 14:00/19:00', 'VIP 160,000원 / R석 130,000원 / S석 90,000원', '뮤지컬', 'poster_lesmis.jpg', 'https://tickets.interpark.com/lesmis'),
('콘서트 임영웅 전국투어', '2025-10-03', '2025-10-05', '고양 킨텍스 제1전시장', '150분', '10/3~5 18:00', 'VIP 165,000원 / 일반 143,000원', '콘서트', 'poster_limyoungwoong.jpg', 'https://ticket.yes24.com/limtour'),
('뮤지컬 위키드',      '2025-09-01', '2025-10-15', '예술의전당 오페라극장', '160분', '화~금 19:30, 토 14:00/19:00, 일 15:00', 'VIP 150,000원 / R석 120,000원 / S석 80,000원', '뮤지컬', 'poster_wicked.jpg', 'https://ticket.interpark.com/wicked'),
('콘서트 IU 2025',     '2025-12-20', '2025-12-21', '서울 잠실종합운동장 올림픽주경기장', '180분', '12/20~21 18:00', 'VIP 187,000원 / 일반 143,000원', '콘서트', 'poster_iu2025.jpg', 'https://ticket.melon.com/iu2025'),
('연극 옥탑방 고양이',  '2025-09-01', '2025-12-31', '대학로 틴틴홀', '100분', '매일 14:00 / 16:30 / 19:00', '전석 40,000원', '공연', 'poster_rooftopcat.jpg', 'https://tickets.interpark.com/rooftopcat')
;

-- 확인용 쿼리
SHOW TABLES;
DESC member;
DESC performance;
SELECT * FROM member;
SELECT * FROM performance;

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