# <img src="https://avatars.githubusercontent.com/u/138138861?s=200&v=4" width="70" height="70"/> 참지마요
**유, 무료 화장실 중개 앱**
<br><br>

## 📖 Development Tech
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
<br>

# 💼 Server Architecture
<img src="https://velog.velcdn.com/images/jmjmjmz732002/post/a6c7a7be-ff27-4723-bfe2-d458ed641fab/image.png">
<br>


# 📝 Service

**동영상을 GIF로 바꾸는 과정이 원할하지 않아서 화질이 좋지 않은은 점 먼저 사과 드립니다.**

## 1. 로그인 및 회원가입
카카오 및 네이버를 통한 SNS 로그인을 제공합니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/6f7b359c-8e63-452b-8b07-10ca919e5354/image.gif">
<br>

## 2-1. 주변 화장실 찾기 (검색하기)
T MAP API를 이용한 검색 기능과 현재 위치를 사용한 지도 정보 제공합니다.<br>
(무료 화장실의 정보는 공공 데이터 포털을 사용하여 DB에 저장하였습니다.)

<img src = "https://velog.velcdn.com/images/hyuntae99/post/097f7970-5a48-43d1-abe5-1743745b3690/image.gif">
<br>

## 2-1. 주변 화장실 찾기 (현재 지도상 위치 사용)
<img src = "https://velog.velcdn.com/images/hyuntae99/post/cac8a8a6-1505-4d47-8600-8361479503a2/image.gif">
<br>

## 3-1. 화장실 상세 정보 (상세 정보 확인)
화장실의 좌변기, 소변기, 거울, 세면대 등의 상세 정보를 제공합니다.<br>
해당 화장실의 리뷰 확인 기능을 제공합니다,

<img src = "https://velog.velcdn.com/images/hyuntae99/post/5a32eeee-00cb-4e8d-90a4-8b95105ae325/image.gif">
<br>

## 3-2. 화장실 상세 정보 (리뷰 확인)
<img src = "https://velog.velcdn.com/images/hyuntae99/post/4a110f4f-4c50-4168-92dd-d5501256255d/image.gif">
<br>

## 4-1. 유료 화장실 QR 결제하기 
미리 충전해둔 포인트를 통해서 결제합니다.<br>
유료 화장실을 등록하신 영업자에게 QR 도어락을 제공하여 QR로 화장실을 개방하도록 설계하였습니다.<br>
(QR 도어락의 경우, 제작이 가능하다고 연락을 받았지만 아직 베타버전의 어플이라서 실물로 구현하지는 못했습니다.)

<img src = "https://velog.velcdn.com/images/hyuntae99/post/1f5c7c09-aa59-4beb-b966-b2e4d777c575/image.gif">
<br>

## 4-2. 결제 후 미니게임 및 사용 후기 작성
화장실을 이용하면서 심심하실까봐 간단한 두더지 게임도 만들었습니다.<br>
이후 화장실 이용이 끝나면 리뷰를 요청하는 알림이 나오게 만들었습니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/25bdeacf-aa71-4d66-b6db-00f53b87e351/image.gif">
<br>

## 5. 리뷰 등록하기
최대한 간단하면서 필요한 정보를 얻을 수 있게 만들었습니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/cb85bed4-b706-43ee-b9e8-eddacb6ec58e/image.gif" width="400" height="600"/>
<br>

## 6-1. 근처 화장실 찾기 (거리순, 별점 높은 순, 별점 낮은 순)
근처 화장실 리스트를 제공합니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/cf2d4dfb-3bb0-44aa-8465-3cc05ef57eb3/image.gif">
<br>

## 6-2. 근처 화장실 찾기 (거리 범위 설정하기)
근처 화장실 리스트를 제공하는 거리의 범위를 설정할 수 있습니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/b9a71888-cb4b-46ae-b88e-5bdac94a6c96/image.gif">
<br>

## 7. 커뮤니티 (미구현)
현재 기본 페이지만 구현했으며 추후 커뮤니티 기능도 추가할 생각입니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/2e3e5834-a831-4f71-8ebf-364df6f55c48/image.gif">
<br>

## 8. 마이페이지 (개인정보 수정, 리뷰 내역, 사용 내역) 및 포인트 충전 (구글 플레이스토어) 
마이페이지에서는 개인정보 수정과 리뷰 내역, 사용 내역을 확인할 수 있습니다.<br>
또한 이용에 필요한 포인트를 구글 플레이스토어와 연동하여 결제하도록 만들었습니다.

<img src = "https://velog.velcdn.com/images/hyuntae99/post/c9feedb9-7c97-481a-a7c2-10a65f386f5e/image.gif">
<br>


