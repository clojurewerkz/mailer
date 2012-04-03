# What is Clojurewerkz Mailer

Mailer is an ActionMailer-inspired mailer library for Clojure. It combines [Postal](https://github.com/drewr/postal), [Clostache](https://github.com/fhd/clostache)
and a number of its own features and conventions to make delivering complex template-based emails as painless as possible.


## Project Goals

 * Do not reinvent the wheel
 * Email delivery should be testable
 * Steal good parts from ActionMailer


## This is a Work In Progress

Core Mailer APIs are stabilized but it is still a work in progress. Keep that in mind. 1.0 will be released in 2012
together with documentation guides and dedicated website.



## Maven Artifacts

### The Most Recent Release

With Leiningen:

    [clojurewerkz/mailer "1.0.0-alpha1"]

With Maven:

    <dependency>
      <groupId>clojurewerkz</groupId>
      <artifactId>mailer</artifactId>
      <version>1.0.0-alpha1</version>
    </dependency>


### Snapshots

If you are comfortable with using snapshots, snapshot artifacts are [released to Clojars](https://clojars.org/clojurewerkz/mailer) every 24 hours.

With Leiningen:

    [clojurewerkz/mailer "1.0.0-SNAPSHOT"]


With Maven:

    <dependency>
      <groupId>clojurewerkz</groupId>
      <artifactId>mailer</artifactId>
      <version>1.0.0-SNAPSHOT</version>
    </dependency>


## Usage

Mailer uses Mustache templates on the classpath and Postal mail message attribute maps. Key functions are:

 * `clojurewerkz.mailer.core/build-email`
 * `clojurewerkz.mailer.core/deliver-email`
 * `clojurewerkz.mailer.core/render`
 * `clojurewerkz.mailer.core/delivery-mode!`
 * `clojurewerkz.mailer.core/with-delivery-mode`
 * `clojurewerkz.mailer.core/with-settings`

``` clojure
(ns my-app
  (:use [clojurewerkz.mailer.core :only [delivery-mode! with-settings with-defaults with-settings build-email deliver-email]]))

;; set default delivery mode (:smtp, :sendmail or :test)
(delivery-mode! :test)

;; build a message (can be used in unit tests or for various forms of delayed delivery)
;;
;; Pleasen note that email/templates/warning.mustache should be on your classpath. For example, with Leiningen 2,
;; you would use :resource-paths for this, like so: :resource-paths ["src/resources"]
(build-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "OMG everything is down!"}
  "email/templates/warning.mustache" {:name "Joe" :host "host3.megacorp.internal"})

;; deliver mail, uses *delivery-mode* value to determine how exactly perform the delivery
(deliver-email {:from "Joe The Robot", :to ["ops@megacorp.internal" "oncall@megacorp.internal"] :subject "OMG everything is down!"}
  "email/templates/warning.mustache" {:name "Joe" :host "host3.megacorp.internal"})

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


## Documentation & Examples

Mailer is a young project and documentation guides are not written yet (sorry!). 1.0 will not be released without doc guides. For code examples, see our test
suite. Once documentation site is up, we will update this document.


## Community

[Mailer has a mailing list](https://groups.google.com/group/clojure-email). Feel free to join it and ask any questions you may have.

To subscribe for announcements of releases, important changes and so on, please follow [@ClojureWerkz](https://twitter.com/#!/clojurewerkz) on Twitter.


## Supported Clojure versions

Mailer is built from the ground up for Clojure 1.3 and up.


## Mailer Is a ClojureWerkz Project

Mailer is part of the group of libraries known as ClojureWerkz, together with
[Neocons](https://github.com/michaelklishin/neocons), [Monger](https://github.com/michaelklishin/monger), [Langohr](https://github.com/michaelklishin/langohr), [Elastisch](https://github.com/clojurewerkz/elastisch), [Quartzite](https://github.com/michaelklishin/quartzite) and several others.


## Continuous Integration

[![Continuous Integration status](https://secure.travis-ci.org/clojurewerkz/mailer.png)](http://travis-ci.org/clojurewerkz/mailer)

CI is hosted by [travis-ci.org](http://travis-ci.org)


## Development

Mailer uses [Leiningen 2](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md). Make sure you have it installed and then run tests against Clojure 1.3.0 and 1.4.0[-beta6] using

    lein2 all test

Then create a branch and make your changes on it. Once you are done with your changes and all tests pass, submit
a pull request on Github.



## License

Copyright © 2012 Michael S. Klishin, Alex Petrov

Distributed under the Eclipse Public License, the same as Clojure.
