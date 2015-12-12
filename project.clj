(defproject basic_fantasy "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.amazonaws/aws-lambda-java-core "1.0.0"]
                 [com.amazonaws/aws-lambda-java-log4j "1.0.0"]
                 [alexa-skills-kit/alexa-skills-kit "1.1"]
                 [org.clojure/data.generators "0.1.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.13"]
                 [commons-io/commons-io "2.4"]
                 [org.apache.commons/commons-lang3 "3.0"]
                 [com.fasterxml.jackson.core/jackson-core "2.6.3"]
                 [com.fasterxml.jackson.core/jackson-databind "2.6.3"]]
  :target-path "target/%s"
  :main basic-fantasy.core
  :profiles {:uberjar {:aot :all}})
