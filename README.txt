#--AWS EC2내에서 바로 빌드할 수도 있기 때문에 폴더 내 실행파일을 삭제하면 안됩니다--#
@UserController
  ※비밀번호 변경
  url : "/user/changePw"
  {account, password}받음

  return: status(200).body(String) //변경 성공
  return: status(404).body(String) //아이디 못찾았을때
  return: status(400).body(String) //데이터베이스 오류



  ※유저 정보 불러오기
  url: "/user/{account}"

  return: status(200).body(data) : 정상적으로 확인
  //data.userImage = String (jpg -> encoding(base64) -> String)
  //data.userTheme = String
  return: status(400).body(data) : 데이터베이스 오류

  //중요한것: 프로필을 올린적이 없으면 userImage = null
  //혹시 null말고 문자열 다르게 설정하는게 나으면 알려주세요
