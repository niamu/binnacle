(ns binnacle.core
  (:gen-class)
  (:require [binnacle.codec :as codec]
            [binnacle.mime :as mime]
            [binnacle.svg :as svg]
            #?@(:clj [[binnacle.clj.io :as io]
                      [clojure.java.classpath :as classpath]
                      [clojure.java.io :as jio]])))

(defn data-url
  [assets path]
  (let [mime (mime/mime path)
        resource (get-in assets path)]
    (str "data:" mime ";"
         (if (= mime "image/svg+xml")
           (str "utf8," (codec/url-encode (svg/as-html resource)))
           (str "base64," resource)))))

#?(:clj
   (defn assets
     []
     (->> (concat (->> (classpath/classpath-directories)
                       (mapcat file-seq))
                  (->> (classpath/classpath-jarfiles)
                       (mapcat classpath/filenames-in-jar)
                       (map jio/resource)))
          io/filter-assets
          io/file-map)))

(defn -main
  [& args]
  (prn (assets)))
