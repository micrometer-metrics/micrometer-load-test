package io.micrometer.spring.perf

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class WebMvcApiSpec extends Simulation {
  val httpProtocol = http.baseURL("http://localhost:8080")

  val testScenario = scenario("load test the people API in micrometer-spring-legacy's samples")
    .exec(http("people")
      .get("/api/people")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200))

  setUp(testScenario.inject(atOnceUsers(500)))
    .protocols(httpProtocol)
    .assertions(forAll.failedRequests.percent.is(0))
}
