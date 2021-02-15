(ns pro.juxt.app
  (:require [pro.juxt.core :as core]
            [pro.juxt.config.core :as config]
            [devtools.core :as devtools]
            [day8.re-frame.http-fx]))

(defn reload! []
  (core/mount!))

(enable-console-print!)

(devtools/install!)

(core/init!)