package dbus;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentsTest {

    @Test
    public void new_parameters_should_return_no_argument() {
        Arguments arguments = new Arguments();
        assertThat(arguments.getArgument("test", String.class)).isEqualTo(Optional.empty());
    }
}
