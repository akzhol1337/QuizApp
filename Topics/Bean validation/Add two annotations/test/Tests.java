import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;

import org.hyperskill.hstest.testcase.CheckResult;
import task.Main;

import java.util.Random;

import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;


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

    final Random rand = new Random();

    final int[] IDS = new int[]{
            1,
            2,
            3,
            97,
            98,
            99,
            rand.nextInt(80) + 10,
            rand.nextInt(80) + 10,
    };

    final int[] INCORRECT_IDS = new int[]{
            -2,
            -1,
            0,
            100,
            101,
            102,
            rand.nextInt(1000) - 10000,
            rand.nextInt(1000) + 10000,
    };

    @DynamicTest
    final DynamicTesting[] dt = new DynamicTesting[]{
            () -> testGet(IDS[0]),
            () -> testGet(IDS[1]),
            () -> testGet(IDS[2]),
            () -> testGet(IDS[3]),
            () -> testGet(IDS[4]),
            () -> testGet(IDS[5]),
            () -> testGet(IDS[6]),
            () -> testGet(IDS[7]),

            () -> testGetBadRequestStatusCode(INCORRECT_IDS[0]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[1]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[2]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[3]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[4]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[5]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[6]),
            () -> testGetBadRequestStatusCode(INCORRECT_IDS[7]),
    };

    CheckResult testGet(int id) {
        HttpResponse response = get("/test/" + id).send();

        throwIfIncorrectStatusCode(response, 200);

        if (!Integer.toString(id).equals(response.getContent()))
            return wrong("Expected: \"" + id + "\", received: \"" + response.getContent() + "\"");

        return correct();
    }

    CheckResult testGetBadRequestStatusCode(int id) {
        HttpResponse response = get("/test/" + id).send();

        throwIfIncorrectStatusCode(response, 500);

        return correct();
    }
}