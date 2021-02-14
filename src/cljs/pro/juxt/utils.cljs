(ns pro.juxt.utils
  (:require [clojure.string :as str]))

(defn redirect-page! [uri]
  (set! (-> js/document .-location .-href) uri))

(defn humanize [s]
  (str/capitalize (str/join " " (str/split s "-"))))
