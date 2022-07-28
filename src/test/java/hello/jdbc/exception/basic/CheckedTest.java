package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checkedCatch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checkedThrow(){
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    /**
     * Exception을 상속받은 예외는 check 예외가 된다.
     */
    static class MyCheckedException extends Exception{
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하던가, 던지거나 둘 중 하나를 필수로 선택해야된다.
     */
    static class Service{
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
          */
        public void callCatch(){
            // 여기서도 잡거나 던지거나 명확하게 선언해줘야 한다.
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // 예외처리 로직
                log.info("예외처리 로직, message = {}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 던지려면 throws 예외를 메서드에 필수로 선언을 해줘야 한다.
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository{
        public void call() throws MyCheckedException {
            // 체크 예외는 밖에서 던질 때 선언을 해줘야 된다.
            throw new MyCheckedException("ex");
        }
    }

}
