package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, "구현 객체를 생성"하고, "연결"하는 책임을 가지는 별도의 설정 클래스

// 1. AppConfig는 애플리케이션의 실제 동작에 필요한 "구현 객체를 생성"한다.
// 2. AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 "생성자를 통해서 주입(연결)"해준다. => 생성자 주입

// 210421 19강 정리
// AppConfig를 통해서 관심사를 확실하게 분리했다.
// 배역, 배우를 생각해보자.
// AppConfig는 공연 기획자다.
// AppConfig는 구체 클래스를 선택한다. 배역에 맞는 담당 배우를 선택한다. 애플리케이션이 어떻게 동작해야 할지 전체 구성을 책임진다.
// 이제 각 배우들은 담당 기능을 실행하는 책임만 지면 된다.
// MemberServiceImpl, OrderServiceImpl은 기능을 실행하는 책임만 지면 된다.
public class AppConfig {
    // MemberService 안에 구현체를 new 했었는데 여기서 해줄 것이다.
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
    // 210422 20강 정리
    // 역할과 구현의 변경

    // 210422 21강 정리
    // 새로운 구조와 할인 정책 적용
    // FixDiscountPolicy > RateDiscountPolicy : AppConfig부분만 고치면 됨
    // "사용 영역"의 어떤 코드도 변경할 필요가 없다.
    // "구성 영역"은 당연히 변경된다. 구성 역할을 담당하는 AppConfig를 애플리케이션이라는 공연의 기획자로 생각하자.
    // 공연 기획자는 공연 참여자인 구현 객체들을 모두 알아야 한다.
}

// 210422 22강
//        전체 흐름 정리
//        지금까지의 흐름을 정리해보자.
//        새로운 할인 정책 개발
//        새로운 할인 정책 적용과 문제점
//        관심사의 분리
//        AppConfig 리팩터링
//        새로운 구조와 할인 정책 적용
//        새로운 할인 정책 개발
//        다형성 덕분에 새로운 정률 할인 정책 코드를 추가로 개발하는 것 자체는 아무 문제가 없음
//        새로운 할인 정책 적용과 문제점
//        새로 개발한 정률 할인 정책을 적용하려고 하니 클라이언트 코드인 주문 서비스 구현체도 함께 변경해야함
//        주문 서비스 클라이언트가 인터페이스인 DiscountPolicy 뿐만 아니라, 구체 클래스인
//        FixDiscountPolicy 도 함께 의존 DIP 위반
//        관심사의 분리
//        애플리케이션을 하나의 공연으로 생각
//        기존에는 클라이언트가 의존하는 서버 구현 객체를 직접 생성하고, 실행함
//        비유를 하면 기존에는 남자 주인공 배우가 공연도 하고, 동시에 여자 주인공도 직접 초빙하는 다양한 책임을
//        가지고 있음
//        공연을 구성하고, 담당 배우를 섭외하고, 지정하는 책임을 담당하는 별도의 공연 기획자가 나올 시점
//        공연 기획자인 AppConfig가 등장
//        AppConfig는 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성하고, 연결하는
//        책임이제부터 클라이언트 객체는 자신의 역할을 실행하는 것만 집중, 권한이 줄어듬(책임이 명확해짐)
//        AppConfig 리팩터링
//        구성 정보에서 역할과 구현을 명확하게 분리
//        역할이 잘 들어남
//        중복 제거
//        새로운 구조와 할인 정책 적용
//        정액 할인 정책 정률% 할인 정책으로 변경
//        AppConfig의 등장으로 애플리케이션이 크게 사용 영역과, 객체를 생성하고 구성(Configuration)하는
//        영역으로 분리
//        할인 정책을 변경해도 AppConfig가 있는 구성 영역만 변경하면 됨, 사용 영역은 변경할 필요가 없음. 물론
//        클라이언트 코드인 주문 서비스 코드도 변경하지 않음