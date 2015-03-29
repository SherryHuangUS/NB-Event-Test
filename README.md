# NB Event Test
If you install Grails, you can run this program with:

`grails -Dserver.port=8090 -Duser.timezone=UTC run-app`

You can change your port as needed. The grails server use UTC as the default timezone. To show date and time consistently, I also use UTC to run this program.

To run test:

`grails test-app`

It will run both unit tests and intergration tests.

## Main Documents
- Domain class: grails-app/domain/nbtest/Event.groovy
- Controller: grails-app/controllers/nbtest/EventController.groovy
- GSP: grails-app/views/event/create.gsp
- Startup: grails-app/conf/Bootstrap.groovy
- Test: test/unit/nbtest/EventControllerSpec.groovy

## Screen Shots
![Alt text](/screenShots/index.png?raw=true "Optional Title")
![Alt text](/screenShots/event.png?raw=true "Optional Title")
![Alt text](/screenShots/events.png?raw=true "Optional Title")
![Alt text](/screenShots/summaryByminute.png?raw=true "Optional Title")
![Alt text](/screenShots/summaryByHour.png?raw=true "Optional Title")
![Alt text](/screenShots/summaryByDay.png?raw=true "Optional Title")
