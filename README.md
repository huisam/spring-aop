## AOP 실제 구현

### cglib을 활용한 AOP 구현 예제

Aspect Oriented Programming에서 가장 중요한 개념에 대해 학습하고 실제 구현해보자    
* JoinPoint : 클라이언트가 호출하는 모든 비즈니스 메소드
* PointCut : 특정 조건에 의한 필터링된 JoinPoint 
* Advice : 횡단 관심에 해당하는 공통 기능의 코드
    * Before : 메소드 호출 이전
    * After : 메소드 호출 이후
    * After-returning : 메소드 정상 실행 후
    * After-throwing : 예외가 발생한 후
    * Around : 위에서 언급한 모든 시점
    
한번 신나게 :smile: 구현해봅시다~!