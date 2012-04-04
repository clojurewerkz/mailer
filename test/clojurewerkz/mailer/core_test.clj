(ns clojurewerkz.mailer.core-test
  (:use clojure.test
        clojurewerkz.mailer.core))

(delivery-mode! :test)

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
        expected {:from "fee@bar.dom"
                  :to "Foo Bar <foo@bar.dom>"
                  :cc ["baz@bar.dom" "Quux <quux@bar.dom>"]
                  :subject "Hello"
                  :body "Hello, Joe!"}]
    (with-delivery-mode :test
      (with-defaults d
        (let [msg (build-email {:from    "fee@bar.dom"
                                :to      "Foo Bar <foo@bar.dom>"
                                :subject "Hello"}
                               "templates/hello.mustache" {:name "Joe"})]
          (doseq [[k v] expected]
            (is (= v (k msg)))))))))


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


;;
;; SMTP Delivery
;;

;; TBD


;;
;; Sendmail Delivery
;;

;; TBD
