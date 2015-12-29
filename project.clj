(defproject bf_helper "1.3.0-SNAPSHOT"
  :description "An Alexa Skill to help Basic Fantasy RPG players with various tasks involved in setting up characters and navigating rules."
  :url "https://github.com/JeffEngebretsen/bf-helper"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [
                 ;Clojure
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.generators "0.1.2"]

                 ;Amazon
                 [com.amazonaws/aws-lambda-java-core "1.0.0"]
                 [alexa-skills-kit/alexa-skills-kit "1.1"]
                 [com.fasterxml.jackson.core/jackson-core "2.6.3"]
                 [com.fasterxml.jackson.core/jackson-databind "2.6.3"]

                 ;Amazon logging
                 [org.slf4j/slf4j-log4j12 "1.7.13"]
                 [com.amazonaws/aws-lambda-java-log4j "1.0.0"]
                 [commons-io/commons-io "2.4"]
                 [org.apache.commons/commons-lang3 "3.0"]]
  :target-path "target/%s"
  :main bf-helper.core
  :handler bf-helper.alexa.handler
  :profiles {:uberjar {:aot :all}})
