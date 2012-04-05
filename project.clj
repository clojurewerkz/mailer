(defproject clojurewerkz/mailer "1.0.0-alpha3"
  :description "An ActionMailer-inspired mailer library. Combines Postal, Clostache, some conventions and support for multiple delivery modes"
  :url "https://github.com/clojurewerkz/mailer"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure             "1.3.0"]
                 [com.draines/postal              "1.7.1"]
                 [de.ubercode.clostache/clostache "1.3.0"]
                 [clojurewerkz/support            "0.1.0-beta2"]
                 [clojurewerkz/route-one          "1.0.0-beta1"]]
  :test-selectors {:default        (fn [m] (and (not (:time-consuming m))
                                                (not (:http-auth m))))
                   :time-consuming :time-consuming
                   :integration    :integration
                   :focus          :focus
                   :all            (constantly true)}
  :source-paths ["src/clojure"]
  :profiles {:dev {:resource-paths ["test/resources"]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0-beta6"]]}}
  :aliases { "all" ["with-profile" "dev:dev,1.4"] }
  :repositories {"clojure-releases" "http://build.clojure.org/releases"
                 "sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false,
                             :releases {:checksum :fail :update :always}}}
  :warn-on-reflection true)
