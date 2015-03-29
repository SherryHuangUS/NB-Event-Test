package nbtest

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import java.text.SimpleDateFormat

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(EventController)
@Mock([Event, User])
class EventControllerSpec extends Specification {

    def setup() {
        final String pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        User user1 = new User(userName: "Test1", lastName: "Huang", firstName: "Xuehua").save(failOnError: true)
        User user2 = new User(userName: "Test2", lastName: "Huang", firstName: "Xuehua").save(failOnError: true)

        Event event1 = new Event(date: sdf.parse("2014-02-28T13:00:00Z"), user: User.get(1), type: EventType.ENTER).save()
        Event event2 = new Event(date: sdf.parse("2014-02-28T13:01:00Z"), user: User.get(2), type: EventType.ENTER).save()
        Event event3 = new Event(date: sdf.parse("2014-02-28T13:02:00Z"), user: User.get(1), type: EventType.COMMENT, message: "Hi there!").save()
        Event event4 = new Event(date: sdf.parse("2014-02-28T13:03:00Z"), user: User.get(2), type: EventType.COMMENT, message: "Hello!").save()
        Event event5 = new Event(date: sdf.parse("2014-02-28T14:00:00Z"), user: User.get(1), type: EventType.HIGHFIVE, otherUser: User.get(2)).save()
        Event event6 = new Event(date: sdf.parse("2014-02-28T14:01:00Z"), user: User.get(1), type: EventType.LEAVE).save()
        Event event7 = new Event(date: sdf.parse("2014-02-28T14:01:00Z"), user: User.get(2), type: EventType.LEAVE).save()

        Event event11 = new Event(date: sdf.parse("2014-03-03T13:00:00Z"), user: User.get(1), type: EventType.ENTER).save()
        Event event12 = new Event(date: sdf.parse("2014-03-03T13:01:00Z"), user: User.get(2), type: EventType.ENTER).save()
        Event event13 = new Event(date: sdf.parse("2014-03-03T13:02:00Z"), user: User.get(1), type: EventType.COMMENT, message: "Hi there!").save()
        Event event14 = new Event(date: sdf.parse("2014-03-03T13:03:00Z"), user: User.get(2), type: EventType.COMMENT, message: "Hello!").save()
        Event event15 = new Event(date: sdf.parse("2014-03-03T14:00:00Z"), user: User.get(1), type: EventType.HIGHFIVE, otherUser: User.get(2)).save()
        Event event16 = new Event(date: sdf.parse("2014-03-03T14:01:00Z"), user: User.get(1), type: EventType.LEAVE).save()
        Event event17 = new Event(date: sdf.parse("2014-03-03T14:01:00Z"), user: User.get(2), type: EventType.LEAVE).save()

        request.addHeader("Accept", "application/json")
    }

    def cleanup() {
    }

    void "Test submitting event with complete params"() {
        given:"params with required data"
        params.date = "2014-02-28T13:00:00Z"
        params.user = "Test1"
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
        params.user = "Test1"
        params.type = "comment"
        params.message = "This is a test message"

        when:"try to save event"
        controller.event()

        then:"should return HTTP response code 400"
        response.status == 400
    }

    void "Test submitting event with wrong params"() {
        given:"params with nonexist user name"
        params.date = "2014-02-28T13:00:00Z"
        params.user = "Test"
        params.type = "comment"
        params.message = "This is a test message"

        when:"try to save event"
        controller.event()

        then:"should return HTTP response code 400"
        response.status == 400
    }

    void "Test getting events with from date and to date"() {
        given:"params with from date and to date"
        params.from = "2014-02-28T13:00:00Z"
        params.to = "2014-03-01T13:00:00Z"

        when:"try to save event"
        controller.getEvents()

        then:"should return HTTP response code 400"
        response.status == 200
        response.json.size() == 7
    }

    void "Test getting events with from date comes after to date"() {
        given:"params with from date and earlier to date"
        params.from = "2014-02-28T13:00:00Z"
        params.to = "2014-02-21T13:00:00Z"

        when:"try to save event"
        controller.getEvents()

        then:"should return HTTP response code 400"
        response.status == 400
        response.text == "Bad Request: Date from should before Date to."
    }

    void "Test getting summary"() {
        given:"params with from date and earlier to date"
        params.from = "2014-02-28T13:00:00Z"
        params.to = "2014-02-28T15:00:00Z"
        params.by = "hour"

        when:"try to save event"
        controller.getSummary()

        then:"should return HTTP response code 400"
        response.status == 200
        response.json.size() == 2
        response.json[0].enters == 2
        response.json[0].highfives == 1
        response.json[0].leaves == 0
        response.json[0].comments == 2
        response.json[1].enters == 0
        response.json[1].highfives == 1
        response.json[1].leaves == 2
        response.json[1].comments == 0
    }

    void "Test getting summary with from date comes after to date"() {
        given:"params with from date and earlier to date"
        params.from = "2014-02-28T13:00:00Z"
        params.to = "2014-02-21T13:00:00Z"
        params.by = "hour"

        when:"try to save event"
        controller.getSummary()

        then:"should return HTTP response code 400"
        response.status == 400
        response.text == "Bad Request: Date from should before Date to."
    }

    void "Test getting summary without time frame"() {
        given:"params with from date and earlier to date"
        params.from = "2014-02-28T13:00:00Z"
        params.to = "2014-03-21T13:00:00Z"

        when:"try to save event"
        controller.getSummary()

        then:"should return HTTP response code 400"
        response.status == 400
        response.text == "Bad Request: Params are not complete. Please offer a time frame."
    }
}
