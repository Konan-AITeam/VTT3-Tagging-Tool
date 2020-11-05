# (주)코난테크놀로지 VTT 클라이언트

비디오 등록 관리및, 비디오 Shot 을 관리하는 페이지 입니다.

JAVA 프로젝트로, Gradle 프로젝트로 오픈하면 됩니다.

## 서비스 주소

> 1. [VTT 클라이언트 데모] 접속 [ http://183.110.246.21:8080/darc4/ ]
> 1. 로그인 -> admin / admin 로긴

콘텐츠 등록은 비디오 파일만 등록 가능합니다.
비디오 파일은 현재 트랜스코더가 연결안된 관계로, `mp4` 파일만 등록하세요.


서버목록
>- [client] VTT 클라이언트 서버 
>- [db] Postgresql 서버
>- [tc] Transcoder 서버
>- [ca] Catalog 서버
>- [st] Streaming 서버


개발환경
>- Intellij IDEA 환경
>- gradle 프로젝트 열기
>- lombok 라이브러리 사용 ex) @Data 
>- Proferences > Build, Excution, Depoloyment > Compiler > Annotation Processors > Enable annotation processing (체크)

빌드
```
$ gradle clean
$ gradle build -x test
$ ls ./build/libs/vtt-client-web-1.0.war
  ./build/libs/vtt-client-web-1.0.war
$
```

DATABASE
> 1. source > doc > sql > *.sql 파일로 테이블 생성, 기본데이타 구조외
> 1.  