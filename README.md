# Im-Guru

전-도사

이용고객 측면: 전문가의 도움이 필요한 사람들

전문고객 측면: 저는 도사입니다. (전 도사)

- 개발 환경
    - Java 17
    - Springboot 3.1.5
    - Gradle (Build Tool)
    - Intellij
    - DB : MySQL + NoSQL (Redis)

- Commit 규칙
    - 기능 별로 개발을 하고 커밋을 한다.
    - 푸쉬 하나당 branch 하나 생성. ex) jsh/user, jsh/board ..  [이름이니셜/개발분야]
    - commit 메세지: {type} : 기능
    - {type}
        - feat : 새로운 기능 추가, 첫 커밋 (기존의 없던 새로운 기능을 개발하여 추가해줄 때 Feat 사용)
        - fix : 장애/에러 수정 (approve 후에, 코드 리뷰를 통해 코드의 수정이 필요할 때, Fix를 통해 commit 반영할 때 사용)
        - docs : 문서 수정에 대한 커밋 (주석 수정 또는 코드에 상관없는 문서 수정의 경우 사용)
        - style : 코드 스타일 혹은 포맷 등에 관한 커밋 (css, front, format 등 사용 우리는 거의 사용할일 x)
        - refactor : 코드 리팩토링에 대한 커밋 (단순 코드 수정이나, 클린 코드 적용, 기존의 기능에서 업그레이드 해줄 때 사용

    - 예시 : 장수혁이 User CRUD 진행
        - jsh/user -> 첫 user관련 기능 추가인경우
            1. feat : create user service
            2. feat : update user service
            3. feat : delete user service
            4. feat : get user service
            5. 또는 feat : Add User Service CRUD (한영 상관 X)
        - 이미 board의 CRUD가 commit되어있는 경우, 코드의 수정이 필요할 때
            1. refactor : board의 create service에서 조건문 추가
            2. 또는 코드 리뷰를 통해 코드의 수정이 필요하다 했을 경우
               fix : board의 delete service에서 exception 처리 추가

- Merge 규칙
    - 각자 개발 담당을 정해서 개발을 진행하고, 위의 커밋 규칙을 지켜서 커밋 + 푸쉬 
    - 푸쉬 후, Sku-Deview Repository에서, Pull Request 생성
    - Pull Request commet에 왜 이렇게 구현했는지, 어떤 기술을 사용했는지, 이 기술이 무엇인지 등 자세히 작성
    - pull request생성 후 상대방에게 연락 -> 상대편이 코드리뷰를 진행하며 문제가 없는지, 수정사항이 필요한지 체크
    - 수정사항이 필요하다고 생각되면 commet를 통해 의견작성
    - 수정사항 모두 반영 후, 서로 모두 ok 했을 때 merge 를 진행


