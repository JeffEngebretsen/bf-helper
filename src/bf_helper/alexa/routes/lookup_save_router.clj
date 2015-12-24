(ns bf-helper.alexa.routes.lookup-save-router
  (:require [bf-helper.alexa.router :refer :all])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defmethod route "LookupSaveIntent"
  [_ slots]
  (SpeechletResponse/newTellResponse
   (doto (new PlainTextOutputSpeech)
     (.setText "I'm still learning this gaming system. Try asking about saves again later." ))))
