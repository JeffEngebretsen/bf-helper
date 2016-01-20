(ns alexa.speechlet
  (:require [clojure.tools.logging :as log]
            [alexa.router :as r]
            [alexa.routes.create-character-router]
            [alexa.routes.create-class-character-router]
            [alexa.routes.create-race-character-router]
            [alexa.routes.lookup-spell-router]
            [alexa.routes.lookup-rule-router]
            [alexa.routes.amazon-router])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech Reprompt SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse IntentRequest SpeechletException)
           (com.amazon.speech.slu Intent Slot))
  (:gen-class
   :main false
   :implements [com.amazon.speech.speechlet.Speechlet]
   :prefix "speechlet-"))

(set! *warn-on-reflection* true)

(defn- slot-map [slots]
  (into {} (map (fn [[k v]]
                  [(keyword (.toLowerCase ^String k)) (.getValue ^Slot v)]) slots)))

(defn- intent-map [intent]
  (let [slots (.getSlots ^Intent intent)]
    {:intent (.getName ^Intent intent)
     :slots (if slots (slot-map slots))}))

(defn- route-intent [intent]
  (let [request (intent-map intent)]
    (log/info request)
    (r/route request)))

(defn- bundle-result [result]
  (let [speech (doto (new PlainTextOutputSpeech)
                 (.setText (:text result)))]
    (when (:card result)
      (let [card (doto (new SimpleCard)
                   (.setTitle (get-in result [:card :title]))
                   (.setContent (get-in result [:card :content])))]
        (SpeechletResponse/newTellResponse speech card)))
    ((SpeechletResponse/newTellResponse speech))))

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
    (bundle-result (route-intent intent))
    (throw (SpeechletException. "No intent"))))

(defn speechlet-onSessionEnded [this request session]
  (log/info "Speechlet onSessionEnded"))








