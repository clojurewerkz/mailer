(ns clojurewerkz.mailer.core
  (:require [postal.message   :as msg]
            [clojure.java.io  :as io]
            [clostache.parser :as clostache])
  (:use [postal.core :only [send-message]]))

;;
;; Implementation
;;

(def ^{:doc "Accumulates mail messages delivered with the :test delivery mode."}
  deliveries (atom []))

(def ^{:dynamic true
       :doc "Delivery mode to use. One of :smtp, :test, :sendmail. With the :test
             delivery mode, real deliveries won't happen. Instead, they will be accumulated to the
             deliveries collection."} *delivery-mode* :smtp)
(def ^{:dynamic true
       :doc "Settings to use for email delivery (e.g. SMTP configuration)"} *delivery-settings*)

(def ^{:dynamic true
       :doc "Default email message parameters (useful for setting From and CC headers, for example)"} *message-defaults* {})

(def delivery-modes (atom {}))

(defn register-delivery-mode
  [mode-name f]
  (swap! delivery-modes assoc (keyword mode-name) f))


(declare build-email)
(defn deliver-in-test-mode
  [m ^String template data]
  (swap! deliveries conj (build-email m template data)))

(defn deliver-with-smtp
  [m ^String template data]
  (send-message *delivery-settings* (build-email m template data)))

(defn deliver-with-sendmail
  [m ^String template data]
  (send-message {} (build-email m template data)))

(definline check-not-nil! [v m]
  `(when (nil? ~v)
     (throw (IllegalArgumentException. ~m))))


;;
;; API
;;

(defn delivery-mode!
  "Sets default delivery mode by altering root binding of *delivery-mode*"
  [mode]
  (alter-var-root (var *delivery-mode*) (constantly mode)))

(defmacro with-delivery-mode
  [mode & body]
  `(binding [*delivery-mode* ~mode]
     ~@body))

(defmacro with-defaults
  [m & body]
  `(binding [*message-defaults* ~m]
     ~@body))

(defmacro with-settings
  [m & body]
  `(binding [*delivery-settings* ~m]
     ~@body))


(defn render
  "Renders a template from a resource (so, it has to be on the classpath)"
  ([^String template]
     (check-not-nil! template "Template resource name cannot be nil!")
     (render template {}))
  ([^String template data]
     (check-not-nil! template "Template resource name cannot be nil!")
     (clostache/render (slurp (io/resource template)) data)))

(defn build-email
  "Builds up a mail message (returned as an immutable map). Body is rendered from a given template."
  ([m ^String template data]
     (merge *message-defaults* m {:body (render template data)})))


(defn deliver-email
  "Delivers a mail message using delivery mode specified by the *delivery-mode* var. Body is rendered from a given template."
  [m ^String template data]
  (if-let [f (get @delivery-modes *delivery-mode*)]
    (f m template data)
    (throw (IllegalArgumentException. (format  "%s delivery mode implementation is not registered. Possibly you misspelled %s?" *delivery-mode* *delivery-mode*)))))



;; register core delivery methods
(register-delivery-mode :test     deliver-in-test-mode)
(register-delivery-mode :smtp     deliver-with-smtp)
(register-delivery-mode :sendmail deliver-with-sendmail)
