import com.google.gson.Gson;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;

import org.hyperskill.hstest.testcase.CheckResult;
import task.Main;

import static org.hyperskill.hstest.testcase.CheckResult.correct;


public class Tests extends SpringTest {

    public Tests() {
        super(Main.class);
    }

    static void throwIfIncorrectStatusCode(HttpResponse response, int status) {
        if (response.getStatusCode() != status) {
            throw new WrongAnswer(response.getRequest().getMethod() +
                    " " + response.getRequest().getLocalUri() +
                    " should respond with status code " + status +
                    ", responded: " + response.getStatusCode() + "\n\n" +
                    "Response body:\n" + response.getContent());
        }
    }

    static class Colors {
        String[] colors;

        public Colors(String[] colors) {
            this.colors = colors;
        }
    }

    final Colors[] COLORS = new Colors[]{
            new Colors(new String[]{"a", "b", "c"}),
            new Colors(new String[]{"a", "b", "c", "d"}),
            new Colors(new String[]{"a", "b", "c", "d", "e"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6"}),
    };

    final Colors[] INCORRECT_COLORS = new Colors[]{
            new Colors(null),
            new Colors(new String[]{}),
            new Colors(new String[]{"a"}),
            new Colors(new String[]{"a", "b"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7", "8"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7", "8", "9"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
                    "b", "c", "d", "e", "f", "e"}),
            new Colors(new String[]{"a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
                    "b", "c", "d", "e", "f", "e", "a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
                    "b", "c", "d", "e", "f", "e", "a", "b", "c", "d", "e", "f", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
                    "b", "c", "d", "e", "f", "e"}),
    };

    final Gson gson = new Gson();

    @DynamicTest
    final DynamicTesting[] dt = new DynamicTesting[]{
            () -> testPostColors(COLORS[0], 200),
            () -> testPostColors(COLORS[1], 200),
            () -> testPostColors(COLORS[2], 200),
            () -> testPostColors(COLORS[3], 200),
            () -> testPostColors(COLORS[4], 200),
            () -> testPostColors(COLORS[5], 200),
            () -> testPostColors(COLORS[6], 200),

            () -> testPostColors(INCORRECT_COLORS[0], 400),
            () -> testPostColors(INCORRECT_COLORS[1], 400),
            () -> testPostColors(INCORRECT_COLORS[2], 400),
            () -> testPostColors(INCORRECT_COLORS[3], 400),
            () -> testPostColors(INCORRECT_COLORS[4], 400),
            () -> testPostColors(INCORRECT_COLORS[5], 400),
            () -> testPostColors(INCORRECT_COLORS[6], 400),
            () -> testPostColors(INCORRECT_COLORS[7], 400),
            () -> testPostColors(INCORRECT_COLORS[8], 400),
    };

    CheckResult testPostColors(Colors colors, int status) {
        HttpResponse response = post("/api/colors", gson.toJson(colors)).send();

        throwIfIncorrectStatusCode(response, status);

        return correct();
    }
}