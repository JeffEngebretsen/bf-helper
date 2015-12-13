(ns bf-helper.intent-router-test
  (:require [clojure.test :refer :all]
            [bf-helper.intent-router :as r])
  (:import [com.amazon.speech.slu Slot]))


(deftest can-convert-slots-to-map
  (is (= {:race :elf} (r/slots->map {"Race" (-> (Slot/builder)
                                            (.withName "Race")
                                            (.withValue "Elf")
                                            (.build))}))))

(deftest can-create-race-character
  (is ((complement nil?)
       (-> (r/route "CreateRaceCharacterIntent" {"Race" (-> (Slot/builder)
                                                            (.withName "Race")
                                                            (.withValue "Elf")
                                                            (.build))})
           (.getOutputSpeech)
           (.getText)))))

(deftest can-create-class-character
  (is ((complement nil?)
       (-> (r/route "CreateClassCharacterIntent" {"Class" (-> (Slot/builder)
                                                            (.withName "Class")
                                                            (.withValue "Thief")
                                                            (.build))})
           (.getOutputSpeech)
           (.getText)))))

(run-tests)
