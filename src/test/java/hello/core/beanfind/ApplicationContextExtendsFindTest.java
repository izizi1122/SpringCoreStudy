package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByParentTypeDuplicate() {
//        DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName() {
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy",DiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        DiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
//        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Configuration
    static  class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
// 210511 31강

// BeanFactory
// 스프링 컨테이너의 최상위 인터페이스
// 스프링 빈을 관리하고 조회하는 역할을 담당한다.
// getBean()을 제공한다

// ApplicationContext
// BeanFactory 기능을 모두 상속받아서 제공한다.
// 빈을 관리하고 검색하는 기능은 BeanFactory가 제공해주는데, 덧붙여서 수많은 부가 기능을 제공한다
// "메시지소스(MessageSource, Interface)를 활용한 국제화 기능"
// 예를들어 한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력
// "환경변수(EnvironmentCapable, Interface)"
// 로컬, 개발, 운영등을 구분해서 처리
// "애플리케이션 이벤트(ApplicationEventPublisher, Interface)"
// 이벤트를 발행하고 구독하는 모델을 편리하게 지원
// "편리한 리소스 조회(ResourceLoader, Interface)"
// 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회

// ApplicationContext는 BeanFactory의 기능을 상속받는다.
// ApplicationContext는 빈 관리기능 + 편리한 부가 기능을 제공한다.
// BeanFactory를 직접 사용할 일을 거의 없다. 부가기능이 포함된 ApplicationContext를 사용한다.
// BeanFactory나 ApplicationContext를 스프링 컨테이너라 한다.