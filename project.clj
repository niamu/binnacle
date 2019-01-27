(defproject com.niamu/binnacle "0.3.5"
  :description "Binnacle provides images and fonts as data"
  :url "http://github.com/niamu/binnacle"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/data.codec "0.1.0"]
                 [hickory "0.7.1"]]
  :source-paths ["src"]
  :profiles {:uberjar {:aot :all}})
