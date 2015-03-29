class UrlMappings {

	static mappings = {
//        "/$controller/$action?/$id?(.$format)?"{
//            constraints {
//                // apply constraints here
//            }
//        }

        "/"(controller: "event", action: "index")
        "/event"(controller: "event", action: "create")
        "/events"(controller: "event", action: "getEvents")
        "/summary"(controller: "event", action: "getSummary")

        "500"(view:'/error')
	}
}
