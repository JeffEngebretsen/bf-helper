(ns bf-helper.speechlet
  (:require [clojure.tools.logging :as log]
            [bf-helper.intent-router :as r])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech Reprompt)
           (com.amazon.speech.speechlet SpeechletResponse))
  (:gen-class
   :main false
   :implements [com.amazon.speech.speechlet.Speechlet]
   :prefix "speechlet-"))

(defn speechlet-onSessionStarted [this request session]
  (log/info "Speechlet onSessionStart"))

(defn speechlet-onLaunch [this request session]
  (log/info "Speechlet onLaunch")
  (SpeechletResponse/newAskResponse
   (doto (new PlainTextOutputSpeech)
     (.setText "Welcome to B.F. Helper. I can do things like roll a character, a thief, or an elf. ... Now, what can I do for you?"))
   (doto (new Reprompt)
     (.setOutputSpeech (doto (new PlainTextOutputSpeech)
                         (.setText "What can I do for you? ... For help you can say, please help me."))))))

(defn speechlet-onIntent [this request session]
  (let [intent (.getIntent request)
        i-name  (.getName intent)
        i-slots (.getSlots intent)]
    (log/info (apply str (interpose " " ["Speechlet.onIntent" i-name i-slots])))
    (r/route i-name i-slots)))

(defn speechlet-onSessionEnded [this request session]
  (log/info "Speechlet onSessionEnded"))
