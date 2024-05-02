# 항해플러스 백앤드 4기 W3: e-Commerce

## [발생 가능한 동시성 이슈](./document/concurrencyIssue.md)
## To-do List (아직 미완성입니다.)
- 배타락 적용
- Test코드 변경

### [swaggerUI](./document/swaggerUI.md)

<h3>1. Milestone</h3>
   
 | 기한        | 할일                     |
 |-----------|------------------------|
 | ~ 3/30(토) | milestone 작성           |
 | ~ 4/1(월)  | 요구사항 분석 및 API명세서 작성    |
 | ~ 4/2(화)  | 시퀀스 다이어그램 작성           |
 | ~ 4/3(수)  | DB설계 및 ERD작성           |
 | ~ 4/4(목)  | Mock API 개발            |
 | ~ 4/6(토)  | 개발구조 구축                |
 | ~ 4/7(일)  | DB연결                   |
 | ~ 4/9(화)  | 잔액충전/조회API 개발          |
 | ~ 4/11(목) | 주문/결제API 개발            |
 | ~ 4/13(토) | 상품조회API 개발             |
 | ~ 4/15(화) | 상위 상품 조회API 개발         |
 | ~ 4/17(목) | 추가개발: 장바구니 추가/삭제API 개발 |
 | ~ 4/18(금) | 추가개발: 장바구니 조회API 개발    |



<h3>2. API 명세</h3>

- 잔액조회
  - GET) user/wallet/{id}
  - request
    - parameter) id: long 필수값
  - response
    - 200: 성공시 User(유저정보 객체) 반환
    - 400: 실패 시 null 반환
  - error
    - id가 유실된 경우
      <br/>
      <br/>
- 잔액충전
  - PATCH) user/wallet/{id}
  - request
    - parameter) id: long 필수값
    - body) amount: int default
  - response
    - 200: 성공시 User(유저정보 객체)  반환
    - 400: 실패 시 null 반환
  - error
    - id가 유실된 경우
    - 충전금액이 5000원 미만인 경우
      <br/>
      <br/>
- 상품 조회
  - GET) product/{id}
  - request
    - parameter) id: long
  - response
    - 200: 성공시 Product(상품 객체) 반환
    - 400: 실패 시 null 반환
      <br/>
      <br/>
- 주문/결제
  - POST) order/{userId}
  - request
    - parameter) userId: long 필수값, body) productIds: List<Long>
  - response
    - 200: 성공시 Order(주문 객체) 반환
    - 400: 실패 시 null 반환
  - error
    - userId가 유실된 경우
    - 상품이 품절된 경우
    - 상품이 결재 중 품절된 경우
    - 잔액이 부족한 경우
      <br/>
      <br/>
- 상위 상품 목록 조회
  - GET) products/top
  - request
    - body) topNum: long, soldOutYn: String
  - response
    - 200: 성공시 List<Product>(상품 객체 리스트) 반환
    - 400: 실패 시 null 반환
      <br/>
      <br/>
- 장바구니 추가
  - POST) basket/{id}
  - request
  - parameter) id: long 필수값 (userId), body) products: List<long> (productId리스트)
  - response
    - 200: 성공시 List<Product>(상품 객체 리스트) 반환
    - 400: 실패 시 null 반환
  - error
    - userId가 유실된 경우
    - 상품이 품절된 경우
    - 상품이 장바구니에 추가되는 중 품절된 경우
      <br/>
      <br/>
- 장바구니 삭제
  - DELETE) basket/{id}
  - request
    - parameter) id: long 필수값 (userId), body) products: List<long> (productId리스트)
  - response
    - 200: 성공시 List<Product>(상품 객체 리스트) 반환
    - 400: 실패 시 null 반환
  - error
    - userId가 유실된 경우
      <br/>
      <br/>
- 장바구니 조회
  - GET) basket/{id}
  - request
    - parameter) id: long 필수값
  - response
    - 200: 성공시 List<Product>(상품 객체 리스트) 반환
    - 400: 실패 시 null 반환
  - Error
    - userId가 유실된 경우
      <br/>
      <br/>


          
<h3>3. 시퀀스 다이어그램</h3>

![잔액조회.png](img%2F%EC%9E%94%EC%95%A1%EC%A1%B0%ED%9A%8C.png)

![잔액충전.png](img%2F%EC%9E%94%EC%95%A1%EC%B6%A9%EC%A0%84.png)

![상품조회.png](img%2F%EC%83%81%ED%92%88%EC%A1%B0%ED%9A%8C.png)

![주문.png](img%2F%EC%A3%BC%EB%AC%B8.png)

![상위상품 조회.png](img%2F%EC%83%81%EC%9C%84%EC%83%81%ED%92%88%20%EC%A1%B0%ED%9A%8C.png)

![장바구니 추가.png](img%2F%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%20%EC%B6%94%EA%B0%80.png)

![장바구니 삭제.png](img%2F%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%20%EC%82%AD%EC%A0%9C.png)

![장바구니 조회.png](img%2F%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%20%EC%A1%B0%ED%9A%8C.png)



<h3>4. ERD</h3>

![e-commerce-erd.png](img%2Fe-commerce-erd.png)


# hanghaeplusw7
