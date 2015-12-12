(ns bf-helper.speechlet
  (:require [clojure.tools.logging :as log]
            [bf-helper.gen-character :refer :all]
            [bf-helper.formatter :refer :all]
            [clojure.data.generators :as gen])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech)
           (com.amazon.speech.speechlet SpeechletResponse))
  (:gen-class
   :main false
   :implements [com.amazon.speech.speechlet.Speechlet]
   :prefix "speechlet-"))

(defn- make-plain-text-output-speech
  [text]
  (let [output (new PlainTextOutputSpeech)]
    (.setText output text)
    output))

(defn speechlet-onSessionStarted [this request session]
  (log/debug "Speechlet onSessionStart"))

(defn speechlet-onLaunch [this request session]
  (log/debug "Speechlet onLaunch"))

(defn speechlet-onIntent [this request session]
  (let [intent (.getIntent request)
        i-name (.getName intent)]
    (log/info (apply str (interpose " " ["Speechlet.onIntent" intent i-name]))))
  (binding [gen/*rnd* (java.util.Random.)]
  (-> (make-character)
      (format-character)
      (make-plain-text-output-speech)
      (SpeechletResponse/newTellResponse))))

(defn speechlet-onSessionEnded [this request session]
  (log/debug "Speechlet onSessionEnded"))
