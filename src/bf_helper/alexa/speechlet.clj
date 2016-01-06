(ns bf-helper.alexa.speechlet
  (:require [clojure.tools.logging :as log]
            [bf-helper.alexa.router :as r]
            [bf-helper.alexa.routes.create-character-router]
            [bf-helper.alexa.routes.create-class-character-router]
            [bf-helper.alexa.routes.create-race-character-router]
            [bf-helper.alexa.routes.lookup-spell-router]
            [bf-helper.alexa.routes.lookup-rule-router]
            [bf-helper.alexa.routes.amazon-router])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech Reprompt)
           (com.amazon.speech.speechlet SpeechletResponse IntentRequest SpeechletException)
           (com.amazon.speech.slu Intent))
  (:gen-class
   :main false
   :implements [com.amazon.speech.speechlet.Speechlet]
   :prefix "speechlet-"))

(set! *warn-on-reflection* true)

(defn speechlet-onSessionStarted [this request session]
  (log/info "Speechlet onSessionStart"))

(defn speechlet-onLaunch [this request session]
  (log/info "Speechlet onLaunch")
  (SpeechletResponse/newAskResponse
   (doto (new PlainTextOutputSpeech)
     (.setText "Welcome to B F Helper. I can do things like roll a character or lookup a spell or rule. ... Now, what can I do for you?"))
   (doto (new Reprompt)
     (.setOutputSpeech (doto (new PlainTextOutputSpeech)
                         (.setText "What can I do for you? ... For help you can say, please help me."))))))

(defn speechlet-onIntent [this request session]
  (log/info "Speechlet.onIntent")
  (if-let [intent (.getIntent ^IntentRequest request)]
    (let [i-name  (.getName ^Intent intent)
          i-slots (.getSlots ^Intent intent)]
      (log/info (str "Intent " i-name))
      (if i-slots
        (log/info (apply str (interpose " " (map #(format "%s: %s" (key %) (.getValue (val %))) i-slots)))))
      (r/route i-name i-slots))
    (throw (SpeechletException. "No intent"))))

(defn speechlet-onSessionEnded [this request session]
  (log/info "Speechlet onSessionEnded"))








