(ns alexa.speechlet-test
  (:require [clojure.test :refer [deftest is]])
  (:import (alexa speechlet)
           (com.amazon.speech.slu Slot Intent)
           (com.amazon.speech.speechlet IntentRequest)
           (java.util Date)))


(def slots
  {"Character" (-> (Slot/builder)
                   (.withName "Character")
                   (.withValue "character")
                   (.build))})

(def intent
  (-> (Intent/builder)
      (.withName "CreateCharacterIntent")
      (.withSlots slots)
      (.build)))

(def intent-request
  (-> (IntentRequest/builder)
      (.withIntent intent)
      (.withRequestId "fakeRequest")
      (.withTimestamp (Date.))
      (.build)))

(deftest can-respond
  (.onIntent (speechlet.) intent-request nil))
