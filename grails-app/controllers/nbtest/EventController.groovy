package nbtest

import groovy.time.TimeCategory
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

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

        // Assume POST body has correct date and enum format
        if (params.date && params.user && params.type) {
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
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please check the url.")
        }
    }

    def getEvents() {
        // Assume all params in request have correct date format
        if (params.from && params.to) {
            Date from = sdf.parse(params.from)
            Date to = sdf.parse(params.to)
            List<Event> events = Event.findAllByDateBetween(from, to)
            println Event.list().date
            respond formatEvents(events), [formats:["json"]]
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please check the url.")
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
        return result
    }

    def getSummary() {
        // Assume all params in request have correct date and time frame format
        if (params.from && params.to && params.by) {
            Date from = sdf.parse(params.from)
            Date to = sdf.parse(params.to)
            String timeFrame = params.by
            respond generateSummary(from, to, timeFrame), [formats:["json"]]
        } else {
            render (status: 400, text: "Bad Request: Params are not complete. Please check the url.")
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

}
