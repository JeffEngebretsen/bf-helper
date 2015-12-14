(ns bf-helper.gen-character-test
  (:require [clojure.test :refer :all]
            [bf-helper.gen-character :refer :all]
            [clojure.data.generators :as gen]))

(deftest populate-abilties-test
  (testing "Can populate abilities with values"
     (binding [gen/*rnd* (java.util.Random. 42)]
       (let [ab (make-abilities)]
         (are [x y] (= x y)
              12 (:str ab)
              12 (:dex ab)
              8  (:con ab)
              14 (:int ab)
              10 (:wis ab)
              11 (:cha ab))))))

(deftest filter-valid-races-test
  (testing "Can filter out invalid races"
    (is (and (= #{:human :elf :halfling :dwarf}
                (set (filter-races {:str 10
                                    :dex 10
                                    :con 10
                                    :int 10
                                    :wis 10
                                    :cha 10})))
             (= #{:halfling :elf :human}
                (set (filter-races {:str 10
                                    :dex 10
                                    :con 10
                                    :int 10
                                    :wis 10
                                    :cha 18})))))))

(deftest filter-valid-classes-test
  (testing "Can filter out invalid classes"
    (is (and (= #{:cleric :fighter :magic-user :thief :fighter+magic-user :thief+magic-user}
                (set (filter-classes {:str 10
                                      :dex 10
                                      :con 10
                                      :int 10
                                      :wis 10
                                      :cha 10}
                                     :elf)))
             (= #{:fighter :thief}
                (set (filter-classes {:str 10
                                      :dex 10
                                      :con 10
                                      :int 10
                                      :wis 8
                                      :cha 10}
                                     :dwarf)))))))

(deftest get-ability-modifiers-test
  (testing "Can get modifiers for ability scores"
    (let [scores {:str 3
                  :dex 5
                  :con 8
                  :int 10
                  :wis 15
                  :cha 18}
          modifiers (get-ability-modifiers scores)]
      (is (and (= -3 (:str modifiers))
               (= -2 (:dex modifiers))
               (= -1 (:con modifiers))
               (= 0 (:int modifiers))
               (= 1 (:wis modifiers))
               (= 3 (:cha modifiers)))))))

(deftest make-full-character-test
  (testing "Can get all the pieces for a full character"
    (binding [gen/*rnd* (java.util.Random. 486)]
      (let [character (make-character)]
        (are [x y] (= x y)
             :halfling (:race character)
             :thief (:class character)
             {:str 1 :dex 2 :con 1 :int -1 :wis 2 :cha 0} (:ability-modifiers character)
             nil (:race-abilities character)
             [:sneak-attack] (:class-abilities character)
             1 (:attack-bonus character)
             {:death-ray 4
              :magic-wands 4
              :paralysis 4
              :dragon-breath 3
              :spells 4} (:saving-throws character)
             110 (:money character))))))

(deftest make-character-race-test
  (testing "Can make a character restricted by race"
    (binding [gen/*rnd* (java.util.Random. 42)]
      (let [character (make-character-race :halfling)]
        (is (= :halfling (:race character)))))))

(deftest can-filter-races-by-class
  (testing "Can filter races by class"
    (let [classes (filter-races-by-class :magic-user)]
      (is (= #{:elf :human} (set classes))))))

(deftest make-character-class-test
  (testing "Can make a character restricted by class"
    (binding [gen/*rnd* (java.util.Random. 42)]
      (let [character (make-character-class :thief)]
        (is (= :thief (:class character)))))))


(deftest can-handle-invalid-class
    (is (nil? (make-character-class :paladin))))

(deftest can-handle-invalid-race
    (is (nil? (make-character-race :gnome))))

(run-tests)
;(map #(ns-unmap *ns* %) (keys (ns-interns *ns*)))
