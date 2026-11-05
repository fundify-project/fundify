# Fundify

> 흩어진 기업 정보를 한 화면에서 비교하다

<img width="1200" height="630" alt="image" src="https://github.com/user-attachments/assets/a25988d1-37f0-4287-bdd8-48f8e9a817c4" />



🔗 **배포 링크**: https://fundify-gilt.vercel.app

---

## 프로젝트 개요

**Fundify(펀디파이)** 는 복잡한 기업 분석을 누구나 쉽게 할 수 있게 만드는 기업 분석 플랫폼입니다.

주식 투자를 결정할 때 DART에서 재무제표를 찾고, 증권사 앱에서 시세를 확인하고, PER·PBR 같은 지표를 여기저기서 긁어모으던 번거로움을 넘어, **종목 하나만 검색하면 재무제표와 시세, 투자 지표를 한 화면에서** 확인할 수 있습니다.

---

### 팀 구성
김채희: PM, 프론트엔드

황수정: 백엔드

### 개발 기간
2026.08.23 ~ 2026.10.18

---

## 기술 스택

### Frontend
| 분류 | 기술 |
|---|---|
| Framework |<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white"> <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white"> |
| Styling | <img src="https://img.shields.io/badge/TailwindCSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white"> |
| State |<img src="https://img.shields.io/badge/Zustand-433E38?style=for-the-badge&logo=zustand&logoColor=white"> |
| Chart | <img src="https://img.shields.io/badge/Recharts-61DAFB?style=for-the-badge&logoColor=black"> |
| Deploy | <img src="https://img.shields.io/badge/Vercel-000000?style=for-the-badge&logo=vercel&logoColor=white"> |

### Backend
| 분류 | 기술 |
|---|---|
| Framework | <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>  <img src="https://img.shields.io/badge/Spring Data JPA -6DB33F?style=flat-square&logo=springboot&logoColor=white"/>|
| Database | <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>|
| Deploy | <img src="https://img.shields.io/badge/AWS-EC2-yellow?logo=amazonaws"/>|

### External API
| API | 용도 |
|---|---|
| DART Open API | 기업개황, 재무제표 |
| 한국투자증권 KIS API | 시세, PER·PBR 등 밸류에이션 지표 |

---

## 서비스 흐름도
<img width="3000" height="1320" alt="image" src="https://github.com/user-attachments/assets/4b2a7a8b-b92d-4867-86a9-4183108b22b4" />

---

## ERD
<img width="953" height="958" alt="image" src="https://github.com/user-attachments/assets/ce3a4a4b-70b9-4db3-b67f-1f4471eae4a2" />

---

## 서비스 소개

### 메인 페이지
- 히어로 슬로건 fade-up 애니메이션 + 배경 민트 그라데이션 효과
- 인기 종목 10개 표시(기업명·현재가·등락률), 클릭 시 해당 기업 상세 페이지로 이동
- 검색창에 기업명·종목코드 입력 후 Enter 또는 "검색" 클릭 시 검색 결과 페이지로 이동
- 등락률은 상승 빨강 / 하락 파랑으로 표시 (국내 증시 표기 관례)

<img width="1422" height="894" alt="Image" src="https://github.com/user-attachments/assets/97f68d7e-6917-4b2f-a218-6eb5f6daeaf7" />

### 검색 결과 페이지
- 현재 검색어와 검색 결과 건수 표시
- 결과 카드에 기업명·종목코드·시장구분·현재가·등락률 노출
- 카드 클릭 시 해당 기업 상세 페이지로 이동
- 검색 결과가 없을 경우 안내 메시지 표시

<img width="1176" height="890" alt="Image" src="https://github.com/user-attachments/assets/ae303ed2-aba3-4f28-9fa2-ebb6d479d372" />


### 기업 상세 페이지
- 상단 헤더에 기업명·종목코드·시장구분·시가총액, 우측에 현재가·등락률 표시
- 시세 차트 기간 버튼(1개월·3개월·6개월) 클릭 시 해당 구간 종가 추이로 전환
- 투자 지표 카드(PER·PBR·ROE·부채비율)에 업종 평균을 나란히 표시하고 저평가·고평가 배지 부여
- 재무제표 최근 5개년(매출액·영업이익·당기순이익·부채비율) 표로 표시, 모바일에서는 가로 스크롤 + 우측 페이드 처리로 추가 데이터 존재를 암시
- "비교 담기" 클릭 시 전역 상태에 저장되고 헤더의 비교 개수 배지 증가
- 존재하지 않는 종목코드로 접근 시 404 페이지 노출

<img width="1104" height="890" alt="Image" src="https://github.com/user-attachments/assets/5bd1b983-dd66-4265-bfcc-c93e7d9c2b69" />


### 기업 비교 페이지
- "기업 추가" 클릭 시 검색 모달이 열리고, 선택한 기업이 비교 목록에 추가 (최대 3개)
- 밸류에이션·수익성·안정성·재무 4개 카테고리로 묶어 지표를 나란히 비교
- 항목별로 더 우수한 값을 민트 색상으로 강조 (PER·PBR·부채비율은 낮을수록, ROE·재무 항목은 높을수록 우위)
- 담긴 기업이 없을 경우 빈 상태 안내 표시

<img width="1050" height="888" alt="Image" src="https://github.com/user-attachments/assets/76e9656a-2e31-4f80-95e7-570e66f153a9" />


---

## 트러블슈팅

| 구분 | 문제점 | 해결사례 |
|---|---|---|
| FE | 동일한 API가 기업 상세에서는 되는데 기업 비교 페이지에서만 CORS로 차단됨 | 비교 페이지만 클라이언트 컴포넌트라 요청이 브라우저에서 출발한 것이 원인. CORS가 브라우저 정책임을 확인하고 백엔드에 허용 출처를 등록해 해결 |
| FE | 로컬에서는 빌드되는데 Vercel 배포 시 `Module not found` 오류 발생 | 폴더명 `Compare`와 import 경로 `compare`의 대소문자 불일치. Windows와 달리 Linux 빌드 환경은 대소문자를 구분해 발생한 문제로, 폴더명을 소문자로 통일해 해결 |
| BE | 재무제표 수집 오류로 인한 부채비율 이상값 | 자본총계 대신 다른 항목이 매핑되어 실제보다 큰 값이 표시됨. 수집 로직 수정 후 정상화 |
| BE | 클라우드 서버 배포 단계의 메모리 부족, 데이터 이전 중 깨짐 | 스왑 메모리 확장 |
