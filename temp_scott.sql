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

CREATE TABLE TBL_TEMP
(	STAR_NUMBER	VARCHAR2(30)  NOT NULL      --�Խù� ��ȣ
,	STAR_TITLE  	VARCHAR2(30)	  NOT NULL      --�Խù� ����
,	STAR_CONTENT	   VARCHAR2(1000)	NOT NULL  --�Խù� ����
,	STAR_IMAGE	    VARCHAR2(30) 	NOT NULL  --�Խù� ����
,	STAR_COUNT	    NUMBER	 NOT NULL  --�Խù� ��ȸ��
,	STAR_REPLY  	    NUMBER	 NOT NULL  --�Խù� ��ۼ�
,	STAR_WRITE  	 DATE	 NOT NULL  --�Խù� �ۼ���
,	STAR_MODIFY	 DATE	 NULL      --�Խù� ������
,	STAR_DELETE  DATE	 NULL      --�Խù� ������
,	WRITER	     VARCHAR2(10)	 NOT NULL     --�Խù� �ۼ���
,   CONSTRAINT TEMP_STAR_NUMBER_PK PRIMARY KEY(STAR_NUMBER)   --�Խù� ��ȣ�� PK �������� ����
);
--Table TBL_TEMP��(��) �����Ǿ����ϴ�.



--�Խù� ��ȣ�� �ִ밪�� ���� ������ ����
SELECT NVL(MAX(STAR_NUMBER), 0) AS MAXNUM
FROM TBL_TEMP;

--���ٱ���
SELECT NVL(MAX(STAR_NUMBER), 0) AS MAXNUM FROM TBL_TEMP
;


--�Խù� �ۼ� ������ ����
INSERT INTO TBL_TEMP(STAR_NUMBER, STAR_TITLE, STAR_CONTENT, STAR_IMAGE, STAR_COUNT, STAR_REPLY, STAR_WRITE, STAR_MODIFY, STAR_DELETE, WRITER)
VALUES(1, '����������', '�����������', '', 0, 0, SYSDATE,  '' , '' ,'SOMIN');


--DB ���ڵ��� ������ �������� ������ ����
SELECT COUNT(*) AS COUNT
FROM TBL_TEMP;

--���ٱ���
SELECT COUNT(*) AS COUNT FROM TBL_TEMP
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
        SELECT STAR_NUMBER, STAR_TITLE, WRITER, STAR_COUNT, TO_CHAR(STAR_WRITE,'YYYY-MM-DD') AS STAR_WRITE
        FROM TBL_TEMP
        ORDER BY STAR_NUMBER DESC
    ) DATA
)
WHERE RNUM>=1 AND RNUM<=10;

--���� ����
SELECT * FROM (SELECT ROWNUM RNUM, DATA.* FROM ( SELECT NUM, NAME, SUBJECT, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_BOARD ORDER BY NUM DESC) DATA) WHERE RNUM>=1 AND RNUM<=10
;



--==============================================================================================================================
-- �Ʒ��� ���� �������� ���� �κ���
--==============================================================================================================================




--Ư�� �Խù� ��ȸ�� ���� ��ȸ Ƚ�� ���� ������ ����
UPDATE TBL_BOARD
SET HITCOUNT = HITCOUNT + 1   --����Ŭ�� HITCOUNT += 1 Ȥ�� HITCOUNT++ �� �����Ƿ�...
WHERE NUM=1;

--���� ����
UPDATE TBL_BOARD SET HITCOUNT = HITCOUNT + 1 WHERE NUM=1
;


--������ �α�ȭ~~!! �ؼ� �ߺ��Ͽ� ��ȸ�� ������ �ø��� ����~~
--��ȸ�� �� ���� �ش� ����� ��ȣ ���� �α׷� ��ϵ�~~~


--Ư�� �Խù��� ������ �о���� ������ ����
--�Խù� ��ȸ�ؼ� ������ �����ϴ� ������
SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT
    , TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED
FROM TBL_BOARD
WHERE NUM=3;

--���� ����
SELECT NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, TO_CHAR(CREATED, 'YYYY-MM-DD') AS CREATED FROM TBL_BOARD WHERE NUM=3
;


-- Ư�� �Խù��� �����ϴ� ������ ����
DELETE
FROM TBL_BOARD
WHERE NUM=12;

--�� �� ����
DELETE FROM TBL_BOARD WHERE NUM=12
;


--Ư�� �Խù��� �����ϴ� ������ ����
--(�Խù� �󼼺��� ������ -> Article.jsp �������� ó��)
--�ۼ���, �н�����, �̸���, ����, ����
UPDATE TBL_BOARD
SET NAME='��μ�', PWD='1234', EMAIL='kms@test.com', SUBJECT='��������', CONTENT='��������'
WHERE NUM=5;

--�� �� ����
UPDATE TBL_BOARD SET NAME='��μ�', PWD='1234', EMAIL='kms@test.com', SUBJECT='��������', CONTENT='��������' WHERE NUM=5
;


--Ư�� �Խù�(50)�� ���� ��ȣ �о���� ������ ����
SELECT NVL(MIN(NUM), -1) NEXTNUM
FROM TBL_BOARD
WHERE NUM>=50;

--���ٷ� �����
SELECT NVL(MIN(NUM), -1) NEXTNUM FROM TBL_BOARD WHERE NUM>=50
;


--Ư�� �Խù�(50)�� ���� ��ȣ �о���� ������ ����
SELECT NVL(MAX(NUM), -1)BEFORENUM
FROM TBL_BOARD
WHERE NUM<50;

--���ٷ� �����
SELECT NVL(MAX(NUM), -1)BEFORENUM FROM TBL_BOARD WHERE NUM<50
;





--------------------------------------------------------------------------------------

SELECT USER
FROM DUAL;



--�ǽ� ���ణ �׽�Ʈ--
SELECT COUNT(*) AS COUNT
FROM TBL_BOARD;
-->>0

--�Խù� �ۼ�
INSERT INTO TBL_BOARD(NUM, NAME, PWD, EMAIL, SUBJECT, CONTENT, IPADDR, HITCOUNT, CREATED)
VALUES(1, '������', '1234', 'kth@test.com', '�ۼ��׽�Ʈ', '���빰�ۼ�', '211.238.142.154', 0, SYSDATE);
--1 �� ��(��) ���ԵǾ����ϴ�.

COMMIT;

ROLLBACK;



SELECT *
FROM TBL_BOARD;

--�Խù� ����
DELETE
FROM TBL_BOARD;
--1 �� ��(��) �����Ǿ����ϴ�.

COMMIT;
--Ŀ�� �Ϸ�.

--���ν��� ���� ���� ���̺� ��ȸ
SELECT *
FROM TBL_BOARD;

--�˻� ������ ���� ��ȸ ������ ����
SELECT COUNT(*) AS COUNT
FROM TBL_BOARD
WHERE SUBJECT LIKE '%����%';
-->�� �� ����
SELECT COUNT(*) AS COUNT FROM TBL_BOARD WHERE SUBJECT LIKE '%����%'
;




