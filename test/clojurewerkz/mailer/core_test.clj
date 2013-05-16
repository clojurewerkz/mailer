(ns clojurewerkz.mailer.core-test
  (:use clojure.test
        clojurewerkz.mailer.core))

(delivery-mode! :test)

(use-fixtures :each reset-deliveries!)

;;
;; Unknown delivery modes
;;

(deftest test-attempt-to-use-unknown-delivery-mode
  (is (thrown? IllegalArgumentException
               (with-delivery-mode :magic-fairies
                 (deliver-email {:from "joe@giove.local" :to "Tom <tom@giove.local>"}
                                "templates/hello.mustache" {:name "Tom"})))))


;;
;; Rendering
;;

(deftest test-rendering-of-resource-template
  (is (= "Hello, Joe!" (render "templates/hello.mustache" {:name "Joe"}))))

(deftest test-rendering-of-resource-template-with-conditions
  (is (= "Hello, Joe!\n" (render "templates/conditional_hello.mustache" {:person {:name "Joe"}}))))



;;
;; Building
;;

(deftest test-building-messages
  (let [d        { :cc ["baz@bar.dom" "Quux <quux@bar.dom>"] }
        expected-hdr {:from "fee@bar.dom"
                      :to "Foo Bar <foo@bar.dom>"
                      :cc ["baz@bar.dom" "Quux <quux@bar.dom>"]
                      :subject "Hello"}
        expected-content "Hello, Joe!"
        expected-type "text/plain"]
    (with-delivery-mode :test
      (with-defaults d
        (let [email (build-email {:from    "fee@bar.dom"
                                  :to      "Foo Bar <foo@bar.dom>"
                                  :subject "Hello"}
                                 "templates/hello.mustache" {:name "Joe"} 
                                 :text/plain)
              content (:content (first (:body email)))
              type (:type (first (:body email)))]
          (doseq [[k v] expected-hdr]
            (is (= v (k email))))
          (is (= content expected-content))
          (is (= type expected-type)))))))


;;
;; Test Delivery
;;

(deftest test-test-delivery-mode
  (is (= 0 (count @deliveries)))
  (with-delivery-mode :test
    (deliver-email {:from "joe@giove.local" :to "Tom <tom@giove.local>"}
                   "templates/hello.mustache" {:name "Tom"})
    (deliver-email {:from "Tom <tom@giove.local>" :to "joe@giove.local"}
                   "templates/hello.mustache" {:name "Joe"}))
  (is (= 2 (count @deliveries))))

(deftest test-reset-deliveries
  (is (= 0 (count @deliveries)))
  (with-delivery-mode :test
    (deliver-email {:from "joe@giove.local" :to "Tom <tom@giove.local>"}
                   "templates/hello.mustache" {:name "Tom"})
    (deliver-email {:from "Tom <tom@giove.local>" :to "joe@giove.local"}
                   "templates/hello.mustache" {:name "Joe"}))
  (is (= 2 (count @deliveries)))
  (reset-deliveries!)
  (is (= 0 (count @deliveries))))


;;
;; SMTP Delivery
;;

;; TBD


;;
;; Sendmail Delivery
;;

;; TBD
