

create sequence SEQ_SECTION_INFO_TB;

create sequence SEQ_SECTION_DEPICTION_TB;

create sequence SEQ_SECTION_QA_TB;

create sequence SEQ_SECTION_RELATION_TB;

create sequence SEQ_SOUND_TB;


create table vttm_section_info_tb
(
  sectionid       numeric not null
    constraint pk_vttm_section_info_tb
    primary key,
  videoid         numeric not null,
  startshotid     numeric,
  endshotid       numeric,
  starttimecode   varchar(50),
  startframeindex numeric,
  endtimecode     varchar(50),
  endframeindex   numeric,
  userid          varchar,
  sectionname     varchar(10)
);

comment on table vttm_section_info_tb
is 'QA구간정보';

comment on column vttm_section_info_tb.sectionid
is '구간ID';

comment on constraint pk_vttm_section_info_tb
on vttm_section_info_tb
is 'QA구간 기본키';

comment on column vttm_section_info_tb.videoid
is '비디오ID';

comment on constraint vttm_section_info_tb_vttm_video_tb_fk
on vttm_section_info_tb
is 'QA구간->비디오 참조키';

comment on column vttm_section_info_tb.starttimecode
is '사작타임코드';

comment on column vttm_section_info_tb.startframeindex
is '시작프레임인덱스';

comment on column vttm_section_info_tb.endtimecode
is '종료타임코드';

comment on column vttm_section_info_tb.endframeindex
is '종료프레임인덱스';

comment on column vttm_section_info_tb.userid
is '사용자id';

comment on column vttm_section_info_tb.sectionname
is '구간명';

comment on column vttm_section_info_tb.startshotid
is '시작구간';

comment on column vttm_section_info_tb.endshotid
is '종료구간';




create table vttm_section_depiction_tb
(
  depictionid numeric not null,
  sectionid    numeric not null
    constraint vttm_section_depiction_tb_vttm_section_info_tb_fk
    references vttm_section_info_tb,
  depiction text,
  userid varchar,
  constraint pk_vttm_section_depiction_tb
  primary key (depictionid)
);

comment on table vttm_section_depiction_tb
is 'QA구간묘사';

comment on column vttm_section_depiction_tb.depictionid
is '묘사ID';

comment on column vttm_section_depiction_tb.sectionid
is '구간ID';

comment on column vttm_section_depiction_tb.depiction
is '묘사';

comment on column vttm_section_depiction_tb.userid
is '사용자id';

comment on constraint pk_vttm_section_depiction_tb
on vttm_section_depiction_tb
is 'QA구간묘사 기본키';

comment on constraint vttm_section_depiction_tb_vttm_section_info_tb_fk
on vttm_section_depiction_tb
is '묘사->QA구간 참조키';



create table vttm_section_qa_tb
(
  questionid numeric not null,
  sectionid    numeric not null
    constraint vttm_section_qa_tb_vttm_section_info_tb_fk
    references vttm_section_info_tb,
  question text,
  answer text,
  wrong_answer1 text,
  wrong_answer2 text,
  wrong_answer3 text,
  wrong_answer4 text,
  userid varchar,
  constraint pk_vttm_section_qa_tb
  primary key (questionid)
);

comment on table vttm_section_qa_tb
is 'QA구간질문';

comment on column vttm_section_qa_tb.questionid
is '질문ID';

comment on column vttm_section_qa_tb.sectionid
is '구간ID';

comment on column vttm_section_qa_tb.question
is '질문';

comment on column vttm_section_qa_tb.answer
is '답변';

comment on column vttm_section_qa_tb.wrong_answer1
is '오답1';

comment on column vttm_section_qa_tb.wrong_answer2
is '오답2';

comment on column vttm_section_qa_tb.wrong_answer3
is '오답3';

comment on column vttm_section_qa_tb.wrong_answer4
is '오답4';

comment on column vttm_section_qa_tb.userid
is '사용자id';

