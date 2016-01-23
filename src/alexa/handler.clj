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
  [[(speechlet.) #{"amzn1.echo-sdk-ams.app.06cf0646-0133-4345-8205-3b35a326568d"}]])
