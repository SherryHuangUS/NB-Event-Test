package nbtest

class User {

    String firstName
    String lastName
    String userName

    // other user information

    static constraints = {
        userName(unique: true)
    }
}
