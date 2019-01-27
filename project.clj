(defproject com.niamu/binnacle "0.4.0-alpha1"
  :description "Binnacle provides images and fonts as data"
  :url "http://github.com/niamu/binnacle"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.codec "0.1.0"]
                 [org.clojure/java.classpath "0.3.0"]
                 [hickory "0.7.1"]]
  :source-paths ["src"]
  :main binnacle.core
  :profiles {:uberjar {:aot :all}})
