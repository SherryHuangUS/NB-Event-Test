# NB-Event-Test
If you install Grails, you can run this program with:

`grails -Dserver.port=8090 -Duser.timezone=UTC run-app`

You can change your port as needed. The grails server use UTC as the default timezone. To show date and time consistently, I also use UTC to run this program.

To run test:

`grails test-app`

It will run both unit tests and intergration tests.

## Main Documents
- Domain class: grails-app/domain/nbtest
- Controller: grails-app/controllers/nbtest/EventController
- GSP: grails-app/views/event
- Test: test/unit/nbtest

