(ns bf-helper.alexa.router-test
  (:require [clojure.test :refer :all]
            [bf-helper.alexa.router :as r]
            [bf-helper.alexa.speechlet])
  (:import [com.amazon.speech.slu Slot]))

(def not-nil? (complement nil?))

(deftest can-create-race-character
  (is (not-nil?
       (-> (r/route "CreateRaceCharacterIntent" {"Race" (-> (Slot/builder)
                                                            (.withName "Race")
                                                            (.withValue "Elf")
                                                            (.build))})
           (.getOutputSpeech)
           (.getText)))))

(deftest can-create-class-character
  (is (not-nil?
       (-> (r/route "CreateClassCharacterIntent" {"Class" (-> (Slot/builder)
                                                            (.withName "Class")
                                                            (.withValue "Thief")
                                                            (.build))})
           (.getOutputSpeech)
           (.getText)))))

(deftest can-handle-bad-race
  (is (not-nil?
       (-> (r/route "CreateRaceCharacterIntent" {"Race" (-> (Slot/builder)
                                                            (.withName "Race")
                                                            (.withValue "Gnome")
                                                            (.build))})
           (.getOutputSpeech)
           (.getText)))))
