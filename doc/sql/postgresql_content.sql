DROP TABLE IF EXISTS DEMO_VIDEO_TB CASCADE;
CREATE TABLE DEMO_VIDEO_TB (
	IDX NUMERIC NOT NULL, /* IDX */
	OBJECTID VARCHAR(255), /* OBJECTID */
	TITLE VARCHAR(255), /* 제목 */
	CONTENT TEXT, /* 내용 */

	ORIFILENAME VARCHAR(255), /* 원본 파일명 */
  ASSETFILENAME VARCHAR(255), /* 에셋 파일명 */
  ASSETFILEPATH VARCHAR(255), /* 에셋 파일경로 */
  ASSETFILESIZE NUMERIC, /* 에셋 파일크기 */
  VOLUMEWIN VARCHAR(255), /* 경로1 */
  VOLUMEETC VARCHAR(255), /* 경로2 */

  TRANSCODINGENDTIME TIMESTAMP WITHOUT TIME ZONE, /* 트랜스코딩 완료시각 */
  TRANSCODINGSTARTTIME TIMESTAMP WITHOUT TIME ZONE, /* 트랜스코딩 시작시각 */
  TRANSCODINGSTATUS NUMERIC, /* 트랜스코딩 상태 */

  CATALOGENDTIME TIMESTAMP WITHOUT TIME ZONE, /* 카탈로깅 완료시각 */
  CATALOGSTARTTIME TIMESTAMP WITHOUT TIME ZONE, /* 카탈로깅 시작시각 */
  CATALOGSTATUS NUMERIC, /* 카탈로깅 상태 */

  CREATETIME TIMESTAMP WITHOUT TIME ZONE, /* 생성시각 */
  CREATEUSER NUMERIC, /* 생성자 */

  MEDIAINFO JSONB, /* 미디어정보 */
  GENRE JSONB, /* 분류 */

  DELFLAG BOOLEAN
);
COMMENT ON TABLE DEMO_VIDEO_TB IS '비디오';

COMMENT ON COLUMN DEMO_VIDEO_TB.IDX IS '비디오 ID';
COMMENT ON COLUMN DEMO_VIDEO_TB.OBJECTID IS 'OBJECT ID';
COMMENT ON COLUMN DEMO_VIDEO_TB.TITLE IS '제목';
COMMENT ON COLUMN DEMO_VIDEO_TB.CONTENT IS '내용';

COMMENT ON COLUMN DEMO_VIDEO_TB.ORIFILENAME IS '원본 파일명';
COMMENT ON COLUMN DEMO_VIDEO_TB.ASSETFILENAME IS '에셋 파일명';
COMMENT ON COLUMN DEMO_VIDEO_TB.ASSETFILEPATH IS '에셋 파일경로';
COMMENT ON COLUMN DEMO_VIDEO_TB.ASSETFILESIZE IS '에셋 파일크기';
COMMENT ON COLUMN DEMO_VIDEO_TB.VOLUMEWIN IS '윈도우 볼륨';
COMMENT ON COLUMN DEMO_VIDEO_TB.VOLUMEETC IS '리눅스 볼륨'; 

COMMENT ON COLUMN DEMO_VIDEO_TB.CATALOGENDTIME IS '카탈로깅 완료시각';
COMMENT ON COLUMN DEMO_VIDEO_TB.CATALOGSTARTTIME IS '카탈로깅 시작시각';
COMMENT ON COLUMN DEMO_VIDEO_TB.CATALOGSTATUS IS '카탈로깅 상태';

COMMENT ON COLUMN DEMO_VIDEO_TB.TRANSCODINGENDTIME IS '트랜스코딩 완료시각';
COMMENT ON COLUMN DEMO_VIDEO_TB.TRANSCODINGSTARTTIME IS '트랜스코딩 시작시각';
COMMENT ON COLUMN DEMO_VIDEO_TB.TRANSCODINGSTATUS IS '트랜스코딩 상태';

ALTER TABLE DEMO_VIDEO_TB ADD CONSTRAINT PK_DEMO_VIDEO_TB PRIMARY KEY ( IDX );
CREATE INDEX VIDEO_DELFLAG_IDX ON DEMO_VIDEO_TB ( DELFLAG ASC, GENRE ASC );




/* 샷 */
DROP TABLE IF EXISTS DEMO_SHOT_TB CASCADE;
CREATE TABLE DEMO_SHOT_TB (
	SHOTID NUMERIC NOT NULL, /* 샷 ID */
	VIDEOID NUMERIC NOT NULL, /* 비디오 ID */

	CONTENT TEXT, /* 내용 */

	STARTTIMECODE VARCHAR(50), /* 시작 타임코드 */
	STARTFRAMEINDEX NUMERIC, /* 시작 프레임 인덱스 */
	ENDTIMECODE VARCHAR(50), /* 끝 타임코드 */
	ENDFRAMEINDEX NUMERIC, /* 끝 프레임 인덱스 */

  ASSETFILENAME VARCHAR(255), /* 에셋 파일명 */
  ASSETFILEPATH VARCHAR(255), /* 에셋 파일경로 */

	OBJECT JSONB, /* JSON STRING */
	DELFLAG BOOLEAN  /* 삭제 여부 */

);

COMMENT ON TABLE DEMO_SHOT_TB IS '샷';
COMMENT ON COLUMN DEMO_SHOT_TB.SHOTID IS '샷 ID';
COMMENT ON COLUMN DEMO_SHOT_TB.VIDEOID IS '비디오 ID';
COMMENT ON COLUMN DEMO_SHOT_TB.CONTENT IS '내용';

COMMENT ON COLUMN DEMO_SHOT_TB.STARTTIMECODE IS '시작 타임코드';
COMMENT ON COLUMN DEMO_SHOT_TB.STARTFRAMEINDEX IS '시작 프레임 인덱스';
COMMENT ON COLUMN DEMO_SHOT_TB.ENDTIMECODE IS '끝 타임코드';
COMMENT ON COLUMN DEMO_SHOT_TB.ENDFRAMEINDEX IS '끝 프레임 인덱스';

COMMENT ON COLUMN DEMO_SHOT_TB.ASSETFILENAME IS '에셋 파일명';
COMMENT ON COLUMN DEMO_SHOT_TB.ASSETFILEPATH IS '에셋 파일경로';

COMMENT ON COLUMN DEMO_SHOT_TB.OBJECT IS '객체정보';
COMMENT ON COLUMN DEMO_SHOT_TB.DELFLAG IS '삭제 여부';

ALTER TABLE DEMO_SHOT_TB	ADD CONSTRAINT PK_DEMO_SHOT_TB PRIMARY KEY (SHOTID);
CREATE INDEX SHOT_VIDEOID_IDX ON DEMO_SHOT_TB ( VIDEOID ASC );
CREATE INDEX SHOT_DELFLAG_IDX	ON DEMO_SHOT_TB ( DELFLAG ASC );




CREATE  SEQUENCE SEQ_VIDEO_TB START 1;
