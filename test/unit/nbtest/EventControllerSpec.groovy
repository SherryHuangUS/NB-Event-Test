package nbtest

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(EventController)
@Mock([Event, User])
class EventControllerSpec extends Specification {

    def setup() {
        User user1 = new User(userName: "Test", lastName: "Huang", firstName: "Xuehua").save(failOnError: true)
    }

    def cleanup() {
    }

    void "Test submitting event with complete params"() {
        given:"params with required data"
        params.date = "2014-02-28T13:00:00Z"
        params.user = "Test"
        params.type = "comment"
        params.message = "This is a test message"

        when:"try to save event"
        controller.event()

        then:"should return HTTP response code 200 and the following data: {'status': 'ok'}"
        response.status == 200
        response.json.status == "ok"
        response.contentType == "application/json;charset=utf-8"
    }

    void "Test submitting event with noncomplete params"() {
        given:"params without date"
        params.user = "Test"
        params.type = "comment"
        params.message = "This is a test message"

        when:"try to save event"
        controller.event()

        then:"should return HTTP response code 400"
        response.status == 400
    }
}
