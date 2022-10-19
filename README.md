# Notichair <br> 
## 시연 영상 - https://youtu.be/-8yfPsHjjrA
**실시간 자세분석 및 교정 스마트체어**


<img src = "https://user-images.githubusercontent.com/79950091/148927494-579f83d3-e8ae-4ec9-956a-529400c2f9b5.png" width= "400" height = "100"/>

> 🏆 PRIME 경진대회 수상 🏆


<br> 

## 요구사항


![방석센서1](https://user-images.githubusercontent.com/74412438/148777486-e8487852-7f4d-4872-810c-7eddaafb777c.png)<br>
**31-cell sensor** (mdxs-16-5610)<br><br>


  
   

## 목차

### 1. 소개
- 프로젝트 내용 소개


### 2. 개발 환경
- 사전 설정 및 환경 구축

### 3. 기능
- Sensors
- App & Server

### 4. 결론
- 결론


<br>

  
## 1. 소개

![image](https://user-images.githubusercontent.com/74412438/148783581-524c4a92-43ee-46f5-b814-6204c159f769.png)


31개의 착석구조로 배열된 cell-sensor에 기반하여 실시간으로 사용자의 자세분석 및 교정을 제공한다. 

대한민국 성인기준 하루 평균 의자에 앉아서 보내는 시간이 8.5시간, 이와 더불어 코로나사회에 맞물려 늘어나는 재택근무 및 단순 여가생활 등 우리의 일상생활은 의자와 더욱 가까워지는 추세이다. 

이러한 환경에서 착석자세를 실시간으로 측정하고 분석해주어 현대인들의 척추건강을 개선시키는 것이 목적이다. 

<br><br>

## 2. 개발 환경

## 버전 확인사항

- 안드로이드 스튜디오 3.4.0 이상의 버전
> [Android Studio Download](http://developer.android.com/studio/index.html)
- API: 21이상
- **필수 조건**<br>
  minSdkVersion 18<br>
  ext.kotlin_version = "1.5.0"
  <br><br>
## Firebase 연동 
 
[Firebase](https://console.firebase.google.com/)에 접속하여 새 프로젝트를 시작한다. 이후 안드로이드 앱을 추가하여 시작한다.<br><br>
![fireabase1](https://user-images.githubusercontent.com/74412438/148933062-37337baa-217c-4045-ac58-40038069e5bb.png)
<br><br>

## Firebase Authentication 설정하기
<br>
Google 로그인을 사용 하므로 '디버그 서명 인증서 SHA-1'을 알아야 한다. <br><br>

'SHA-1' 추출법 : 안드로이드 스튜디오 내 Gradle->Tasks->android->signInReport<br><br>
![image](https://user-images.githubusercontent.com/74412438/148934855-290a9772-76c7-45f4-9d6d-1b4324f76f02.png)
<br><br>

구성 파일을 다운로드 한 뒤, 생성된 프로젝트 파일-> app 폴더에 저장한다.
<br><br>

![image](https://user-images.githubusercontent.com/74412438/148936441-1ce71695-f267-4d48-b129-992a1923da01.png)

build.gradle (moudle :app )에 작성한다.
<br><br>
![image](https://user-images.githubusercontent.com/74412438/148940676-04fec567-dc2a-4deb-92a8-027d6137f4b6.png)
![image](https://user-images.githubusercontent.com/74412438/148941595-dd434afe-d604-4ac2-ad03-5cbfaaed4b25.png)
![image](https://user-images.githubusercontent.com/74412438/148948078-09fc4a49-b204-48e1-ba2e-33aa93e58d4c.png)

Android Studio에 Firebase 추가 완료

Firebase Authentication 설정하기
이메일/비밀번호 로그인과 , 구글 로그인 , 페이스북 로그인을 사용 하는데, Authentication의 Sign-in method의 3가지 로그인 방법을 활성화 시킨다. 이메일/비밀번호 로그인, 구글 로그인은 파이어베이스 사이트에서 해결 할 수 있지만, 페이스북 로그인은 페이스북 개발자 사이트에 접속을 해서 연결시켜야 한다.

## 페이스북 연동하기 

[페이스북 개발자](https://developers.facebook.com/) 사이트 접속해서 로그인을 한 뒤 '새 앱 추가'를 한다.
 제품 추가에서 페이스북 로그인을 선택한다. 로그인 플랫폼 선택에서 안드로이드를 클릭한다.<br>
 
<br>

####   builde.gralde(Module:app)

  `implementation 'com.facebook.android: facebook-android-sdk:[4,5)')`  
     컴파일문을 추가하여 최신 버전의 SDK를 컴파일한다.

<br><br>
![image](https://user-images.githubusercontent.com/74412438/148961832-e55b2734-cd4e-4621-acb9-1769877d8cf8.png)



<br>

## 주요 기술 및 기능 설명

### 1. 로그인/회원가입<br>
- 구글과 페이스북 연동을 통한 로그인
- 이메일을 통한 회원가입
- Firebase 연동 <br><br>

### 2. 착석형태에 따른 real-time data 수신 <br>

#### 사용자의 자세 및 착석형태에 따른 real-time data를 31개의 cell-sensor를 통하여 실시간으로 수신


| <center>착석 형태</center>  | <center>real-time data</center>  | <center>설명</center>  |
| ------------ | ------------ |------------ |
| non press | ![image](https://user-images.githubusercontent.com/74412438/148961512-34fd282c-2980-4ab9-9a0b-65d681599795.png)  | 사용자가 착석하지 않은 상태로서 모든 셀에서 0이 수신된다.  |
| front press | ![image](https://user-images.githubusercontent.com/74412438/148961545-631ab3ec-3030-46a5-a478-b1193d49e716.png)  | 사용자가 엉덩이를 빼고 의자 앞쪽에 걸쳐앉은 형태로서 앞쪽 셀에서만 실시간으로 데이터가 수신된다.  |
| left press  | ![image](https://user-images.githubusercontent.com/74412438/148959726-a40b55ee-4934-4773-90f5-a1677b18b042.png)  |  사용자가 좌측으로 다리를 꼬는 등 좌측하중으로 인한 데이터가 수신된다. |
| right press  | ![image](https://user-images.githubusercontent.com/74412438/148959903-ef744db3-1953-4ea8-918d-abe810e05e27.png)  |  사용자가 우측으로 다리를 꼬는 등 우측하중으로 인한 데이터가 수신된다. |
| back press  | ![image](https://user-images.githubusercontent.com/74412438/148959977-c6abf976-4dd2-4fb9-9215-1612b7567d8f.png)  |  사용자가 의자를 뒤로 젖히는 등의 후측하중으로 후면의 셀에서만 데이터가 수신된다. |


<br><br><br>


- 사용자의 자세 및 착석형태에 따른 real-time data를 31개의 cell-sensor를 통하여 실시간으로 받아들이고 이를 압력값에 따라 색상의 가시화된 형태로 피드백을 준다. 
- 사용자의 착석데이터를 분석한 결과에 따라 착석형태를 판단하여 현재상태를 표시해준다.
- 착석형태에 따라 사용자의 착석패턴을 교정할수있도록 실시간으로 피드백을 줌으로서 즉각적인 자세교정효과를 기대할수있다.<br>
<br>
 //착성상태에 따른 변화 이미지

<br>
### 3. 마이페이지<br>

<h4>하드웨어 구성</h4>
![방석센서34](https://user-images.githubusercontent.com/74412438/148797607-0f518d6c-9bb3-47d4-9348-aca511603b3c.png)



<br>


<br> 
 
## 4. 결론

Notichair는 mdxs-16-5610센서를 31개의 cell에서 실시간으로 데이터를 감지할수있도록 기능을 구현하였다. 

실시간 피드백을 통하여 사용자의 착석형태 및 구도를 개선하주며, 연동된 스마트폰을 통해서 각종 알림과 자세개선에 대한 보다 구체적인 점검을 받을수 있도록 하였다. 

31개의 세분화된 센서들을 이용하여 기존의 여러 방석센서들과 차별점을 두었으며, 이를 통해 착석데이터를 분석 및 도출하는 데에 있어 더 정밀한 값을 다루었다. 

실시간으로 받아지는 세밀한 데이터 셋을 더 정밀하게 구축하여 사용자의 자세교정에 보다 더 정확한 피드백을 주는것을 목적으로 지속적인 연구 및 업데이트를 수행할 예정이다.<br><br>


<p align="center"><img src="https://user-images.githubusercontent.com/74412438/152303314-99650b07-3c1a-496a-b808-26ba437fcba0.png" width = 300px></p>
