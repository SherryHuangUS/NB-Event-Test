package nbtest

class Event {

    Date dateCreated
    Date date
    User user
    EventType type
    String message
    User otherUser

    static constraints = {
        message(nullable: true, blank: true)
        otherUser(nullable: true, blank: true)
    }
}
