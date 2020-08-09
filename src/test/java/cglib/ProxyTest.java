package cglib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyTest {

    private final Enhancer enhancer = new Enhancer();


    @BeforeEach
    void setUp() {
        enhancer.setSuperclass(KeyMaker.class);
        enhancer.setCallback(new KeyMakerHandler());
    }

    @Test
    @DisplayName("key의 previous가 null이면 date를 리턴하는지 테스트")
    void key_null_proxy_test() {
        /* given */
        String date = "date";
        Key key = new Key(null);

        /* when */
        final KeyMaker keyMakerProxy = (KeyMaker) enhancer.create();

        /* then */
        assertThat(keyMakerProxy.build(key, date)).isEqualTo("date");
    }

    @Test
    @DisplayName("key의 previous가 null이 아니면 이전 키를 리턴하는지 테스트")
    void key_not_null_proxy_test() {
        /* given */
        String date = "date";
        Key key = new Key("previous");

        /* when */
        final KeyMaker keyMakerProxy = (KeyMaker) enhancer.create();

        /* then */
        assertThat(keyMakerProxy.build(key, date)).isEqualTo("previous");
    }
}
