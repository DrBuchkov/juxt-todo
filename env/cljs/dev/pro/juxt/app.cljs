(ns pro.juxt.app
  (:require [pro.juxt.core :as core]
            [devtools.core :as devtools]))

(defn reload! []
  (core/mount!))

(enable-console-print!)

(devtools/install!)

(core/init!)