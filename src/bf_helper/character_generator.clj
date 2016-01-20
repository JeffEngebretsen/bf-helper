(ns bf-helper.character-generator
  (:require [clojure.string :as str]
            [clojure.data.generators :as gen]
            [bf-helper.data :refer :all]
            [bf-helper.data.character-tables :refer :all]))

(defrecord BfRace [name requirement classes hit-die saving-throws languages])
(defrecord BfClass [name requirement hit-die weapons armor special-abilities])
(defrecord BfCharacter [class race ability-scores ability-modifiers race-abilities class-abilities exp-needed hit-points attack-bonus saving-throws money])

(def races (load-res "races/" map->BfRace))
(def classes (load-res "classes/" map->BfClass))

(load "data/character_tables")

(defn roll
  "Roll an s sided die n times."
  [n s]
  (reduce + (take n (repeatedly #(+ 1 (gen/uniform 0 s))))))

(defn make-abilities
  "Roll ability scores for all characteristics.
  Returns a map of abilitys to scores."
  []
  (zipmap [:str :dex :con :int :wis :cha] (repeatedly #(roll 3 6))))

(defn get-ability-modifiers
  [abilities]
  (into {} (map #(vec [% (ability-modifiers (% abilities))]) [:str :dex :con :int :wis :cha])))

(defn filter-races
  "Checks the supplied ability scores to the requirements
  for each race returning a list of valid races."
  ([abilities] (filter-races races abilities))
  ([races abilities] (keys (filter (fn [[_ data]]
                                     (every? true? (for [requirement (:requirement data)
                                                         :let [[ab f n] requirement]]
                                                     ((resolve f) (abilities ab) n))))
                                   races))))

(defn filter-races-by-class
  "Retrns all the races for which clazz is a valid option"
  [clazz]
  (keys (filter (fn [[race stats]]
                  (some #{clazz} (:classes stats))) races)))

(defn- remove-combos
  "Remove combination classes"
  [classes]
  (filter #(= 1 (count (str/split (name %) #"\+"))) classes))

(defn- filter-single-classes
  "Checks if the supplied race meets the requirements for the single classes
  they may be."
  [abilities race]
  (filter (fn [a-class]
            (let [[ab f n] (:requirement (a-class classes))]
              ((resolve f) (abilities ab) n)))
          (remove-combos (get-in races [race :classes]))))

(defn- get-combo-classes
  "Get combination classes for race."
  [race]
  (filter #(< 1 (count (str/split (name %) #"\+"))) (get-in races [race :classes])))

(defn- filter-combo-classes
  "Checks if the supplied race meets the requirements for the combo classes
  by seeing if they already qualify for the individual classes."
  [classes race]
  (into classes (filter #(every? (set classes) (map keyword (str/split (name %) #"\+")))
                        (get-combo-classes race))))

(defn filter-classes
  "Checks the supplied ability scores against the valid classes for
  the supplied race returning a list of valid classes."
  [abilities race]
  (let [single-classes (filter-single-classes abilities race)]
    (filter-combo-classes single-classes race)))

(defn make-character
  [& {:keys [ability-scores race clazz]
      :or {ability-scores (make-abilities)
           race (gen/rand-nth (filter-races ability-scores))
           clazz (gen/rand-nth (filter-classes ability-scores race))}}]
  (-> {}
      (assoc :class clazz)
      (assoc :race race)
      (assoc :ability-scores ability-scores)
      (assoc :ability-modifiers (get-ability-modifiers ability-scores))
      ;(assoc :race-abilities (get-in races [race :special-abilities]))
      (assoc :class-abilities (get-in classes [clazz :special-abilities]))
      ;(assoc :exp-needed (get-in classes [clazz :exp 2]))
      ;(assoc :hit-points (get-hit-points character))
      (assoc :attack-bonus 1)
      (assoc :saving-throws (get-in races [race :saving-throws]))
      (assoc :money (* 10 (roll 3 6)))))

(defn make-character-race
  [race]
  (if (races race)
    (let [ability-scores (make-abilities)
         valid-races (filter-races ability-scores)]
    (if (some #{race} valid-races)
      (make-character :race race :ability-scores ability-scores)
      (recur race)))))

(defn- get-stats-for-races
  [race-keys]
  (into {} (map #(vector % (% races)) race-keys)))

;TODO support mixed classes
(defn make-character-class
  [clazz]
  (if (classes clazz)
    (let [[ab f n] (get-in classes [clazz :requirement])
        valid-races (get-stats-for-races (filter-races-by-class clazz))]
    (loop [ability-scores (make-abilities)]
      (if ((resolve f) (ab ability-scores) n)
        (make-character :ability-scores ability-scores
                        :clazz clazz
                        :race (gen/rand-nth (filter-races valid-races ability-scores)))
        (recur (make-abilities)))))))
