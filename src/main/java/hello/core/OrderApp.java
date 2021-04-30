package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    //    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();
//
//        Long memberId = 1L;
//        Member member = new Member(memberId, "memberA", Grade.VIP);
//        memberService.join(member);
//
//        Order order = orderService.createOrder(memberId, "itemA", 10000);
//
//        System.out.println("order = " + order);
//        System.out.println("order.calculatePrice() = " + order.calculatePrice());
//
//    }
//    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();
//        Long memberId = 1L;
//        Member member = new Member(memberId, "memberA", Grade.VIP);
//        memberService.join(member);
//
//        Order order = orderService.createOrder(memberId, "itemA", 10000);
//
//        System.out.println("order = " + order);
//        System.out.println("order.calculatePrice() = " + order.calculatePrice());
//    }

    // 210429 25강 정리
    // "ApplicationContext"를 스프링 컨테이너라 한다.

    // 기존에는 개발자가 AppConfig를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제부터는 스프링 컨테이너를 통해서 사용한다.

    // 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성) 정보로 사용한다. 여기서 @Bean이라 적힌 메서드를
    // 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.

    // 스프링 빈은 @Bean이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다("memberService", "orderService")
    // @Bean(name="asdf") 를 한다면 "asdf"으로 사용할 수도 있다
    // 주의: 빈 이름은 항상 다른이름을 부여해야 한다. 같은 이름을 부여하면, 다른 빈이 무시되거나,
    // 기존 빈을 덮어버리거나 설정에따라 오류가 발생한다.

    // 이전에는 개발자가 필요한 객체를 AppConfig를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통해서 필요한
    // 스프링 빈(객체)를 찾아야 한다. 스프링 빈은 ac.getBean()메서드를 사용해서 찾을 수 있다.

    // 기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로 등록하고,
    // 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.

    // AppConfig를 직접 다루는거와 대신 스프링 컨테이너를 사용한 장점은 이후 강의에서 이어짐.
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
// 210429 26강 정리
// ApplicationContext를 스프링 컨테이너라 한다

// ApplicationContext는 인터페이스이다

// 스프링 컨테이너는 XML을 기반으로 만들 수 있고, 애노테이션 기반의 자바 설정 클래스로 만들 수 있다.
// 최근에는 XML기반보다 애노테이션 기반이 선호되고 있다.

// 직전에 AppConfig를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만든 것이다.

// 자바 설정 클래스를 기반으로 스프링 컨테이너(ApplicationContext)를 만들어보자
// new AnnotationConfigApplicationContext(AppConfig.class)는 ApplicationContext 인터페이스의 구현체이다.


// 스프링 컨테이너의 생성과정
// 1. 스프링 컨테이너 생성
// 스프링 컨테이너를 생성할 때는 구성정보를 지정해주어야 한다.
// 여기서는 AppConfig.class를 구성 정보로 지정했다.

// 2. 스프링 빈 등록
// 패라미터로 받은 구성 정보를 사용해서 등록한다.
// @Bean이 붙은 메서드를 스프링 빈 저장소에 등록한다
// key : name="이름"|methodName / value : method return

// 3. 스프링 빈 의존관계 설정-준비

// 4. 스프링 빈 의존관계 설정-완료
// 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)한다.
// 단순히 자바 코드를 호출하는 것 같지만, 차이가 있다. 이 차이는 뒤에 싱글톤 컨테이너에서 설명한다.