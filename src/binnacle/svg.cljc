(ns binnacle.svg
  (:require
   #?(:clj [clojure.java.io :as io])
   [hickory.core :as hickory]
   [hickory.render :as render]))

(defn empty-string?
  [s]
  (and (string? s) (re-matches #"\s+$" s)))

(defn clean-hiccup
  "Remove empty spaces between tags"
  [[tag attrs & children]]
  [tag attrs (into [:g] (remove empty-string? children))])

#?(:clj
   (defn as-hiccup
     [file]
     (->> (slurp (io/input-stream file))
          hickory/parse-fragment
          (map hickory/as-hiccup)
          (remove string?) ;; remove comments and doctype
          first
          clean-hiccup)))

(defn as-html
  [hiccup]
  (render/hiccup-to-html [hiccup]))
