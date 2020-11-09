package com.konantech.spring.entities;

public interface ErrorCode {
    // User
    int ERR_1001_ALREADY_REGISTERED_EMAIL       = 1001; // 이미 등록된 이메일
    int ERR_1002_NOT_EXIST_EMAIL                = 1002; // 존재하지 않는 이메일
    int ERR_1003_NOT_EQUALS_PASSWORD            = 1003; // 패스워드가 일치하지 않음
    int ERR_1201_EMPTY_API_KEY                  = 1201; // API키가 빈값
    int ERR_1202_NOT_EXIST_API_KEY              = 1202; // 존재하지 않는 API키
    int ERR_1203_INVALID_USERS_TYPE             = 1203; // 잘못된 유저타입

    // Check
    int ERR_2001_INPUT_PARAM_FIELD_IS_EMPTY     = 2001; // 빈 입력 필드
    int ERR_2002_INPUT_PARAM_LIMIT_MINIMUM      = 2002; // 최소 입력문자 제한
    int ERR_2003_INPUT_PARAM_LIMIT_MAXIMUM      = 2003; // 최대 입력문자 제한
    int ERR_2004_INPUT_PARAM_USE_FORBIDDEN_CHAR = 2004; // 입력제한 문자
    int ERR_2005_INVALID_EMAIL_FORM             = 2005; // 올바르지 않은 이메일 형식

    // Commons
    int ERR_6001_INVALID_PARAMETERS             = 6001; // 파라미터에러

    // Service
    int ERR_7001_NOT_REGISTERED                 = 7001; // 등록하지 않은 유저
    int ERR_7003_SLACK_SEARCH_KEYWORD           = 7003; // 키워드가 입력되지 않음
    int ERR_7005_NOT_SELECT_AGENT               = 7005; // 검색할 에이전트가 선택되지 않음
    int ERR_7006_ALREADY_ADDED_TO_USER          = 7006; // 해당 유저에게 이미 추가되어 있는 슬랙 서비스
    int ERR_7007_ACCESS_ERROR                   = 7007; // 유효하지 않은 코드
    int ERR_7010_COMPSERVER_ERROR               = 7010; // 컴포넌트 오류

    // Agent
    int ERR_8005_FILE_NOT_FOUND                 = 8005; // 존재하지 않는 파일
    int ERR_8999_UNKNOWN                        = 8999; // 기타 에러
}
