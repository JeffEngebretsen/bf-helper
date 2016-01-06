(ns bf-helper.alexa.router
  (import (com.amazon.speech.speechlet SpeechletException)))

(defmulti route
  (fn [i-name i-slots] i-name))

(defmethod route :default
  [_ _]
  (throw (SpeechletException. "Invalid Intent")))



