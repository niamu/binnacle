(ns binnacle.clj.io
  (:require [binnacle.codec :as codec]
            [binnacle.mime :as mime]
            [binnacle.svg :as svg]
            [clojure.java.io :as io]
            [clojure.string :as string])
  (:import [java.io ByteArrayOutputStream]))

(defn file-bytes
  [file]
  (with-open [xin (io/input-stream file)
              xout (ByteArrayOutputStream.)]
    (io/copy xin xout)
    (.toByteArray xout)))

(defn filter-assets
  [files]
  (filter #((some-fn mime/image? mime/font?)
            (mime/extension (.getPath %)))
          files))

(defn path
  [file]
  (let [project-dir (.getCanonicalPath (io/file "."))
        filename (.getPath file)
        filename (if (string/starts-with? filename project-dir)
                   (subs filename (count project-dir))
                   filename)
        filename (if (= (type file) java.net.URL)
                   (last (string/split filename #"\.jar!/"))
                   filename)
        filename (string/replace filename #"^/?resources/" "")]
    (mapv keyword
          (->> (string/split filename #"/")
               (remove empty?)))))

(defn file-map
  [files]
  (reduce (fn [accl [path data]]
            (assoc-in accl path data))
          {}
          (map #(vector (path %)
                        (if (mime/svg? (mime/extension (.getPath %)))
                          (svg/as-hiccup %)
                          (codec/base64-encode (file-bytes %))))
               files)))
