package dbus;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentsTest {

    @Test
    public void new_parameters_should_return_no_argument() {
        Arguments arguments = new Arguments();
        assertThat(arguments.getArgument("test", String.class)).isEqualTo(Optional.empty());
    }

    @Test
    public void parameters_should_return_the_provided_argument() {
        // given
        Arguments arguments = new Arguments();
        // when
        arguments.putArgument("test", String.class, "value");
        // then
        assertThat(arguments.getArgument("test", String.class)).isEqualTo(Optional.of("value"));
    }

    @Test
    public void parameters_should_return_the_provided_the_second_argument() {
        // given
        Arguments arguments = new Arguments();
        String key2 = "string argument 2";
        // when
        arguments.putArgument("string argument", String.class, "value");
        arguments.putArgument(key2, String.class, "value 2");
        // then
        assertThat(arguments.getArgument(key2, String.class)).isEqualTo(Optional.of("value 2"));
    }

    @Test
    public void parameters_should_accept_different_types() {
        // given
        Arguments arguments = new Arguments();
        // when
        arguments.putArgument("test", String.class, "value");
        arguments.putArgument("test 2", Integer.class, 2);
        // then
        assertThat(arguments.getArgument("test 2", Integer.class)).isEqualTo(Optional.of(2));
    }

    @Test
    public void parameter_list_should_return_a_list_of_the_right_type() {
        // given
        Arguments arguments = new Arguments();
        String key = "list of string argument";
        // when
        arguments.putArgumentList(key, String.class, List.of("value"));
        // then
        assertThat(arguments.getArgumentList(key, String.class)).isEqualTo(List.of("value"));
    }
}
