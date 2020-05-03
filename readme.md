# ProjectPOS(2020)
- 2016년 포트폴리오로 제작한 Project(Bakery 사업장의 POS 시스템 개발)를 Spring Boot Project로 다시 제작(Remaster)
    - 이전 6인 팀으로 제작되었던 프로젝트를 1인 개발로 다시 구현하기 때문에 모든 기능의 재구현보다는 핵심 기능이 잘 작동하는 방향으로 개발 진행  
    - 구현 목표 : "`상품` 판매를 위해 등록된 `재고`를 `판매`할 수 있는 POS 시스템"
- 세부적인 내용은 [Notion_Gentledot](https://www.notion.so/gentledot/ProjectPOS-2020-2c2fde23a4ff4f53826f9c66b51c6540) 에 정리.

# 이전 프로젝트는...
[프로젝트 코드](https://bitbucket.org/gentledot/class601_projects/src/master/) (비공개) ~~(필요 시 공개로 전환~~)
## 개요
### 프로젝트 목적
![프로젝트 개요](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/83291f05-9fe1-488f-a702-7c48bbf8c05b/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20200503%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20200503T112203Z&X-Amz-Expires=86400&X-Amz-Signature=fe59957bcad2f44b7e8d3ea27313a27da878f6c9f13c0db483afe6bc65d386f0&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22 "매장관리시스템과 POS 시스템")
- POS 시스템
    - 상품 구분을 체계적으로 정리하기 위한 상품에 대한 카테고리 분류체계 수립
    - 효과적인 직원 근태관리를 위한 기능 구현
    - 상품 판매 정보를 기록하고 판매현황을 명확히 파악할 수 있는 판매관리 기능 구현
- 매장관리 시스템
    - 주문 내역 및 판매 실적에 대한 데이터를 저장,  현황 정보를 보관하는 매출관리 체계 수립
    - 안정적이고 신선한 재고확보를 위한 재고관리 기능 구현
    - 매장 내 근무자의 체계적인 관리를 위한 기능 구현
- 목적
    - 효율적인 매출관리 및 상품관리 시스템 구축
    - 원활한 매장운영을 위한 현황 데이터 제공

## 시스템 환경
### 시스템 명세
- 구현 환경
    - Oracle VirtualBox
        - Cent OS 6 (DB)
        - SVN (Ubuntu LTS(2016))
- Framework
    - eGovFramework 3.5
    - Apache Tomcat 8.0
    - Oracle Database 11g
    - Maven Project
    - MyBatis

### 프로그램(기능) List 
- 직원 관리
    - 직원 현황
    - 일일체크리스트
    - 근무 일정
- 상품 관리
    - 상품 정보 등록
    - 상품 주문
    - 재고 목록
    - 유통기한 임박 상품 조회
- 매출 관리
    - POS 기능
    - 매출 정보 조회

# 어떻게 '다시' 구현할 것인가?


