package hello.core.member;

public class MemberServiceImpl implements MemberService{ // 구현체가 1개뿐일때는 관례상 인터페이스명 + Impl이라고 많이 씀
    // AppConfig를 통해서 이쪽에는 구현체에 대한 부분이 사라졌다
    // 이를 생성자 주입..
    private final MemberRepository memberRepository;

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