comment on constraint pk_vttm_section_qa_tb
on vttm_section_qa_tb
is 'QA구간질문 기본키';

comment on constraint vttm_section_qa_tb_vttm_section_info_tb_fk
on vttm_section_qa_tb
is '질문->QA구간 참조키';



create table vttm_section_relation_tb
(
  relationid numeric not null,
  videoid numeric not null
  constraint vttm_section_relation_tb_subject_vttm_video_tb_fk
  references vttm_video_tb,
  subject_sectionid    numeric not null
    constraint vttm_section_relation_tb_subject_vttm_section_info_tb_fk
    references vttm_section_info_tb,
  object_sectionid    numeric not null
    constraint vttm_section_relation_tb_object_vttm_section_info_tb_fk
    references vttm_section_info_tb,
  relationcode varchar(30),
  userid varchar,
  constraint pk_vttm_section_relation_tb
  primary key (relationid)
);

comment on table vttm_section_relation_tb
is 'QA구간관계';

comment on column vttm_section_relation_tb.relationid
is '관계ID';

comment on column vttm_section_relation_tb.videoid
is '비디오ID';

comment on column vttm_section_relation_tb.subject_sectionid
is '주체구간ID';

comment on column vttm_section_relation_tb.object_sectionid
is '객체구간ID';

comment on column vttm_section_relation_tb.relationcode
is '관계코드';

comment on column vttm_section_relation_tb.userid
is '사용자id';

comment on constraint pk_vttm_section_relation_tb
on vttm_section_relation_tb
is 'QA구간관계 기본키';

comment on constraint vttm_section_relation_tb_subject_vttm_video_tb_fk
on vttm_section_relation_tb
is '관계 객체구간->비디오 참조키';


comment on constraint vttm_section_relation_tb_subject_vttm_section_info_tb_fk
on vttm_section_relation_tb
is '관계 주체구간->QA구간 참조키';

comment on constraint vttm_section_relation_tb_object_vttm_section_info_tb_fk
on vttm_section_relation_tb
is '관계 객체구간->QA구간 참조키';


create table vttm_sound_tb
(
  soundid numeric not null,
  videoid    numeric not null
    constraint vttm_sound_tb_vttm_video_tb_fk
    references vttm_video_tb,
  starttimecode varchar(50),
  endtimecode varchar(50),
  soundtype varchar(30),
  userid varchar,
  constraint pk_vttm_sound_tb
  primary key (soundid)
);

comment on table vttm_sound_tb
is '소리정보';

comment on column vttm_sound_tb.soundid
is '소리ID';

comment on column vttm_sound_tb.videoid
is '비디오ID';

comment on column vttm_sound_tb.starttimecode
is '시작타임코드';

comment on column vttm_sound_tb.endtimecode
is '종료타임코드';

comment on column vttm_sound_tb.soundtype
is '소리타입';

comment on column vttm_sound_tb.userid
is '사용자id';

comment on constraint pk_vttm_sound_tb
on vttm_sound_tb
is '소리정보 기본키';

comment on constraint vttm_sound_tb_vttm_video_tb_fk
on pk_vttm_sound_tb
is '소리->비디오 참조키';


insert into
  vttm_code_tb
  (code, group_code, code_name, code_reference, registed_time, updated_time, use_yn)
values
  (
    'D0102'
    ,'D01'
    ,'관계 타입'
    ,'QA 관계타입 그룹코드'
    ,now()
    ,now()
    ,'Y'
  )
;

insert into
  vttm_code_tb
  (code, group_code, code_name, code_reference, registed_time, updated_time, use_yn)
values
  (
    'D010201'
    ,'D0102'
    ,'causation'
    ,'인과'
    ,now()
    ,now()
    ,'Y'
  )
;

insert into
  vttm_code_tb
  (code, group_code, code_name, code_reference, registed_time, updated_time, use_yn)
values
  (
    'D010202'
    ,'D0102'
    ,'intention'
    ,'의도'
    ,now()
    ,now()
    ,'Y'
  )
;