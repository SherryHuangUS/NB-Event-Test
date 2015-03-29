package nbtest

import groovy.time.TimeCategory
import org.codehaus.groovy.grails.web.json.JSONObject

import java.text.ParseException
import java.text.SimpleDateFormat

class EventController {

    final String pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'";
    final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    def index() {
        render(view: "index", model: [events: Event.list(), users: User.list()])
    }

    def create() {
    }

    def event() {
        println "event params -> " + params
        if (checkDateFormat(params.date) && checkUser(params.user) && checkEventType(params.type)) {
            Event event = new Event()
            event.date = sdf.parse(params.date)
            event.user = User.findByUserName(params.user)
            event.type = params.type.toUpperCase() as EventType

            if (params.message) {
                event.message = params.message
            }
            if (params.otherUser) {
                event.otherUser = User.findByUserName(params.otherUser)
            }
            event.save(failOnError: true)
            if (event.hasErrors()) {
                respond event.errors, [formats:["json"]]
            } else {
                render(status: 200, contentType: "application/json", text: '{"status": "ok"}')
            }
        }
    }

    def getEvents() {
        if (checkDateFormat(params.from) && checkDateFormat(params.to)) {
            Date from = sdf.parse(params.from)
            Date to = sdf.parse(params.to)
            if (from.before(to)) {
                List<Event> events = Event.findAllByDateBetween(from, to)
                println Event.list().date
                respond formatEvents(events)//, [formats:["json"]]
            } else {
                render (status: 400, text: "Bad Request: Date from should before Date to.")
            }
        }
    }

    private static formatEvents(List<Event> events) {
        def result = []
        events.each {
            JSONObject event = new JSONObject()
            event.date = it.date
            event.user = it.user.userName
            event.type = it.type.value()
            if (it.message) event.message = it.message
            if (it.otherUser) event.otheruser = it.otherUser.userName
            result.add(event)
        }
        println result
        return result
    }

    def getSummary() {
        if (checkDateFormat(params.from) && checkDateFormat(params.to) && checkTimeFrame(params.by)) {
            Date from = sdf.parse(params.from)
            Date to = sdf.parse(params.to)
            String timeFrame = params.by
            if (from.before(to)) {
                respond generateSummary(from, to, timeFrame), [formats:["json"]]
            } else {
                render (status: 400, text: "Bad Request: Date from should before Date to.")
            }
        }
    }

    private List generateSummary(Date from, Date to, String timeFrame) {
        def result = []
        while (from.before(to)) {
            JSONObject summary = new JSONObject()
            summary.rolledUpDate = from
            int enter = 0, leave = 0, comment = 0, highfive = 0
            Date mid = plusTimeFrame(from, timeFrame)
            if (mid.after(to)) mid = to

            def events = Event.findAllByDateBetween(from, mid)
            events.each {
                if (it.type == EventType.ENTER) {
                    enter++
                } else if (it.type == EventType.LEAVE) {
                    leave++
                } else if (it.type == EventType.COMMENT) {
                    comment++
                } else if (it.type == EventType.HIGHFIVE) {
                    highfive++
                }
            }

            summary.enters = enter
            summary.leaves = leave
            summary.comments = comment
            summary.highfives = highfive
            result.add(summary)
            from = mid
        }
        println result
        return result
    }

    private Date plusTimeFrame(Date from, String by) {
        use(TimeCategory) {
            if (by == "minute") {
                return from + 1.minute
            } else if (by == "hour") {
                return from + 1.hour
            } else if (by == "day") {
                return from + 1.day
            }
        }

    }

    def checkDateFormat(String dateString) {
        if (dateString) {
            try {
                sdf.parse(dateString)
            } catch (ParseException e) {
                render (status: 400, text: "Date format is not supported. Please use \"yyyy-MM-dd'T'hh:mm:ss'Z'\".")
                return false
            }
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please offer a date.")
            return false
        }
        return true
    }

    def checkUser(String userName) {
        if (userName) {
            if (!User.findByUserName(userName)) {
                render (status: 400, text: "User is not exist. Please try \"Sherry\" or \"Kevin\".")
                return false
            }
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please offer a user name.")
            return false
        }
        return true
    }

    def checkEventType(String eventType) {
        if (eventType) {
            if (!EventType.values().val.contains(params.type)) {
                render(status: 400, text: "Event type is not supported. Please try \"enter\", \"leave\", \"comment\" or \"highfive\".")
                return false
            }
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please offer a event type.")
            return false
        }
        return true
    }

    def checkTimeFrame(String timeFrame) {
        if (timeFrame) {
            if (!["minute", "hour", "day"].contains(params.by)) {
                render (status: 400, text: "Time frame is not supported. Please try \"minute\", \"hour\" or \"day\".")
                return false
            }
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please offer a time frame.")
            return false
        }
        return true
    }


}
