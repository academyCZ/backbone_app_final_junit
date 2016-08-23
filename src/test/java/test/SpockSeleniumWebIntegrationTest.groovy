package test

import org.academy.java.Application
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import spock.lang.Specification
import test.integrationTestTools.SeleniumTestHelper

@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(value = "server.port=8080")
class SpockSeleniumWebIntegrationTest extends Specification {

    private static SeleniumTestHelper seleniumTestHelper;
    private static boolean firstTest = true;

    def setupSpec() {
        seleniumTestHelper = new SeleniumTestHelper();
        seleniumTestHelper.openBrowser();
    }

    def setup() {
        seleniumTestHelper.navigateToHomepage();
        if (firstTest) {
            seleniumTestHelper.login();
            firstTest = false;
        }
    }

    def "Test radio, checkbox and text question creation"() {

        when:
        seleniumTestHelper.createQuestionInFirstInterview(questionText, typeIndex);

        then:
        seleniumTestHelper.getFirstInterviewLastQuestionText() == questionText;

        where:
        questionText | typeIndex
        "This is question with checkbox" | SeleniumTestHelper.CHECKBOX_INDEX
        "This is question with textarea" | SeleniumTestHelper.TEXTAREA_INDEX
        "This is question with radio" | SeleniumTestHelper.RADIO_INDEX
    }

    def "Test radio and checkbox answer creation"() {

        setup:
        seleniumTestHelper.createQuestionInFirstInterview(questionText, typeIndex);

        when:
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion(answerText);

        then:
        seleniumTestHelper.getFirstInterviewLastQuestionLastAnswerText() == answerText;

        where:
        questionText | answerText | typeIndex
        "This is question with checkbox" | "This is checkbox answer" | SeleniumTestHelper.CHECKBOX_INDEX
        "This is question with radio" | "This is radio answer" | SeleniumTestHelper.RADIO_INDEX
    }

    /** Assignment **/

    def "Test question deletion"() {
        setup:
        seleniumTestHelper.createQuestionInFirstInterview("This is question with checkbox", SeleniumTestHelper.CHECKBOX_INDEX);
        int nElementsBeforeDelete = seleniumTestHelper.getFirstInterviewQuestionDivsNumber();

        when:
        seleniumTestHelper.deleteFirstInterviewLastQuestion();

        then:
        nElementsBeforeDelete - 1 == seleniumTestHelper.getFirstInterviewQuestionDivsNumber();
    }

    def "Test answer deletion"() {
        setup:
        seleniumTestHelper.createQuestionInFirstInterview("This is question with checkbox", SeleniumTestHelper.CHECKBOX_INDEX);
        seleniumTestHelper.createAnswerInFirstInterviewLastQuestion("This is radio answer");

        when:
        seleniumTestHelper.deleteFirstInterviewLastQuestionLastAnswer();

        then:
        seleniumTestHelper.getFirstInterviewLastQuestionAnswerDivsNumber() == 0;
    }

    /** End of assignment **/

    def cleanupSpec() {
        seleniumTestHelper.closeBrowser();
    }
}
