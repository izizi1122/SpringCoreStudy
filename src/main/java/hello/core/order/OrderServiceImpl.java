package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{
    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 인터페이스에만 의존함.. new 구현체가 명시되지않음.
    // 누군가 구현객체를 대신 생성하고 주입해주어야 한다. - DIP를 위해
    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }
    // 설계변경으로 OrderServiceImpl은 FixDiscountPolicy를 의존하지 않는다!
    // 단지 DiscountPolicy인터페이스만 의존한다.
    // OrderServiceImpl입장에서 생성자를 통해 어떤 구현객체가 들어올지(주입될지)는 알 수 없다.
    // OrderServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서 결정된다.
    // OrderServiceImpl은 이제부터 실행에만 집중하면 된다.
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
