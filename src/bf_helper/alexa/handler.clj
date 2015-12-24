(ns bf-helper.alexa.handler
  (:require [clojure.tools.logging :as log])
  (:import [bf_helper.alexa speechlet])
  (:gen-class
   :main false
   :extends com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler
   :constructors {[] [com.amazon.speech.speechlet.Speechlet java.util.Set]}
   :init "init"))

(set! *warn-on-reflection* true)

(defn -init []
  (log/debug "Handler called")
  [[(bf_helper.alexa.speechlet.) #{}]])
