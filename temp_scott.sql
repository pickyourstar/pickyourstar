SELECT USER
FROM DUAL;


--�Խ��� ���� ���̺� ����
/*
CREATE TABLE TBL_BOARD
(NUM            NUMBER(9)               NOT NULL   --�Խù� ��ȣ
,NAME           VARCHAR2(30)            NOT NULL   --�Խù� �ۼ���
,PWD            VARCHAR2(20)            NOT NULL   --�Խù� ��ȣ
,EMAIL          VARCHAR2(50)                        --�ۼ��� �̸���
,SUBJECT        VARCHAR2(100)           NOT NULL   --�Խù� ����
,CONTENT        VARCHAR2(4000)          NOT NULL   --�Խù� ����
,IPADDR         VARCHAR2(20)                        --������ Ŭ���̾�Ʈ IP �ּ�
,HITCOUNT       NUMBER      DEFAULT 0           NOT NULL  --�Խù� ��ȸ��
,CREATED        DATE        DEFAULT SYSDATE     NOT NULL  --�Խù� �ۼ���
,CONSTRAINT BOARD_NUM_PK PRIMARY KEY(NUM)                  --�Խù� ��ȣ�� PK �������� ����
);
*/
--Table TBL_BOARD��(��) �����Ǿ����ϴ�.


DROP TABLE TBL_TEMP PURGE;
--Table TBL_TEMP��(��) �����Ǿ����ϴ�.


CREATE TABLE TBL_STAR
(NUM            NUMBER(9)               NOT NULL   --�Խù� ��ȣ
,NAME           VARCHAR2(30)            NOT NULL   --�Խù� �ۼ���
,PWD            VARCHAR2(20)            NOT NULL   --�Խù� ��ȣ
,EMAIL          VARCHAR2(50)                        --�ۼ��� �̸���
,SUBJECT        VARCHAR2(100)           NOT NULL   --�Խù� ����
,CONTENT        VARCHAR2(4000)          NOT NULL   --�Խù� ����
,IPADDR         VARCHAR2(20)                        --������ Ŭ���̾�Ʈ IP �ּ�
,HITCOUNT       NUMBER      DEFAULT 0           NOT NULL  --�Խù� ��ȸ��
,CREATED        DATE        DEFAULT SYSDATE     NOT NULL  --�Խù� �ۼ���
,CONSTRAINT STAR_NUM_PK PRIMARY KEY(NUM)                  --�Խù� ��ȣ�� PK �������� ����
);
--Table TBL_STAR��(��) �����Ǿ����ϴ�.





--�Խù� ��ȣ�� �ִ밪�� ���� ������ ����
SELECT NVL(MAX(NUM), 0) AS MAXNUM
FROM TBL_STAR;

--���ٱ���
SELECT NVL(MAX(NUM), 0) AS MAXNUM FROM TBL_STAR
;




--�Խù� �ۼ� ������ ����
INSERT INTO TBL_STAR(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED)
VALUES(1, '������', '1234', 'ganada@test.com', '�ۼ��׽�Ʈ', '�Խù������ۼ�', 'localhost', 0, SYSDATE);
--1 �� ��(��) ���ԵǾ����ϴ�.

--���ٱ���
INSERT INTO TBL_STAR(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED) VALUES(1, '������', '1234', 'ganada@test.com', '�ۼ��׽�Ʈ', '�Խù������ۼ�', 'localhost', 0, SYSDATE)
;




--DB ���ڵ��� ������ �������� ������ ����
SELECT COUNT(*) AS COUNT
FROM TBL_STAR;

--���ٱ���
SELECT COUNT(*) AS COUNT FROM TBL_STAR
;





--               1       10
--Ư�� ������(���۹�ȣ~����ȣ) �Խù� ����� �о���� ������ ����
--��ȣ, �ۼ���, ����, ��ȸ��, �ۼ���

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

