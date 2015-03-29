import nbtest.EventType
import nbtest.Event
import nbtest.User

import java.text.SimpleDateFormat

class BootStrap {

    def init = { servletContext ->
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        generateUsers()
        if (Event.count() == 0) {
            generateEvents()
        }
        println User.list()
        println Event.list()
    }

    def generateUsers() {
        User user1 = new User(userName: "Sherry", lastName: "Huang", firstName: "Xuehua").save()
        User user2 = new User(userName: "Kevin", lastName: "Lin", firstName: "Ruhong").save()
    }

    final String pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    def generateEvents() {
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

    }

    def destroy = {
    }
}
