(ns bf-helper.handler
  (:require [bf-helper.speechlet :refer :all]
            [clojure.tools.logging :as log])
  (:gen-class
   :main false
   :extends com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler
   :constructors {[] [com.amazon.speech.speechlet.Speechlet java.util.Set]}
   :init "init"))

(defn -init []
  (log/debug "Handler called")
  [[(bf_helper.speechlet.) #{}]])
