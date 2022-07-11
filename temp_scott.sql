SELECT USER
FROM DUAL;


--게시판 전용 테이블 생성
/*
CREATE TABLE TBL_BOARD
(NUM            NUMBER(9)               NOT NULL   --게시물 번호
,NAME           VARCHAR2(30)            NOT NULL   --게시물 작성자
,PWD            VARCHAR2(20)            NOT NULL   --게시물 암호
,EMAIL          VARCHAR2(50)                        --작성자 이메일
,SUBJECT        VARCHAR2(100)           NOT NULL   --게시물 제목
,CONTENT        VARCHAR2(4000)          NOT NULL   --게시물 내용
,IPADDR         VARCHAR2(20)                        --접속한 클라이언트 IP 주소
,HITCOUNT       NUMBER      DEFAULT 0           NOT NULL  --게시물 조회수
,CREATED        DATE        DEFAULT SYSDATE     NOT NULL  --게시물 작성일
,CONSTRAINT BOARD_NUM_PK PRIMARY KEY(NUM)                  --게시물 번호에 PK 제약조건 설정
);
*/
--Table TBL_BOARD이(가) 생성되었습니다.


DROP TABLE TBL_TEMP PURGE;
--Table TBL_TEMP이(가) 삭제되었습니다.


CREATE TABLE TBL_STAR
(NUM            NUMBER(9)               NOT NULL   --게시물 번호
,NAME           VARCHAR2(30)            NOT NULL   --게시물 작성자
,PWD            VARCHAR2(20)            NOT NULL   --게시물 암호
,EMAIL          VARCHAR2(50)                        --작성자 이메일
,SUBJECT        VARCHAR2(100)           NOT NULL   --게시물 제목
,CONTENT        VARCHAR2(4000)          NOT NULL   --게시물 내용
,IPADDR         VARCHAR2(20)                        --접속한 클라이언트 IP 주소
,HITCOUNT       NUMBER      DEFAULT 0           NOT NULL  --게시물 조회수
,CREATED        DATE        DEFAULT SYSDATE     NOT NULL  --게시물 작성일
,CONSTRAINT STAR_NUM_PK PRIMARY KEY(NUM)                  --게시물 번호에 PK 제약조건 설정
);
--Table TBL_STAR이(가) 생성되었습니다.





--게시물 번호의 최대값을 얻어내는 쿼리문 구성
SELECT NVL(MAX(NUM), 0) AS MAXNUM
FROM TBL_STAR;

--한줄구성
SELECT NVL(MAX(NUM), 0) AS MAXNUM FROM TBL_STAR
;




--게시물 작성 쿼리문 구성
INSERT INTO TBL_STAR(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED)
VALUES(1, '가나다', '1234', 'ganada@test.com', '작성테스트', '게시물내용작성', 'localhost', 0, SYSDATE);
--1 행 이(가) 삽입되었습니다.

--한줄구성
INSERT INTO TBL_STAR(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED) VALUES(1, '가나다', '1234', 'ganada@test.com', '작성테스트', '게시물내용작성', 'localhost', 0, SYSDATE)
;




--DB 레코드의 개수를 가져오는 쿼리문 구성
SELECT COUNT(*) AS COUNT
FROM TBL_STAR;

--한줄구성
SELECT COUNT(*) AS COUNT FROM TBL_STAR
;





--               1       10
--특정 영역의(시작번호~끝번호) 게시물 목록을 읽어오는 쿼리문 구성
--번호, 작성자, 제목, 조회수, 작성일

SELECT *
FROM 
(
    SELECT ROWNUM RNUM, DATA.*
    FROM
    (
        SELECT NUM, NAME, SUBJECT, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED
        FROM TBL_STAR
        ORDER BY NUM DESC
    ) DATA
)
WHERE RNUM>=1 AND RNUM<=10;

--한줄 구성
SELECT * FROM (SELECT ROWNUM RNUM, DATA.* FROM ( SELECT NUM, NAME, SUBJECT, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_STAR ORDER BY NUM DESC) DATA) WHERE RNUM>=1 AND RNUM<=10
;



--특정 게시물 조회에 따른 조회 횟수 증가 쿼리문 구성
UPDATE TBL_STAR
SET HITCOUNT = HITCOUNT + 1   --오라클은 HITCOUNT += 1 혹은 HITCOUNT++ 이 없으므로...
WHERE NUM=1;

--한줄 구성
UPDATE TBL_STAR SET HITCOUNT = HITCOUNT + 1 WHERE NUM=1
;


--요즘은 로그화~~!! 해서 중복하여 조회수 무작정 올리지 못함~~
--조회할 때 마다 해당 사람의 번호 또한 로그로 기록됨~~~


--특정 게시물의 내용을 읽어오는 쿼리문 구성
--게시물 조회해서 실제로 열람하는 행위임
SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT
    , TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED
FROM TBL_STAR
WHERE NUM=3;

--한줄 구성
SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_STAR WHERE NUM=3
;


-- 특정 게시물을 삭제하는 쿼리문 구성
DELETE
FROM TBL_STAR
WHERE NUM=12;

--한 줄 구성
DELETE FROM TBL_STAR WHERE NUM=12
;


--특정 게시물을 수정하는 쿼리문 구성
--(게시물 상세보기 페이지 -> Article.jsp 내에서의 처리)
--작성자, 패스워드, 이메일, 제목, 내용
UPDATE TBL_STAR
SET NAME='가나다', PWD='1234', EMAIL='ganada@test.com', SUBJECT='수정제목', CONTENT='수정내용'
WHERE NUM=5;

--한 줄 구성
UPDATE TBL_ SET NAME='가나다', PWD='1234', EMAIL='ganada@test.com', SUBJECT='수정제목', CONTENT='수정내용' WHERE NUM=5
;


--특정 게시물(50)의 다음 번호 읽어오는 쿼리문 구성
SELECT NVL(MIN(NUM), -1) NEXTNUM
FROM TBL_STAR
WHERE NUM>=50;

--한줄로 만들기
SELECT NVL(MIN(NUM), -1) NEXTNUM FROM TBL_STAR WHERE NUM>=50
;


--특정 게시물(50)의 이전 번호 읽어오는 쿼리문 구성
SELECT NVL(MAX(NUM), -1)BEFORENUM
FROM TBL_STAR
WHERE NUM<50;

--한줄로 만들기
SELECT NVL(MAX(NUM), -1)BEFORENUM FROM TBL_STAR WHERE NUM<50
;





--------------------------------------------------------------------------------------

SELECT USER
FROM DUAL;



--실습 진행간 테스트--
SELECT COUNT(*) AS COUNT
FROM TBL_STAR;
-->>0

--게시물 작성
INSERT INTO TBL_BOARD(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED)
VALUES(1, '가나다', '1234', 'ganada@test.com', '작성테스트', '내용물작성', 'localhost', 0, SYSDATE);
--1 행 이(가) 삽입되었습니다.


SELECT *
FROM TBL_STAR;

--게시물 삭제
DELETE
FROM TBL_STAR;
--1 행 이(가) 삭제되었습니다.

COMMIT;
--커밋 완료.

--프로시저 실행 이후 테이블 조회
SELECT *
FROM TBL_STAR;

--검색 데이터 갯수 조회 쿼리문 구성
SELECT COUNT(*) AS COUNT
FROM TBL_STAR
WHERE SUBJECT LIKE '%음식%';


-->한 줄 구성
SELECT COUNT(*) AS COUNT FROM TBL_STAR WHERE SUBJECT LIKE '%음식%'
;




