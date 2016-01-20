(ns alexa.handler
  (:require [clojure.tools.logging :as log])
  (:gen-class
    :main false
    :extends com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler
    :constructors {[] [com.amazon.speech.speechlet.Speechlet java.util.Set]}
    :init "init")
  (:import (alexa speechlet)))

(set! *warn-on-reflection* true)

(defn -init []
  (log/debug "Handler called")
  [[(speechlet.) #{}]])
