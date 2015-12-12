(ns bf-helper.gen-character-test
  (:require [clojure.test :refer :all]
            [bf-helper.dice :refer :all]
            [clojure.data.generators :as gen]))

(deftest roll-ability-test
  (testing "Can roll an ability"
    (binding [gen/*rnd* (java.util.Random. 42)]
      (let [x (roll 3 6)]
        (is (= x 12))))))

(run-tests)
