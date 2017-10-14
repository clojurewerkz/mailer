# What is Clojurewerkz Mailer

Mailer is an ActionMailer-inspired mailer library for Clojure. It combines [Postal](https://github.com/drewr/postal), [Clostache](https://github.com/fhd/clostache)
and a number of its own features and conventions to make delivering complex template-based emails as painless as possible.


## Project Goals

 * Do not reinvent the wheel
 * Email delivery should be testable
 * Steal good parts from ActionMailer


## Project Maturity

Mailer is a moderately mature project, the API hasn't changed in several years.



## Maven Artifacts

Mailer artifacts are [released to Clojars](https://clojars.org/clojurewerkz/mailer).
If you are using Maven, add the following repository definition to your

`pom.xml`:

``` xml
<repository>
  <id>clojars.org</id>
  <url>http://clojars.org/repo</url>
</repository>
```


### The Most Recent Release

With Leiningen:

    [clojurewerkz/mailer "1.3.0"]

With Maven:

    <dependency>
      <groupId>clojurewerkz</groupId>
      <artifactId>mailer</artifactId>
      <version>1.3.0</version>
    </dependency>



## Documentation

Mailer uses Mustache templates on the classpath and Postal mail message attribute maps. Key functions are:

 * `clojurewerkz.mailer.core/build-email`
 * `clojurewerkz.mailer.core/deliver-email`
 * `clojurewerkz.mailer.core/render`
 * `clojurewerkz.mailer.core/delivery-mode!`
 * `clojurewerkz.mailer.core/with-delivery-mode`
 * `clojurewerkz.mailer.core/with-settings`

``` clojure
(ns my-app
  (:require [clojurewerkz.mailer.core :refer [delivery-mode! with-settings with-defaults with-settings build-email deliver-email]]))

;; set default delivery mode (:smtp, :sendmail or :test)
(delivery-mode! :test)

;; build a message (can be used in unit tests or for various forms of delayed delivery)
;;
;; Pleasen note that email/templates/warning.mustache should be on your classpath. For example, with Leiningen 2,
;; you would use :resource-paths for this, like so: :resource-paths ["src/resources"]
(build-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "OMG everything is down!"}
  "email/templates/warning.mustache" {:name "Joe" :host "host3.megacorp.internal"})

;;build a message using an HTML template, specify parameter mime type :text/html
(build-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "OMG everything is down!"}
  "email/templates/warning.html.mustache" {:name "Joe" :host "host3.megacorp.internal"} :text/html)

;; build a message using alternative message body, specify alternative plain-text body in addition to main HTML body of the message
(build-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "Hello!"}
             "templates/html_hello.mustache" {:name "Joe"} :text/html
             "templates/hello.mustache" {:name "Joe"} :text/plain)

;; deliver mail, uses *delivery-mode* value to determine how exactly perform the delivery, defaults to :text/plain
(deliver-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "OMG everything is down!"}
  "email/templates/warning.mustache" {:name "Joe" :host "host3.megacorp.internal"})

;; deliver mail, specify html content type
(deliver-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "OMG everything is down!"}
  "email/templates/warning.html.mustache" {:name "Joe" :host "host3.megacorp.internal"} :text/html)

;; deliver mail using alternative message body, specify alternative plain-text body in addition to main HTML body of the message
(deliver-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "Hello!"}
               "templates/html_hello.mustache" {:name "Joe"} :text/html
               "templates/hello.mustache" {:name "Joe"} :text/plain)

;; alter message defaults, for example, From header
(with-defaults { :from "Joe The Robot <robot@megacorp.internal>" :subject "[Do Not Reply] Warning! Achtung! Внимание!" }
  (send-warnings))

;; alter delivery mode (effective for current thread only):
(with-delivery-mode :smtp
  (do-something))

;; alter SMTP settings (effective for current thread only, only makes sense for :smtp delivery mode):
(with-settings { :host "smtp.megacorp.internal" }
  (with-delivery-mode :smtp
    (do-something-that-delivers-email-over-smtp)))

;; render a template
(render "templates/hello.mustache" {:name "Joe"}) ;; => "Hello, Joe"
```


## Community

[Mailer has a mailing list](https://groups.google.com/group/clojure-email). Feel free to join it and ask any questions you may have.

To subscribe for announcements of releases, important changes and so on, please follow [@ClojureWerkz](https://twitter.com/#!/clojurewerkz) on Twitter.


## Supported Clojure versions

Mailer requires Clojure 1.6+.


## Mailer Is a ClojureWerkz Project

Mailer is part of the group of libraries known as ClojureWerkz,
together with [Neocons](http://clojureneo4j.info),
[Monger](http://clojuremongodb.info),
[Langohr](http://clojurerabbitmq.info),
[Elastisch](https://clojureelasticsearch.info),
[Quartzite](https://github.com/michaelklishin/quartzite) and several
others.


## Continuous Integration

[![Continuous Integration status](https://secure.travis-ci.org/clojurewerkz/mailer.png)](http://travis-ci.org/clojurewerkz/mailer)

CI is hosted by [travis-ci.org](http://travis-ci.org)


## Development

Mailer uses [Leiningen 2](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md). Make sure you have it installed and then run tests
against all supported Clojure versions using

    lein all test

Then create a branch and make your changes on it. Once you are done with your changes and all tests pass, submit
a pull request on Github.



## License

Copyright © 2012-2017 Michael S. Klishin, Alex Petrov, and the ClojureWerkz team.

Distributed under the Eclipse Public License, the same as Clojure.