--���� ����
SELECT * FROM (SELECT ROWNUM RNUM, DATA.* FROM ( SELECT NUM, NAME, SUBJECT, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_STAR ORDER BY NUM DESC) DATA) WHERE RNUM>=1 AND RNUM<=10
;



--Ư�� �Խù� ��ȸ�� ���� ��ȸ Ƚ�� ���� ������ ����
UPDATE TBL_STAR
SET HITCOUNT = HITCOUNT + 1   --����Ŭ�� HITCOUNT += 1 Ȥ�� HITCOUNT++ �� �����Ƿ�...
WHERE NUM=1;

--���� ����
UPDATE TBL_STAR SET HITCOUNT = HITCOUNT + 1 WHERE NUM=1
;


--������ �α�ȭ~~!! �ؼ� �ߺ��Ͽ� ��ȸ�� ������ �ø��� ����~~
--��ȸ�� �� ���� �ش� ����� ��ȣ ���� �α׷� ��ϵ�~~~


--Ư�� �Խù��� ������ �о���� ������ ����
--�Խù� ��ȸ�ؼ� ������ �����ϴ� ������
SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT
    , TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED
FROM TBL_STAR
WHERE NUM=3;

--���� ����
SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_STAR WHERE NUM=3
;


-- Ư�� �Խù��� �����ϴ� ������ ����
DELETE
FROM TBL_STAR
WHERE NUM=12;

--�� �� ����
DELETE FROM TBL_STAR WHERE NUM=12
;


--Ư�� �Խù��� �����ϴ� ������ ����
--(�Խù� �󼼺��� ������ -> Article.jsp �������� ó��)
--�ۼ���, �н�����, �̸���, ����, ����
UPDATE TBL_STAR
SET NAME='������', PWD='1234', EMAIL='ganada@test.com', SUBJECT='��������', CONTENT='��������'
WHERE NUM=5;

--�� �� ����
UPDATE TBL_ SET NAME='������', PWD='1234', EMAIL='ganada@test.com', SUBJECT='��������', CONTENT='��������' WHERE NUM=5
;


--Ư�� �Խù�(50)�� ���� ��ȣ �о���� ������ ����
SELECT NVL(MIN(NUM), -1) NEXTNUM
FROM TBL_STAR
WHERE NUM>=50;

--���ٷ� �����
SELECT NVL(MIN(NUM), -1) NEXTNUM FROM TBL_STAR WHERE NUM>=50
;


--Ư�� �Խù�(50)�� ���� ��ȣ �о���� ������ ����
SELECT NVL(MAX(NUM), -1)BEFORENUM
FROM TBL_STAR
WHERE NUM<50;

--���ٷ� �����
SELECT NVL(MAX(NUM), -1)BEFORENUM FROM TBL_STAR WHERE NUM<50
;





--------------------------------------------------------------------------------------

SELECT USER
FROM DUAL;



--�ǽ� ���ణ �׽�Ʈ--
SELECT COUNT(*) AS COUNT
FROM TBL_STAR;
-->>0

--�Խù� �ۼ�
INSERT INTO TBL_BOARD(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED)
VALUES(1, '������', '1234', 'ganada@test.com', '�ۼ��׽�Ʈ', '���빰�ۼ�', 'localhost', 0, SYSDATE);
--1 �� ��(��) ���ԵǾ����ϴ�.


SELECT *
FROM TBL_STAR;

--�Խù� ����
DELETE
FROM TBL_STAR;
--1 �� ��(��) �����Ǿ����ϴ�.

COMMIT;
--Ŀ�� �Ϸ�.

--���ν��� ���� ���� ���̺� ��ȸ
SELECT *
FROM TBL_STAR;

--�˻� ������ ���� ��ȸ ������ ����
SELECT COUNT(*) AS COUNT
FROM TBL_STAR
WHERE SUBJECT LIKE '%����%';


-->�� �� ����
SELECT COUNT(*) AS COUNT FROM TBL_STAR WHERE SUBJECT LIKE '%����%'
;




