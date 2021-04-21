package hello.core.member;


public class MemberServiceImpl implements MemberService { // 구현체가 1개뿐일때는 관례상 인터페이스명 + Impl이라고 많이 씀
    // AppConfig를 통해서 이쪽에는 구현체에 대한 부분이 사라졌다
    // 이를 생성자 주입..
    private final MemberRepository memberRepository;

    // MemberServiceImpl은 MemoryMemberRepository를 의존하지 않는다
    // 단지 MemberRepository 인터페이스만 의존한다.
    // MemberServiceImpl입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
    // MemberServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서 결정된다.
    // MemberServiceImpl은 이제부터 "의존관계에 대한 고민은 외부"에 맡기고 "실행에만 집중"하면 된다.

    // "DIP완성" - MemberServiceImpl은 MemberRepository인 추상에만 의존하면 된다. 이제 구체 클래스를 몰라도 된다.
    // "관심사의 분리" - 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.

    // 클라이언트인 MemberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것과 같다고 해서
    // DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
