(ns alexa.router
  (:import (com.amazon.speech.speechlet SpeechletException)))

(defmulti route
  (fn [request] (:intent request)))

(defmethod route :default
  [_]
  (throw (SpeechletException. "Invalid Intent")))



